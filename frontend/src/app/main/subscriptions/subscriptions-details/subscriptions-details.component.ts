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

  ngAfterViewInit(): void {}

  ngOnInit(): void {}
}
