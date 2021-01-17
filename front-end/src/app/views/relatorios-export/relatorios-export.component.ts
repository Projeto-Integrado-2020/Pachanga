import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RelatorioEstoqueService } from 'src/app/services/relatorios/relatorio-estoque.service';
import { RelatorioVendaService } from 'src/app/services/relatorios/relatorio-venda.service';
import { TranslateService } from '@ngx-translate/core';
import { RelatorioAreaSegService } from 'src/app/services/relatorios/relatorio-area-seg.service';
import { GetFestaService } from 'src/app/services/get-festa/get-festa.service';
import html2canvas from 'html2canvas';
import { Img, PdfMakeWrapper, Table, Txt } from 'pdfmake-wrapper';
import { MatDialog } from '@angular/material';
import { RelatoriosExportDialogComponent } from '../relatorios-export-dialog/relatorios-export-dialog.component';
import { ProcessingDialogComponent } from '../processing-dialog/processing-dialog.component';
import { RelatorioCheckinService } from 'src/app/services/relatorios/relatorio-checkin.service';

@Component({
  selector: 'app-relatorios-export',
  templateUrl: './relatorios-export.component.html',
  styleUrls: ['./relatorios-export.component.scss']
})
export class RelatoriosExportComponent implements OnInit {

  showLegend = true;
  showLabels = true;
  isDoughnut = false;
  legendPosition = 'right';

  codFesta: string;
  festa: any;
  quantidadeItemValores = [];
  consumoItemValores = [];
  perdaItemValores = [];
  consumoProdutoValores = [];
  problemasPorArea = [];
  resolucoesPorUsuario = [];
  emissoesChamadas = [];
  ingressosDataSet = [];
  ingressosPagosDataSet = [];
  ingressosCompradosEntradasValores = [];
  faixaEtariaValores = [];
  generosValores = [];
  quantidadeEntradasHoraValores = [];
  ingressosFestaCheckedUncheckedValores = [];

  colorScheme = {
    domain: ['#d63333', '#a833d6', '#d68f33', '#d63395', '#d6d333', '#4633d6', '#87d633', '#338dd6', '#33d659', '#33d6bb']
  };

  constructor(public relEstoqueService: RelatorioEstoqueService, public relatorioVendaService: RelatorioVendaService,
              public relAreaSegService: RelatorioAreaSegService, public getFestaService: GetFestaService,
              public relatorioCheckin: RelatorioCheckinService, public router: Router,
              public translateService: TranslateService, public dialog: MatDialog) { }

  ngOnInit() {
    const idFesta = this.router.url;
    this.codFesta = idFesta.substring(idFesta.indexOf('&') + 1, idFesta.indexOf('/', idFesta.indexOf('&')));
    this.resgatarFesta(this.codFesta);
    this.consumoItemEstoque();
    this.quantidadeItemEstoque();
    this.perdaItemEstoque();
    this.consumoProduto();
    this.problemasArea(this.codFesta);
    this.chamadasUsuario(this.codFesta);
    this.usuariosPorEmissao(this.codFesta);
    this.getRelatorioIngressos();
    this.getRelatorioIngressosPagos();
    this.faixaEtaria();
    this.generos();
    this.ingressosFestaCheckedUnchecked();
    this.quantidadeEntradasHora();
  }

  resgatarFesta(codFesta) {
    this.getFestaService.acessarFesta(codFesta).subscribe((resp: any) => {
      this.getFestaService.setFarol(false);
      this.festa = resp;
    });
  }

  faixaEtaria() {
    this.relatorioCheckin.faixaEtaria(this.codFesta).subscribe((resp: any) => {
      const dataSetTemp = [];
      for (const faixaEtaria of Object.keys(resp.quantitadeFaixaEtaria)) {
        dataSetTemp.push({
          name: faixaEtaria,
          value: resp.quantitadeFaixaEtaria[faixaEtaria]
        });
      }
      this.faixaEtariaValores = dataSetTemp;
    });
  }

  generos() {
    this.relatorioCheckin.genero(this.codFesta).subscribe((resp: any) => {
      const dataSetTemp = [];
      for (const genero of Object.keys(resp.quantidadeGenero)) {
        dataSetTemp.push({
          name: genero,
          value: resp.quantidadeGenero[genero]
        });
      }
      this.generosValores = dataSetTemp;
    });
  }

