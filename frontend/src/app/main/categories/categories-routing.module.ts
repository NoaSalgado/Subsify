import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { CategoriesHomeComponent } from "./categories-home/categories-home.component";
import { CategoriesNewComponent } from "./categories-new/categories-new.component";
import { CategoriesDetailsComponent } from "./categories-details/categories-details.component";

const routes: Routes = [
  {
    path: "",
    component: CategoriesHomeComponent,
  },
  {
    path: "new",
    component: CategoriesNewComponent,
  },
  {
    path: ":CAT_ID",
    component: CategoriesDetailsComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CategoriesRoutingModule {}
