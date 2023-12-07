import { Component, Injector, ViewChild } from "@angular/core";
import { getMonthlyPricefunction } from "src/app/shared/shared.module";
import {
  OChartComponent,
  DonutChartConfiguration,
} from "ontimize-web-ngx-charts";
import {
  Expression,
  FilterExpressionUtils,
  OComboComponent,
  OFilterBuilderComponent,
  OTableComponent,
  OTranslateService,
} from "ontimize-web-ngx";
import { ActivatedRoute, Router } from "@angular/router";

@Component({
  selector: "app-subscriptions-home",
  templateUrl: "./subscriptions-home.component.html",
  styleUrls: ["./subscriptions-home.component.css"],
})
export class SubscriptionsHomeComponent {
  @ViewChild("filterBuilderCat", { static: false })
  filterBuilderCat: OFilterBuilderComponent;
  @ViewChild("categories", { static: false })
  categories: OComboComponent;
  @ViewChild("subLapseTable", { static: false })
  subLapseTable: OTableComponent;
  @ViewChild("subLapseChart", { static: false })
  subLapseChart: OChartComponent;

  protected platformChart: OChartComponent;
  protected chartData: Array<any>;
  protected chartParameters: DonutChartConfiguration;
  protected getMonthlyPrice = getMonthlyPricefunction;
  protected userRole: string;

  constructor(
    protected injector: Injector,
    private router: Router,
    private actRoute: ActivatedRoute
  ) {
    this.chartParameters = new DonutChartConfiguration();
    this.chartParameters.showLabels = true;
    this.chartParameters.cornerRadius = 0;
    this.chartParameters.donutRatio = 0.1;
  }

  createFilterCat(values: Array<{ attr; value }>): Expression {
    let filters: Array<Expression> = [];
    values.forEach((fil) => {
      if (fil.value) {
        if (fil.attr === "CAT_NAME") {
          filters.push(
            FilterExpressionUtils.buildExpressionEquals("CAT_NAME", fil.value)
          );
        }
      }
    });
    if (filters.length > 0) {
      return filters.reduce((exp1) =>
        FilterExpressionUtils.buildComplexExpression(
          exp1,
          null,
          FilterExpressionUtils.OP_EQUAL
        )
      );
    } else {
      return null;
    }
  }

  onDataLoaded(event) {
    console.log(event);
    if (event && event.length > 0 && event[0].ROLENAME) {
      this.userRole = event[0].ROLENAME;

      if (this.userRole === "admin") {
        this.router.navigate(["../../", "plans"], {
          relativeTo: this.actRoute,
        });
      } else {
        this.router.navigate(["../../", "subscriptions"], {
          relativeTo: this.actRoute,
        });
      }
    } else {
      console.error("Invalid data received:", event);
    }
  }

  resetFilter($event) {
    this.filterBuilderCat.triggerReload();
  }

  setChartData($event) {
    this.chartData = $event.map((subLapse) => {
      const data = {
        x: subLapse.PLATF_NAME,
        y: subLapse.shared_price / subLapse.FR_VALUE,
      };
      return data;
    });
  }

  onTabChange(event) {
    (<any>this.subLapseChart).chartWrapper.update();
  }
}
