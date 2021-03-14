import { Component, OnInit, Inject } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { Router } from '@angular/router';
import { catchError, take } from 'rxjs/operators';
import { CepApiService } from 'src/app/services/cep-api/cep-api.service';
import { DadosCompraIngressoService } from 'src/app/services/dados-compra-ingresso/dados-compra-ingresso.service';
import { GerarIngressoService } from 'src/app/services/gerar-ingresso/gerar-ingresso.service';
import { LoginService } from 'src/app/services/loginService/login.service';
import { ProcessingDialogComponent } from '../processing-dialog/processing-dialog.component';
import { SuccessDialogComponent } from '../success-dialog/success-dialog.component';

@Component({
  selector: 'app-gerar-boleto-dialog',
  templateUrl: './gerar-boleto-dialog.component.html',
  styleUrls: ['./gerar-boleto-dialog.component.scss']
})
export class GerarBoletoDialogComponent implements OnInit {

  form: FormGroup;
  festa: any;
  preco: any;
  ingressos: any;
  formCheckOut: FormGroup;
  loadingDialog: any;

  constructor(@Inject(MAT_DIALOG_DATA) data, public formBuilder: FormBuilder,
              public dialog: MatDialog, public cepService: CepApiService, public router: Router,
              public dadosService: DadosCompraIngressoService, public ingressosService: GerarIngressoService,
              public loginService: LoginService) {
    this.festa = data.festa;
    this.preco = data.preco;
    this.ingressos = data.ingressos;
    this.formCheckOut = data.form;
  }

  ngOnInit() {
    this.gerarForm();
  }

  gerarForm() {
    this.form = this.formBuilder.group({
      nomePagador: new FormControl('', Validators.required),
      identificacao: new FormControl('', Validators.required),
      email: new FormControl('', [Validators.required, Validators.email]),
      pais: new FormControl('', Validators.required),
      estado: new FormControl('', Validators.required),
      cidade: new FormControl('', Validators.required),
      cep: new FormControl('', Validators.required),
      bairro: new FormControl('', Validators.required),
      rua: new FormControl('', Validators.required),
      numero: new FormControl('', Validators.required)
    });
  }

  get f() { return this.form.controls; }

  gerarIngressos() {
    const ingressosCheckout = [];
    for (const lote of this.ingressos) {
        for (let i = 0; i < lote.quantidade.length; i++) {
            ingressosCheckout.push({
                codLote: lote.lote.codLote,
                festa: {codFesta: lote.lote.codFesta},
                codUsuario: this.loginService.getusuarioInfo().codUsuario,
                statusIngresso: 'U',
                statusCompra: 'C',
                boleto: true,
                preco: lote.precoUnico,
                nomeTitular: this.formCheckOut.get('nome' + lote.lote.codLote + '-' + i).value,
                emailTitular: this.formCheckOut.get('email' + lote.lote.codLote + '-' + i).value
            });
        }
    }
    const body = {
      listaIngresso: ingressosCheckout,
      infoPagamento: {
        nome: this.form.get('nomePagador').value,
        cpf: this.form.get('identificacao').value,
        email: this.form.get('email').value,
        pais: this.form.get('pais').value,
        estado: this.form.get('estado').value,
        cidade: this.form.get('cidade').value,
        cep: this.form.get('cep').value,
        rua: this.form.get('rua').value,
        numero: this.form.get('numero').value,
        bairro: this.form.get('bairro').value,
        preco: this.preco
      }
    };

    this.openDialogProcessing();
    this.ingressosService.adicionarIngressos(body).pipe(
      take(1),
      catchError(error => {
        this.loadingDialog.close();
        return this.ingressosService.handleError(error);
      })
    ).subscribe((resp: any) => {
      window.open(resp[0].urlBoleto);
      this.router.navigate(['/meus-ingressos']);
      this.dadosService.cleanStorage();
      this.dialog.closeAll();
      this.openDialogSuccess('BOLEGERA');
    });
  }
/*
  gerarBoleto() {
    const boleto = {
      reference_id: 'ex-00001',
      description: 'Ingresso para a festa ' + this.festa.nomefesta,
      amount: {
        value: Number(this.preco + '00'),
        currency: 'BRL'
      },
      payment_method: {
        type: 'BOLETO',
        boleto: {
          due_date: '2020-12-25',
          instruction_lines: {
            line_1: 'Pagamento processado para DESC Fatura',
            line_2: 'Via PagSeguro'
          },
          holder: {
            name: this.form.get('nomePagador').value,
            tax_id: this.form.get('identificacao').value,
            email: this.form.get('email').value,
            address: {
              country: this.form.get('pais').value,
              region: this.form.get('estado').value,
              region_code: this.form.get('estado').value,
              city: this.form.get('cidade').value,
              postal_code: this.form.get('cep').value,
              street: this.form.get('rua').value,
              number: this.form.get('numero').value,
              locality: this.form.get('bairro').value
            }
          }
        }
      },
      notification_urls: `${environment.URL_BACK}ingresso/updateStatusCompra?codBoleto=${'codBoleto'}`
    };

    this.pagSeguroService.gerarBoleto(boleto).subscribe((resp: any) => {
      this.gerarIngressos(resp);
    });
  }
*/

  buscarEndereco() {
    this.cepService.resgatarEndereco(this.form.get('cep').value).subscribe((resp: any) => {
      this.form.get('pais').setValue('Brasil');
      this.form.get('estado').setValue(resp.uf);
      this.form.get('cidade').setValue(resp.localidade);
      this.form.get('bairro').setValue(resp.bairro);
      this.form.get('rua').setValue(resp.logradouro);
    });
  }

  openDialogProcessing() {
    this.loadingDialog = this.dialog.open(ProcessingDialogComponent, {
        width: '20rem',
        disableClose: true,
        data: {
            tipo: 'BOLETO'
        }
    });
  }

  openDialogSuccess(message: string) {
    this.dialog.open(SuccessDialogComponent, {
      width: '20rem',
      data: {message}
    });
  }

}
