import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-alerta-estoque',
  templateUrl: './alerta-estoque.component.html',
  styleUrls: ['./alerta-estoque.component.scss']
})
export class AlertaEstoqueComponent implements OnInit {

  notificacao: any;

  constructor(@Inject(MAT_DIALOG_DATA) data) {
    this.notificacao = data.notificacao;
  }

  ngOnInit() {
  }

  getUrlFesta() {
    const nomeFesta = this.notificacao.nomeFesta.toLowerCase().replace('-', '').replace('–', '')
                        .replace(/\s+/g, '-').replace('ç', 'c')
                        .replace('º', '').replace('ª', '')
                        .normalize('NFD').replace(/[\u0300-\u036f]/g, '')
                        .replace(/[`~!@#$%^&*()_|+\=?;:'",.<>\{\}\[\]\\\/]/gi, '');
    const url = '../festas/' + nomeFesta + '&' + this.notificacao.codFesta + '/estoque';
    return url;
  }

}
