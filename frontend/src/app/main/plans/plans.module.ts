import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OntimizeWebModule } from 'ontimize-web-ngx';
import { PlansRoutingModule } from './plans-routing-module';
import { PlansHomeComponent } from './plans-home/plans-home.component';
import { PlansNewComponent } from './plans-new/plans-new.component';
import { PlansDetailsComponent } from './plans-details/plans-details.component';
import { PlansDetailsNewComponent } from './plans-details/plans-details-new/plans-details-new.component';


@NgModule({
  imports: [
    CommonModule,
    OntimizeWebModule,
    PlansRoutingModule
  ],
  declarations: [PlansHomeComponent,PlansNewComponent, PlansDetailsComponent,PlansDetailsNewComponent]
})
export class PlansModule { }
