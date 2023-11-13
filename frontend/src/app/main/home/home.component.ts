import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { OListComponent } from 'ontimize-web-ngx';
import { MatDialog } from '@angular/material/dialog';
import { AlertDialogComponent } from '../alerts/alert-dialog/alert-dialog.component';

@Component({
  selector: 'home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})

export class HomeComponent implements OnInit {
  @ViewChild('list', { static: false }) list: OListComponent
  public remainingDays: Array<number>;
  public showRenewal: Array<boolean>;
  public webLinks: Array<string>;
  
  user_role;

  constructor(
    private router: Router,
    private actRoute: ActivatedRoute,
    private dialog: MatDialog
  ) {
  }
  openAlertDialog(title: string, message: string): void {
    this.dialog.open(AlertDialogComponent, {
      width: '300px',
      data: { title, message },
    });
  }

  showAlert(): void {
    this.openAlertDialog('Aviso', 'Esto caduca');
  }
  calculateDays() {
    this.showRenewal = this.list.dataArray.map(sub => !sub.subs_autorenewal);
    console.log(this.showRenewal);
    this.webLinks = this.list.dataArray.map(sub => sub.platf_link);
    console.log(this.webLinks);
    const endDates = this.list.dataArray.map(sub => sub.SUB_LAPSE_END);

    this.remainingDays = endDates.map(date => {

      const datediff = new Date(date).getTime() - new Date().getTime()
      const remainingDays = Math.ceil(datediff / (1000 * 60 * 60 * 24))
      return remainingDays
    })

  }

  ngOnInit() {
  
  }

  onDataLoaded(event){
    console.log(event);
    if (event && event.length > 0 && event[0].ROLENAME) {
    
    this.user_role=event[0].ROLENAME;
    
    if(this.user_role === 'admin'){
      
      this.router.navigate(['../../','plans'], {relativeTo: this.actRoute})
    } else{
      this.router.navigate(['../../','user-home'],{relativeTo:this.actRoute})
    }
  } else {
    console.error('Invalid data received:', event);
  }
  }

  navigate() {
    this.router.navigate(['../', 'login'], { relativeTo: this.actRoute });
  }

  goToDoc(link: string) {
    window.open(link, "_blank");
  }

}
