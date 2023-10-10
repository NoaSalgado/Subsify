import { Component, OnInit } from "@angular/core";
import { getMonthlyPricefunction } from "src/app/shared/shared.module";

@Component({
  selector: "app-subscriptions-home",
  templateUrl: "./subscriptions-home.component.html",
  styleUrls: ["./subscriptions-home.component.css"],
})
export class SubscriptionsHomeComponent implements OnInit {
  
  public getMonthlyPrice = getMonthlyPricefunction;

  constructor() {}
  ngOnInit() {}
}
