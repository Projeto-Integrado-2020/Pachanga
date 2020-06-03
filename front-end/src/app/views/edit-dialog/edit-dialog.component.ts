import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material';
import { LoginService } from '../../services/loginService/login.service';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { MustMatch } from '../utils/matchPassword';
import { EditAccountService } from 'src/app/services/editAccountService/edit-account.service';
import { SuccessDialogComponent } from '../success-dialog/success-dialog.component';

@Component({
  selector: 'app-edit-dialog',
  templateUrl: './edit-dialog.component.html',
  styleUrls: ['./edit-dialog.component.scss']
})
export class EditDialogComponent implements OnInit {

  public campo: string;
  public form: FormGroup;
  public modJson;

  maxDate = new Date();

  constructor(@Inject(MAT_DIALOG_DATA) data, public loginService: LoginService,
              public formBuilder: FormBuilder, public editService: EditAccountService,
              public modal: MatDialog) {
    this.campo = data.campo;
  }

  get f() { return this.form.controls; }

  ngOnInit() {

    this.modJson = JSON.parse(JSON.stringify(this.loginService.usuarioInfo));

    switch (this.campo) {
      case 'nome':
        this.form = this.formBuilder.group({
          nomeAntigo: new FormControl({value: '', disabled: true}),
          nome: new FormControl('', Validators.required),
        });
        break;
      case 'email':
        this.form = this.formBuilder.group({
          emailAntigo: new FormControl({value: '', disabled: true}),
          email: new FormControl('', [Validators.required, Validators.email]),
          senha: new FormControl('', Validators.required)
        });
        break;
      case 'dtNasc':
        this.form = this.formBuilder.group({
          dtNascAntiga: new FormControl({value: '', disabled: true}),
          dtNasc: new FormControl('', Validators.required),
        });
        break;
      case 'sexo':
        this.form = this.formBuilder.group({
          sexoAntigo: new FormControl({value: '', disabled: true}),
          sexo: new FormControl('', Validators.required),
        });
        break;
      case 'senha':
        this.form = this.formBuilder.group({
          senhaAntiga: new FormControl('', Validators.required),
          senha: new FormControl('', [Validators.required,
            Validators.pattern('^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$')]),
          confirmacaoSenha: new FormControl('', Validators.required),
        }, {
          validator: MustMatch('senha', 'confirmacaoSenha')
        });
        break;
    }
  }

  salvarNome(nome: string) {
    this.modJson.nomeUser = nome;
    this.callService();
  }

  salvarEmail(senha: string, email: string) {
    this.modJson.emailNovo = email;
    this.modJson.senha = senha;
    this.callService();
  }

  salvarDtNasc(dtNasc: string) {
    this.modJson.dtNasc = dtNasc.slice(6, 10) + '-' + dtNasc.slice(3, 5) + '-' + dtNasc.slice(0, 2);
    this.callService();
  }

  salvarSexo(sexo: string) {
    this.modJson.sexo = sexo;
    this.callService();
  }

  salvarSenha(senhaAntiga: string, senha: string) {
    this.modJson.senhaNova = senha;
    this.modJson.senha = senhaAntiga;
    this.callService();
  }

  callService() {
    this.editService.atualizar(this.modJson, this.loginService.usuarioInfo).subscribe(resp => {
      this.loginService.setusuarioInfo(resp);
      this.editService.setFarol(false);
      this.modal.closeAll();
      this.openDialogSuccess('1');
    });
  }

  openDialogSuccess(message: string) {
    this.modal.open(SuccessDialogComponent, {
      width: '20rem',
      data: {message}
    });
  }

}
