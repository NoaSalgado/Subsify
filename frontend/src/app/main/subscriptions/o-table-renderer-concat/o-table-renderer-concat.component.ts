import { Component, Injector, TemplateRef, ViewChild } from "@angular/core";
import { OBaseTableCellRenderer } from "ontimize-web-ngx";

@Component({
  selector: "o-table-renderer-concat",
  templateUrl: "./o-table-renderer-concat.component.html",
  styleUrls: ["./o-table-renderer-concat.component.css"],
})
export class OTableRendererConcatComponent extends OBaseTableCellRenderer {
  @ViewChild("templateref", { read: TemplateRef, static: false })
  public templateref: TemplateRef<any>;

  constructor(protected injector: Injector) {
    super(injector);
  }

  getCellData(cellvalue: any, rowvalue: Object) {
    return rowvalue["USER_"] + " " + rowvalue["USER_SUB_VIRTUAL"];
  }
}
