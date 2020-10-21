import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material';

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
              public formBuilder: FormBuilder) {
    this.codFesta = data.codFesta;
    this.component = data.component;
  }

  ngOnInit() {
    this.form = this.formBuilder.group({
      nome: new FormControl('', Validators.required),
      url: new FormControl('', Validators.required),
    });
  }

  get f() { return this.form.controls; }

  adicionarForm(nome, url) {
    const produto = {
      nome,
      url
    };
  }

}
