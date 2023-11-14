import { Component, Injector, OnInit, ViewChild } from "@angular/core";
import { getMonthlyPricefunction } from "src/app/shared/shared.module";
import { OChartComponent, DonutChartConfiguration } from 'ontimize-web-ngx-charts';
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

export class SubscriptionsHomeComponent implements OnInit {
@ViewChild("filterBuilderCat", { static: false })
filterBuilderCat: OFilterBuilderComponent;
@ViewChild("categories", { static: false }) categories: OComboComponent;
@ViewChild("subLapseTable",{static:false}) subLapseTable:OTableComponent;
@ViewChild("subLapseChart",{static:false}) subLapseChart:OChartComponent;

protected platformChart: OChartComponent;
public chartData;
protected chartParameters: DonutChartConfiguration;
public getMonthlyPrice = getMonthlyPricefunction;
user_role;

constructor(protected injector: Injector,private router: Router,private actRoute: ActivatedRoute) {

this.chartParameters = new DonutChartConfiguration();
this.chartParameters.showLabels = true;
this.chartParameters.cornerRadius = 0;
this.chartParameters.donutRatio = 0.1;
}

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

  onDataLoaded2(event){
    console.log(event);
    if (event && event.length > 0 && event[0].ROLENAME) {
    
    this.user_role=event[0].ROLENAME;
    
    if(this.user_role === 'admin'){
      
      this.router.navigate(['../../','plans'], {relativeTo: this.actRoute})
    } else{
      this.router.navigate(['../../','subscriptions'],{relativeTo:this.actRoute})
    }
  } else {
    console.error('Invalid data received:', event);
  }

  }
  resetFilter($event) {
    this.filterBuilderCat.triggerReload();
  }

  setChartData($event){
    console.log(this.subLapseChart.dataArray);
      this.chartData=$event.map(subLapse=>{
      const data={
        x:subLapse.PLATF_NAME,
        y:subLapse.shared_price/subLapse.FR_VALUE
      }
      return data;
     });
  }
}
