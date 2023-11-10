import { NgModule } from '@angular/core';
import { OntimizeWebModule } from 'ontimize-web-ngx';

import { SharedModule } from '../shared/shared.module';
import { SignUpRoutingModule } from './sign-up-routing.module';
import { SignUpComponent } from './sign-up.component';

@NgModule({
  imports: [
    SharedModule,
    OntimizeWebModule,
    SignUpRoutingModule
  ],
  declarations: [
    SignUpComponent
  ]
})
export class SignUpModule { }
