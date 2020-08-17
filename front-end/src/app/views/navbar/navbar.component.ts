import { Component, OnInit, Renderer2, ViewChild, ElementRef } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { MatDialog } from '@angular/material';
import { LoginComponent } from '../login/login.component';
import { CadastroComponent } from '../cadastro/cadastro.component';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Observable, interval, Subscription } from 'rxjs';
import { map, shareReplay } from 'rxjs/operators';
import { LoginService } from 'src/app/services/loginService/login.service';
import { NotificacoesService } from 'src/app/services/notificacoes-service/notificacoes.service';

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
  alertIds: number[] = [];
  alerts: any[] = [];
  notificacoesUsuario: any[] = [];
  notificacoesGrupo: any[] = [];
  notificacaoConvidado: any[] = [];

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
    public loginService: LoginService,
    public notifService: NotificacoesService
    ) {
    translate.addLangs(['pt', 'en']);
    translate.setDefaultLang('pt');
    const browserLang = translate.getBrowserLang();
    translate.use(browserLang.match(/pt|en/) ? browserLang : 'pt');
    this.puxarNovosAlertas();

  }

  puxarNovosAlertas() {
    const source = interval(5000);
    source.subscribe(
      () => {
        this.carregarArray(this.notifService.getNotificacoes());
        this.contarAlertasNaoLidos();
      }
    );
  }

  carregarArray(observavel: Observable<object>) {
    return observavel.subscribe(
      (response: any) => {
        for (const notif of response.notificacoesUsuario) {
          if ( this.alerts.filter( e => e.notificacao === notif.notificacao).length === 0) {
            this.alerts.push(Object.assign(notif, {tipoUser: true, tempo: 'agora mesmo', alertaOpcoes: false}));
          }
        }
        for (const notif of response.notificacoesGrupo) {
        if ( this.alerts.filter( e => e.notificacao === notif.notificacao).length === 0) {
          this.alerts.push(Object.assign(notif, {tipo: false, tempo: 'agora mesmo'}));
          }
        }
      }
    );
  }




  contarAlertasNaoLidos(): void {
    let count = 0;
    for (const i of this.alerts) {
      if (i.status === 'N' || i.destaque) {
        count++;
      }
    }
    this.alertNumbers = count;
  }

  alterarAlerta(alerta): void {
    alerta.destaque ? this.alertNumbers++ : this.alertNumbers--;
    this.notifService.destacarNotificacao(alerta.notificacao);
    alerta.destaque = !alerta.destaque;
    alerta.alertaOpcoes = false;
  }

  deletarAlerta(alerta): void {
    const index = this.alerts.indexOf(alerta);
    this.alerts.splice(index, 1);
    this.notifService.deletarNotificacao(alerta.notificacao).subscribe();
    // CHAMAR METODO DELETAR ALERTA DO NOTIFICACAO-SERVICE!
  }

  // abrir janela de notificações

  fecharNotificacoes(): void {
    this.visibilidadeNotificacoes = false;
    this.alerts.forEach(alert => {
      alert.status = 'L';
      this.alertIds.push(alert.notificacao);
    });
    this.contarAlertasNaoLidos();
    this.notifService.atualizarNotificacoes(this.alertIds);
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
  openDialogLogin(): void {
    this.login.open(LoginComponent, {
      width: '20rem',
    });
  }
 // método para abrir modal de cadastro
  openDialogCadastro(): void {
    this.cadastro.open(CadastroComponent, {
      width: '20rem'
    });
  }

  ngOnInit() {
    this.contarAlertasNaoLidos();
    this.visibilidadeAlerta = this.alerts.length === 0;
  }

}
