import { Component, OnInit } from '@angular/core';
import { SocialLoginBaseComponent } from '../social-login-base/social-login-base.component';
import { FacebookLoginProvider, GoogleLoginProvider } from 'angularx-social-login';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: [
    './login.component.scss',
    '../../../../node_modules/font-awesome/css/font-awesome.css'
  ]
})

export class LoginComponent extends SocialLoginBaseComponent implements OnInit {

  userLogin;
  senhaLogin;

  signInWithGoogle(): void {
    this.authService.signIn(FacebookLoginProvider.PROVIDER_ID);
    const userJson = {
      tipConta: 'G',
      email: this.user.email
    };
    this.loginService.logar(userJson).subscribe();
  }

  signInWithFB(): void {
    this.authService.signIn(FacebookLoginProvider.PROVIDER_ID);
    const userJson = {
      tipConta: 'F',
      email: this.user.email
    };
    this.loginService.logar(userJson).subscribe();
  }

  signInWithPachanga(email, senha): void {
    const userJson = {
      tipConta: 'P',
      email,
      senha
    };
    this.loginService.logar(userJson).subscribe();
  }

}
