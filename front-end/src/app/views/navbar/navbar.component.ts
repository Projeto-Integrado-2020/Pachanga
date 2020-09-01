import { Component, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { MatDialog} from '@angular/material';
import { LoginComponent } from '../login/login.component';
import { CadastroComponent } from '../cadastro/cadastro.component';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Observable, interval, Subscription } from 'rxjs';
import { map, shareReplay } from 'rxjs/operators';
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

  isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
    .pipe(
      map(result => result.matches),
      shareReplay()
    );

  constructor(
    public translate: TranslateService,
    public dialog: MatDialog,
    private breakpointObserver: BreakpointObserver,
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

  switchIdioma(): void {
    if (this.translate.currentLang === 'pt') {
      this.translate.use('en');
    } else {
      this.translate.use('pt');
    }
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
