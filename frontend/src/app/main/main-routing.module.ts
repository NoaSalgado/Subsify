import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { AuthGuardService } from "ontimize-web-ngx";

import { MainComponent } from "./main.component";

export const routes: Routes = [
  {
    path: "",
    component: MainComponent,
    canActivate: [AuthGuardService],
    children: [
      { path: "", redirectTo: "home", pathMatch: "full" },
      {
        path: "home",
        loadChildren: () =>
          import("./home/home.module").then((m) => m.HomeModule),
        data:{
          oPermission:{
            permissionId: "home-chart-route"
          }
        }
      },
      {
        path: "subscriptions",
        loadChildren: () =>
          import("./subscriptions/subscriptions.module").then(
            (m) => m.SubscriptionsModule
          ),
      },
      {
        path: "categories",
        loadChildren: () =>
          import("./categories/categories.module").then(
            (m) => m.CategoriesModule
          ),
      },
      {
        path: "platforms",
        loadChildren: () =>
          import("./platforms/platforms.module").then(
            (m) => m.PlatformsModule
          ),
      },
      {
        path: "plans",
        loadChildren: () =>
          import("./plans/plans.module").then(
            (m) => m.PlansModule
          ),
      },
      {
        path: "charts",
        loadChildren: () =>
          import("./charts/charts.module").then(
            (m) => m.ChartsModule
          ),
      }
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class MainRoutingModule {}
