import { Component, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { MatDialog } from '@angular/material';
import { LoginComponent } from '../login/login.component';
import { CadastroComponent } from '../cadastro/cadastro.component';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Observable } from 'rxjs';
import { map, shareReplay } from 'rxjs/operators';
import { LoginService } from 'src/app/services/loginService/login.service';
import { InviteDialogComponent } from '../invite-dialog/invite-dialog.component';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
  providers: [LoginComponent],
})
export class NavbarComponent implements OnInit {

  isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
    .pipe(
      map(result => result.matches),
      shareReplay()
    );

  constructor(public translate: TranslateService, public login: MatDialog,
              public cadastro: MatDialog, public invite: MatDialog, private breakpointObserver: BreakpointObserver,
              public loginComponent: LoginComponent, public loginService: LoginService) {
    translate.addLangs(['pt', 'en']);
    translate.setDefaultLang('pt');
    const browserLang = translate.getBrowserLang();
    translate.use(browserLang.match(/pt|en/) ? browserLang : 'pt');
  }

  langPt() {
    this.translate.use('pt');
  }

  langEn() {
    this.translate.use('en');
  }

// método para abrir modal de login
  openDialogLogin() {
    this.login.open(LoginComponent, {
      width: '20rem',
    });
  }
// método para abrir modal de cadastro
  openDialogCadastro() {
    this.cadastro.open(CadastroComponent, {
      width: '20rem'
    });
  }

  openDialogPROVISORIO() {
    this.invite.open(InviteDialogComponent, {
      width: '20rem',
    });
  }

  ngOnInit() {
  }

}
