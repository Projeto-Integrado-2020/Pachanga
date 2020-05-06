import { Component, OnInit } from '@angular/core';
import { AuthService } from 'angularx-social-login';
import { FacebookLoginProvider, GoogleLoginProvider } from 'angularx-social-login';
import { SocialUser } from 'angularx-social-login';
import { LoginService } from 'src/app/services/loginService/login.service';

@Component({
  selector: 'app-social-login-base',
  templateUrl: './social-login-base.component.html',
  styleUrls: ['./social-login-base.component.scss']
})
export class SocialLoginBaseComponent implements OnInit {

  public user: SocialUser;
  public loggedIn: boolean;

  constructor(public authService: AuthService, public loginService: LoginService) { }

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

  signOut(): void {
    this.authService.signOut();
  }

  ngOnInit() {
    this.authService.authState.subscribe((user) => {
      this.user = user;
      console.log(this.user);
      this.loggedIn = (user != null);
    });
  }

}
