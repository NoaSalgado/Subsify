import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OntimizeWebModule } from 'ontimize-web-ngx';
import { PlatformsRoutingModule } from './platforms-routing.module';
import { PlatformsHomeComponent } from './platforms-home/platforms-home.component';
import { PlatformsNewComponent } from './platforms-new/platforms-new.component';
import { PlatformsDetailsComponent } from './platforms-details/platforms-details.component';


@NgModule({
  declarations: [PlatformsHomeComponent, PlatformsNewComponent, PlatformsDetailsComponent],
  imports: [
    CommonModule,
    OntimizeWebModule,
    PlatformsRoutingModule
  ]
})
export class PlatformsModule { }
