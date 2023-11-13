import { Component, OnInit, ViewChild } from '@angular/core';
import { Expression, FilterExpressionUtils, OFilterBuilderComponent } from 'ontimize-web-ngx';

@Component({
  selector: 'app-custom-platform-home',
  templateUrl: './custom-platform-home.component.html',
  styleUrls: ['./custom-platform-home.component.css']
})
export class CustomPlatformHomeComponent implements OnInit {
  @ViewChild("filterBuilderNumUsers", { static: false })
  filterBuilderNumUsers: OFilterBuilderComponent;
  constructor() { }

  ngOnInit() {
  }

  createFilterNumUsers(values: Array<{ attr; value }>): Expression {
    let filters: Array<Expression> = [];
    values.forEach((fil) => {
      if (fil.value) {
        if (fil.attr === "USERS") {
          filters.push(
            FilterExpressionUtils.buildExpressionMoreEqual("USERS", fil.value)
          );
        }
      }
    });
    if (filters.length > 0) {
      return filters.reduce((exp1,) =>
        FilterExpressionUtils.buildComplexExpression(
          exp1,
          null,
          FilterExpressionUtils.OP_MORE_EQUAL
        )
      );
    } else {
      return null;
    }
  }

  resetFilter($event) {
    this.filterBuilderNumUsers.triggerReload();
  }

}
