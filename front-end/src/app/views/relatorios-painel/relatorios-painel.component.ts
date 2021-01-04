import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { GetFestaService } from 'src/app/services/get-festa/get-festa.service';
import { GetFormsService } from 'src/app/services/get-forms/get-forms.service';
import { GetSheetsService } from 'src/app/services/get-sheets/get-sheets.service';
import { RelatorioAreaSegService } from 'src/app/services/relatorios/relatorio-area-seg.service';
import { RelatorioEstoqueService } from 'src/app/services/relatorios/relatorio-estoque.service';

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

  saleData = [
    { name: "Dose Vodka", value: 80 },
    { name: "Dose Whisky", value: 45 },
    { name: "Combo Vodka Energ", value: 26 },
    { name: "Ceveja", value: 218 },
    { name: "Água", value: 38 }
  ];

  paises = [
    {
      "name": "Vodka",
      "series": [
        {
          "name": "1990",
          "value": 62000000
        },
        {
          "name": "2010",
          "value": 73000000
        },
        {
          "name": "2011",
          "value": 89400000
        }
      ]
    },
  
    {
      "name": "Whisky",
      "series": [
        {
          "name": "1990",
          "value": 250000000
        },
        {
          "name": "2010",
          "value": 309000000
        },
        {
          "name": "2011",
          "value": 311000000
        }
      ]
    },
  
    {
      "name": "Cerveja",
      "series": [
        {
          "name": "1990",
          "value": 58000000
        },
        {
          "name": "2010",
          "value": 50000020
        },
        {
          "name": "2011",
          "value": 58000000
        }
      ]
    },
    {
      "name": "Agua",
      "series": [
        {
          "name": "1990",
          "value": 57000000
        },
        {
          "name": "2010",
          "value": 62000000
        }
      ]
    }
  ];
  

  colorScheme = {
    domain: ['#5AA454', '#E44D25', '#CFC0BB', '#7aa3e5', '#a8385d', '#aae3f5']
  };


  constructor(
    private fb: FormBuilder,
    public getFestaService: GetFestaService,
    public router: Router,
    public relEstoqueService: RelatorioEstoqueService,
    public relAreaSegService: RelatorioAreaSegService,
    public forms: GetFormsService,
    public getSheetsService: GetSheetsService
    ) {
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
      
      // this.problemasArea();
      // this.chamadasUsuario();
      // this.usuarioSolucionador();
      // this.consumoItemEstoque();
      // this.perdaItemEstoque();
      // this.quantidadeItemEstoque();
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
      this.problemasArea();
    });
  }
  // relatorios areaseg
  problemasArea() {
    this.relAreaSegService.problemasArea().subscribe((resp: any) => {
        console.log('problemasArea');
        console.log(resp);
        this.chamadasUsuario();
      });
  }
  chamadasUsuario() {
    this.relAreaSegService.chamadasUsuario().subscribe((resp: any) => {
      console.log('chamadasUsuario');
      console.log(resp);
      this.usuarioSolucionador();
    });
  }
  usuarioSolucionador() {
    this.relAreaSegService.usuarioSolucionador().subscribe((resp: any) => {
      console.log('usuarioSolucionador');
      console.log(resp);
      this.consumoItemEstoque();
    });
  }

  // relatorios estoque
  consumoItemEstoque() {
    this.relEstoqueService.consumoItemEstoque().subscribe((resp: any) => {
      console.log('consumoItemEstoque');
      console.log(resp);
      this.perdaItemEstoque();
    });
  }
  perdaItemEstoque() {
    this.relEstoqueService.perdaItemEstoque().subscribe((resp: any) => {
      console.log('perdaItemEstoque');
      console.log(resp);
      this.quantidadeItemEstoque();
    });
  }
  quantidadeItemEstoque() {
    this.relEstoqueService.quantidadeItemEstoque().subscribe((resp: any) => {
      console.log('quantidadeItemEstoque');
      console.log(resp);
    });
  }

}
