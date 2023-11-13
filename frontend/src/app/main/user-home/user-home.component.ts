import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { OListComponent } from 'ontimize-web-ngx';
import { MatDialog } from '@angular/material/dialog';

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
  user_role;

  constructor(
    private router: Router,
    private actRoute: ActivatedRoute,
    private dialog: MatDialog) { 
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
