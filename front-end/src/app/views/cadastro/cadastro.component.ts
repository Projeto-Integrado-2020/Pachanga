import { Component, OnInit } from '@angular/core';

import {FormControl, Validators} from '@angular/forms';

import { SocialLoginBaseComponent } from '../social-login-base/social-login-base.component';

@Component({
  selector: 'app-cadastro',
  templateUrl: './cadastro.component.html',
  styleUrls: [
    './cadastro.component.scss',
    '../../../../node_modules/font-awesome/css/font-awesome.css'
  ]
})
export class CadastroComponent extends SocialLoginBaseComponent implements OnInit {

  emailFormControl = new FormControl('', [
    Validators.required,
    Validators.email,
  ]);

  signUpWithPachanga(nome, dtNasc, sexo, email, senha): void {
    const userJson = {
      tipConta: 'P',
      email,
      senha,
      nomeUser: nome,
      dtNasc,
      sexo
    };
    console.log(JSON.stringify(userJson));
    this.loginService.cadastrar(userJson).subscribe();
  }

}
