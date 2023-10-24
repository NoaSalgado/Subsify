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
} from "ontimize-web-ngx";
import { D3Locales } from "src/app/shared/d3-locale/locales";

declare let d3: any;

@Component({
  selector: "app-category-expense-chart",
  templateUrl: "./category-expense-chart.component.html",
  styleUrls: ["./category-expense-chart.component.css"],
})
export class CategoryExpenseChartComponent implements OnInit, AfterViewInit {
  @ViewChild("categoryChart", { static: false })
  protected categoryChart: OChartComponent;
  protected chartParameters: MultiBarChartConfiguration;
  protected service: OntimizeService;
  protected d3Locale: any;

  constructor(
    protected injector: Injector,
    private d3LocaleService: D3LocaleService
  ) {
    this.d3Locale = this.d3LocaleService.getD3LocaleConfiguration();
    this.configureChart(this.d3Locale);

    this.service = this.injector.get(OntimizeService);
  }

  ngOnInit(): void {
    this.configureService();
  }

  ngAfterViewInit(): void {
    if (this.categoryChart) {
      let chartService: ChartService = this.categoryChart.getChartService();
      if (chartService) {
        let chartOps = chartService.getChartOptions();
        chartOps["yAxis"]["tickFormat"] = function (d) {
          return d3.format(",f")(d) + "€";
        };
        let yScale = d3.scale.linear();
        chartOps["yScale"] = yScale;
        chartOps["yDomain"] = [0, 300];
      }

      this.getData();
    }
  }

  private configureChart(locale: any): void {
    this.chartParameters = new MultiBarChartConfiguration();
    this.chartParameters.showXAxis = true;
    this.chartParameters.showYAxis = true;
    this.chartParameters.margin.left = 70;
    this.chartParameters.margin.bottom = 15;
    this.chartParameters.xDataType = locale.timeFormat("%B - %Y");
  }

  protected configureService(): void {
    const conf = this.service.getDefaultServiceConfiguration("subLapses");
    this.service.configureService(conf);
  }

  private getData(): void {
    this.service
      .query(
        {},
        [
          "sub_lapse_start",
          "sub_lapse_end",
          "sub_lapse_price",
          "fr_value",
          "cat_name",
        ],
        "subLapseChartCategory"
      )
      .subscribe((res) => {
        if (res.code === 0) {
          this.processData(res.data);
        }
      });
  }

  processData(data: any): void {
    const chartData = [];

    const subsStartDates = data.map((sub) => {
      const subStartMonth = new Date(sub.sub_lapse_start).getMonth();
      const subStartYear = new Date(sub.sub_lapse_start).getFullYear();
      return new Date(subStartYear, subStartMonth);
    });

    const subsEndDates = data.map((sub) => {
      const currentYear = new Date().getFullYear();
      const currentMonth = new Date().getMonth();
      const subEndMonth = new Date(sub.sub_lapse_end).getMonth();
      return new Date(sub.sub_lapse_end).getFullYear() > currentYear ||
        subEndMonth > currentMonth
        ? new Date(currentYear, currentMonth)
        : new Date(currentYear, subEndMonth);
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

    const categories = Array.from(new Set(data.map((sub) => sub.cat_name)));
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
        sub_lapse_start: startDate,
        sub_lapse_end: endDate,
        sub_lapse_price: price,
        fr_value: frequency,
        cat_name: category,
      } = subLapse;
      const currentCategoryObj = chartData.find(
        (catObj) => catObj.key === category
      );

      this.getSubscriptionPaymentDates(
        new Date(startDate),
        new Date(endDate)
      ).forEach((date) => {
        const currentValueObj = currentCategoryObj.values.find(
          (value) => value.x.getTime() === date.getTime()
        );
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
      endDate.getFullYear() > currentYear ||
      endDate.getMonth() + 1 > currentMonth + 1
        ? new Date(currentYear, currentMonth, 1)
        : new Date(endDate.getFullYear(), endDate.getMonth());

    const subscriptionPaymentDates = [new Date(paymentStartDate)];

    while (paymentStartDate < paymentEndDate) {
      const nextPayment = new Date(
        paymentStartDate.setMonth(paymentStartDate.getMonth() + 1)
      );
      subscriptionPaymentDates.push(nextPayment);
    }
    return subscriptionPaymentDates;
  }

  private setChartData(data: Array<Object>): void {
    this.categoryChart.setDataArray(data);
    this.categoryChart.reloadData();
  }

  createFilter(values: Array<{ attr; value }>): Expression {
    let filters: Array<Expression> = [];
    values.forEach((fil) => {
      if (fil.value) {
        if (fil.attr === "SUB_LAPSE_START") {
          filters.push(
            FilterExpressionUtils.buildExpressionMoreEqual(
              "SUB_LAPSE_START",
              fil.value
            )
          );
        }
        if (fil.attr === "SUB_LAPSE_END") {
          filters.push(
            FilterExpressionUtils.buildExpressionLessEqual(
              "SUB_LAPSE_END",
              fil.value
            )
          );
        }
      }
    });

    if (filters.length > 0) {
      console.log(
        filters.reduce((exp1, exp2) =>
          FilterExpressionUtils.buildComplexExpression(
            exp1,
            exp2,
            FilterExpressionUtils.OP_AND
          )
        )
      );
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
