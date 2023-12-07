import { AfterViewInit, Component, ViewChild } from "@angular/core";
import { OFormComponent } from "ontimize-web-ngx";
import { SubscriptionServiceService } from "../subscription-service.service";
@Component({
  selector: "app-subscriptions-details",
  templateUrl: "./subscriptions-details.component.html",
  styleUrls: ["./subscriptions-details.component.css"],
})
export class SubscriptionsDetailsComponent implements AfterViewInit {
  @ViewChild("subscriptionDetailForm", { static: false })
  subscriptionDetailForm: OFormComponent;
  @ViewChild("sharedUsersForm", { static: false })
  sharedUsersForm: OFormComponent;

  protected sharedPrice: number;
  protected totalPrice: number;
  protected displaySharedPrice = true;
  protected showInput = true;

  constructor(private subsService: SubscriptionServiceService) {}

  ngAfterViewInit(): void {
    this.registerForm();
  }

  registerForm() {
    this.subsService.registerForm(this.subscriptionDetailForm);
  }

  getSharedValue() {
    if (this.sharedPrice === this.totalPrice) {
      return;
    } else {
      this.displaySharedPrice = false;
      return this.sharedPrice;
    }
  }

  getSharedCount(event) {
    this.sharedPrice = event.shared_price;
    this.totalPrice = event.SUB_LAPSE_PRICE;
  }

  public displayInput(event) {
    this.showInput = false;
  }
}
