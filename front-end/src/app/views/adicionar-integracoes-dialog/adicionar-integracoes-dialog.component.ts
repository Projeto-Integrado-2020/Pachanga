import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material';
import { AdicionarIntegracaoService } from 'src/app/services/adicionar-integracao/adicionar-integracao.service';
import { EventbriteApiService } from 'src/app/services/eventbrite-api/eventbrite-api.service';
import { SymplaApiService } from 'src/app/services/sympla-api/sympla-api.service';

@Component({
  selector: 'app-adicionar-integracoes-dialog',
  templateUrl: './adicionar-integracoes-dialog.component.html',
  styleUrls: ['./adicionar-integracoes-dialog.component.scss']
})
export class AdicionarIntegracoesDialogComponent implements OnInit {

  component: any;
  codFesta: any;
  form: FormGroup;

  constructor(@Inject(MAT_DIALOG_DATA) data, public dialog: MatDialog,
              public formBuilder: FormBuilder, public criarService: AdicionarIntegracaoService,
              public symplaService: SymplaApiService, public eventbriteService: EventbriteApiService) {
    this.codFesta = data.codFesta;
    this.component = data.component;
  }

  ngOnInit() {
    this.form = this.formBuilder.group({
      terceiro: new FormControl('', Validators.required),
      codEvento: new FormControl('', Validators.required),
      token: new FormControl('', Validators.required)
    });
  }

  get f() { return this.form.controls; }

  submitForm(terceiroInt, codEvent, token) {
    const integracao = {
      codFesta: this.codFesta,
      terceiroInt,
      codEvent,
      token
    };
    this.checarIntegracao(integracao);
  }

  checarIntegracao(integracao) {
    if (integracao.terceiroInt === 'S') {
      this.symplaService.testSymplaConnection(integracao.codEvent, integracao.token).subscribe((resp: any) => {
        this.criarIntegracao(integracao);
      });
    } else if (integracao.terceiroInt === 'E') {
      this.eventbriteService.testEventbriteConnection(integracao.codEvent, integracao.token).subscribe((resp: any) => {
        console.log(integracao);
        this.criarIntegracao(integracao);
      });
    }
  }

  criarIntegracao(integracao) {
    this.criarService.adicionarIntegracao(integracao).subscribe((resp: any) => {
      this.criarService.setFarol(false);
      this.component.ngOnInit();
      this.dialog.closeAll();
    });
  }

}
