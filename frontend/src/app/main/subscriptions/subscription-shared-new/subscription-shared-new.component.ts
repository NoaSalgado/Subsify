import { Component, ViewChild } from "@angular/core";
import { OFormComponent } from "ontimize-web-ngx";
import { SubscriptionServiceService } from "../subscription-service.service";

@Component({
  selector: "app-subscription-shared-new",
  templateUrl: "./subscription-shared-new.component.html",
  styleUrls: ["./subscription-shared-new.component.css"],
})
export class SubscriptionSharedNewComponent {
  @ViewChild("subscriptionDetailForm", { static: false })
  subscriptionDetailForm: OFormComponent;
  constructor(private subsService: SubscriptionServiceService) {}

  reload() {
    this.subsService.reloadForm();
  }
}
