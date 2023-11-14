import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { AuthGuardService, PermissionsGuardService } from "ontimize-web-ngx";

import { MainComponent } from "./main.component";

export const routes: Routes = [
  {
    path: "",
    component: MainComponent,
    canActivate: [AuthGuardService],
    canActivateChild: [PermissionsGuardService],
    children: [
      { path: "", redirectTo: "home", pathMatch: "full" },
      {
        path: "home",
        loadChildren: () =>
          import("./home/home.module").then((m) => m.HomeModule),
        data:{
          oPermission:{
            permissionId: "home-route"
          }
        }
      },
      {
        path: "user-home",
        loadChildren: () =>
          import("./user-home/user-home.module").then((m) => m.UserHomeModule),
          data:{
            oPermission:{
              permissionId: "user-home-route"
            }
          }
      },
      {
        path: "subscriptions",
        loadChildren: () =>
          import("./subscriptions/subscriptions.module").then(
            (m) => m.SubscriptionsModule
          ),
          data:{
            oPermission:{
              permissionId: "subscription-home-route",
              restrictedPermissionsRedirect: "home"
            }
          }
      },
      {
        path: "categories",
        loadChildren: () =>
          import("./categories/categories.module").then(
            (m) => m.CategoriesModule
          ),
          data:{
            oPermission:{
              permissionId: "categories-table-route"
            }
          }
      },
      {
        path: "platforms",
        loadChildren: () =>
          import("./platforms/platforms.module").then(
            (m) => m.PlatformsModule
          ),
          data:{
            oPermission:{
              permissionId: "platforms-table-route"
            }
          }
      },
      {
        path: "plans",
        loadChildren: () =>
          import("./plans/plans.module").then(
            (m) => m.PlansModule
          ),
          data:{
            oPermission:{
              permissionId: "plans-table-route"
            }
          }
      },
      {
        path: "charts",
        loadChildren: () =>
          import("./charts/charts.module").then(
            (m) => m.ChartsModule
          ),
          data:{
            oPermission:{
              permissionId: "charts-table-route"
            }
          }
      },
      {
        path: "custom-platform",
        loadChildren: () =>
          import("./custom-platform/custom-platform.module").then(
            (m) => m.CustomPlatformModule
          ),
          data:{
            oPermission:{
              permissionId: "custom-platform-table-route"
            }
          }
      }
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class MainRoutingModule {}
