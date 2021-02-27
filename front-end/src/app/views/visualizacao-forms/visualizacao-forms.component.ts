import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { GetFestaService } from 'src/app/services/get-festa/get-festa.service';
import { GetFormsService } from 'src/app/services/get-forms/get-forms.service';
import { GetSheetsService } from 'src/app/services/get-sheets/get-sheets.service';
import { RelatorioAreaSegService } from 'src/app/services/relatorios/relatorio-area-seg.service';
import { RelatorioEstoqueService } from 'src/app/services/relatorios/relatorio-estoque.service';

@Component({
  selector: 'app-visualizacao-forms',
  templateUrl: './visualizacao-forms.component.html',
  styleUrls: ['./visualizacao-forms.component.scss']
})
export class VisualizacaoFormsComponent implements OnInit {

  festa: any;
  festaNome: string;
  columns: any;
  formsResult: any;
  sheets = [];

  constructor(
    public getFestaService: GetFestaService,
    public router: Router,
    public relEstoqueService: RelatorioEstoqueService,
    public relAreaSegService: RelatorioAreaSegService,
    public forms: GetFormsService,
    public getSheetsService: GetSheetsService
    ) {}

  ngOnInit() {
    this.getQuestionarios();
  }

  getQuestionarios() {
    let idFesta = this.router.url;
    idFesta = idFesta.substring(idFesta.indexOf('&') + 1, idFesta.indexOf('/', idFesta.indexOf('&')));
    this.forms.getQuestionarios(idFesta).subscribe((resp: any) => {
      this.formsResult = resp;
      for (const questionario of this.formsResult) {
        questionario.urlQuestionario = questionario.urlQuestionario.
        substring(39, questionario.urlQuestionario.length - questionario.urlQuestionario.
          substring(questionario.urlQuestionario.indexOf('', 83)).length);
        this.getSheets(questionario.urlQuestionario);
      }
    });
  }

  getSheets(urlQuestionario) {
    this.getSheetsService.getSheets(urlQuestionario).subscribe((resp: any) => {
      this.sheets.push(resp);
    });
  }

}
