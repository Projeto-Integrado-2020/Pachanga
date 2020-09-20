import { Component, OnInit } from '@angular/core';
import { interval, Observable, Subscription } from 'rxjs';
import { environment } from '../environments/environment';
import { AuthTokenService } from './services/auth-token/auth-token.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})

export class AppComponent implements OnInit {
  title = 'Pachanga';
  source: any;
  subscription: Subscription;

  constructor(public authService: AuthTokenService) {

  }

  ngOnInit() {
    if (environment.production) {
      if (location.protocol === 'http:') {
        window.location.href = location.href.replace('http', 'https');
      }
    }
    this.authService.getOAuth().subscribe((resp: any) => {
      this.authService.setToken(resp);
      this.updateToken();
    });
  }

  updateToken() {
    this.source = interval((new Date(this.authService.token.timeToken).getTime() - new Date().getTime()) + 100);
    this.subscription = this.source.subscribe(
      () => {
        this.authService.getOAuth().subscribe((resp: any) => {
          this.authService.setToken(resp);
          this.destroySource();
        });
      }
    );
  }

  destroySource() {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
    this.source = null;
    this.updateToken();
  }
}
