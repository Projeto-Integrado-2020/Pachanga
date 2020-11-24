import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { DadosCompraIngressoService } from 'src/app/services/dados-compra-ingresso/dados-compra-ingresso.service';
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
  public form: FormGroup;
  public split: any;
  public lotes = [];
  subscription: Subscription;
  source: any;
  public ingressos = [];
  public checkoutMassege;

  constructor(public formBuilder: FormBuilder, public getFestaService: GetFestaService, public getLote: GetLotePublicoService,
              public router: Router, public buyIngresso: DadosCompraIngressoService) {
                this.form = this.formBuilder.group({
                });
              }

  resgatarLote() {
    let idFesta = this.router.url;
    idFesta = idFesta.substring(idFesta.indexOf('&') + 1, idFesta.indexOf('/', idFesta.indexOf('&')));

    this.getLote.getLotePublico(idFesta).subscribe((resp: any) => {
      this.lotes = resp;
      this.getLote.setFarol(false);
      this.buildForm();
    });
  }

  resgatarFesta() {
    let idFesta = this.router.url;
    idFesta = idFesta.substring(idFesta.indexOf('&') + 1, idFesta.indexOf('/', idFesta.indexOf('&')));

    this.getFestaService.acessarFesta(idFesta).subscribe((resp: any) => {
      this.getFestaService.setFarol(false);
      this.festa = resp;
    });
  }

  ngOnInit() {
    this.resgatarLote();
    this.resgatarFesta();
    // this.buyIngresso.sharedMessage.subscribe(message => this.checkoutMassege = message);
  }

  getDateFromDTF(date) {
    let conversion = date.split(' ', 1);
    conversion = conversion[0].split('-');
    return conversion[2] + '/' + conversion[1] + '/' + conversion[0];
  }

  getTimeFromDTF(date) {
    return date.split(' ')[1];
  }

  checkout() {
    const lotesSelected = [];
    let precoTotal = 0;
    for (const lote of this.lotes) {
      const quantidade = this.form.get('quantidade-' + lote.codLote).value;
      if (quantidade) {
        const quantidadeArray = [];
        for (let i = 0; i < quantidade; i++) {
          quantidadeArray.push(i);
        }
        lotesSelected.push({
          quantidade: quantidadeArray,
          precoUnico: lote.preco,
          lote
        });
        precoTotal += lote.preco;
      }
    }
    this.buyIngresso.addIngresso(lotesSelected);
    this.buyIngresso.addPrecoTotal(precoTotal);
    return this.redirectUrl();
  }

  validationFormButton() {
    let flag = 0;
    let loteCount = 0;

    for (const lote of this.lotes) {
      const quantidade = this.form.get('quantidade-' + lote.codLote).value;
      loteCount ++;
      if (!quantidade) {
        flag++;
      }
    }

    if (flag === loteCount) {
      return true;
    }
    return false;
  }

  redirectUrl() {
    const nomeFesta = this.festa.nomeFesta.toLowerCase().replace('-', '').replace('–', '')
                        .replace(/\s+/g, '-').replace('ç', 'c')
                        .replace('º', '').replace('ª', '')
                        .normalize('NFD').replace(/[\u0300-\u036f]/g, '')
                        .replace(/[`~!@#$%^&*()_|+\=?;:'",.<>\{\}\[\]\\\/]/gi, '');
    const url = '../' + nomeFesta + '&' + this.festa.codFesta + '/venda-ingressos/venda-checkout';
    console.log(url);
    this.router.navigate([url]);
  }

  get f() { return this.form.controls; }

  buildForm() {
    const group = {};
    for (const lote of this.lotes) {
      group['quantidade-' + lote.codLote] = new FormControl('');
    }
    this.form = this.formBuilder.group(group);
  }

}
