import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { OntimizeWebModule } from 'ontimize-web-ngx';

export function getMonthlyPricefunction(rowData: Array<any>): number{
  return rowData["SUB_LAPSE_PRICE"]/rowData["FR_VALUE"]
}

@NgModule({
  imports: [
    OntimizeWebModule
  ],
  declarations: [
  ],
  exports: [
    CommonModule
  ]
})
export class SharedModule { }



