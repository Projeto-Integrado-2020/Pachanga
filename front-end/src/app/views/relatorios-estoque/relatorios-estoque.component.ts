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

  mySlideOptions = {items: 1, dots: true, nav: true};
  myCarouselOptions = {items: 80, dots: true, nav: true};

  colorScheme = {
    domain: ['#d63333', '#a833d6', '#d68f33', '#d63395', '#d6d333', '#4633d6', '#87d633', '#338dd6', '#33d659', '#33d6bb']
  };

  constructor(public relEstoqueService: RelatorioEstoqueService, public router: Router) { }

  ngOnInit() {
    const idFesta = this.router.url;
    this.codFesta = idFesta.substring(idFesta.indexOf('&') + 1, idFesta.indexOf('/', idFesta.indexOf('&')));
    this.consumoItemEstoque();
    this.quantidadeItemEstoque();
    this.perdaItemEstoque();
    this.consumoProduto();
  }

  // relatorios estoque
  consumoItemEstoque() {
    this.relEstoqueService.consumoItemEstoque(this.codFesta).subscribe((resp: any) => {
      // const mockResp = MOCK_AQUI;
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
      // const mockResp = MOCK_AQUI;
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
      // const mockResp = MOCK_AQUI;
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
          nomeProduto: 'Brahma',
          quantidadeConsumo: 3486
        },
        {
          nomeProduto: 'Skol',
          quantidadeConsumo: 4072
        },
        {
          nomeProduto: 'Itaipava',
          quantidadeConsumo: 2991
        },
        {
          nomeProduto: 'Vodka',
          quantidadeConsumo: 3030
        },
        {
          nomeProduto: 'Whiskey',
          quantidadeConsumo: 1430
        },
        {
          nomeProduto: 'Conhaque',
          quantidadeConsumo: 2680
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

  formatDate(value): any {
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

  toolTipDate(value): any {
    let formatOptions;
    if (value.getSeconds() !== 0) {
      formatOptions = { day: '2-digit', month: '2-digit', hour: '2-digit', minute: '2-digit', second: '2-digit' };
    }
    return new Intl.DateTimeFormat('pt-br', formatOptions).format(value);
  }
}
