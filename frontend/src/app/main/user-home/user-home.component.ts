import { Component, Injector, OnInit, ViewChild } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { OListComponent, OntimizeService } from "ontimize-web-ngx";
import { MatDialog } from "@angular/material/dialog";
import { THIS_EXPR } from "@angular/compiler/src/output/output_ast";

@Component({
  selector: "app-user-home",
  templateUrl: "./user-home.component.html",
  styleUrls: ["./user-home.component.css"],
})
export class UserHomeComponent implements OnInit {
  @ViewChild("list", { static: false }) list: OListComponent;
  public remainingDays: Array<number>;
  public showRenewal: Array<boolean>;
  public webLinks: Array<string>;
  public priceToShow: Array<number>;
  public incrementalPrice: Array<number>;
  private service: OntimizeService;
  public test = "data:image/png;base64,CiA8cmRmOlJERiB4bWxuczpyZGY9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkvMDIvMjItcmRmLXN5bnRheC1ucyMiPgogIDxyZGY6RGVzY3JpcHRpb24gcmRmOmFib3V0PSIiCiAgICB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIKICAgIHhtbG5zOnN0RXZ0PSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvc1R5cGUvUmVzb3VyY2VFdmVudCMiCiAgICB4bWxuczpkYz0iaHR0cDovL3B1cmwub3JnL2RjL2VsZW1lbnRzLzEuMS8iCiAgICB4bWxuczpHSU1QPSJodHRwOi8vd3d3LmdpbXAub3JnL3htcC8iCiAgICB4bWxuczp0aWZmPSJodHRwOi8vbnMuYWRvYmUuY29tL3RpZmYvMS4wLyIKICAgIHhtbG5zOnhtcD0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wLyIKICAgeG1wTU06RG9jdW1lbnRJRD0iZ2ltcDpkb2NpZDpnaW1wOjhiM2VjNGMxLWJjN2YtNDg2OC04NWYxLWE2ODYwNzAzOWQyZiIKICAgeG1wTU06SW5zdGFuY2VJRD0ieG1wLmlpZDo2MjI1OWM4NS02N2YzLTRlZjYtODIxOC1iMmUwZTQ4OGRjMzgiCiAgIHhtcE1NOk9yaWdpbmFsRG9jdW1lbnRJRD0ieG1wLmRpZDoyYThiNDIzZi03ZjIzLTQ0YTctYjM3YS0zZTIxYTE2ZTMyYjMiCiAgIGRjOkZvcm1hdD0iaW1hZ2UvcG5nIgogICBHSU1QOkFQST0iMi4wIgogICBHSU1QOlBsYXRmb3JtPSJMaW51eCIKICAgR0lNUDpUaW1lU3RhbXA9IjE3MDAwNDAzMjIwNTUyNjAiCiAgIEdJTVA6VmVyc2lvbj0iMi4xMC4zNCIKICAgdGlmZjpPcmllbnRhdGlvbj0iMSIKICAgeG1wOkNyZWF0b3JUb29sPSJHSU1QIDIuMTAiCiAgIHhtcDpNZXRhZGF0YURhdGU9IjIwMjM6MTE6MTVUMTA6MjU6MjArMDE6MDAiCiAgIHhtcDpNb2RpZnlEYXRlPSIyMDIzOjExOjE1VDEwOjI1OjIwKzAxOjAwIj4KICAgPHhtcE1NOkhpc3Rvcnk";
  public images: Array<string>;

  constructor(
    private router: Router,
    private actRoute: ActivatedRoute,
    private injector: Injector
  ) {
    this.service = this.injector.get(OntimizeService);
  }
  calculateDays() {
    this.showRenewal = this.list.dataArray.map((sub) => !sub.SUBS_AUTORENEWAL);
    console.log(this.showRenewal);
    this.webLinks = this.list.dataArray.map((sub) => sub.PLATF_LINK);
    console.log(this.webLinks);
    
    const endDates = this.list.dataArray.map((sub) => sub.SUB_LAPSE_END);

    this.images = this.list.dataArray.map((sub, i, arr)=> {
      if (!sub.PLATF_IMAGE) return null;
      const image = `data:image/png;base64,${sub.PLATF_IMAGE.bytes}`;
      console.log(i, image)
      return image;
    });

    this.remainingDays = endDates.map((date) => {
      const datediff = new Date(date).getTime() - new Date().getTime();
      const remainingDays = Math.ceil(datediff / (1000 * 60 * 60 * 24));
      return remainingDays;
    });

    this.priceToShow = this.list.dataArray.map((sub) => {
      if (
        (sub.SLC_PRICE && sub.SLC_END <= sub.SUB_LAPSE_END) ||
        (sub.PLAN_PRICE_END && sub.PLAN_PRICE_END <= sub.SUB_LAPSE_END)
      ) {
        return sub.PLAN_PRICE_VALUE;
      }
      return sub.SUB_LAPSE_PRICE;
    });
    this.incrementalPrice = this.list.dataArray.map((sub) => {
      if (
        (sub.SLC_PRICE && sub.SLC_END <= sub.SUB_LAPSE_END) ||
        (sub.PLAN_PRICE_END && sub.PLAN_PRICE_END <= sub.SUB_LAPSE_END)
      ) {
        return sub.PLAN_PRICE_VALUE - sub.SUB_LAPSE_PRICE;
      }
      return undefined;
    });
  }

  ngOnInit() {}
  navigate() {
    this.router.navigate(["../", "login"], { relativeTo: this.actRoute });
  }

  goToDoc(link: string) {
    window.open(link, "_blank");
  }
}
