import { Component, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { MatDialog } from '@angular/material';
import { LoginComponent } from '../login/login.component';
import { CadastroComponent } from '../cadastro/cadastro.component';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {

  constructor(public translate: TranslateService, public login: MatDialog, public cadastro: MatDialog) {
    translate.addLangs(['pt', 'en']);
    translate.setDefaultLang('pt');
    const browserLang = translate.getBrowserLang();
    translate.use(browserLang.match(/pt|en/) ? browserLang : 'pt');
  }

  langPt(){
    this.translate.use('pt');
  }

  langEn(){
    this.translate.use('en');
  }

// método para abrir modal de login
  openDialogLogin(){
    this.login.open(LoginComponent, {
      width: '20rem',
    });
  }
// método para abrir modal de cadastro
  openDialogCadastro(){
    this.cadastro.open(CadastroComponent, {
      width: '20rem'
    });
  }

  ngOnInit() {
  }

}