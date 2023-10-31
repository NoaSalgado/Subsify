import { NgModule, LOCALE_ID } from "@angular/core";
import es from '@angular/common/locales/es';
import { OntimizeWebModule } from "ontimize-web-ngx";
import { SharedModule } from "../../shared/shared.module";
import { HomeRoutingModule } from "./home-routing.module";
import { HomeComponent } from "./home.component";
import { registerLocaleData } from '@angular/common';

registerLocaleData(es);
@NgModule({
  imports: [SharedModule, OntimizeWebModule, HomeRoutingModule],
  declarations: [HomeComponent],
  providers: [{
    provide: LOCALE_ID,
    useValue: 'es-ES' // 'de-DE' for Germany, 'fr-FR' for France ...
  },
]
})
export class HomeModule {}
