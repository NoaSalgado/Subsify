import {
  AfterViewInit,
  Component,
  Injector,
  OnInit,
  ViewChild,
} from "@angular/core";
import { D3LocaleService } from "../../../shared/d3-locale/d3Locale.service";
import {
  ChartService,
  MultiBarChartConfiguration,
  OChartComponent,
} from "ontimize-web-ngx-charts";
import {
  OntimizeService,
  FilterExpressionUtils,
  Expression,
  OTableComponent,
  OTranslateService,
} from "ontimize-web-ngx";
import { D3Locales } from "src/app/shared/d3-locale/locales";
import { Subscription } from "rxjs";
import { CategoryExpenseChartService } from "./category-expense-chart.service";

declare let d3: any;

@Component({
  selector: "app-category-expense-chart",
  templateUrl: "./category-expense-chart.component.html",
  styleUrls: ["./category-expense-chart.component.css"],
})
export class CategoryExpenseChartComponent implements OnInit, AfterViewInit {
  @ViewChild("subLapseTable", { static: false }) subLapseTable: OTableComponent;
  @ViewChild("categoryChart", { static: false })
  protected categoryChart: OChartComponent;
  protected chartParameters: MultiBarChartConfiguration;
  private translateServiceSubscription: Subscription;
  protected service: OntimizeService;
  protected d3Locale: any;
  protected subLpases: any;
  private translateService: any;

  constructor(
    protected injector: Injector,
    private d3LocaleService: D3LocaleService,
    private translate: OTranslateService,
    private catExService: CategoryExpenseChartService
  ) {
    this.d3Locale = this.d3LocaleService.getD3LocaleConfiguration();
    this.translateServiceSubscription =
      this.translate.onLanguageChanged.subscribe(() => {
        location.reload();
      });
    this.configureChart(this.d3Locale);
    this.service = this.injector.get(OntimizeService);
  }

  ngOnInit(): void {
    this.configureService();
  }

  private calculateMaxExpenseValue(): number {
    const maxExpense = Math.max(
      ...this.subLpases.map((sub) => sub.SUB_LAPSE_PRICE)
    );

    return Math.ceil(maxExpense) + 10;
  }

  private configureChart(locale: any): void {
    this.chartParameters = new MultiBarChartConfiguration();
    this.chartParameters.showXAxis = true;
    this.chartParameters.showYAxis = true;
    this.chartParameters.margin.left = 70;
    this.chartParameters.margin.bottom = 15;
    this.chartParameters.xDataType = locale.timeFormat("%B - %Y");
  }

  private configuraChartOps(): void {
    if (this.categoryChart) {
      let chartService: ChartService = this.categoryChart.getChartService();
      if (chartService) {
        let chartOps = chartService.getChartOptions();

        chartOps["yAxis"]["tickFormat"] = function (d) {
          return d.toLocaleString("es-ES", {
            style: "currency",
            currency: "EUR",
          });
        };

        const maxExpenseValue = this.calculateMaxExpenseValue();
        console.log(this.calculateMaxExpenseValue());
        var yScale = d3.scale.linear();
        chartOps["yScale"] = yScale;
        chartOps["yDomain"] = [0, maxExpenseValue];
      }
    }
  }

  protected configureService(): void {
    const conf = this.service.getDefaultServiceConfiguration("subLapses");
    this.service.configureService(conf);
  }

  protected getSubLapses(event) {
    this.subLpases = event;
    this.processData(this.subLpases);
    this.configuraChartOps();
  }

