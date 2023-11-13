import { CustomPlatformsRoutingModule } from './custom-platforms-routing.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CustomPlatformsHomeComponent } from './custom-platforms-home/custom-platforms-home.component';
import { OntimizeWebModule } from 'ontimize-web-ngx';

@NgModule({
  imports: [
    CommonModule,
    OntimizeWebModule,
    CustomPlatformsRoutingModule
  ],
  declarations: [CustomPlatformsHomeComponent]
})
export class CustomPlatformsModule { }
