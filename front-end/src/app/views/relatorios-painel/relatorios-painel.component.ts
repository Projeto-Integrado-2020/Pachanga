import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { GetFestaService } from 'src/app/services/get-festa/get-festa.service';
import { GetFormsService } from 'src/app/services/get-forms/get-forms.service';
import { GetSheetsService } from 'src/app/services/get-sheets/get-sheets.service';

@Component({
  selector: 'app-relatorios-painel',
  templateUrl: './relatorios-painel.component.html',
  styleUrls: ['./relatorios-painel.component.scss']
})
export class RelatoriosPainelComponent implements OnInit {

  options: FormGroup;
  festa: any;
  festaNome: string;
  columns: any;
  formsResult: any;
  sheets = [];

  constructor(public fb: FormBuilder, public getFestaService: GetFestaService, public router: Router,
              public forms: GetFormsService, public getSheetsService: GetSheetsService) {
    this.options = fb.group({
      bottom: 55,
      top: 0
    });
  }

  ngOnInit() {
    this.getFesta();
  }
  
  getFesta() {
    this.sheets = [];
    let idFesta = this.router.url;
    idFesta = idFesta.substring(idFesta.indexOf('&') + 1, idFesta.indexOf('/', idFesta.indexOf('&')));
    this.getFestaService.acessarFesta(idFesta).subscribe((resp: any) => {
      this.getFestaService.setFarol(false);
      this.festa = resp;
      this.festaNome = resp.nomeFesta;
      this.getQuestionarios(idFesta);
    });
  }

  getQuestionarios(idFesta) {
    this.forms.getQuestionarios(idFesta).subscribe((resp: any) => {
      this.forms.setFarol(false);
      this.formsResult = resp;
      console.log(this.formsResult);
      for (const questionario of this.formsResult) {
        questionario.urlQuestionario = questionario.urlQuestionario.substring(39, questionario.urlQuestionario.length - 20);
        this.getSheets(questionario.urlQuestionario);
      }
    });
  }

  getSheets(urlQuestionario) {
    this.getSheetsService.getSheets(urlQuestionario).subscribe((resp: any) => {
      this.sheets.push(resp);
      console.log(this.sheets);
    });
  }

}