  ingressosFestaCheckedUnchecked() {
    this.relatorioCheckin.checkedUnchecked(this.codFesta).subscribe((resp: any) => {
      const dataSetTemp = [];
      for (const ingressosFestaCheckedUnchecked of Object.keys(resp.ingressoFestaCheckedUnchecked)) {
        const seriesTemp = [];
        for (const data of Object.keys(resp.ingressoFestaCheckedUnchecked[ingressosFestaCheckedUnchecked])) {
          seriesTemp.push(
            {
              name: this.translateService.instant('RELATORIOCHECKIN.NAOENTROU'),
              value: parseInt(data, 10)
            },
            {
              name: this.translateService.instant('RELATORIOCHECKIN.ENTROU'),
              value: parseInt(resp.ingressoFestaCheckedUnchecked[ingressosFestaCheckedUnchecked][data], 10)
            }
          );
        }
        dataSetTemp.push({
          name: ingressosFestaCheckedUnchecked,
          series: seriesTemp
        });
      }
      this.ingressosFestaCheckedUncheckedValores = dataSetTemp;
    });
  }

  quantidadeEntradasHora() {
    this.relatorioCheckin.qtdEntradasHora(this.codFesta).subscribe((resp: any) => {
      const seriesTemp = [];
      for (const quantidadeEntradasHora of Object.keys(resp.quantidadePessoasHora)) {
        seriesTemp.push({
          name: new Date(quantidadeEntradasHora),
          value: resp.quantidadePessoasHora[quantidadeEntradasHora]
        });
      }
      const dataSetTemp = [{
        name: this.translateService.instant('RELATORIOCHECKIN.QTDCHECKIN'),
        series: seriesTemp
      }];
      this.quantidadeEntradasHoraValores = dataSetTemp;
      console.log(this.quantidadeEntradasHoraValores);
    });
  }

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
      // const mockResp = MOCK_AQUI;
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

  problemasArea(codFesta) {
    this.relAreaSegService.problemasArea(codFesta).subscribe((resp: any) => {
        const entries = Object.entries(resp.problemasArea);
        const dataset = [];
        for (const [prop, val] of entries) {
          dataset.push(
            {
              name: prop,
              value: val
            }
          );
        }
        this.problemasPorArea = dataset;
      });
  }


  chamadasUsuario(codFesta) {
    this.relAreaSegService.chamadasUsuario(codFesta).subscribe((resp: any) => {
      const chamadasEmitidas = resp.chamadasEmitidasFuncionario;
      const dataset = [];

      for (const username in chamadasEmitidas) {
        if (chamadasEmitidas.hasOwnProperty(username)) {
          const data = {
            name: username,
            series: [
              {
                name: this.translateService.instant('RELATARPROB.F'),
                value: parseInt(Object.keys(chamadasEmitidas[username])[0], 10)
              },
              {
                name: this.translateService.instant('RELATARPROB.E'),
                value: Object.values(chamadasEmitidas[username])[0]
              }
            ]
          };
          dataset.push(data);

          this.resolucoesPorUsuario = dataset;
        }
      }
    });
  }

  usuariosPorEmissao(codFesta) {
    this.relAreaSegService.usuarioSolucionador(codFesta).subscribe((resp: any) => {
      const dataset = [];
      const solucionadorAlertasSeguranca = resp.solucionadorAlertasSeguranca;
      for (const solucionador in solucionadorAlertasSeguranca) {
        if (solucionadorAlertasSeguranca.hasOwnProperty(solucionador)) {
          dataset.push(
            {
              name: solucionador,
              value: solucionadorAlertasSeguranca[solucionador]
            }
          );
        }
      }
      this.emissoesChamadas = dataset;
    });
  }

  getRelatorioIngressos() {
    this.relatorioVendaService.ingressosFesta(this.codFesta).subscribe((resp: any) => {
      const dataSetTemp = [];
      for (const ingresso of Object.keys(resp.ingressos)) {
        dataSetTemp.push({
          name: ingresso,
          value: resp.ingressos[ingresso]
        });
      }
      this.ingressosDataSet = dataSetTemp;
    });
  }

  getRelatorioIngressosPagos() {
    this.relatorioVendaService.ingressosFestaCompradosPagos(this.codFesta).subscribe((resp: any) => {
      const dataSetTemp = [];
      for (const ingresso of Object.keys(resp.ingressosCompradosPagos)) {
        const serieTemp = [];
        for (const data of Object.keys(resp.ingressosCompradosPagos[ingresso])) {
          serieTemp.push(
            {
              name: this.translateService.instant('RELATORIOVENDA.PAGO'),
              value: data
            },
            {
              name: this.translateService.instant('RELATORIOVENDA.COMPRADO'),
              value: resp.ingressosCompradosPagos[ingresso][data]
            }
          );
        }
        dataSetTemp.push({
          name: ingresso,
          series: serieTemp
        });
      }
      this.ingressosPagosDataSet = dataSetTemp;
    });
  }

