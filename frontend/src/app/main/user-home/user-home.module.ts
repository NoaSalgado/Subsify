import { NgModule, LOCALE_ID } from "@angular/core";
import es from '@angular/common/locales/es';
import { OntimizeWebModule } from "ontimize-web-ngx";
import { SharedModule } from "../../shared/shared.module";

import { registerLocaleData } from '@angular/common';
import { UserHomeComponent } from "./user-home.component";
import { UserHomeRoutingModule } from "./user-home-routing.module";

registerLocaleData(es);
@NgModule({
  imports: [SharedModule, OntimizeWebModule, UserHomeRoutingModule],
  declarations: [UserHomeComponent],
  providers: [{
    provide: LOCALE_ID,
    useValue: 'es-ES' // 'de-DE' for Germany, 'fr-FR' for France ...
  },
]
})
export class UserHomeModule { }
