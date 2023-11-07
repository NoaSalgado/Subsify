import { Injectable } from "@angular/core";
import { OFormComponent } from "ontimize-web-ngx";

@Injectable({
  providedIn: "root",
})
export class SubscriptionServiceService {
  private _form: OFormComponent;

  constructor() {}

  get form() {
    return this._form;
  }

  registerForm(form: OFormComponent) {
    this._form = form;
  }

  reloadForm() {
    console.log(this.form);
    this.form.reload(true);
  }
}
