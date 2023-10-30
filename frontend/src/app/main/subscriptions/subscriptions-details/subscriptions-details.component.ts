import { AfterViewInit, Component, OnInit, ViewChild } from "@angular/core";
import { OFormComponent } from "ontimize-web-ngx";

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
  constructor() {}

  ngAfterViewInit(): void {
    this.sharedUsersForm.onFormInitStream.subscribe(() => {
      this.sharedUsersForm.setInsertMode();
    });
  }

  transferData(): void {
    const subID = this.subscriptionDetailForm.formData["SUBS_ID"].value;
    this.sharedUsersForm.setFieldValue("SUBS_ID", subID);
  }
}
