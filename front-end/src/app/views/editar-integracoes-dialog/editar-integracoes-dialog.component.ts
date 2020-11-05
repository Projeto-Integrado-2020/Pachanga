import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material';
import { EditarIntegracaoService } from 'src/app/services/editar-integracao/editar-integracao.service';

@Component({
  selector: 'app-editar-integracoes-dialog',
  templateUrl: './editar-integracoes-dialog.component.html',
  styleUrls: ['./editar-integracoes-dialog.component.scss']
})
export class EditarIntegracoesDialogComponent implements OnInit {

  component: any;
  codFesta: any;
  integracao: any;
  form: FormGroup;

  constructor(@Inject(MAT_DIALOG_DATA) data, public editarService: EditarIntegracaoService,
              public dialog: MatDialog, public formBuilder: FormBuilder) {
    this.integracao = data.integracao;
    this.codFesta = data.codFesta;
    this.component = data.component;
  }

  ngOnInit() {
    this.form = this.formBuilder.group({
      terceiro: new FormControl(this.integracao.terIntegrado, Validators.required),
      codEvento: new FormControl(this.integracao.codEvent, Validators.required),
      token: new FormControl(this.integracao.privateToken, Validators.required)
    });
  }

  get f() { return this.form.controls; }

  editarIntegracao(terIntegrado, codEvent, privateToken) {
    const integracao = {
      codInfo: this.integracao.codInfo,
      codFesta: this.codFesta,
      terIntegrado,
      codEvent,
      privateToken
    };

    this.editarService.editarIntegracao(integracao).subscribe((resp: any) => {
      this.editarService.setFarol(false);
      this.component.ngOnInit();
      this.dialog.closeAll();
    });
  }

}
