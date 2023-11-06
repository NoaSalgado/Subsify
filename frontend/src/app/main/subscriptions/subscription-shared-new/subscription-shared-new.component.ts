import {
  Component,
  EventEmitter,
  OnInit,
  Output,
  ViewChild,
} from "@angular/core";
import { OFormComponent } from "ontimize-web-ngx";

@Component({
  selector: "app-subscription-shared-new",
  templateUrl: "./subscription-shared-new.component.html",
  styleUrls: ["./subscription-shared-new.component.css"],
})
export class SubscriptionSharedNewComponent implements OnInit {
  @ViewChild("subscriptionDetailForm", { static: false })
  subscriptionDetailForm: OFormComponent;
  constructor() {}

  ngOnInit() {}

  reload() {
    location.reload();
  }
}
