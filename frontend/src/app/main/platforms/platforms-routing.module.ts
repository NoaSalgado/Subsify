import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PlatformsHomeComponent } from './platforms-home/platforms-home.component';
import { PlatformsNewComponent } from './platforms-new/platforms-new.component';
import { PlatformsDetailsComponent } from './platforms-details/platforms-details.component';

const routes: Routes = [
  {
    path: '',
    component: PlatformsHomeComponent
  },
  {
    path: "new",
    component: PlatformsNewComponent,
  },
  {
    path: ":PLATF_ID",
    component: PlatformsDetailsComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PlatformsRoutingModule { }
