import { Component, ViewChild } from "@angular/core";
import { OCurrencyInputComponent, OFormComponent } from "ontimize-web-ngx";

@Component({
  selector: "app-subscriptions-new",
  templateUrl: "./subscriptions-new.component.html",
  styleUrls: ["./subscriptions-new.component.css"],
})
export class SubscriptionsNewComponent {
  @ViewChild("subsForm", { static: false }) subsForm: OFormComponent;
  @ViewChild("price", { static: false }) priceForm: OCurrencyInputComponent;

  protected show = true;
  private planPrice: number;

  public setPriceValue(event): void {
    const planPriceID = this.subsForm.getFieldValue("PLAN_PRICE_ID");
    const selectedPlan = event.target.dataArray.filter(
      (plan) => plan.PLAN_PRICE_ID == planPriceID
    );
    this.planPrice = selectedPlan[0].PLAN_PRICE_VALUE;

    this.subsForm.setFieldValue(
      "SUB_LAPSE_PRICE",
      selectedPlan[0].PLAN_PRICE_VALUE
    );
  }

  public displayInput(event) {
    if (this.planPrice !== this.priceForm.getValue()) {
      this.show = false;
    }
  }
}
