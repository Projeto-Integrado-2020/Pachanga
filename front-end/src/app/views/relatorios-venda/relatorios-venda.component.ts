import { Component, OnInit } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { RelatorioVendaService } from 'src/app/services/relatorios/relatorio-venda.service';
@Component({
  selector: 'app-relatorios-venda',
  templateUrl: './relatorios-venda.component.html',
  styleUrls: ['./relatorios-venda.component.scss']
})
export class RelatoriosVendaComponent implements OnInit {

  codFesta: string;
  ingressosDataSet = [];
  ingressosPagosDataSet = [];
  lucroLotesDataSet = [];
  lucroFestaDataSet = [];

  colorScheme = {
    domain: ['#d63333', '#a833d6', '#d68f33', '#d63395', '#d6d333', '#4633d6', '#87d633', '#338dd6', '#33d659', '#33d6bb']
  };

  constructor(public relatorioVendaService: RelatorioVendaService, public router: Router, public translateService: TranslateService) {
  }

  ngOnInit() {
    const idFesta = this.router.url;
    this.codFesta = idFesta.substring(idFesta.indexOf('&') + 1, idFesta.indexOf('/', idFesta.indexOf('&')));
    this.getRelatorioIngressos();
    this.getRelatorioIngressosPagos();
    this.getRelatorioLucro();
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

  getRelatorioLucro() {
    this.relatorioVendaService.lucroFesta(this.codFesta).subscribe((resp: any) => {
      let dataSetTemp = [];
      for (const lucroLote of Object.keys(resp.infoLucroEsperado.lucroLote)) {
        const serieTemp = [];
        serieTemp.push(
          {
            name: this.translateService.instant('RELATORIOVENDA.LUCROREAL'),
            value: parseFloat(resp.infoLucroReal.lucroLote[lucroLote])
          },
          {
            name: this.translateService.instant('RELATORIOVENDA.LUCROESP'),
            value: parseFloat(resp.infoLucroEsperado.lucroLote[lucroLote]) - parseFloat(resp.infoLucroReal.lucroLote[lucroLote])
          }
        );
        dataSetTemp.push({
          name: lucroLote,
          series: serieTemp
        });
      }
      this.lucroLotesDataSet = dataSetTemp;
      dataSetTemp = [{
        name: resp.nomeFesta,
        series: [
          {
            name: this.translateService.instant('RELATORIOVENDA.LUCROREAL'),
            value: parseFloat(resp.infoLucroReal.lucroTotal)
          },
          {
            name: this.translateService.instant('RELATORIOVENDA.LUCROESP'),
            value: parseFloat(resp.infoLucroEsperado.lucroTotal) - parseFloat(resp.infoLucroReal.lucroTotal)
          }
        ]
      }];
      this.lucroFestaDataSet = dataSetTemp;
    });
  }
}
