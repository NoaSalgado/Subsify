import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OntimizeWebModule } from 'ontimize-web-ngx';
import { PlatformsRoutingModule } from './platforms-routing.module';
import { PlatformsHomeComponent } from './platforms-home/platforms-home.component';
import { PlatformsNewComponent } from './platforms-new/platforms-new.component';


@NgModule({
  declarations: [PlatformsHomeComponent, PlatformsNewComponent],
  imports: [
    CommonModule,
    OntimizeWebModule,
    PlatformsRoutingModule
  ]
})
export class PlatformsModule { }
