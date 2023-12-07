import {
  Component,
  Inject,
  Injector,
  OnInit,
  ViewChild,
  ViewEncapsulation,
} from "@angular/core";
import {
  AuthService,
  LocalStorageService,
  NavigationService,
  OFormComponent,
  OntimizeService,
} from "ontimize-web-ngx";

@Component({
  selector: "sign-up",
  styleUrls: ["./sign-up.component.scss"],
  templateUrl: "./sign-up.component.html",
  encapsulation: ViewEncapsulation.None,
})
export class SignUpComponent implements OnInit {
  protected userService: OntimizeService;
  @ViewChild("signUpForm", { static: false })
  signUpForm: OFormComponent;

  constructor(
    public injector: Injector,
    @Inject(AuthService) private authService: AuthService
  ) {
    this.userService = this.injector.get(OntimizeService);
  }

  ngOnInit() {
    this.configureUserService();
    this.signUpForm.setInsertMode();
  }

  public configureUserService() {
    const conf = this.userService.getDefaultServiceConfiguration("users");
    this.userService.configureService(conf);
  }

  public async signUpUser() {
    this.signUpForm.insert();
  }
}
