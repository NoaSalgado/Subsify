import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CustomPlatformRoutingModule } from './custom-platform-routing.module';
import { OntimizeWebModule } from 'ontimize-web-ngx';
import { CustomPlatformNewComponent } from './custom-platform-new/custom-platform-new.component'

@NgModule({
  declarations: [CustomPlatformNewComponent],
  imports: [
    CommonModule,
    OntimizeWebModule,
    CustomPlatformRoutingModule
  ]
})
export class CustomPlatformModule { }
