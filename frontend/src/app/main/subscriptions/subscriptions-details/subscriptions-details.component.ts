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
  protected total_price;
  public display_shared_price = true;

  getSharedValue(){
    if (this.shared_price === this.total_price){
      
      return;
    }else{
      this.display_shared_price = false;
      return this.shared_price;
    }
  }

  getSharedCount(event){
    this.shared_price = event.shared_price;
    this.total_price = event.SUB_LAPSE_PRICE;
  }

  ngAfterViewInit(): void {
  }

  ngOnInit(): void {}
}
