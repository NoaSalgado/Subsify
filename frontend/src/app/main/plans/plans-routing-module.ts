import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PlansHomeComponent } from './plans-home/plans-home.component';
import { PlansNewComponent } from './plans-new/plans-new.component';
import { PlansDetailsComponent } from './plans-details/plans-details.component';

const routes: Routes = [
  {
    path: '',
    component: PlansHomeComponent
  },
  {
    path: 'new',
    component: PlansNewComponent
  },
  {
    path: ":PLAN_ID",
    component: PlansDetailsComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PlansRoutingModule { }
