import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { MatDialog } from '@angular/material';
import { Router } from '@angular/router';
import { IPayPalConfig, ICreateOrderRequest } from 'ngx-paypal';
import { Subscription } from 'rxjs';
import { DadosCompraIngressoService } from 'src/app/services/dados-compra-ingresso/dados-compra-ingresso.service';
import { GerarIngressoService } from 'src/app/services/gerar-ingresso/gerar-ingresso.service';
import { GetFestaService } from 'src/app/services/get-festa/get-festa.service';
import { GerarBoletoDialogComponent } from '../gerar-boleto-dialog/gerar-boleto-dialog.component';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.scss']
})
export class CheckoutComponent implements OnInit {

    public festaNome: string;
    options: FormGroup;
    public festa: any;
    public statusFesta: any;
    panelOpenState = false;
    public forms = {};
    estoques: any;
    dataSources = [];
    subscription: Subscription;
    source: any;
    /* tslint:disable */
    public payPalConfig ? : IPayPalConfig;
    /* tslint:enable */
    precoTotal: string;
    public ingressos = [];
    public lotes = [];

    constructor(public getFestaService: GetFestaService, public router: Router, public getIngressoCheckout: DadosCompraIngressoService,
                public ingressosService: GerarIngressoService, public dialog: MatDialog) { }

    ngOnInit() {
        this.initConfig();
        this.source = null;
        let idFesta = this.router.url;
        this.dataSources = [];
        this.getIngressos();

        idFesta = idFesta.substring(idFesta.indexOf('&') + 1, idFesta.indexOf('/', idFesta.indexOf('&')));
        this.getFestaService.acessarFesta(idFesta).subscribe((resp: any) => {
            this.getFestaService.setFarol(false);
            this.festa = resp;
            this.festaNome = resp.nomeFesta;
            this.statusFesta = resp.statusFesta;
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

    /* tslint:disable */
    initConfig(): void {
        this.payPalConfig = {
            currency: 'BRL',
            clientId: 'AUxyHzPBLdUiT3YoqNfrdqrKrI7PU3mJAjt35B3cL4B8otyw1V6w_A3V4JQa08V2NM12obeNm2CKnH_G',
            createOrderOnClient: (data) => < ICreateOrderRequest > {
                intent: 'CAPTURE',
                purchase_units: [{
                    amount: {
                        currency_code: 'BRL',
                        value: this.precoTotal,
                        breakdown: {
                            item_total: {
                                currency_code: 'BRL',
                                value: this.precoTotal
                            }
                        }
                    },
                    items: this.gerarItems()
                }]
            },
            advanced: {
                commit: 'true'
            },
            style: {
                label: 'paypal',
                layout: 'vertical',
                size: 'responsive',
                color: 'blue',
                shape: 'rect'
            },
            onApprove: (data, actions) => {
            },
            onClientAuthorization: (data) => {
                console.log('FOI! PAGUEI');
            },
            onCancel: (data, actions) => {
            },
            onError: err => {
            },
            onClick: (data, actions) => {
            },
        };
    }
    /* tslint:enable */

    gerarItems() {
        const items = [];

        for (const lote of this.ingressos) {
            items.push({
                name: lote.lote.nomeLote,
                quantity: lote.quantidade.length,
                unit_amount: {
                    currency_code: 'BRL',
                    value: lote.precoUnico
                }
            });
        }

        return items;
    }

    openDialogBoleto() {
        this.dialog.open(GerarBoletoDialogComponent, {
            width: '50rem',
            data: {
                festa: this.festa,
                preco: this.precoTotal
            }
        });
    }

  getIngressos() {
    this.ingressos = this.getIngressoCheckout.getIngressos();
    this.precoTotal = this.getIngressoCheckout.getPrecoTotal();
  }

}
