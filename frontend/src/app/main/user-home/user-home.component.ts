import {
  Component,
  ElementRef,
  Injector,
  OnInit,
  ViewChild,
} from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { OListComponent, OTranslateService, OntimizeService } from "ontimize-web-ngx";
import { MatDialog } from "@angular/material/dialog";
import { THIS_EXPR } from "@angular/compiler/src/output/output_ast";

@Component({
  selector: "app-user-home",
  templateUrl: "./user-home.component.html",
  styleUrls: ["./user-home.component.css"],
})
export class UserHomeComponent implements OnInit {
  @ViewChild("list", { static: false }) list: OListComponent;
  public remainingDays: Array<string>;
  public showRenewal: Array<boolean>;
  public webLinks: Array<string>;
  public priceToShow: Array<number>;
  public incrementalPrice: Array<number>;
  public decrementalPrice: Array<number>;
  private service: OntimizeService;
  public images: Array<string>;
  public image = "data:image/png:./assets/images/subsify_round_logo.png";

  constructor(
    private router: Router,
    private actRoute: ActivatedRoute,
    private injector: Injector,
    private translate: OTranslateService,
  ) {
    this.service = this.injector.get(OntimizeService);
  }
  calculateDays() {
    this.showRenewal = this.list.dataArray.map((sub) => !sub.SUBS_AUTORENEWAL);
    this.webLinks = this.list.dataArray.map((sub) => sub.PLATF_LINK);

    const endDates = this.list.dataArray.map((sub) => sub.SUB_LAPSE_END);

    this.images = this.list.dataArray.map((sub, i, arr) => {
      if (!sub.PLATF_IMAGE)
        this.image = "./assets/images/subsify_round_logo.png";
      else this.image = `data:image/png;base64,${sub.PLATF_IMAGE.bytes}`;
      console.log(i, this.image);
      return this.image;
    });

    const language = this.translate.getCurrentLang();
    let tomorrow;
    let today;
    let In;
    let days;

    if (language === "en") {
      tomorrow = "tomorrow";
      today = "today";
      In = " in ";
      days = " days";

    }
    else if (language === "es") {
      tomorrow = "mañana";
      today = "hoy";
      In = " en ";
      days = " días";
    }

    this.remainingDays = endDates.map((date) => {
      const datediff = new Date(date).getTime() - new Date().getTime();
      const remainingDays = Math.ceil(datediff / (1000 * 60 * 60 * 24));
      //const translation = 
      //const remainingDaysString = "{{ "RENEW-ON" | oTranslate }}";
      if (remainingDays === 1) { return tomorrow; } else if (remainingDays === 0) { return today; } return In + remainingDays.toString() + days;
    });

    this.priceToShow = this.list.dataArray.map((sub) => {
      if (
        (sub.SLC_START && sub.SLC_END <= sub.SUB_LAPSE_END) ||
        (sub.PLAN_PRICE_END && sub.PLAN_PRICE_END <= sub.SUB_LAPSE_END)
      ) {
        return sub.PLAN_PRICE_VALUE;
      }

      if (sub.SLC_START && sub.SLC_START <= sub.SUB_LAPSE_END) {
        return sub.SLC_PRICE;
      }
      return sub.SUB_LAPSE_PRICE;
    });
    this.incrementalPrice = this.list.dataArray.map((sub) => {
      if (
        (sub.SLC_START && sub.SLC_END <= sub.SUB_LAPSE_END) ||
        (sub.PLAN_PRICE_END && sub.PLAN_PRICE_END <= sub.SUB_LAPSE_END)
      ) {
        if (sub.PLAN_PRICE_VALUE - sub.SUB_LAPSE_PRICE >= 0) {
          return sub.PLAN_PRICE_VALUE - sub.SUB_LAPSE_PRICE;
        }
        return undefined;
      }
      return undefined;
    });

    this.decrementalPrice = this.list.dataArray.map((sub) => {
      if (sub.SLC_START && sub.SLC_START <= sub.SUB_LAPSE_END) {
        return sub.SUB_LAPSE_PRICE - sub.SLC_PRICE;
      }
      return undefined;
    });
  }

  ngOnInit() { }
  navigate() {
    this.router.navigate(["../", "login"], { relativeTo: this.actRoute });
  }

  goToDoc(link: string) {
    window.open(link, "_blank");
  }
}
