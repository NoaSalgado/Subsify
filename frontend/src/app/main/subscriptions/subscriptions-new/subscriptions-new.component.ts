import { Component, OnInit, ViewChild } from "@angular/core";
import { OCurrencyInputComponent, OFormComponent } from "ontimize-web-ngx";
import { SubscriptionServiceService } from "../subscription-service.service";

@Component({
  selector: "app-subscriptions-new",
  templateUrl: "./subscriptions-new.component.html",
  styleUrls: ["./subscriptions-new.component.css"],
})
export class SubscriptionsNewComponent implements OnInit {
  @ViewChild("subsForm", { static: false }) subsForm: OFormComponent;
  @ViewChild("price", { static: false }) priceForm: OCurrencyInputComponent;

  constructor(private subscriptionService: SubscriptionServiceService) {}

  ngOnInit() {}

  public setPriceValue(event): void {
    const plan_price_Id = this.subsForm.getFieldValue("PLAN_PRICE_ID");
    const selectedPlan = event.target.dataArray.filter(
      (plan) => plan.PLAN_PRICE_ID == plan_price_Id
    );
    this.plan_price = selectedPlan[0].PLAN_PRICE_VALUE;

    this.subsForm.setFieldValue(
      "SUB_LAPSE_PRICE",
      selectedPlan[0].PLAN_PRICE_VALUE
    );
  }

  public show = true;
  public plan_price;

  public displayInput(event) {
    if (this.plan_price !== this.priceForm.getValue()) {
      this.show = false;
    }
  }

  // reloadTable() {
  //   this.subscriptionService.realoadTable();
  // }
}
