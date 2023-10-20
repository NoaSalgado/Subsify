import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OntimizeWebModule } from 'ontimize-web-ngx';
import { PlansRoutingModule } from './plans-routing-module';
import { PlansHomeComponent } from './plans-home/plans-home.component';
import { PlansNewComponent } from './plans-new/plans-new.component';

@NgModule({
  imports: [
    CommonModule,
    OntimizeWebModule,
    PlansRoutingModule
  ],
  declarations: [PlansHomeComponent,PlansNewComponent]
})
export class PlansModule { }
