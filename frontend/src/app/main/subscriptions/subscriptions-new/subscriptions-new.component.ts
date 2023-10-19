import { Component, OnInit, ViewChild } from "@angular/core";
import { OFormComponent } from "ontimize-web-ngx";

@Component({
  selector: "app-subscriptions-new",
  templateUrl: "./subscriptions-new.component.html",
  styleUrls: ["./subscriptions-new.component.css"],
})
export class SubscriptionsNewComponent implements OnInit {
  @ViewChild("subsForm", { static: false }) subsForm: OFormComponent;
  //@ViewChild("comboPlan", { static: false }) subsForm: OFormComponent;

  constructor() {}

  ngOnInit() {}

  public setPriceValue(event): void {
    const planId = this.subsForm.getFieldValue("PLAN_ID");
    const selectedPlan = event.target.dataArray.filter(plan => plan.PLAN_ID == planId);

    this.subsForm.setFieldValue(
      "SUB_LAPSE_PRICE",
      selectedPlan[0].PLAN_PRICE_VALUE
    );
  }
}
