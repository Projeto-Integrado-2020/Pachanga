import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material';
import { AdicionarFormService } from 'src/app/services/adicionar-form/adicionar-form.service';

@Component({
  selector: 'app-adicionar-form-dialog',
  templateUrl: './adicionar-form-dialog.component.html',
  styleUrls: ['./adicionar-form-dialog.component.scss']
})
export class AdicionarFormDialogComponent implements OnInit {

  component: any;
  codFesta: any;
  form: FormGroup;
  constructor(@Inject(MAT_DIALOG_DATA) data, public dialog: MatDialog,
              public formBuilder: FormBuilder, public criarService: AdicionarFormService) {
    this.codFesta = data.codFesta;
    this.component = data.component;
  }

  ngOnInit() {
    this.form = this.formBuilder.group({
      nome: new FormControl('', Validators.required),
      /* tslint:disable */
      url: new FormControl('', [Validators.required, Validators.pattern(/^[A-Za-z][A-Za-z\d.+-]*:\/*(?:\w+(?::\w+)?@)?[^\s/]+(?::\d+)?(?:\/[\w#!:.?+=&%@\-/]*)?$/)]),
      /* tslint:enable */
    });
  }

  get f() { return this.form.controls; }

  adicionarForm(nomeQuestionario, urlQuestionario) {
    const form = {
      codFesta: this.codFesta,
      nomeQuestionario,
      urlQuestionario
    };

    this.criarService.adicionarQuestionario(form, this.codFesta).subscribe((resp: any) => {
      this.criarService.setFarol(false);
      this.component.ngOnInit();
      this.dialog.closeAll();
    });
  }

}
