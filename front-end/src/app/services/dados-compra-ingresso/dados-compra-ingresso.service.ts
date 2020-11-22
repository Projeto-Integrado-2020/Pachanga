import { Injectable } from '@angular/core';
import { GetLotePublicoService } from '../get-lote-publico/get-lote-publico.service';

@Injectable({
  providedIn: 'root'
})

export class DadosCompraIngressoService {

  constructor(public getLote: GetLotePublicoService) { }

  public ingresso = [];
  public precoTotal= [];

  addIngresso(item) {
    this.ingresso.push(item);
    console.log(this.ingresso);
  }

  addPrecoTotal(precoTotal) {
    this.precoTotal.push(precoTotal);
    console.log(this.precoTotal);
  }

  getIngressos() {
    return this.ingresso;
  }

}
