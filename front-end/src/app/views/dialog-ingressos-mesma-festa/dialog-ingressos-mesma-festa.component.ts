import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dialog-ingressos-mesma-festa',
  templateUrl: './dialog-ingressos-mesma-festa.component.html',
  styleUrls: ['./dialog-ingressos-mesma-festa.component.scss']
})
export class DialogIngressosMesmaFestaComponent implements OnInit {

  ingressos: any;
  ingressoSelecionado: string;

  constructor(
    @Inject(MAT_DIALOG_DATA) data,
    private router: Router
    ) {
    this.ingressos = data.ingressos;
  }

  ngOnInit() {
    console.log(this.ingressos);
  }

  acessarUrl(urlBoleto){
    this.router.navigate([]).then(() => {  window.open(urlBoleto, '_blank'); });
  }

}
