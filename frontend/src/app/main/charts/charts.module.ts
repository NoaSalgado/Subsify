import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OntimizeWebModule } from 'ontimize-web-ngx';
import { CategoryExpenseChartComponent } from './category-expense-chart/category-expense-chart.component';
import { OChartModule } from 'ontimize-web-ngx-charts';
import { ChartsRoutingModule } from './charts-routing-module';



@NgModule({
  imports: [
    CommonModule,OntimizeWebModule,OChartModule,ChartsRoutingModule
  ],
  declarations: [CategoryExpenseChartComponent]
})
export class ChartsModule { }
