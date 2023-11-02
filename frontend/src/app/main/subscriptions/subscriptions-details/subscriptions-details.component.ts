import {
  AfterViewInit,
  Component,
  Injector,
  OnInit,
  ViewChild,
} from "@angular/core";
import { OFormComponent } from "ontimize-web-ngx";
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
  protected shared_price;

  getSharedValue(){
    return this.shared_price;
  }

  getSharedCount(event){
    this.shared_price = event.shared_price;
  }

  ngAfterViewInit(): void {
  }

  ngOnInit(): void {}
}
