import { Component, OnInit } from '@angular/core';

import {FormControl, Validators} from '@angular/forms';

import { SocialLoginBaseComponent } from '../social-login-base/social-login-base.component';

import { FacebookLoginProvider, GoogleLoginProvider } from 'angularx-social-login';


@Component({
  selector: 'app-cadastro',
  templateUrl: './cadastro.component.html',
  styleUrls: [
    './cadastro.component.scss',
    '../../../../node_modules/font-awesome/css/font-awesome.css'
  ]
})
export class CadastroComponent extends SocialLoginBaseComponent implements OnInit {

  nomeCadastro;
  dtnascCadastro;
  sexoCadastro;
  emailCadastro;
  senhaCadastro;

  email = new FormControl('', [Validators.required, Validators.email]);
  senha = new FormControl('', Validators.required);
  senha2 = new FormControl('', Validators.required);
  sexo = new FormControl('', Validators.required);
  dtnasc = new FormControl('', Validators.required);
  nome = new FormControl('', Validators.required);

  getErroEmail() {
    return this.email.hasError('required') ? 'Este campo é obrigatório.' : '';
  }

  getErroSenha() {
    return this.senha.hasError('required') ? 'Este campo é obrigatório.' : '';
  }

  getErroSenha2() {
    return this.senha2.hasError('required') ? 'Este campo é obrigatório.' : '';
  }

  getErroSexo() {
    return this.sexo.hasError('required') ? 'Este campo é obrigatório.' : '';
  }

  getErroDtnasc() {
    return this.dtnasc.hasError('required') ? 'Este campo é obrigatório.' : '';
  }

  getErroNome() {
    return this.nome.hasError('required') ? 'Este campo é obrigatório.' : '';
  }

  signUpWithPachanga(nome, dtNasc, sexo, email, senha): void {
    const userJson = {
      tipConta: 'P',
      email,
      senha,
      nomeUser: nome,
      dtNasc,
      sexo
    };
    this.loginService.cadastrar(userJson).subscribe();
  }

  signUpWithGoogle(): void {
    this.authService.signIn(GoogleLoginProvider.PROVIDER_ID).then((user) => {
      this.user = user;
      const userJson = {
        tipConta: 'G',
        nomeUser: this.user.name,
        email: this.user.email,
        dtNasc: '',
        sexo: ''
      };
      this.loginService.cadastrar(userJson).subscribe();
    });
  }

  signUpWithFB(): void {
    this.authService.signIn(FacebookLoginProvider.PROVIDER_ID).then((user) => {
      this.user = user;
      const userJson = {
        tipConta: 'F',
        email: this.user.email,
        nomeUser: this.user.name,
        dtNasc: '',
        sexo: ''
      };
      this.loginService.cadastrar(userJson).subscribe();
    });
  }

}
