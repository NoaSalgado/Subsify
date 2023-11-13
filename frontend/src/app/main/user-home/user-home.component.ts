import { Component, Injector, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { OListComponent, OntimizeService } from 'ontimize-web-ngx';
import { MatDialog } from '@angular/material/dialog';
import { THIS_EXPR } from '@angular/compiler/src/output/output_ast';

@Component({
  selector: 'app-user-home',
  templateUrl: './user-home.component.html',
  styleUrls: ['./user-home.component.css']
})
export class UserHomeComponent implements OnInit {
  @ViewChild('list', { static: false }) list: OListComponent
  public remainingDays: Array<number>;
  public showRenewal: Array<boolean>;
  public webLinks: Array<string>;
  public priceToShow: Array<number>;
  public incrementalPrice: Array<number>;
  private service:OntimizeService

  constructor(
    private router: Router,
    private actRoute: ActivatedRoute,
    private injector:Injector) { 
     this.service=this.injector.get(OntimizeService)
   }
   calculateDays() {

    this.showRenewal = this.list.dataArray.map(sub => !sub.SUBS_AUTORENEWAL);
    console.log(this.showRenewal);
    this.webLinks = this.list.dataArray.map(sub => sub.PLATF_LINK);
    console.log(this.webLinks)
    const endDates = this.list.dataArray.map(sub => sub.SUB_LAPSE_END);

    this.remainingDays = endDates.map(date => {

      const datediff = new Date(date).getTime() - new Date().getTime()
      const remainingDays = Math.ceil(datediff / (1000 * 60 * 60 * 24))
      return remainingDays
    })

    this.priceToShow=this.list.dataArray.map(sub=>{
      if(sub.SLC_PRICE && sub.SLC_END<=sub.SUB_LAPSE_END){
        return sub.PLAN_PRICE_VALUE
      }
      return sub.SUB_LAPSE_PRICE

    })
    this.incrementalPrice=this.list.dataArray.map(sub=>{
      if(sub.SLC_PRICE && sub.SLC_END<=sub.SUB_LAPSE_END){
        return sub.PLAN_PRICE_VALUE-sub.SUB_LAPSE_PRICE
      }
      return undefined
    })

  

  }

  ngOnInit() {
  }
  navigate() {
    this.router.navigate(['../', 'login'], { relativeTo: this.actRoute });
  }

  goToDoc(link: string) {
    window.open(link, "_blank");
  }
}
