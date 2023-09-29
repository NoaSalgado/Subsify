import { Component, OnInit } from "@angular/core";
@Component({
  selector: "app-subscriptions-home",
  templateUrl: "./subscriptions-home.component.html",
  styleUrls: ["./subscriptions-home.component.css"],
})
export class SubscriptionsHomeComponent implements OnInit {
  public renovationDate = this.calculateRenovationDate;
  calculateRenovationDate(rowData: Array<any>): Date {
    let aux: Date = new Date(rowData["SUBS_START_DATE"]);
    let frecuencia: number = rowData["SUBS_FREQUENCY"];
    aux.setMonth(aux.getMonth() + frecuencia);
    /*switch(frecuencia){
      case 1:
      aux.setDate(aux.getMonth() + 1)
            break;
      case 2:
      aux.setDate(aux.getMonth() + 3)
            break;
      case 3:
      aux.setDate(aux.getMonth() + 12)
            break;
      default: 1;
    }*/
    return aux;
  }
  constructor() {}
  ngOnInit() {}
}
