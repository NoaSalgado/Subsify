import { Component, ViewChild } from "@angular/core";
import { OFormComponent } from "ontimize-web-ngx";

@Component({
  selector: "app-custom-platform-new",
  templateUrl: "./custom-platform-new.component.html",
  styleUrls: ["./custom-platform-new.component.css"],
})
export class CustomPlatformNewComponent {
  @ViewChild("customServiceForm", { static: false })
  customServiceForm: OFormComponent;
  public x = "custom";

  @ViewChild("customServiceForm", { static: false })
  protected customServForm: OFormComponent;

  ngAfterViewInit(): void {
    this.customServiceForm.onFormInitStream.subscribe(() => {
      this.customServiceForm.setInsertMode();
    });
  }
}
