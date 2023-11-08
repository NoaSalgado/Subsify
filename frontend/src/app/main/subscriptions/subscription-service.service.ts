import { Injectable } from "@angular/core";
import { OFormComponent, OTableComponent } from "ontimize-web-ngx";

@Injectable({
  providedIn: "root",
})
export class SubscriptionServiceService {
  private _form: OFormComponent;
  private _table: OTableComponent;

  constructor() {}

  get form() {
    return this._form;
  }

  registerTable(table: OTableComponent) {
    this._table = table;
  }

  registerForm(form: OFormComponent) {
    this._form = form;
  }

  // realoadTable() {
  //   setTimeout(() => {
  //     console.log("here");
  //     this._table.reloadData(true);
  //   }, 1001);
  // }

  reloadForm() {
    this.form.reload(true);
  }
}
