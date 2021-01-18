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

  mySlideOptions = { items: 1, dots: true, nav: true };
  myCarouselOptions = { items: 80, dots: true, nav: true };

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
}
