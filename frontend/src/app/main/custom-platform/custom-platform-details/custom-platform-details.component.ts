import { Component, ViewChild } from "@angular/core";
import { OFormComponent } from "ontimize-web-ngx";

@Component({
  selector: "app-custom-platform-details",
  templateUrl: "./custom-platform-details.component.html",
  styleUrls: ["./custom-platform-details.component.css"],
})
export class CustomPlatformDetailsComponent {
  @ViewChild("customNewPlatformForm", { static: false })
  customServiceForm: OFormComponent;

  ngAfterViewInit(): void {
    this.customServiceForm.onFormInitStream.subscribe(() => {
      this.customServiceForm.setInsertMode();
    });
  }
}
