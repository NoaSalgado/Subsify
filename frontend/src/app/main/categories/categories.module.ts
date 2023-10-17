import { CategoriesHomeComponent } from './categories-home/categories-home.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CategoriesNewComponent } from './categories-new/categories-new.component';
import { CategoriesRoutingModule } from './categories-routing.module';
import { OntimizeWebModule } from 'ontimize-web-ngx';
import { CategoriesDetailsComponent } from './categories-details/categories-details.component';

@NgModule({
  imports: [
    CommonModule,
    OntimizeWebModule,
    CategoriesRoutingModule
  ],
  declarations: [CategoriesHomeComponent, CategoriesNewComponent, CategoriesDetailsComponent]
})
export class CategoriesModule { }
