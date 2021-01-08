import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RelatorioEstoqueService } from 'src/app/services/relatorios/relatorio-estoque.service';

@Component({
  selector: 'app-relatorios-estoque',
  templateUrl: './relatorios-estoque.component.html',
  styleUrls: ['./relatorios-estoque.component.scss']
})
export class RelatoriosEstoqueComponent implements OnInit {

  codFesta: string;
  quantidadeItemValores = [];

  mySlideOptions={items: 1, dots: true, nav: true};
  myCarouselOptions={items: 80, dots: true, nav: true};

  constructor(public relEstoqueService: RelatorioEstoqueService, public router: Router) { }

  ngOnInit() {
    let idFesta = this.router.url;
    this.codFesta = idFesta.substring(idFesta.indexOf('&') + 1, idFesta.indexOf('/', idFesta.indexOf('&')));
    this.consumoItemEstoque();
    this.quantidadeItemEstoque();
    this.perdaItemEstoque();
  }

  // relatorios estoque
  consumoItemEstoque() {
    this.relEstoqueService.consumoItemEstoque(this.codFesta).subscribe((resp: any) => {
      console.log('consumoItemEstoque');
      console.log(resp);
    });
  }
  perdaItemEstoque() {
    this.relEstoqueService.perdaItemEstoque(this.codFesta).subscribe((resp: any) => {
      console.log('perdaItemEstoque');
      console.log(resp);
    });
  }
  quantidadeItemEstoque() {
    this.relEstoqueService.quantidadeItemEstoque(this.codFesta).subscribe((resp: any) => {
      console.log('quantidadeItemEstoque');
      console.log(resp);
    });
  }

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

}
