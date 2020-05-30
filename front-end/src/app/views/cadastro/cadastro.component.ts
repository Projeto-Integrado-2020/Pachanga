import { Component, OnInit } from '@angular/core';

import { FormControl, Validators } from '@angular/forms';

import { SocialLoginBaseComponent } from '../social-login-base/social-login-base.component';

import { FacebookLoginProvider, GoogleLoginProvider } from 'angular4-social-login';

import { MustMatch } from '../utils/matchPassword';


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

  ngOnInit() {
    this.form = this.formBuilder.group({
      email: new FormControl('', [Validators.required, Validators.email]),
      senha: new FormControl('', [Validators.required,
        Validators.pattern('^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$')]),
      confirmacaoSenha: new FormControl('', Validators.required),
      sexo: new FormControl('', Validators.required),
      dtnasc: new FormControl('', Validators.required),
      nome: new FormControl('', Validators.required),
      termos: new FormControl(false, Validators.requiredTrue)
    }, {
      validator: MustMatch('senha', 'confirmacaoSenha')
      });
  }

  signUpWithPachanga(nome, dtNasc, sexo, email, senha): void {
    const userJson = {
      tipConta: 'P',
      email,
      senha,
      nomeUser: nome,
      dtNasc: dtNasc.slice(6, 10) + '-' + dtNasc.slice(3, 5) + '-' + dtNasc.slice(0, 2),
      sexo
    };
    this.cadastrar_se(userJson);
  }

  signUpWithGoogle(): void {
    this.authService.signIn(GoogleLoginProvider.PROVIDER_ID).then((user) => {
      this.user = user;
      const userJson = {
        tipConta: 'G',
        nomeUser: this.user.name,
        email: this.user.email,
        dtNasc: '',
        sexo: 'N'
      };
      this.cadastrar_se(userJson);
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
        sexo: 'N'
      };
      this.cadastrar_se(userJson);
    });
  }

}
