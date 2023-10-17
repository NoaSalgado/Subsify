import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PlatformsHomeComponent } from './platforms-home/platforms-home.component';
import { PlatformsNewComponent } from './platforms-new/platforms-new.component';

const routes: Routes = [
  {
    path: '',
    component: PlatformsHomeComponent
  },
  {
    path: "new",
    component: PlatformsNewComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PlatformsRoutingModule { }
