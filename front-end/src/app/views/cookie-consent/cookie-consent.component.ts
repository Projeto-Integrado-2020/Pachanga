import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-cookie-consent',
  templateUrl: './cookie-consent.component.html',
  styleUrls: ['./cookie-consent.component.scss']
})
export class CookieConsentComponent implements OnInit {

  cookiesConsent = (localStorage.getItem('Cookie') === 'OK');

  constructor() { }

  ngOnInit() {
  }

  confirmarCookies() {
    localStorage.setItem('Cookie', 'OK');
    this.cookiesConsent = true;
  }

}
