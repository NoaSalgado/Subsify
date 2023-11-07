import { AfterViewInit, Component, OnInit, ViewChild } from "@angular/core";
import { getMonthlyPricefunction } from "src/app/shared/shared.module";
import {
  Expression,
  FilterExpressionUtils,
  OComboComponent,
  OFilterBuilderComponent,
  OTableComponent,
} from "ontimize-web-ngx";
import { SubscriptionServiceService } from "../subscription-service.service";

@Component({
  selector: "app-subscriptions-home",
  templateUrl: "./subscriptions-home.component.html",
  styleUrls: ["./subscriptions-home.component.css"],
})
export class SubscriptionsHomeComponent implements OnInit, AfterViewInit {
  @ViewChild("subLapseTable", { static: false }) sublapseTable: OTableComponent;
  @ViewChild("filterBuilderCat", { static: false })
  filterBuilderCat: OFilterBuilderComponent;
  @ViewChild("categories", { static: false }) categories: OComboComponent;

  public getMonthlyPrice = getMonthlyPricefunction;

  constructor(private subscriptionsSevice: SubscriptionServiceService) {}
  ngOnInit() {}

  ngAfterViewInit(): void {
    // this.subscriptionsSevice.registerTable(this.sublapseTable);
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

  resetFilter($event) {
    this.filterBuilderCat.triggerReload();
  }
}
