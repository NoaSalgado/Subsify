import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { SubscriptionsRoutingModule } from "./subscriptions-routing.module";
import { OntimizeWebModule } from "ontimize-web-ngx";
import { SubscriptionsHomeComponent } from "./subscriptions-home/subscriptions-home.component";
import { SubscriptionsNewComponent } from "./subscriptions-new/subscriptions-new.component";
import { SubscriptionsDetailsComponent } from "./subscriptions-details/subscriptions-details.component";
import { SubscriptionSharedNewComponent } from "./subscription-shared-new/subscription-shared-new.component";

@NgModule({
  declarations: [
    SubscriptionsHomeComponent,
    SubscriptionsNewComponent,
    SubscriptionsDetailsComponent,
    SubscriptionSharedNewComponent,
  ],
  imports: [CommonModule, SubscriptionsRoutingModule, OntimizeWebModule],
})
export class SubscriptionsModule {}
