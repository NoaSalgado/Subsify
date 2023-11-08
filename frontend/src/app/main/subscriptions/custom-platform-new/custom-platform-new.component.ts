import { Component, OnInit, ViewChild } from "@angular/core";
import { OFormComponent } from "ontimize-web-ngx";

@Component({
  selector: "app-custom-platform-new",
  templateUrl: "./custom-platform-new.component.html",
  styleUrls: ["./custom-platform-new.component.scss"],
})
export class CustomPlatformNewComponent implements OnInit {
  @ViewChild("customServiceForm", { static: false })
  customServiceForm: OFormComponent;
  public x = "custom";

  @ViewChild("customServiceForm", { static: false })
  customServForm: OFormComponent;

  constructor() {}

  ngOnInit() {}

  ngAfterViewInit(): void {
    this.customServiceForm.onFormInitStream.subscribe(() => {
      this.customServiceForm.setInsertMode();
    });
  }

  printEvent(event) {
    console.log(event);
  }
}
