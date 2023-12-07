import { Component } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";

@Component({
  selector: "home",
  templateUrl: "./home.component.html",
  styleUrls: ["./home.component.css"],
})
export class HomeComponent {
  protected userRole: string;

  constructor(private router: Router, private actRoute: ActivatedRoute) {}

  onDataLoaded(event) {
    console.log(event);
    if (event && event.length > 0 && event[0].ROLENAME) {
      this.userRole = event[0].ROLENAME;

      if (this.userRole === "admin") {
        this.router.navigate(["../../", "plans"], {
          relativeTo: this.actRoute,
        });
      } else {
        this.router.navigate(["../../", "user-home"], {
          relativeTo: this.actRoute,
        });
      }
    } else {
      console.error("Invalid data received:", event);
    }
  }
}
