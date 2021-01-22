import { Component, OnInit } from '@angular/core';

import { FormGroup, FormBuilder, FormControl } from '@angular/forms';

import { LoginService } from 'src/app/services/loginService/login.service';
import { MatDialog } from '@angular/material';
import { EditDialogComponent } from '../edit-dialog/edit-dialog.component';

@Component({
  selector: 'app-perfil-dialog',
  templateUrl: './perfil-dialog.component.html',
  styleUrls: ['./perfil-dialog.component.scss']
})
export class PerfilDialogComponent implements OnInit {

  public form: FormGroup;

  constructor(public loginService: LoginService, public formBuilder: FormBuilder,
              public edit: MatDialog) {
  }

  get f() { return this.form.controls; }

  ngOnInit() {
    this.form = this.formBuilder.group({
      nome: new FormControl({value: '', disabled: true}),
      pronome: new FormControl({value: '', disabled: true}),
      email: new FormControl({value: '', disabled: true}),
      dtNasc: new FormControl({value: '', disabled: true}),
      sexo: new FormControl({value: '', disabled: true}),
      senha: new FormControl({value: '', disabled: true})
    });
  }

  openDialogEdit(campo: string) {
    this.edit.open(EditDialogComponent, {
      width: '20rem',
      data: {campo}
    });
  }

}
