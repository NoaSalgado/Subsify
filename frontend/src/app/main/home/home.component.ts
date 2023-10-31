import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { OListComponent } from 'ontimize-web-ngx';

@Component({
  selector: 'home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})

export class HomeComponent implements OnInit {
@ViewChild('list',{static:false}) list:OListComponent
public remainingDays: Array<number>;

  constructor(
    private router: Router,
    private actRoute: ActivatedRoute
  ) {
  }

  calculateDays() {
    
    const endDates = this.list.dataArray.map(sub=> sub.SUB_LAPSE_END);
    this.remainingDays= endDates.map(date=>{
        const datediff = new Date(date).getTime()- new Date().getTime()
        const remainingDays = Math.ceil(datediff/(1000*60*60*24))
        return remainingDays
    })

  }

  ngOnInit() {
  }

  navigate() {
    this.router.navigate(['../', 'login'], { relativeTo: this.actRoute });
  }

  goToDoc() {
    window.open("https://www.netflix.com/es/", "_blank");
  }

}
