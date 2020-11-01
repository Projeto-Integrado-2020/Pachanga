import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-alerta-seguranca',
  templateUrl: './alerta-seguranca.component.html',
  styleUrls: ['./alerta-seguranca.component.scss']
})
export class AlertaSegurancaComponent implements OnInit {

  notificacao: any;

  constructor(@Inject(MAT_DIALOG_DATA) data) {
    this.notificacao = data.alerta;
  }

  ngOnInit() {
  }

  getUrlFesta() {
    const codFesta = this.notificacao.notificacaoArea.codFesta;
    const nomeFesta = this.notificacao.notificacaoArea.nomeFesta.toLowerCase().replace('-', '').replace('–', '')
                        .replace(/\s+/g, '-').replace('ç', 'c')
                        .replace('º', '').replace('ª', '')
                        .normalize('NFD').replace(/[\u0300-\u036f]/g, '')
                        .replace(/[`~!@#$%^&*()_|+\=?;:'",.<>\{\}\[\]\\\/]/gi, '');
    const url = '../festas/' + nomeFesta + '&' + codFesta + '/painel-seguranca';
    return url;
  }

}
