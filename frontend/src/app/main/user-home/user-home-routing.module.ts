import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { PermissionsGuardService } from 'ontimize-web-ngx';
import { UserHomeComponent } from './user-home.component';

const routes: Routes = [
  {
    path: '',
    component: UserHomeComponent,
  }
 
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserHomeRoutingModule { }
