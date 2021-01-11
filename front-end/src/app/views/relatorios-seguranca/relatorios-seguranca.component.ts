import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { RelatorioAreaSegService } from 'src/app/services/relatorios/relatorio-area-seg.service';

@Component({
  selector: 'app-relatorios-seguranca',
  templateUrl: './relatorios-seguranca.component.html',
  styleUrls: ['./relatorios-seguranca.component.scss']
})
export class RelatoriosSegurancaComponent implements OnInit {



  codFesta = this.router.url.substring(this.router.url.indexOf('&') + 1, this.router.url.indexOf('/', this.router.url.indexOf('&')));
  public problemasPorArea = [];
  public resolucoesPorUsuario = [];
  public emissoesChamadas = [];
  showLegend = true;
  showLabels = true;
  isDoughnut = false;
  legendPosition = 'right';
  colorScheme = {
    domain: ['#5AA454', '#A10A28', '#C7B42C', '#AAAAAA']
  };

  constructor(
    private relAreaSegService: RelatorioAreaSegService,
    private router: Router,
    private translateService: TranslateService
  ) { }

  ngOnInit() {
    this.problemasArea(this.codFesta);
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
        this.chamadasUsuario(codFesta);
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
          this.usuariosPorEmissao(codFesta);
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
}
