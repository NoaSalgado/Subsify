import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OntimizeWebModule } from 'ontimize-web-ngx';
import { PlatformsRoutingModule } from './platforms-routing.module';
import { PlatformsHomeComponent } from './platforms-home/platforms-home.component';


@NgModule({
  declarations: [PlatformsHomeComponent],
  imports: [
    CommonModule,
    OntimizeWebModule,
    PlatformsRoutingModule
  ]
})
export class PlatformsModule { }
