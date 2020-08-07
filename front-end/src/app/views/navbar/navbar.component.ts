import { Component, OnInit, Renderer2, ViewChild, ElementRef } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { MatDialog } from '@angular/material';
import { LoginComponent } from '../login/login.component';
import { CadastroComponent } from '../cadastro/cadastro.component';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Observable, interval, Subscription } from 'rxjs';
import { map, shareReplay } from 'rxjs/operators';
import { LoginService } from 'src/app/services/loginService/login.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
  providers: [LoginComponent],
})
export class NavbarComponent implements OnInit {

  // variáveis para sistema de alerta
  alertNumbers: number;
  visibilidadeAlerta: boolean;
  visibilidadeNotificacoes: boolean;

  alertaOpcoes: boolean;
  selected: number;

  alerts = [
    {
      texto: 'Alô alô alô',
      tempo: '1 minuto atrás',
      alertaOpcoes: false,
      lido: false,
      naolido: false
    },
    {
      texto: 'Salci fu fu',
      tempo: '3 minutos atrás',
      alertaOpcoes: false,
      lido: false,
      naolido: false
    },
    {
      texto: 'Iê iê iê ',
      tempo: '5 minutos atrás',
      alertaOpcoes: false,
      lido: false,
      naolido: false
    },
    {
      texto: 'Iê iê iê ',
      tempo: '5 minutos atrás',
      alertaOpcoes: false,
      lido: false,
      naolido: false
    },
    {
      texto: 'Iê iê iê ',
      tempo: '5 minutos atrás',
      alertaOpcoes: false,
      lido: false,
      naolido: false
    },
    {
      texto: 'Iê iê iê ',
      tempo: '5 minutos atrás',
      alertaOpcoes: false,
      lido: false,
      naolido: false
    },
    {
      texto: 'Iê iê iê ',
      tempo: '5 minutos atrás',
      alertaOpcoes: false,
      lido: false,
      naolido: false
    },
    {
      texto: 'Iê iê iê ',
      tempo: '5 minutos atrás',
      alertaOpcoes: false,
      lido: false,
      naolido: false
    }
  ];

  isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
    .pipe(
      map(result => result.matches),
      shareReplay()
    );

  constructor(
    public translate: TranslateService,
    public login: MatDialog,
    public cadastro: MatDialog,
    public invite: MatDialog,
    private breakpointObserver: BreakpointObserver,
    public loginComponent: LoginComponent,
    public loginService: LoginService
    ) {
    translate.addLangs(['pt', 'en']);
    translate.setDefaultLang('pt');
    const browserLang = translate.getBrowserLang();
    translate.use(browserLang.match(/pt|en/) ? browserLang : 'pt');

  }
  //MOCK DE CRIACAO DE ALERTA(APAGAR DEPOIS)

  criarAlertaPROVISORIO() {
    const xingamentos = [
      'Vai troxa',
      'Seu merda',
      'Vai à merda',
      'É isso msm otário',
      'F***-se',
      'Chupa aqui'
    ]
    const xingamento = xingamentos[Math.floor(Math.random() * xingamentos.length)];
    
    this.alerts.unshift(
      {
        texto: xingamento,
        tempo: 'Agora mesmo',
        alertaOpcoes: false,
        lido: false,
        naolido:false
      }
    )
  }
  // usar esta função para puxar novos alertas; Rodar de 5 em 5 segundos


  puxarNovosAlertas() {
    const source = interval(5000);
    source
      .subscribe(val => {
        //this.criarAlertaPROVISORIO()
        this.contarAlertasNaoLidos()
    });
   }

  contarAlertasNaoLidos(): void {
    let count = 0;
    for(const i of this.alerts) {
      if(!i.lido || i.naolido) {
        count++;
      }
    }
    this.alertNumbers = count;
  }

  alterarAlerta(alerta): void {
    alerta.naolido ? this.alertNumbers++ : this.alertNumbers--;
    alerta.naolido = !alerta.naolido;
    alerta.alertaOpcoes = false;
  }

  // abrir janela de notificações

  fecharNotificacoes(): void {
    this.visibilidadeNotificacoes = false;
    this.alerts.forEach(alert => {
      alert.lido = true;
    });
    this.contarAlertasNaoLidos();
  }

  abrirAlertaOpcoes(alert): void {
    alert.alertaOpcoes = true;
  }

  // traduções
  langPt(): void {
    this.translate.use('pt');
  }

  langEn(): void {
    this.translate.use('en');
  }

 // método para abrir modal de login
  openDialogLogin(): void{
    this.login.open(LoginComponent, {
      width: '20rem',
    });
  }
 // método para abrir modal de cadastro
  openDialogCadastro(): void{
    this.cadastro.open(CadastroComponent, {
      width: '20rem'
    });
  }

  ngOnInit() {
    this.contarAlertasNaoLidos();
    this.visibilidadeAlerta = this.alerts.length === 0;
    this.puxarNovosAlertas();
  }

}
