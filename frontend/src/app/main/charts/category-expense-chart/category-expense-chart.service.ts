import { Injectable } from "@angular/core";

@Injectable({
  providedIn: "root",
})
export class CategoryExpenseChartService {
  private isDataFiltered: boolean = false;
  constructor() {}

  get dataFiltered() {
    return this.isDataFiltered;
  }

  updateDataFiltered(value: boolean) {
    this.isDataFiltered = value;
  }
}