  gerarPDFRelatorios() {
    this.openDialogProcessing();
    const pdf = new PdfMakeWrapper();
    pdf.info({
      title: 'Relatórios Pachanga',
      author: 'EventManager',
      subject: 'Relatórios Pachanga'
    });
    this.criarPaginaPDF(pdf, this.festa).then(() => {
      pdf.create().getBase64((base64: any) => {
        this.dialog.closeAll();
        this.openDialogExport(base64);
      });
    });
  }

  openDialogExport(pdf) {
    this.dialog.open(RelatoriosExportDialogComponent, {
      width: '55rem',
      data: {
        pdf,
        codFesta: this.codFesta
      }
    });
  }

  openDialogProcessing() {
    this.dialog.open(ProcessingDialogComponent, {
        width: '20rem',
        disableClose: true,
        data: {
            tipo: 'EXPORTPDF'
        }
    });
  }

  async criarPaginaPDF(pdf, festa) {
    pdf.styles({
      textosLow: {
        fontSize: 10,
        margin: 5
      },
      header: {
        color: '#9c2152',
        bold: true,
        fontSize: 20,
      },
      title: {
        color: '#6b219c',
        bold: true,
        fontSize: 25,
        margin: [5, 5, 5, 15]
      },
      tableHeader: {
        margin: [15, 15, 15, 25]
      },
      tituloRelatorio: {
        bold: true,
        fontSize: 15,
        margin: [10, 10, 0, 5]
      },
      imagem: {
        margin: [0, 10]
      }
    });

    pdf.add(new Txt('Pachanga').alignment('center').style('header').end);
    pdf.add(new Txt('https://pachanga.herokuapp.com/').alignment('center').style('textosLow').end);
    pdf.add(new Table([
      [
        new Txt(festa.nomeFesta).style('title').end
      ],
      [
        new Txt('Horário da festa: ' + this.processarDateTime(festa.horarioInicioFesta) + ' - ' +
            this.processarDateTime(festa.horarioFimFesta)).style('textosLow').end
      ],
      [
        new Txt('Local: ' + festa.codEnderecoFesta).style('textosLow').end
      ]
    ]).widths([ 500, 10 ]).style('tableHeader').end);
    await this.montarImagensEstoque(pdf);
    await this.montarImagensSeguranca(pdf);
    await this.montarImagensVenda(pdf);
    await this.montarImagensCheckin(pdf);
  }

  async montarImagensEstoque(pdf) {
    if (this.consumoItemValores.length > 0) {
      pdf.add(new Txt(this.translateService.instant('RELATORIOESTOQUE.CONSUMOESTOQUE')).alignment('center').style('tituloRelatorio').end);
      for (const data of this.consumoItemValores) {
        const element = document.getElementById('consumoItem-' + data.nomeEstoque);
        const url = await html2canvas(element.parentElement);
        pdf.add(await new Img(url.toDataURL('image/png')).width('515').style('imagem').build());
      }
    }
    if (this.perdaItemValores.length > 0) {
      pdf.add(new Txt(this.translateService.instant('RELATORIOESTOQUE.PERDAESTOQUE')).alignment('center').style('tituloRelatorio').end);
      for (const data of this.perdaItemValores) {
        const element = document.getElementById('perdaItem-' + data.nomeEstoque);
        const url = await html2canvas(element.parentElement);
        pdf.add(await new Img(url.toDataURL('image/png')).width('515').style('imagem').build());
      }
    }
    if (this.quantidadeItemValores.length > 0) {
      pdf.add(new Txt(this.translateService.instant('RELATORIOESTOQUE.QUANTIDADEESTOQUE'))
        .alignment('center')
        .style('tituloRelatorio').end);
      for (const data of this.quantidadeItemValores) {
        const element = document.getElementById('quantidadeItem-' + data.nomeEstoque);
        const url = await html2canvas(element.parentElement);
        pdf.add(await new Img(url.toDataURL('image/png')).width('515').style('imagem').build());
      }
    }
    if (this.consumoProdutoValores.length > 0) {
      pdf.add(new Txt(this.translateService.instant('RELATORIOESTOQUE.CONSUMOPRODUTO')).alignment('center').style('tituloRelatorio').end);
      const element = document.getElementById('consumoProduto');
      const url = await html2canvas(element);
      pdf.add(await new Img(url.toDataURL('image/png')).width('515').style('imagem').build());
    }
  }

