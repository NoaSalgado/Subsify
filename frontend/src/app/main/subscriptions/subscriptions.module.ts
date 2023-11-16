import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { SubscriptionsRoutingModule } from "./subscriptions-routing.module";
import { OntimizeWebModule } from "ontimize-web-ngx";
import { SubscriptionsHomeComponent } from "./subscriptions-home/subscriptions-home.component";
import { SubscriptionsNewComponent } from "./subscriptions-new/subscriptions-new.component";
import { SubscriptionsDetailsComponent } from "./subscriptions-details/subscriptions-details.component";
import { SubscriptionSharedNewComponent } from "./subscription-shared-new/subscription-shared-new.component";
import { OTableRendererConcatComponent } from "./o-table-renderer-concat/o-table-renderer-concat.component";
import { CustomPlatformNewComponent } from "./custom-platform-new/custom-platform-new.component";
import { OChartModule } from "ontimize-web-ngx-charts";
import { SubscriptionCustomPriceNewComponent } from './subscription-custom-price-new/subscription-custom-price-new.component';

@NgModule({
  declarations: [
    SubscriptionsHomeComponent,
    SubscriptionsNewComponent,
    SubscriptionsDetailsComponent,
    SubscriptionSharedNewComponent,
    OTableRendererConcatComponent,
    CustomPlatformNewComponent,
    SubscriptionCustomPriceNewComponent,
    
  ],
  imports: [CommonModule, SubscriptionsRoutingModule, OntimizeWebModule,OChartModule],
})
export class SubscriptionsModule {}
