import { Injectable } from '@angular/core';
import { GetLotePublicoService } from '../get-lote-publico/get-lote-publico.service';

@Injectable({
  providedIn: 'root'
})

export class DadosCompraIngressoService {

  constructor(public getLote: GetLotePublicoService) { }

  public ingresso = JSON.parse(sessionStorage.getItem('ingressos'));
  public precoTotal = JSON.parse(sessionStorage.getItem('precoTotal'));

  addIngresso(item) {
    sessionStorage.setItem('ingressos', JSON.stringify(item));
    this.ingresso = item;
  }

  addPrecoTotal(precoTotal) {
    sessionStorage.setItem('precoTotal', JSON.stringify(precoTotal));
    this.precoTotal = precoTotal;
  }

  getIngressos() {
    return this.ingresso;
  }

  getPrecoTotal() {
    return this.precoTotal;
  }

  cleanStorage() {
    this.ingresso = null;
    this.precoTotal = null;
    sessionStorage.removeItem('ingressos');
    sessionStorage.removeItem('precoTotal');
  }

}
