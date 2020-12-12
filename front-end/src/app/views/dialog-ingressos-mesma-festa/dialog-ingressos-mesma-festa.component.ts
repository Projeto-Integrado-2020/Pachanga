import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-dialog-ingressos-mesma-festa',
  templateUrl: './dialog-ingressos-mesma-festa.component.html',
  styleUrls: ['./dialog-ingressos-mesma-festa.component.scss']
})
export class DialogIngressosMesmaFestaComponent implements OnInit {

  ingressos: any;
  ingressoSelecionado: string;

  constructor(@Inject(MAT_DIALOG_DATA) data) {
    this.ingressos = data.ingressos;
  }

  ngOnInit() {
    console.log(this.ingressos);
  }

  apagar(valor) {
    console.log(valor);
  }

}
