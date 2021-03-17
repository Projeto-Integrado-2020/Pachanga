import { Component, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { MatDialog} from '@angular/material';
import { LoginComponent } from '../login/login.component';
import { CadastroComponent } from '../cadastro/cadastro.component';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Observable, interval, Subscription } from 'rxjs';
import { map, shareReplay } from 'rxjs/operators';
import { LoginService } from 'src/app/services/loginService/login.service';
import { PerfilDialogComponent } from '../perfil-dialog/perfil-dialog.component';

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

    INGLES = 'EN-US';
    PORTUGUES = 'PT-BR';

  selectedLang = 'pt';
  selected = this.PORTUGUES;
  linguas: any = [
    {
      classe: 'btn btn--lang btn--lang--pt',
      lingua: 'PT-BR'
    },
    {
      classe: 'btn btn--lang btn--lang--en',
      lingua: 'EN-US'
    }
  ];

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

  selectLang(event: any) {
    const lingua = event.value === this.INGLES ? 'en' : 'pt';
    this.translate.use(lingua);
    this.selectedLang = event.value;
  }

 // método para abrir modal de login
  openDialogLogin(): void {
    this.dialog.open(LoginComponent, {
      width: '20rem',
    });
  }
// acessar infoemações de perfil
  openDialogPerfil(): void {
    this.dialog.open(PerfilDialogComponent, {
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
