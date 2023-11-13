import { Component, OnInit, ViewChild } from '@angular/core';
import { OFormComponent } from 'ontimize-web-ngx';

@Component({
  selector: 'app-custom-platform-details',
  templateUrl: './custom-platform-details.component.html',
  styleUrls: ['./custom-platform-details.component.scss']
})
export class CustomPlatformDetailsComponent implements OnInit {
  @ViewChild("customNewPlatformForm", { static: false })
  customServiceForm: OFormComponent;
  public x = "custom";

  @ViewChild("customNewPlatformForm", { static: false })
  customServForm: OFormComponent;
  constructor() { }

  ngOnInit() {

  }

  ngAfterViewInit(): void {
    this.customServiceForm.onFormInitStream.subscribe(() => {
      this.customServiceForm.setInsertMode();
    });
  }


}
