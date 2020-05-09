import { Component, OnInit } from '@angular/core';
import { AuthService } from 'angularx-social-login';
import { SocialUser } from 'angularx-social-login';
import { LoginService } from 'src/app/services/loginService/login.service';
import { FormGroup, FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-social-login-base',
  templateUrl: './social-login-base.component.html',
  styleUrls: ['./social-login-base.component.scss']
})
export class SocialLoginBaseComponent implements OnInit {

  public user: SocialUser;

  public form: FormGroup;

  constructor(public authService: AuthService, public loginService: LoginService, public formBuilder: FormBuilder) { }

  get f() { return this.form.controls; }

  signOut(): void {
    this.authService.signOut();
  }

  ngOnInit() {

  }

}
