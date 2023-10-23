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
import { OntimizeService,FilterExpressionUtils, Expression } from "ontimize-web-ngx";

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

  constructor(protected injector: Injector) {
    this.chartParameters = new MultiBarChartConfiguration();
    this.chartParameters.showXAxis = true;
    this.chartParameters.showYAxis = true;
    this.chartParameters.margin.left = 70;
    this.chartParameters.margin.bottom = 15;

    this.service = this.injector.get(OntimizeService);
  }

  ngOnInit(): void {
    this.configureService();
  }

  ngAfterViewInit(): void {
    this.getData();
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

    const currentYearSubs = data.filter((subLapse) => {
      const currentYear = new Date().getFullYear();
      return (
        new Date(subLapse.sub_lapse_start).getFullYear() === currentYear ||
        new Date(subLapse.sub_lapse_end).getFullYear() === currentYear
      );
    });

    const subsStartMonths = currentYearSubs.map((sub) => {
      const currentYear = new Date().getFullYear();
      return new Date(sub.sub_lapse_start).getFullYear() === currentYear
        ? new Date(sub.sub_lapse_start).getMonth() + 1
        : 1;
    });

    const subsEndMonths = currentYearSubs.map((sub) => {
      const currentYear = new Date().getFullYear();
      const currentMonth = new Date().getMonth() + 1;
      return new Date(sub.sub_lapse_end).getFullYear() > currentYear ||
        new Date(sub.sub_lapse_end).getMonth() + 1 > currentMonth
        ? currentMonth
        : new Date(sub.sub_lapse_end).getMonth() + 1;
    });

    const chartStartMonth = Math.min(...subsStartMonths);
    const chartEndMonth = Math.max(...subsEndMonths);
    const chartMonths = [];
    for (let i = chartStartMonth; i <= chartEndMonth; i++) {
      chartMonths.push(i);
    }

    const categories = Array.from(
      new Set(currentYearSubs.map((sub) => sub.cat_name))
    );
    categories.forEach((category) => {
      const values = chartMonths.map((month) => ({
        x: month,
        y: 0,
      }));
      chartData.push({
        key: category,
        values,
      });
    });

    currentYearSubs.forEach((subLapse) => {
      const {
        sub_lapse_start: startDate,
        sub_lapse_end: endDate,
        sub_lapse_price: price,
        fr_value: frequency,
        cat_name: category,
      } = subLapse;
      const categoryIndex = this.getObjectIndex(chartData, category);
      this.getSubscriptionPaymentMonths(
        new Date(startDate),
        new Date(endDate)
      ).forEach((month) => {
        const monthIndex = this.getObjectIndex(
          chartData[categoryIndex].values,
          month
        );
        chartData[categoryIndex].values[monthIndex].y += price / frequency;
      });
    });
    console.log(chartData);
    this.setChartData(chartData);
  }

  private existsValue(arr: Array<Object>, value: string | number): boolean {
    return arr.some((obj) => Object.values(obj).includes(value));
  }

  private getSubscriptionPaymentMonths(
    startDate: Date,
    endDate: Date
  ): Array<number> {
    const subscriptionPaymentMonths = [];

    const currentYear = new Date().getFullYear();
    const currentMonth = new Date().getMonth() + 1;

    const startMonth =
      startDate.getFullYear() === currentYear ? startDate.getMonth() + 1 : 1;

    const endMonth =
      endDate.getFullYear() > currentYear ||
      endDate.getMonth() + 1 > currentMonth + 1
        ? currentMonth + 1
        : endDate.getMonth() + 1;

    for (let i = startMonth; i < endMonth; i++) {
      subscriptionPaymentMonths.push(i);
    }
    console.log(subscriptionPaymentMonths);
    return subscriptionPaymentMonths;
  }

  private getObjectIndex(arr: Array<Object>, value: string | number): number {
    return arr.findIndex((obj) => Object.values(obj).includes(value));
  }

  private setChartData(data: Array<Object>): void {
    this.categoryChart.setDataArray(data);
    this.categoryChart.reloadData();
    console.log(this.categoryChart.dataArray);
  }

  createFilter(values: Array<{ attr, value }>): Expression {
    let filters: Array<Expression> = [];
    values.forEach(fil => {
       if (fil.value) {
          if (fil.attr === 'PLAN_PRICE_START') {
             filters.push(FilterExpressionUtils.buildExpressionMoreEqual('PLAN_PRICE_START' ,fil.value));
          }
          if (fil.attr === 'PLAN_PRICE_END') {
             filters.push(FilterExpressionUtils.buildExpressionLessEqual('PLAN_PRICE_END', fil.value));
          }
        
       }
    });

    if (filters.length > 0) {
       return filters.reduce((exp1, exp2) => FilterExpressionUtils.buildComplexExpression(exp1, exp2, FilterExpressionUtils.OP_AND));
    } else {
       return null;
    }
 }
}

