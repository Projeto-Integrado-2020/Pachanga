import { Component, NgZone, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material';
import { Router } from '@angular/router';
import { IPayPalConfig, ICreateOrderRequest } from 'ngx-paypal';
import { DadosCompraIngressoService } from 'src/app/services/dados-compra-ingresso/dados-compra-ingresso.service';
import { GerarIngressoService } from 'src/app/services/gerar-ingresso/gerar-ingresso.service';
import { GetFestaService } from 'src/app/services/get-festa/get-festa.service';
import { LoginService } from 'src/app/services/loginService/login.service';
import { GerarBoletoDialogComponent } from '../gerar-boleto-dialog/gerar-boleto-dialog.component';
import { ProcessingDialogComponent } from '../processing-dialog/processing-dialog.component';
import { SuccessDialogComponent } from '../success-dialog/success-dialog.component';

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
    /* tslint:disable */
    public payPalConfig ? : IPayPalConfig;
    /* tslint:enable */
    precoTotal: string;
    public ingressos = [];
    form: FormGroup;

    constructor(public getFestaService: GetFestaService, public router: Router, public getIngressoCheckout: DadosCompraIngressoService,
                public ingressosService: GerarIngressoService, public dialog: MatDialog,
                public formBuilder: FormBuilder, public loginService: LoginService,
                public ngZone: NgZone) { }

    ngOnInit() {
        this.initConfig();
        let idFesta = this.router.url;
        this.getIngressos();
        this.gerarForm();

        idFesta = idFesta.substring(idFesta.indexOf('&') + 1, idFesta.indexOf('/', idFesta.indexOf('&')));
        this.getFestaService.acessarFesta(idFesta).subscribe((resp: any) => {
            this.getFestaService.setFarol(false);
            this.festa = resp;
            this.festaNome = resp.nomeFesta;
            this.statusFesta = resp.statusFesta;
        });
    }

    get f() { return this.form.controls; }

    gerarForm() {
        const group = {};
        for (const lote of this.ingressos) {
            for (let i = 0; i < lote.quantidade.length; i++) {
                group['nome' + lote.lote.codLote + '-' + i] = new FormControl('', Validators.required);
                group['email' + lote.lote.codLote + '-' + i] = new FormControl('', [Validators.required, Validators.email]);
            }
        }
        this.form = this.formBuilder.group(group);
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
                this.openDialogProcessing();
            },
            onClientAuthorization: (data) => {
                this.ngZone.run(() => this.gerarIngressosPayPal());
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

    openDialogSuccess(message: string) {
        this.dialog.open(SuccessDialogComponent, {
            width: '20rem',
            data: {message}
        });
    }

    openDialogProcessing() {
        this.dialog.open(ProcessingDialogComponent, {
            width: '20rem',
            disableClose: true
        });
    }

    getIngressos() {
        this.ingressos = this.getIngressoCheckout.getIngressos();
        this.precoTotal = this.getIngressoCheckout.getPrecoTotal();
    }

    gerarIngressosPayPal() {
        const ingressosCheckout = [];
        for (const lote of this.ingressos) {
            for (let i = 0; i < lote.quantidade.length; i++) {
                ingressosCheckout.push({
                    codLote: lote.lote.codLote,
                    festa: {codFesta: lote.lote.codFesta},
                    codUsuario: this.loginService.getusuarioInfo().codUsuario,
                    statusIngresso: 'U',
                    statusCompra: 'P',
                    dataCompra: new Date(),
                    preco: lote.precoUnico,
                    nomeTitular: this.form.get('nome' + lote.lote.codLote + '-' + i).value,
                    emailTitular: this.form.get('email' + lote.lote.codLote + '-' + i).value
                });
            }
        }
        this.ingressosService.adicionarIngressos(ingressosCheckout).subscribe((resp: any) => {
            this.router.navigate(['/meus-ingressos']);
            this.getIngressoCheckout.cleanStorage();
            this.dialog.closeAll();
            this.openDialogSuccess('COMPAPRO');
        });
    }
}
