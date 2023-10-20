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
import { OntimizeService } from "ontimize-web-ngx";

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

  protected data: Array<Object> = [];
  protected columns: Array<String> = [];

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

  protected configureService() {
    const conf = this.service.getDefaultServiceConfiguration("subLapses");
    this.service.configureService(conf);
  }

  // getData() {
  //   this.service
  //     .query({}, ["MONTH", "total_price_category"], "subLapseChartCategory")
  //     .subscribe((res) => {
  //       console.log(res.data);
  //       res.data.forEach((element) => {
  //         let chartDataObject = {};
  //         chartDataObject["Key"] = element.cat_name;
  //         chartDataObject["values"] = [
  //           {
  //             x: element.MONTH,
  //             y: element.total_price_category,
  //           },
  //         ];
  //         this.data.push(chartDataObject);
  //       });
  //       console.log(this.data);
  //       this.categoryChart.setDataArray(this.data);
  //       this.categoryChart.reloadData();
  //     });
  // }

  getData() {
    this.categoryChart.setDataArray([
      {
        key: "Deportes",
        values: [
          {
            x: 10,
            y: 250,
          },
          {
            x: 11,
            y: 25,
          },
        ],
      },
      {
        key: "Video",
        values: [
          {
            x: 10,
            y: 150,
          },
          {
            x: 11,
            y: 20,
          },
        ],
      },
    ]);
    this.categoryChart.reloadData();
  }
}
