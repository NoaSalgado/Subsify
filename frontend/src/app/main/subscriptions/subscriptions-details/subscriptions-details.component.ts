import {
  AfterViewInit,
  Component,
  Injector,
  OnInit,
  ViewChild,
} from "@angular/core";
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

  protected shared_price;
  protected total_price;
  protected users;
  protected virtual_user;
  public display_shared_price = true;

  constructor(private subsService: SubscriptionServiceService) {}

  ngAfterViewInit(): void {
    this.registerForm();
  }

  registerForm() {
    this.subsService.registerForm(this.subscriptionDetailForm);
  }

  getSharedValue() {
    if (this.shared_price === this.total_price) {
      return;
    } else {
      this.display_shared_price = false;
      return this.shared_price;
    }
  }

  getSharedCount(event) {
    this.shared_price = event.shared_price;
    this.total_price = event.SUB_LAPSE_PRICE;
  }
}
