import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { GetFestaService } from 'src/app/services/get-festa/get-festa.service';

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
    { name: 'Dose Vodka', value: 80 },
    { name: 'Dose Whisky', value: 45 },
    { name: 'Combo Vodka Energ', value: 26 },
    { name: 'Ceveja', value: 218 },
    { name: 'Água', value: 38 }
  ];

  paises = [
    {
      name: 'Vodka',
      series: [
        {
          name: new Date('2021-01-06T15:39:39.563'),
          value: 10
        },
        {
          name: new Date('2021-01-06T15:40:39.563'),
          value: 5
        },
        {
          name: new Date('2021-01-06T15:40:55.563'),
          value: 12
        }
      ]
    },

    {
      name: 'Whisky',
      series: [
        {
          name: new Date('2021-01-06T11:39:39.563'),
          value: 2
        },
        {
          name: new Date('2021-01-06T12:39:39.563'),
          value: 8
        },
        {
          name: new Date('2021-01-06T13:39:39.563'),
          value: 4
        }
      ]
    },

    {
      name: 'Cerveja',
      series: [
        {
          name: new Date('2021-01-06T15:49:19.563'),
          value: 14
        },
        {
          name: new Date('2021-01-06T15:59:29.563'),
          value: 50
        },
        {
          name: new Date('2021-01-06T16:39:39.563'),
          value: 15
        }
      ]
    },
    {
      name: 'Agua',
      series: [
        {
          name: new Date('2021-01-06T15:19:39.563'),
          value: 6
        },
        {
          name: new Date('2021-01-06T16:29:39.563'),
          value: 14
        },
        {
          name: new Date('2021-01-06T17:29:39.563'),
          value: 14
        },
        {
          name: new Date('2021-01-06T18:49:39.563'),
          value: 14
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
    public router: Router
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
      this.festa = resp;
      this.festaNome = resp.nomeFesta;
    });
  }

}
