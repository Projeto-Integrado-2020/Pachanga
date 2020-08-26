import { Component, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { MatDialog} from '@angular/material';
import { LoginComponent } from '../login/login.component';
import { CadastroComponent } from '../cadastro/cadastro.component';
import { LoginService } from 'src/app/services/loginService/login.service';

export interface ConviteData {
  mensagem: string;
}
@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
  providers: [LoginComponent],
})

export class NavbarComponent implements OnInit {

  constructor(
    public translate: TranslateService,
    public dialog: MatDialog,
    public loginComponent: LoginComponent,
    public loginService: LoginService,
    ) {
    translate.addLangs(['pt', 'en']);
    translate.setDefaultLang('pt');
    const browserLang = translate.getBrowserLang();
    translate.use(browserLang.match(/pt|en/) ? browserLang : 'pt');
  }

  // traduções
  langPt(): void {
    this.translate.use('pt');
  }

  langEn(): void {
    this.translate.use('en');
  }

 // método para abrir modal de login
  openDialogLogin(): void {
    this.dialog.open(LoginComponent, {
      width: '20rem',
    });
  }
 // método para abrir modal de cadastro
  openDialogCadastro(): void {
    this.dialog.open(CadastroComponent, {
      width: '20rem'
    });
  }

  ngOnInit() {
  }

}
