import { NgModule } from "@angular/core";
import { OntimizeWebModule } from "ontimize-web-ngx";
import { OChartModule } from 'ontimize-web-ngx-charts';
import { SharedModule } from "../../shared/shared.module";
import { HomeRoutingModule } from "./home-routing.module";
import { HomeComponent } from "./home.component";
import { CategoryExpenseChartComponent } from './category-expense-chart/category-expense-chart.component';

@NgModule({
  imports: [SharedModule, OntimizeWebModule, HomeRoutingModule, OChartModule],
  declarations: [HomeComponent, CategoryExpenseChartComponent],
})
export class HomeModule {}
