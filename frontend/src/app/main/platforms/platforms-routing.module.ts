import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PlatformsHomeComponent } from './platforms-home/platforms-home.component';


const routes: Routes = [
  {
    path: '',
    component: PlatformsHomeComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PlatformsRoutingModule { }
