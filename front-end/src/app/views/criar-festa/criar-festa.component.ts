import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-criar-festa',
  templateUrl: './criar-festa.component.html',
  styleUrls: ['./criar-festa.component.scss']
})
export class CriarFestaComponent implements OnInit {

  public form: FormGroup;

  constructor(public formBuilder: FormBuilder) { }

  ngOnInit() {
    this.form = this.formBuilder.group({
      nomeFesta: new FormControl('', Validators.required),
      descFesta: new FormControl('', Validators.required),
      endereco: new FormControl('', Validators.required),
      inicioData: new FormControl('', Validators.required),
      fimData: new FormControl('', Validators.required),
      inicioHora: new FormControl('', Validators.required),
      fimHora: new FormControl('', Validators.required),
      organizador: new FormControl('', Validators.required),
      descOrganizador: new FormControl('', Validators.required),
    });
  }

  get f() { return this.form.controls; }

}
