import { Component, OnInit, Renderer2, ViewChild, ElementRef } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { MatDialog} from '@angular/material';
import { LoginComponent } from '../login/login.component';
import { CadastroComponent } from '../cadastro/cadastro.component';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Observable, interval, Subscription } from 'rxjs';
import { map, shareReplay } from 'rxjs/operators';
import { LoginService } from 'src/app/services/loginService/login.service';
import { NotificacoesService } from 'src/app/services/notificacoes-service/notificacoes.service';
import { FestaDetalhesDialogComponent } from '../festa-detalhes-dialog/festa-detalhes-dialog.component';

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

  // variáveis para sistema de alerta
  alertNumbers: number;
  visibilidadeAlerta: boolean;
  visibilidadeNotificacoes: boolean;

  alertaOpcoes: boolean;
  selected: number;
  alertIds: number[] = [];

  notificacoesUsuario: any[] = [];
  notificacoesGrupo: any[] = [];
  notificacaoConvidado: any[] = [];

  convmsg: any;

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
    public notifService: NotificacoesService
    ) {
    translate.addLangs(['pt', 'en']);
    translate.setDefaultLang('pt');
    const browserLang = translate.getBrowserLang();
    translate.use(browserLang.match(/pt|en/) ? browserLang : 'pt');
  }

  puxarNovosAlertas() {
    const source = interval(10000);
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
        this.notificacoesUsuario = response.notificacoesUsuario;
        this.notificacoesGrupo = response.notificacoesGrupo;
        this.notificacaoConvidado = response.notificacaoConvidado;
      }
    );

    //this.convites = alerts.filter(alert => )
  }




  contarAlertasNaoLidos(): void {
    let count = 0;
    for (const i of this.notificacoesUsuario) {
      if (i.status === 'N' || i.destaque) {
        count++;
      }
    }
    this.alertNumbers = count + this.notificacoesGrupo.length + this.notificacaoConvidado.length;
  }

  alterarAlerta(alerta): void {
    alerta.destaque ? this.alertNumbers++ : this.alertNumbers--;
    this.notifService.destacarNotificacao(alerta.notificacao);
    alerta.destaque = !alerta.destaque;
    alerta.alertaOpcoes = false;
  }

  deletarAlerta(alerta, arrayName): void {
    const index = arrayName.indexOf(alerta);
    arrayName.splice(index, 1);
    this.notifService.deletarNotificacao(alerta.notificacao).subscribe();
    // CHAMAR METODO DELETAR ALERTA DO NOTIFICACAO-SERVICE!
  }

  // abrir janela de notificações

  fecharNotificacoes(): void {
    this.visibilidadeNotificacoes = false;
    this.notificacoesUsuario.forEach(alert => {
      alert.status = 'L';
      this.alertIds.push(alert.notificacao);
    });
    this.contarAlertasNaoLidos();
    this.notifService.atualizarNotificacoes(this.alertIds).subscribe();
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

  openDialogConvite(alerta): void {

      this.dialog.open(FestaDetalhesDialogComponent, {
      width: '20rem',
      data: {
        mensagem: alerta.mensagem
      }
    });
  }

  ngOnInit() {
    if(this.loginService.usuarioAutenticado){
      this.carregarArray(this.notifService.getNotificacoes());
      this.contarAlertasNaoLidos();
      this.visibilidadeAlerta = this.notificacoesUsuario.length +
                              this.notificacoesGrupo.length +
                              this.notificacaoConvidado.length === 0;
      this.puxarNovosAlertas();
    }
  }

}
