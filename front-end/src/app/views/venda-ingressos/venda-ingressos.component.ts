import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { GetFestaService } from 'src/app/services/get-festa/get-festa.service';
import { GetLoteUnicoService } from 'src/app/services/get-lote-unico/get-lote-unico.service';

@Component({
  selector: 'app-venda-ingressos',
  templateUrl: './venda-ingressos.component.html',
  styleUrls: ['./venda-ingressos.component.scss']
})
export class VendaIngressosComponent implements OnInit {

  public festaNome: string;
  public lotes: any;
  options: FormGroup;
  public festa: any;
  public statusFesta: any;
  panelOpenState = false;
  public forms = {};
  estoques: any;
  dataSources = [];
  subscription: Subscription;
  source: any;

  constructor(public getFestaService: GetFestaService, public getLote: GetLoteUnicoService, public router: Router) { }

  resgatarLote() {
    this.getLote.getLoteUnico(this.festa.codFesta).subscribe((resp: any) => {
      this.lotes = resp;
      this.getLote.setFarol(false);
    });
  }

  ngOnInit() {
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
      this.resgatarLote();
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

}
