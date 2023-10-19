import { Component, OnInit, ViewChild } from "@angular/core";
import { OFormComponent } from "ontimize-web-ngx";

@Component({
  selector: "app-subscriptions-new",
  templateUrl: "./subscriptions-new.component.html",
  styleUrls: ["./subscriptions-new.component.css"],
})
export class SubscriptionsNewComponent implements OnInit {
  @ViewChild("subsForm", { static: false }) subsForm: OFormComponent;

  constructor() {}

  ngOnInit() {}

  public setPriceValue(): void {
    this.subsForm.setFieldValue(
      "SUB_LAPSE_PRICE",
      this.subsForm.getFieldValue("PLAN_ID")
    );
  }
}
