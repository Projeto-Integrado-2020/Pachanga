import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { GetFestaService } from 'src/app/services/get-festa/get-festa.service';

@Component({
  selector: 'app-leitor-qr',
  templateUrl: './leitor-qr.component.html',
  styleUrls: ['./leitor-qr.component.scss']
})
export class LeitorQrComponent implements OnInit {

  festa: any;
  festaNome: string;
  scannerEnabled = true;
  form: any;
  constructor(public getFestaService: GetFestaService, public router: Router,
              public formBuilder: FormBuilder) { }

  get f() { return this.form.controls; }

  ngOnInit() {
    this.resgatarFesta();

    this.form = this.formBuilder.group({
      codIngresso: new FormControl('', Validators.required)
    });
  }

  resgatarFesta() {
    let idFesta = this.router.url;
    idFesta = idFesta.substring(idFesta.indexOf('&') + 1, idFesta.indexOf('/', idFesta.indexOf('&')));
    this.getFestaService.acessarFesta(idFesta).subscribe((resp: any) => {
      this.getFestaService.setFarol(false);
      this.festa = resp;
      this.festaNome = resp.nomeFesta;
    });
  }

  scanSuccessHandler(event: any) {
    this.scannerEnabled = false;
    console.log(event);
  }

  public enableScanner() {
    this.scannerEnabled = !this.scannerEnabled;
  }

}
