import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CustomPlatformRoutingModule } from './custom-platform-routing.module';
import { OntimizeWebModule } from 'ontimize-web-ngx';
import { CustomPlatformHomeComponent } from './custom-platform-home/custom-platform-home.component';
import { CustomPlatformDetailsComponent } from './custom-platform-details/custom-platform-details.component';



@NgModule({
  declarations: [CustomPlatformHomeComponent,CustomPlatformDetailsComponent],
  imports: [
    CommonModule,
    OntimizeWebModule,
    CustomPlatformRoutingModule,
  ]
})
export class CustomPlatformModule { }
