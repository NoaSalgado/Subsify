import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { SubscriptionsHomeComponent } from "./subscriptions-home/subscriptions-home.component";
import { SubscriptionsNewComponent } from "./subscriptions-new/subscriptions-new.component";
import { SubscriptionsDetailsComponent } from "./subscriptions-details/subscriptions-details.component";

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
    path: ":SUBS_ID",
    component: SubscriptionsDetailsComponent,
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class SubscriptionsRoutingModule {}
