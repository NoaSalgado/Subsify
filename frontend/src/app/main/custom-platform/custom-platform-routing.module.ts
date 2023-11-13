import { CustomPlatformDetailsComponent } from './custom-platform-details/custom-platform-details.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CustomPlatformHomeComponent } from './custom-platform-home/custom-platform-home.component';



const routes: Routes = [{
  path : '',
  component: CustomPlatformHomeComponent
},{
  path: ":PLATF_NAME",
  component: CustomPlatformDetailsComponent
}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CustomPlatformRoutingModule { }
