import { Component, OnInit, Inject } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material';
import { PagSeguroService } from 'src/app/services/pag-seguro/pag-seguro.service';

@Component({
  selector: 'app-gerar-boleto-dialog',
  templateUrl: './gerar-boleto-dialog.component.html',
  styleUrls: ['./gerar-boleto-dialog.component.scss']
})
export class GerarBoletoDialogComponent implements OnInit {

  form: FormGroup;
  festa: any;
  preco: any;

  constructor(@Inject(MAT_DIALOG_DATA) data, public formBuilder: FormBuilder,
              public pagSeguroService: PagSeguroService, public dialog: MatDialog) {
    this.festa = data.festa;
    this.preco = data.preco;
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

  gerarBoleto() {
    const boleto = {
      reference_id: 'ex-00001',
      description: 'Ingresso para a festa ' + this.festa.nomefesta,
      amount: {
        value: 1000,
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
              region_code: 'SP',
              city: this.form.get('cidade').value,
              postal_code: this.form.get('cep').value,
              street: this.form.get('rua').value,
              number: this.form.get('numero').value,
              locality: this.form.get('bairro').value
            }
          }
        }
      },
      notification_urls: [
        'https://yourserver.com/nas_ecommerce/277be731-3b7c-4dac-8c4e-4c3f4a1fdc46/'
      ]
    };

    this.pagSeguroService.gerarBoleto(boleto).subscribe((resp: any) => {
      console.log(resp);
      window.open(resp.links[0].href, '_blank');
      this.dialog.closeAll();
    });
  }

}
