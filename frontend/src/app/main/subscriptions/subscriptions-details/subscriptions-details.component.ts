import { AfterViewInit, Component, Injector, OnInit, ViewChild } from "@angular/core";
@Component({
  selector: "app-subscriptions-details",
  templateUrl: "./subscriptions-details.component.html",
  styleUrls: ["./subscriptions-details.component.css"],
})

export class SubscriptionsDetailsComponent implements OnInit {

  constructor(protected injector: Injector) {

  }

  ngOnInit(): void {
  }
}
