import {
  AfterViewInit,
  Component,
  Injector,
  OnInit,
  ViewChild,
} from "@angular/core";
import { OFormComponent, OntimizeService } from "ontimize-web-ngx";

@Component({
  selector: "app-subscriptions-details",
  templateUrl: "./subscriptions-details.component.html",
  styleUrls: ["./subscriptions-details.component.css"],
})
export class SubscriptionsDetailsComponent implements AfterViewInit, OnInit {
  @ViewChild("subscriptionDetailForm", { static: false })
  subscriptionDetailForm: OFormComponent;
  @ViewChild("sharedUsersForm", { static: false })
  sharedUsersForm: OFormComponent;
  protected service: OntimizeService;
  private sharedSubs;

  constructor(protected injector: Injector) {
    this.service = this.injector.get(OntimizeService);
  }

  ngOnInit(): void {
    this.configureService();
  }

  protected configureService(): void {
    const conf = this.service.getDefaultServiceConfiguration("sharedSubs");
    this.service.configureService(conf);
  }

  getSubscriptionCount(subID) {
    if (this.service !== null) {
      const filter = {
        SUBS_ID: subID,
      };
      this.service
        .query(filter, ["SHARED_SUB_USER"], "sharedSub")
        .subscribe((resp) => {
          if (resp.code === 0) {
            this.updateSubLapsePrice(resp.data);
          } else {
            alert("Impossible to query data!");
          }
        });
    }
  }

  ngAfterViewInit(): void {
    this.sharedUsersForm.onFormInitStream.subscribe(() => {
      this.sharedUsersForm.setInsertMode();
    });
  }

  async transferData() {
    const subID = this.subscriptionDetailForm.formData["SUBS_ID"].value;
    this.sharedUsersForm.setFieldValue("SUBS_ID", subID);
    this.getSubscriptionCount(subID);
  }

  updateSubLapsePrice(subsCount): void {
    console.log(subsCount);
    const price = this.subscriptionDetailForm.getFieldValue("SUB_LAPSE_PRICE");
    this.subscriptionDetailForm.setFieldValue(
      "SUB_LAPSE_PRICE",
      price / (subsCount.length + 1)
    );
  }
}
