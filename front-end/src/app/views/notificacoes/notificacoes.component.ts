import { Component, OnInit } from '@angular/core';
import { MatDialog} from '@angular/material';
import { Observable, interval } from 'rxjs';
import { LoginService } from 'src/app/services/loginService/login.service';
import { NotificacoesService } from 'src/app/services/notificacoes-service/notificacoes.service';
import { FestaDetalhesDialogComponent } from '../festa-detalhes-dialog/festa-detalhes-dialog.component';
import { AceitoMembroDetalhesService } from 'src/app/services/aceito-membro-detalhes/aceito-membro-detalhes.service';
import { EstoqueMinDetalhesService } from 'src/app/services/estoque-min-detalhes/estoque-min-detalhes.service';
import { AlertaEstoqueComponent } from '../alerta-estoque/alerta-estoque.component';
import { TranslateService } from '@ngx-translate/core';
import { AlertaSegurancaComponent } from '../alerta-seguranca/alerta-seguranca.component';

@Component({
  selector: 'app-notificacoes',
  templateUrl: './notificacoes.component.html',
  styleUrls: ['./notificacoes.component.scss']
})
export class NotificacoesComponent implements OnInit {

  // variáveis para sistema de alerta
  alertNumbers: number;
  visibilidadeAlerta: boolean;
  visibilidadeNotificacoes: boolean;

  alertaOpcoes: boolean;
  selected: number;
  alertIds: number[] = [];
  notificacoes: any[] = [];

  convmsg: any;

  constructor(public dialog: MatDialog,
              public loginService: LoginService, public notifService: NotificacoesService,
              public accDetail: AceitoMembroDetalhesService, public estoqueMinDetail: EstoqueMinDetalhesService,
              public translate: TranslateService) {

  }

  ngOnInit() {
    this.puxarNovosAlertas();
  }

  contarAlertasNaoLidos(): void {
    let count = 0;
    for (const i of this.notificacoes) {
      // if (!i.mensagem.includes('ESTBAIXO') && i.status === 'N' || i.destaque) {
      if (i.status === 'N' || i.destaque || i.codConvidado || i.grupo) {
        count++;
      }
    }
    this.alertNumbers = count;
  }

  alterarAlerta(alerta): void {
    alerta.destaque ? this.alertNumbers++ : this.alertNumbers--;
    this.notifService.destacarNotificacao(alerta.notificacao).subscribe();
    alerta.destaque = !alerta.destaque;
    alerta.alertaOpcoes = false;
  }

  deletarAlerta(alerta): void {
    const index = this.notificacoes.indexOf(alerta);
    this.notificacoes.splice(index, 1);
    this.notifService.deletarNotificacao(alerta).subscribe();
    // CHAMAR METODO DELETAR ALERTA DO NOTIFICACAO-SERVICE!
  }

  // abrir janela de notificações

  fecharNotificacoes(): void {
    this.visibilidadeNotificacoes = false;
    this.notificacoes.forEach(alert => {
      if (alert.status) {
        alert.status = 'L';
        this.alertIds.push(alert.notificacao);
      }
    });
    this.contarAlertasNaoLidos();
    this.notifService.atualizarNotificacoes(this.alertIds).subscribe();
  }

  abrirAlertaOpcoes(alert): void {
    alert.alertaOpcoes = true;
  }

  puxarNovosAlertas() {
    if (this.loginService.usuarioAutenticado) {
      this.carregarArray(this.notifService.getNotificacoes());
      this.contarAlertasNaoLidos();
      this.visibilidadeAlerta = this.notificacoes.length === 0;
    }
    const source = interval(5000);
    source.subscribe(
      () => {
        if (this.loginService.usuarioAutenticado) {
          this.carregarArray(this.notifService.getNotificacoes());
          this.contarAlertasNaoLidos();
        } else {
          this.notificacoes = [];
        }
      }
    );
  }

  carregarArray(observavel: Observable<object>) {
    return observavel.subscribe(
      (response: any) => {
        if (!this.visibilidadeNotificacoes) {
          this.notificacoes = this.procurarAlertas(response.notificacoesUsuario)
                              .concat(response.notificacoesGrupo)
                              .concat(response.notificacaoConvidado)
                              .sort(this.dataEmissaoSort);
        }
      }
    );
  }

  dataEmissaoSort(a, b) {
    if (new Date(a.dataEmissao) > new Date(b.dataEmissao)) {
      return -1;
    } else {
      return 1;
    }
  }




  procurarAlertas(resp) {
    const notificacoes = [];
    const alertas = [];
    for (const notificacao of resp) {
      if (notificacao.mensagem.includes('ESTBAIXO') && notificacao.status === 'N') {
        this.openDialogEstoqueAlert(notificacao);
        alertas.push(notificacao.notificacao);
      } else if (notificacao.mensagem.includes('AREAPROB') && notificacao.status === 'N') {
        this.openDialogSegurancaAlert(notificacao);
        alertas.push(notificacao.notificacao);
      } else {
        notificacoes.push(notificacao);
      }
    }
    this.notifService.atualizarNotificacoes(alertas).subscribe();
    return notificacoes;
  }

  createUrl(nomeFesta, codFesta) {
    nomeFesta = nomeFesta.toLowerCase().replace('-', '').replace('–', '')
                        .replace(/\s+/g, '-').replace('ç', 'c')
                        .replace('º', '').replace('ª', '')
                        .normalize('NFD').replace(/[\u0300-\u036f]/g, '')
                        .replace(/[`~!@#$%^&*()_|+\=?;:'",.<>\{\}\[\]\\\/]/gi, '');
    const url = '../festas/' + nomeFesta + '&' + codFesta;
    return url;
  }

  createUrlEstoque(nomeFesta, mensagem) {
    const codFesta = mensagem.split('&')[0].split('?')[1];
    nomeFesta = nomeFesta.toLowerCase().replace('-', '').replace('–', '')
                        .replace(/\s+/g, '-').replace('ç', 'c')
                        .replace('º', '').replace('ª', '')
                        .normalize('NFD').replace(/[\u0300-\u036f]/g, '')
                        .replace(/[`~!@#$%^&*()_|+\=?;:'",.<>\{\}\[\]\\\/]/gi, '');
    const url = '../festas/' + nomeFesta + '&' + codFesta;
    return url;
  }

  createUrlSeguranca(nomeFesta, codFesta) {
    nomeFesta = nomeFesta.toLowerCase().replace('-', '').replace('–', '')
                        .replace(/\s+/g, '-').replace('ç', 'c')
                        .replace('º', '').replace('ª', '')
                        .normalize('NFD').replace(/[\u0300-\u036f]/g, '')
                        .replace(/[`~!@#$%^&*()_|+\=?;:'",.<>\{\}\[\]\\\/]/gi, '');
    const url = '../festas/' + nomeFesta + '&' + codFesta;
    return url;
  }

  
  openDialogEstoqueAlert(alerta): void {
    this.dialog.open(AlertaEstoqueComponent, {
      width: '23rem',
      data: {
        alerta
      }
    });
  }

  openDialogSegurancaAlert(alerta): void {
    this.dialog.open(AlertaSegurancaComponent, {
      width: '23rem',
      data: {
        alerta
      }
    });
  }

  openDialogConvite(alerta): void {
    this.dialog.open(FestaDetalhesDialogComponent, {
      width: '23rem',
      data: {
        alerta
      }
    });
  }
}
