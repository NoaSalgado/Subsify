import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { SubscriptionsHomeComponent } from "./subscriptions-home/subscriptions-home.component";
import { SubscriptionsNewComponent } from "./subscriptions-new/subscriptions-new.component";
import { SubscriptionsDetailsComponent } from "./subscriptions-details/subscriptions-details.component";
import { SubscriptionSharedNewComponent } from "./subscription-shared-new/subscription-shared-new.component";
import { SubscriptionCustomPriceNewComponent } from "./subscription-custom-price-new/subscription-custom-price-new.component";

const routes: Routes = [
  {
    path: "",
    component: SubscriptionsHomeComponent,
  },
  {
    path: "new",
    component: SubscriptionsNewComponent,
  },
  {
    path: ":SUB_LAPSE_ID",
    component: SubscriptionsDetailsComponent,
  },
  {
    path: ":SUB_LAPSE_ID/new",
    component: SubscriptionSharedNewComponent,
  },
  {
    path: ":SUB_LAPSE_ID/new-custom-price",
    component: SubscriptionCustomPriceNewComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class SubscriptionsRoutingModule {}
