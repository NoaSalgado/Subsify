import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CustomPlatformNewComponent } from './custom-platform-new/custom-platform-new.component';

const routes: Routes = [{
  path : '',
  component: CustomPlatformNewComponent
  }  
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CustomPlatformRoutingModule { }
