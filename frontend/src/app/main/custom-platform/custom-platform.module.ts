import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CustomPlatformRoutingModule } from './custom-platform-routing.module';
import { OntimizeWebModule } from 'ontimize-web-ngx';
import { CustomPlatformHomeComponent } from './custom-platform-home/custom-platform-home.component';


@NgModule({
  declarations: [CustomPlatformHomeComponent],
  imports: [
    CommonModule,
    OntimizeWebModule,
    CustomPlatformRoutingModule
  ]
})
export class CustomPlatformModule { }
