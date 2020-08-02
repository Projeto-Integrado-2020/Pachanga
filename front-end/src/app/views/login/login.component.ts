import { Component, OnInit } from '@angular/core';
import { SocialLoginBaseComponent } from '../social-login-base/social-login-base.component';
import { FacebookLoginProvider, GoogleLoginProvider } from 'angular4-social-login';

import { FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: [
    './login.component.scss',
    '../../../../node_modules/font-awesome/css/font-awesome.css'
  ]
})

export class LoginComponent extends SocialLoginBaseComponent implements OnInit {

  userLogin: any;
  senhaLogin: any;

  ngOnInit() {
    this.form = this.formBuilder.group({
      email: new FormControl('', [Validators.required, Validators.email]),
      senha: new FormControl('', Validators.required)
    });
  }

  signInWithGoogle(): void {
    this.authService.signIn(GoogleLoginProvider.PROVIDER_ID).then((user) => {
      this.user = user;
      const userJson = {
        tipConta: 'G',
        email: this.user.email,
        nomeUser: this.user.name,
        conta: this.user.id
      };
      this.autenticar(userJson);
    });
  }

  signInWithFB(): void {
    this.authService.signIn(FacebookLoginProvider.PROVIDER_ID).then((user) => {
      this.user = user;
      const userJson = {
        tipConta: 'F',
        email: this.user.email,
        nomeUser: this.user.name,
        conta: this.user.id
      };
      this.autenticar(userJson);
    });
  }

  signInWithPachanga(email, senha): void {
    const userJson = {
      tipConta: 'P',
      email,
      senha
    };
    this.autenticar(userJson);
  }

}
