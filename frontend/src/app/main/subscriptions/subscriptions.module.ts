import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { SubscriptionsRoutingModule } from "./subscriptions-routing.module";
import { OntimizeWebModule } from "ontimize-web-ngx";
import { SubscriptionsHomeComponent } from "./subscriptions-home/subscriptions-home.component";

@NgModule({
  declarations: [SubscriptionsHomeComponent],
  imports: [CommonModule, SubscriptionsRoutingModule, OntimizeWebModule],
})
export class SubscriptionsModule {}
