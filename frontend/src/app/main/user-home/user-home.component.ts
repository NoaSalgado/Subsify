import { Component, Injector, ViewChild } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { OListComponent, OTranslateService } from "ontimize-web-ngx";

@Component({
  selector: "app-user-home",
  templateUrl: "./user-home.component.html",
  styleUrls: ["./user-home.component.css"],
})
export class UserHomeComponent {
  @ViewChild("list", { static: false }) list: OListComponent;
  protected remainingDays: Array<string>;
  protected showRenewal: Array<boolean>;
  protected webLinks: Array<string>;
  protected endDates: Array<Date>;
  protected priceToShow: Array<number>;
  protected incrementalPrice: Array<number>;
  protected decrementalPrice: Array<number>;
  protected images: Array<string>;
  protected image = "data:image/png:./assets/images/subsify_round_logo.png";
  protected isListEmpty = false;

  constructor(private translate: OTranslateService) {}

  onListDataLoaded(event) {
    this.isListEmpty = event.length === 0;
    this.processData();
  }

  processData() {
    this.getSubscriptionsToCancel();
    this.getWebLinks();
    this.getEndDates();
    this.getImages();
    this.getRemainingDays();
    this.getPriceToShow();
    this.getIncrementalPrice();
    this.getDecrementalPrice();
  }

  getSubscriptionsToCancel() {
    this.showRenewal = this.list.dataArray.map((sub) => !sub.SUBS_AUTORENEWAL);
  }

  getWebLinks() {
    this.webLinks = this.list.dataArray.map((sub) => sub.PLATF_LINK);
  }

  getEndDates() {
    this.endDates = this.list.dataArray.map((sub) => sub.SUB_LAPSE_END);
  }

  getImages() {
    this.images = this.list.dataArray.map((sub, i, arr) => {
      if (!sub.PLATF_IMAGE)
        this.image = "./assets/images/subsify_round_logo.png";
      else this.image = `data:image/png;base64,${sub.PLATF_IMAGE.bytes}`;
      return this.image;
    });
  }

  getRemainingDays() {
    const language = this.translate.getCurrentLang();
    let tomorrow: string;
    let today: string;
    let preposition: string;
    let days: string;

    if (language === "en") {
      tomorrow = "tomorrow";
      today = "today";
      preposition = " in ";
      days = " days";
    } else if (language === "es") {
      tomorrow = "mañana";
      today = "hoy";
      preposition = " en ";
      days = " días";
    }

    this.remainingDays = this.endDates.map((date) => {
      const datediff = new Date(date).getTime() - new Date().getTime();
      const remainingDays = Math.ceil(datediff / (1000 * 60 * 60 * 24));

      if (remainingDays === 1) {
        return tomorrow;
      } else if (remainingDays === 0) {
        return today;
      }
      return preposition + remainingDays.toString() + days;
    });
  }

  getPriceToShow() {
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
  }

  getIncrementalPrice() {
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
  }

  getDecrementalPrice() {
    this.decrementalPrice = this.list.dataArray.map((sub) => {
      if (sub.SLC_START && sub.SLC_START <= sub.SUB_LAPSE_END) {
        return sub.SUB_LAPSE_PRICE - sub.SLC_PRICE;
      }
      return undefined;
    });
  }

  goToPlatform(link: string) {
    window.open(link, "_blank");
  }
}
