import { Component, OnInit } from '@angular/core';

import { FormGroup, FormBuilder, FormControl } from '@angular/forms';

import { LoginService } from 'src/app/services/loginService/login.service';
import { MatDialog } from '@angular/material';
import { EditDialogComponent } from '../edit-dialog/edit-dialog.component';

@Component({
  selector: 'app-perfil',
  templateUrl: './perfil.component.html',
  styleUrls: ['./perfil.component.scss']
})
export class PerfilComponent implements OnInit {

  public form: FormGroup;
  public userDtNasc: string;

  constructor(public loginService: LoginService, public formBuilder: FormBuilder,
              public edit: MatDialog) {
  }

  get f() { return this.form.controls; }

  ngOnInit() {
    if (this.loginService.usuarioInfo.dtNasc != null){
      this.userDtNasc = this.loginService.usuarioInfo.dtNasc;
      this.userDtNasc = this.userDtNasc.slice(8, 10) + '/' +
                        this.userDtNasc.slice(5, 7) + '/' +
                        this.userDtNasc.slice(0, 4);
    }

    this.form = this.formBuilder.group({
      nome: new FormControl({value: '', disabled: true}),
      email: new FormControl({value: '', disabled: true}),
      dtNasc: new FormControl({value: '', disabled: true}),
      sexo: new FormControl({value: '', disabled: true}),
      senha: new FormControl({value: '', disabled: true})
    });
  }

  openDialogEdit(campo: string) {
    this.edit.open(EditDialogComponent, {
      width: '20rem',
      data: {campo: campo}
    });
  }

}
