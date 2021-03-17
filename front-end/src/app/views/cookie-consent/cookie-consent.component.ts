import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material';
import { TermosUsoDialogComponent } from '../termos-uso-dialog/termos-uso-dialog.component';

@Component({
  selector: 'app-cookie-consent',
  templateUrl: './cookie-consent.component.html',
  styleUrls: ['./cookie-consent.component.scss']
})
export class CookieConsentComponent implements OnInit {

  cookiesConsent = (localStorage.getItem('Cookie') === 'OK');

  constructor(public modal: MatDialog) { }

  ngOnInit() {
  }

  confirmarCookies() {
    localStorage.setItem('Cookie', 'OK');
    this.cookiesConsent = true;
  }

  openTermosUso() {
    this.modal.open(TermosUsoDialogComponent, {
      width: '35rem'
    });
  }

}
