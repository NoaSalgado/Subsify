import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CategoryExpenseChartComponent } from './category-expense-chart/category-expense-chart.component';


const routes: Routes = [
  {
    path: '',
    component: CategoryExpenseChartComponent
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ChartsRoutingModule { }
