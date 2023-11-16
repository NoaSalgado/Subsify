import { Component, OnInit, ViewChild } from "@angular/core";
import { OCurrencyInputComponent, OFormComponent } from "ontimize-web-ngx";

@Component({
  selector: "app-subscription-custom-price-new",
  templateUrl: "./subscription-custom-price-new.component.html",
  styleUrls: ["./subscription-custom-price-new.component.css"],
})
export class SubscriptionCustomPriceNewComponent implements OnInit {
  @ViewChild("formPrice", { static: false }) formPrice: OCurrencyInputComponent;
  constructor() {}

  ngOnInit() {}
}
