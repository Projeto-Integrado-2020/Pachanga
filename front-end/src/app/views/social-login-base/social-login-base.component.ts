import { Component, OnInit } from '@angular/core';
import { AuthService } from 'angularx-social-login';
import { FacebookLoginProvider, GoogleLoginProvider } from 'angularx-social-login';
import { SocialUser } from 'angularx-social-login';

@Component({
  selector: 'app-social-login-base',
  templateUrl: './social-login-base.component.html',
  styleUrls: ['./social-login-base.component.scss']
})
export class SocialLoginBaseComponent implements OnInit {

  private user: SocialUser;
  private loggedIn: boolean;

  constructor(private authService: AuthService) { }

  signInWithGoogle(): void {
    this.authService.signIn(GoogleLoginProvider.PROVIDER_ID);
  }

  signInWithFB(): void {
    this.authService.signIn(FacebookLoginProvider.PROVIDER_ID);
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
