import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { GetFestaService } from 'src/app/services/get-festa/get-festa.service';
import { GetLotePublicoService } from 'src/app/services/get-lote-publico/get-lote-publico.service';

@Component({
  selector: 'app-venda-ingressos',
  templateUrl: './venda-ingressos.component.html',
  styleUrls: ['./venda-ingressos.component.scss']
})
export class VendaIngressosComponent implements OnInit {

  public festa = {
    nomeFesta: null,
    codFesta: null,
    horarioInicioFesta: '2020-01-01 00:00:00',
    horarioFimFesta: '2020-01-01 00:00:00',
    codEnderecoFesta: null,
    descricaoFesta: null
  };
  public split: any;
  public lotes: any;
  subscription: Subscription;
  source: any;

  constructor(public getFestaService: GetFestaService, public getLote: GetLotePublicoService, public router: Router) { }

  resgatarLote(codFesta) {
    this.getLote.getLotePublico(codFesta).subscribe((resp: any) => {
      this.lotes = resp;
      this.getLote.setFarol(false);
    });
  }

  ngOnInit() {
    this.source = null;
    this.lotes = [];
    let idFesta = this.router.url;
    idFesta = idFesta.substring(idFesta.indexOf('&') + 1, idFesta.indexOf('/', idFesta.indexOf('&')));

    this.getFestaService.acessarFesta(idFesta).subscribe((resp: any) => {
      this.getFestaService.setFarol(false);
      this.festa = resp;
      this.resgatarLote(this.festa.codFesta);
    });
  }

  getDateFromDTF(date) {
    let conversion = date.split(' ', 1);
    conversion = conversion[0].split('-');
    return conversion[2] + '/' + conversion[1] + '/' + conversion[0];
  }

  getTimeFromDTF(date) {
    return date.split(' ')[1];
  }

}