  processData(data: any): void {
    const chartData = [];

    const subsStartDates = data.map((sub) => {
      const subStartMonth = new Date(sub.SUB_LAPSE_START).getMonth();
      const subStartYear = new Date(sub.SUB_LAPSE_START).getFullYear();
      return new Date(subStartYear, subStartMonth);
    });

    const subsEndDates = data.map((sub) => {
      const currentYear = new Date().getFullYear();
      const currentMonth = new Date().getMonth();
      const subEndMonth = new Date(sub.SUB_LAPSE_END).getMonth();

      if (!this.catExService.dataFiltered) {
        return new Date(sub.SUB_LAPSE_END).getFullYear() > currentYear ||
          subEndMonth > currentMonth
          ? new Date(currentYear, currentMonth)
          : new Date(currentYear, subEndMonth);
      } else {
        return new Date(currentYear, subEndMonth - 1);
      }
    });

    const chartStartDate = new Date(Math.min(...subsStartDates));
    const chartEndDate = new Date(Math.max(...subsEndDates));
    let chartDates = [new Date(chartStartDate)];

    while (chartStartDate < chartEndDate) {
      const nextDate = new Date(
        chartStartDate.setMonth(chartStartDate.getMonth() + 1)
      );

      chartDates.push(nextDate);
    }

    const categories = Array.from(new Set(data.map((sub) => sub.CAT_NAME)));
    categories.forEach((category) => {
      const values = chartDates.map((date) => ({
        x: date,
        y: 0,
      }));
      chartData.push({
        key: category,
        values,
      });
    });

    data.forEach((subLapse) => {
      const {
        SUB_LAPSE_START: startDate,
        SUB_LAPSE_END: endDate,
        shared_price: price,
        FR_VALUE: frequency,
        CAT_NAME: category,
      } = subLapse;
      const currentCategoryObj = chartData.find(
        (catObj) => catObj.key === category
      );
      this.getSubscriptionPaymentDates(
        new Date(startDate),
        new Date(endDate)
      ).forEach((date) => {
        const currentValueObj = currentCategoryObj.values.find((value) => {
          return value.x.getTime() === date.getTime();
        });
        currentValueObj.y += price / frequency;
      });
    });
    this.setChartData(chartData);
  }

  private getSubscriptionPaymentDates(
    startDate: Date,
    endDate: Date
  ): Array<Date> {
    const currentYear = new Date().getFullYear();
    const currentMonth = new Date().getMonth();

    const paymentStartDate =
      startDate.getFullYear() === currentYear
        ? new Date(startDate.getFullYear(), startDate.getMonth())
        : new Date(currentYear, 1, 1);

    const paymentEndDate =
      endDate.getFullYear() > currentYear || endDate.getMonth() > currentMonth
        ? new Date(currentYear, currentMonth + 1, 1)
        : new Date(endDate.getFullYear(), endDate.getMonth());

    const subscriptionPaymentDates = [new Date(paymentStartDate)];

    while (paymentStartDate < paymentEndDate) {
      const nextPayment = new Date(
        paymentStartDate.setMonth(paymentStartDate.getMonth() + 1)
      );
      if (paymentStartDate >= paymentEndDate) break;
      subscriptionPaymentDates.push(nextPayment);
    }
    return subscriptionPaymentDates;
  }

  private setChartData(data: Array<Object>): void {
    this.categoryChart.setDataArray(data);
    this.categoryChart.reloadData();
  }

  private filterData() {
    this.catExService.updateDataFiltered(true);
  }

  createFilter(values: Array<{ attr; value }>): Expression {
    let filters: Array<Expression> = [];
    values.forEach((fil, i) => {
      if (fil.value) {
        if (fil.attr === "SUB_LAPSE_START" && i === 0) {
          filters.push(
            FilterExpressionUtils.buildExpressionMoreEqual(
              "SUB_LAPSE_START",
              fil.value
            )
          );
        }
        if (fil.attr === "SUB_LAPSE_START" && i === 1) {
          filters.push(
            FilterExpressionUtils.buildExpressionLessEqual(
              "SUB_LAPSE_START",
              fil.value
            )
          );
        }
      }
    });

    if (filters.length > 0) {
      return filters.reduce((exp1, exp2) =>
        FilterExpressionUtils.buildComplexExpression(
          exp1,
          exp2,
          FilterExpressionUtils.OP_AND
        )
      );
    } else {
      return null;
    }
  }
}
