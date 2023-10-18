import { Component, OnInit } from '@angular/core';
import { D3LocaleService } from '../../../shared/d3-locale/d3Locale.service';
import { DiscreteBarChartConfiguration } from 'ontimize-web-ngx-charts';

@Component({
  selector: 'app-category-expense-chart',
  templateUrl: './category-expense-chart.component.html',
  styleUrls: ['./category-expense-chart.component.css']
})
export class CategoryExpenseChartComponent implements OnInit {
  public chartParameters: DiscreteBarChartConfiguration;

  constructor() {
    this.chartParameters = new DiscreteBarChartConfiguration();
    this.chartParameters.showXAxis = true;
    this.chartParameters.showYAxis = true;
    this.chartParameters.margin.left = 70;
    this.chartParameters.margin.bottom = 15;
  }
  ngOnInit() {
  }

}
