import { Component, OnInit } from '@angular/core';

import {FormControl, Validators} from '@angular/forms';

interface Sexo {
  value: string;
  viewValue: string;
}

@Component({
  selector: 'app-cadastro',
  templateUrl: './cadastro.component.html',
  styleUrls: ['./cadastro.component.scss']
})
export class CadastroComponent implements OnInit {
  sexo: Sexo[] = [
    {value: 'm', viewValue: 'Masculino'},
    {value: 'f', viewValue: 'Feminino'},
    {value: 't', viewValue: 'Transgênero'},
    {value: 'o', viewValue: 'Outro(s)'}
  ];

  emailFormControl = new FormControl('', [
    Validators.required,
    Validators.email,
  ]);

  constructor() { }

  ngOnInit() {
  }
}
