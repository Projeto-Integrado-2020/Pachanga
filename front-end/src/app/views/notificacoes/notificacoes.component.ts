import { Component, OnInit } from '@angular/core';
import { MatDialog} from '@angular/material';
import { LoginComponent } from '../login/login.component';
import { Observable, interval, Subscription } from 'rxjs';
import { LoginService } from 'src/app/services/loginService/login.service';
import { NotificacoesService } from 'src/app/services/notificacoes-service/notificacoes.service';
import { FestaDetalhesDialogComponent } from '../festa-detalhes-dialog/festa-detalhes-dialog.component';
import { AceitoMembroDetalhesService } from 'src/app/services/aceito-membro-detalhes/aceito-membro-detalhes.service';
import { EstoqueMinDetalhesService } from 'src/app/services/estoque-min-detalhes/estoque-min-detalhes.service';

@Component({
  selector: 'app-notificacoes',
  templateUrl: './notificacoes.component.html',
  styleUrls: ['./notificacoes.component.scss'],
  providers: [LoginComponent]
})
export class NotificacoesComponent implements OnInit {

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

  constructor(public dialog: MatDialog, public loginComponent: LoginComponent,
              public loginService: LoginService, public notifService: NotificacoesService,
              public accDetail: AceitoMembroDetalhesService, public estoqueMinDetail: EstoqueMinDetalhesService) {

  }

  ngOnInit() {
    if (this.loginService.usuarioAutenticado) {
      this.carregarArray(this.notifService.getNotificacoes());
      this.contarAlertasNaoLidos();
      this.visibilidadeAlerta = this.notificacoesUsuario.length +
                              this.notificacoesGrupo.length +
                              this.notificacaoConvidado.length === 0;
      this.puxarNovosAlertas();
    }
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

  puxarNovosAlertas() {
    if (this.loginService.usuarioAutenticado) {
      const source = interval(5000);
      source.subscribe(
        () => {
          this.carregarArray(this.notifService.getNotificacoes());
          this.contarAlertasNaoLidos();
        }
      );
    }
  }

  carregarArray(observavel: Observable<object>) {
    return observavel.subscribe(
      (response: any) => {
        if (!this.visibilidadeNotificacoes) {
          this.notificacoesUsuario = response.notificacoesUsuario;
          this.notificacoesGrupo = response.notificacoesGrupo;
          this.notificacaoConvidado = response.notificacaoConvidado;
        }
      }
    );
  }

  openDialogConvite(alerta): void {
    this.dialog.open(FestaDetalhesDialogComponent, {
      width: '23rem',
      data: {
          mensagem: alerta.mensagem
      }
    });
  }
}
