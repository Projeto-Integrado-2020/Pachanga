import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { DadosCompraIngressoService } from 'src/app/services/dados-compra-ingresso/dados-compra-ingresso.service';
import { GetFestaService } from 'src/app/services/get-festa/get-festa.service';
import { GetLotePublicoService } from 'src/app/services/get-lote-publico/get-lote-publico.service';
import { GetLoteUnicoPublicoService } from 'src/app/services/get-lote-unico-publico/get-lote-unico-publico.service';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.scss']
})
export class CheckoutComponent implements OnInit {

  public festaNome: string;
  public lotes: [];
  options: FormGroup;
  public festa: any;
  public statusFesta: any;
  panelOpenState = false;
  public forms = {};
  estoques: any;
  dataSources = [];
  subscription: Subscription;
  source: any;
  public ingressos = [];

  constructor(public getFestaService: GetFestaService, public getLote: GetLoteUnicoPublicoService, public router: Router,
              public getIngressoCheckout: DadosCompraIngressoService) { }

  resgatarLote(codFesta) {
    this.getLote.getLoteUnicoPublico(codFesta).subscribe((resp: any) => {
      this.lotes = resp;
      this.getLote.setFarol(false);
    });
  }

  ngOnInit() {
    this.getIngressos();
    console.log(this.ingressos)
    this.source = null;
    this.lotes = [];
    let idFesta = this.router.url;
    this.dataSources = [];
    idFesta = idFesta.substring(idFesta.indexOf('&') + 1, idFesta.indexOf('/', idFesta.indexOf('&')));

    this.getFestaService.acessarFesta(idFesta).subscribe((resp: any) => {
      this.getFestaService.setFarol(false);
      this.festa = resp;
      this.festaNome = resp.nomeFesta;
      this.statusFesta = resp.statusFesta;
      this.resgatarLote(this.festa.codFesta);
    });
  }

  getDateFromDTF(date) {
    let conversion = date.split('T', 1);
    conversion = conversion[0].split('-');
    return conversion[2] + '/' + conversion[1] + '/' + conversion[0];
  }

  getTimeFromDTF(date) {
    return date.split('T')[1];
  }

  getIngressos() {
    this.ingressos = this.getIngressoCheckout.getIngressos();
  }

}
