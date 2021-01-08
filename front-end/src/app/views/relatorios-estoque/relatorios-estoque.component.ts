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
  consumoItemValores = [];
  perdaItemValores = [];
  consumoProdutoValores = [];

  mySlideOptions={items: 1, dots: true, nav: true};
  myCarouselOptions={items: 80, dots: true, nav: true};

  colorScheme = {
    domain: ['#5AA454', '#E44D25', '#CFC0BB', '#7aa3e5', '#a8385d', '#aae3f5']
  };

  constructor(public relEstoqueService: RelatorioEstoqueService, public router: Router) { }

  ngOnInit() {
    let idFesta = this.router.url;
    this.codFesta = idFesta.substring(idFesta.indexOf('&') + 1, idFesta.indexOf('/', idFesta.indexOf('&')));
    this.consumoItemEstoque();
    this.quantidadeItemEstoque();
    this.perdaItemEstoque();
    this.consumoProduto();
  }

  // relatorios estoque
  consumoItemEstoque() {
    this.relEstoqueService.consumoItemEstoque(this.codFesta).subscribe((resp: any) => {
      const mockResp = [
        {
          "nomeEstoque": "Principal",
          "informacoesEstoque": [
            {
              "nomeProduto": "teste",
              "quantidadeHora": {
                "2021-01-07T14:11:15.998030": 5,
                "2021-01-07T14:17:49.656018": 20
              }
            },
            {
              "nomeProduto": "teste1",
              "quantidadeHora": {
                "2021-01-07T14:12:15.998030": 1,
                "2021-01-07T14:13:49.656018": 2,
                "2021-01-07T14:15:49.656018": 5
              }
            },
            {
              "nomeProduto": "teste3",
              "quantidadeHora": {
                "2021-01-07T14:19:15.998030": 10,
                "2021-01-07T14:25:49.656018": 25,
                "2021-01-07T14:32:49.656018": 50,
                "2021-01-07T15:19:49.656018": 78
              }
            }
          ]
        },
        {
          "nomeEstoque": "oi",
          "informacoesEstoque": [
            {
              "nomeProduto": "teste",
              "quantidadeHora": {
                "2021-01-07T11:11:15.998030": 0,
                "2021-01-07T12:17:49.656018": 1,
                "2021-01-07T13:18:49.656018": 8,
                "2021-01-07T14:19:49.656018": 14
              }
            },
            {
              "nomeProduto": "teste1",
              "quantidadeHora": {
                "2021-01-07T14:11:15.998030": 50,
                "2021-01-07T14:17:49.656018": 65,
                "2021-01-08T14:18:49.656018": 78,
                "2021-01-09T14:19:49.656018": 82
              }
            }
          ]
        }
      ];
      const finalTemp = [];
      for (const estoque of resp) {
        const produtoList = [];
        let maiorHora = new Date('2020-01-01T00:00:00.000000');
        for (const produto of estoque.informacoesEstoque) {
          const series = [];
          for (const hora of Object.keys(produto.quantidadeHora)) {
            if (new Date(hora) > maiorHora) {
              maiorHora = new Date(hora);
            }
            series.push({
              name: new Date(hora),
              value: produto.quantidadeHora[hora]
            });
          }
          produtoList.push({
            name: produto.nomeProduto,
            series
          });
        }
        for (const data of produtoList) {
          data.series.push({
            name: new Date(maiorHora),
            value: data.series[data.series.length - 1].value
          });
        }
        finalTemp.push({
          nomeEstoque: estoque.nomeEstoque,
          dataSet: produtoList
        });
      }
      this.consumoItemValores = finalTemp;
    });
  }
  perdaItemEstoque() {
    this.relEstoqueService.perdaItemEstoque(this.codFesta).subscribe((resp: any) => {
      const mockResp = [
        {
          "nomeEstoque": "Principal",
          "informacoesEstoque": [
            {
              "nomeProduto": "teste",
              "quantidadeHora": {
                "2021-01-07T14:11:15.998030": 0,
                "2021-01-07T14:17:49.656018": 10,
                "2021-01-07T14:18:49.656018": 15,
                "2021-01-07T14:19:49.656018": 20
              }
            },
            {
              "nomeProduto": "teste1",
              "quantidadeHora": {
                "2021-01-07T14:12:15.998030": 0,
                "2021-01-07T14:13:49.656018": 5,
                "2021-01-07T14:15:49.656018": 6,
                "2021-01-07T14:17:49.656018": 7
              }
            },
            {
              "nomeProduto": "teste3",
              "quantidadeHora": {
                "2021-01-07T14:19:15.998030": 0,
                "2021-01-07T14:25:49.656018": 1
              }
            }
          ]
        },
        {
          "nomeEstoque": "oi",
          "informacoesEstoque": [
            {
              "nomeProduto": "teste",
              "quantidadeHora": {
                "2021-01-07T11:11:15.998030": 0
              }
            },
            {
              "nomeProduto": "teste1",
              "quantidadeHora": {
                "2021-01-07T14:11:15.998030": 0,
                "2021-01-07T14:17:49.656018": 5,
                "2021-01-08T14:18:49.656018": 6
              }
            }
          ]
        }
      ];
      const finalTemp = [];
      for (const estoque of resp) {
        const produtoList = [];
        let maiorHora = new Date('2020-01-01T00:00:00.000000');
        for (const produto of estoque.informacoesEstoque) {
          const series = [];
          for (const hora of Object.keys(produto.quantidadeHora)) {
            if (new Date(hora) > maiorHora) {
              maiorHora = new Date(hora);
            }
            series.push({
              name: new Date(hora),
              value: produto.quantidadeHora[hora]
            });
          }
          produtoList.push({
            name: produto.nomeProduto,
            series
          });
        }
        for (const data of produtoList) {
          data.series.push({
            name: new Date(maiorHora),
            value: data.series[data.series.length - 1].value
          });
        }
        finalTemp.push({
          nomeEstoque: estoque.nomeEstoque,
          dataSet: produtoList
        });
      }
      this.perdaItemValores = finalTemp;
    });
  }
  quantidadeItemEstoque() {
    this.relEstoqueService.quantidadeItemEstoque(this.codFesta).subscribe((resp: any) => {
      const mockResp = [
        {
          "nomeEstoque": "Principal",
          "informacoesEstoque": [
            {
              "nomeProduto": "teste",
              "quantidadeHora": {
                "2021-01-07T14:11:15.998030": 100,
                "2021-01-07T14:17:49.656018": 55
              }
            },
            {
              "nomeProduto": "teste1",
              "quantidadeHora": {
                "2021-01-07T14:12:15.998030": 125,
                "2021-01-07T14:13:49.656018": 120,
                "2021-01-07T14:15:49.656018": 155
              }
            },
            {
              "nomeProduto": "teste3",
              "quantidadeHora": {
                "2021-01-07T14:19:15.998030": 100,
                "2021-01-07T14:25:49.656018": 95,
                "2021-01-07T14:32:49.656018": 75,
                "2021-01-07T15:19:49.656018": 68
              }
            }
          ]
        },
        {
          "nomeEstoque": "oi",
          "informacoesEstoque": [
            {
              "nomeProduto": "teste",
              "quantidadeHora": {
                "2021-01-07T11:11:15.998030": 100,
                "2021-01-07T12:17:49.656018": 55,
                "2021-01-07T13:18:49.656018": 78,
                "2021-01-07T14:19:49.656018": 76
              }
            },
            {
              "nomeProduto": "teste1",
              "quantidadeHora": {
                "2021-01-07T14:11:15.998030": 100,
                "2021-01-07T14:17:49.656018": 55,
                "2021-01-08T14:18:49.656018": 78,
                "2021-01-09T14:19:49.656018": 120
              }
            }
          ]
        }
      ];
      const finalTemp = [];
      for (const estoque of resp) {
        const produtoList = [];
        let maiorHora = new Date('2020-01-01T00:00:00.000000');
        for (const produto of estoque.informacoesEstoque) {
          const series = [];
          for (const hora of Object.keys(produto.quantidadeHora)) {
            if (new Date(hora) > maiorHora) {
              maiorHora = new Date(hora);
            }
            series.push({
              name: new Date(hora),
              value: produto.quantidadeHora[hora]
            });
          }
          produtoList.push({
            name: produto.nomeProduto,
            series
          });
        }
        for (const data of produtoList) {
          data.series.push({
            name: new Date(maiorHora),
            value: data.series[data.series.length - 1].value
          });
        }
        finalTemp.push({
          nomeEstoque: estoque.nomeEstoque,
          dataSet: produtoList
        });
      }
      this.quantidadeItemValores = finalTemp;
    });
  }
  consumoProduto() {
    this.relEstoqueService.consumoProduto(this.codFesta).subscribe((resp: any) => {
      const mockResp = [
        {
          nomeProduto: 'Produto 1',
          quantidadeConsumo: 580
        },
        {
          nomeProduto: 'Produto 2',
          quantidadeConsumo: 220
        },
        {
          nomeProduto: 'Produto 3',
          quantidadeConsumo: 137
        },
        {
          nomeProduto: 'Produto 4',
          quantidadeConsumo: 782
        },
        {
          nomeProduto: 'Produto 5',
          quantidadeConsumo: 568
        }
      ];
      const finalTemp = [];
      for (const produto of resp) {
        finalTemp.push({
          name: produto.nomeProduto,
          value: produto.quantidadeConsumo
        });
      }
      this.consumoProdutoValores = finalTemp;
    });
  }

  formatDate(value) {
    let formatOptions;
    if (value.getSeconds() !== 0) {
      formatOptions = { second: '2-digit' };
    } else if (value.getMinutes() !== 0) {
      formatOptions = { hour: '2-digit', minute: '2-digit' };
    } else if (value.getHours() !== 0) {
      formatOptions = { hour: '2-digit', minute: '2-digit' };
    } else if (value.getDate() !== 1) {
      formatOptions = value.getDay() === 0 ? { month: 'short', day: '2-digit' } : { weekday: 'short', day: '2-digit', month: '2-digit' };
    } else if (value.getMonth() !== 0) {
      formatOptions = { month: 'long' };
    } else {
      formatOptions = { year: 'numeric' };
    }
    return new Intl.DateTimeFormat('pt-br', formatOptions).format(value);
  }

  toolTipDate(value) {
    let formatOptions;
    if (value.getSeconds() !== 0) {
      formatOptions = { day: '2-digit', month: '2-digit', hour: '2-digit', minute: '2-digit', second: '2-digit' };
    }
    return new Intl.DateTimeFormat('pt-br', formatOptions).format(value);
  }
}
