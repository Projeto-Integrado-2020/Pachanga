import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-editar-form-dialog',
  templateUrl: './editar-form-dialog.component.html',
  styleUrls: ['./editar-form-dialog.component.scss']
})
export class EditarFormDialogComponent implements OnInit {

  component: any;
  codFesta: any;
  googleForm: any;
  form: FormGroup;

  constructor(@Inject(MAT_DIALOG_DATA) data,
              public dialog: MatDialog, public formBuilder: FormBuilder) {
    this.googleForm = data.form;
    this.codFesta = data.codFesta;
    this.component = data.component;
  }

  ngOnInit() {
    this.form = this.formBuilder.group({
      nome: new FormControl(this.googleForm.nome, Validators.required),
      url: new FormControl(this.googleForm.url, Validators.required)
    });
  }

  get f() { return this.form.controls; }

  editarForm(nome, url) {
    const produto = {
      codQuestionario: this.googleForm.codQuestionario,
      codFesta: this.codFesta,
      nome,
      url
    };
  }

}