  async montarImagensSeguranca(pdf) {
    if (this.problemasPorArea.length > 0) {
      pdf.add(new Txt(this.translateService.instant('RELATORIOSEGURANCA.PROBAREA')).alignment('center').style('tituloRelatorio').end);
      const element = document.getElementById('problemasPorArea');
      const url = await html2canvas(element);
      pdf.add(await new Img(url.toDataURL('image/png')).width('515').style('imagem').build());
    }
    if (this.resolucoesPorUsuario.length > 0) {
      pdf.add(new Txt(this.translateService.instant('RELATORIOSEGURANCA.CONCSOL')).alignment('center').style('tituloRelatorio').end);
      const element = document.getElementById('resolucoesPorUsuario');
      const url = await html2canvas(element);
      pdf.add(await new Img(url.toDataURL('image/png')).width('515').style('imagem').build());
    }
    if (this.emissoesChamadas.length > 0) {
      pdf.add(new Txt(this.translateService.instant('RELATORIOSEGURANCA.CONCXTOTAL')).alignment('center').style('tituloRelatorio').end);
      const element = document.getElementById('emissoesChamadas');
      const url = await html2canvas(element);
      pdf.add(await new Img(url.toDataURL('image/png')).width('515').style('imagem').build());
    }
  }

  async montarImagensVenda(pdf) {
    if (this.ingressosDataSet.length > 0) {
      pdf.add(new Txt(this.translateService.instant('RELATORIOVENDA.INGRESSOS')).alignment('center').style('tituloRelatorio').end);
      const element = document.getElementById('ingressos');
      const url = await html2canvas(element);
      pdf.add(await new Img(url.toDataURL('image/png')).width('515').style('imagem').build());
    }
    if (this.ingressosPagosDataSet.length > 0) {
      pdf.add(new Txt(this.translateService.instant('RELATORIOVENDA.INGRESSOPAGOCOMPRADO'))
        .alignment('center')
        .style('tituloRelatorio').end);
      const element = document.getElementById('ingressosPagos');
      const url = await html2canvas(element);
      pdf.add(await new Img(url.toDataURL('image/png')).width('515').style('imagem').build());
    }
  }

  async montarImagensCheckin(pdf) {
    if (this.faixaEtariaValores.length > 0) {
      pdf.add(new Txt(this.translateService.instant('RELATORIOCHECKIN.FAIXAETARIA')).alignment('center').style('tituloRelatorio').end);
      const element = document.getElementById('faixaEtaria');
      const url = await html2canvas(element);
      pdf.add(await new Img(url.toDataURL('image/png')).width('515').style('imagem').build());
    }
    if (this.generosValores.length > 0) {
      pdf.add(new Txt(this.translateService.instant('RELATORIOCHECKIN.GENERO'))
        .alignment('center')
        .style('tituloRelatorio').end);
      const element = document.getElementById('generos');
      const url = await html2canvas(element);
      pdf.add(await new Img(url.toDataURL('image/png')).width('515').style('imagem').build());
    }
    if (this.quantidadeEntradasHoraValores.length > 0) {
      pdf.add(new Txt(this.translateService.instant('RELATORIOCHECKIN.DTHRCHECKIN')).alignment('center').style('tituloRelatorio').end);
      const element = document.getElementById('quantidadeEntradasHora');
      const url = await html2canvas(element);
      pdf.add(await new Img(url.toDataURL('image/png')).width('515').style('imagem').build());
    }
    if (this.ingressosFestaCheckedUncheckedValores.length > 0) {
      pdf.add(new Txt(this.translateService.instant('RELATORIOCHECKIN.RELACAOCOMPRACHECKIN'))
        .alignment('center')
        .style('tituloRelatorio').end);
      const element = document.getElementById('checkedUnchecked');
      const url = await html2canvas(element);
      pdf.add(await new Img(url.toDataURL('image/png')).width('515').style('imagem').build());
    }
  }

  processarDateTime(datetime) {
    const datetimeSplit = datetime.split(' ');
    const data = datetimeSplit[0].split('-').reverse().join('/');

    return data + ', ' + datetimeSplit[1].slice(0, 5);
  }

}
