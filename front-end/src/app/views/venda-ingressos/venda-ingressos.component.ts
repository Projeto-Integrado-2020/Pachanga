import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { DadosCompraIngressoService } from 'src/app/services/dados-compra-ingresso/dados-compra-ingresso.service';
import { GetCuponsService } from 'src/app/services/get-cupons/get-cupons.service';
import { GetFestaVendaIngressosService } from 'src/app/services/get-festa-venda-ingressos/get-festa-venda-ingressos.service';
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
  public form2: FormGroup;
  public split: any;
  public lotes = [];
  subscription: Subscription;
  source: any;
  public ingressos = [];
  public checkoutMassege;
  public cupomDesc: any;
  public colorCheck = 'preto';

  constructor(public formBuilder: FormBuilder, public getFestaService: GetFestaVendaIngressosService, public getLote: GetLotePublicoService,
              public router: Router, public buyIngresso: DadosCompraIngressoService, public getCupons: GetCuponsService) {
                this.form2 = this.formBuilder.group({
                  cupom: new FormControl('', Validators.required)
                });
                this.form = this.formBuilder.group({});
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

  aplicarCupom(nomeCupom) {
    this.getCupons.getCupomUnico(this.festa.codFesta, nomeCupom).subscribe((resp: any) => {
      this.cupomDesc = resp;
      this.getCupons.setFarol(false);
      if (this.cupomDesc) {
        this.colorCheck = 'verde';
      } else {
        this.colorCheck = 'vermelho';
      }
    });

  }

  resgatarFesta() {
    let idFesta = this.router.url;
    idFesta = idFesta.substring(idFesta.indexOf('&') + 1, idFesta.indexOf('/', idFesta.indexOf('&')));

    this.getFestaService.getFestaVenda(idFesta).subscribe((resp: any) => {
      this.getFestaService.setFarol(false);
      this.festa = resp;
    });
  }

  parserFloat(valor) {
    return valor.toFixed(2);
  }

  ngOnInit() {
    this.resgatarLote();
    this.resgatarFesta();
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
        let precoCompra = lote.preco;
        if (this.cupomDesc) {
          if (this.cupomDesc.tipoDesconto === 'V') {
            if (lote.preco - this.cupomDesc.precoDesconto < 0) {
              precoCompra = 0;
            } else {
              precoCompra = lote.preco - this.cupomDesc.precoDesconto;
            }
          } else if (this.cupomDesc.tipoDesconto === 'P') {
            precoCompra = lote.preco - (this.cupomDesc.porcentagemDesc / 100) * lote.preco;
          }
        }
        lotesSelected.push({
          quantidade: quantidadeArray,
          precoUnico: precoCompra,
          lote
        });
        precoTotal += precoCompra * quantidade;
      }
    }
    this.buyIngresso.addIngresso(lotesSelected);
    this.buyIngresso.addPrecoTotal(precoTotal);
    return this.redirectUrl();
  }

  calcDesconto(preco, precoDesconto, porcentagemDesconto, tipo) {
    if (tipo === 'P') {
      return preco - (porcentagemDesconto / 100) * preco;
    } else {
      if ((preco - precoDesconto) < 0) {
        return 0;
      }
      return preco - precoDesconto;
    }
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

  get f2() { return this.form2.controls; }

  buildForm() {
    const group = {};
    for (const lote of this.lotes) {
      group['quantidade-' + lote.codLote] = new FormControl('');
    }
    this.form = this.formBuilder.group(group);
  }

}
