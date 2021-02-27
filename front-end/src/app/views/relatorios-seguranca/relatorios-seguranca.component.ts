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
  public problemasPorArea = [
    {
    name: 'Camarotes A',
    value:1
    },
    {
    name: 'Camarotes B',
    value:3
    },
    {
    name: 'Camarotes C',
    value:2
    },
    {
    name: 'Pista Centro',
    value:4
    },
    {
    name: 'Pista e Bar A',
    value:1
    },
    {
    name: 'Pista e Bar B',
    value:2
    }
  ];
  public resolucoesPorUsuario = [

    {
      name: 'Fernando D.',
      series: [
        {
          name: this.translateService.instant('RELATARPROB.F'),
          value: 1
        },
        {
          name: this.translateService.instant('RELATARPROB.E'),
          value: 0
        }
      ]
    },
    {
      name: 'Luciano',
      series: [
        {
          name: this.translateService.instant('RELATARPROB.F'),
          value: 1
        },
        {
          name: this.translateService.instant('RELATARPROB.E'),
          value: 2
        }
      ]
    },
    {
      name: 'Hernanes',
      series: [
        {
          name: this.translateService.instant('RELATARPROB.F'),
          value: 1
        },
        {
          name: this.translateService.instant('RELATARPROB.E'),
          value: 1
        }
      ]
    },
    {
      name: 'Simão',
      series: [
        {
          name: this.translateService.instant('RELATARPROB.F'),
          value: 3
        },
        {
          name: this.translateService.instant('RELATARPROB.E'),
          value: 1
        }
      ]
    },
    {
      name:  'Sandra',
      series: [
        {
          name: this.translateService.instant('RELATARPROB.F'),
          value: 0
        },
        {
          name: this.translateService.instant('RELATARPROB.E'),
          value: 1
        }
      ]
    },
    {
      name:  'Camilla Sotero',
      series: [
        {
          name: this.translateService.instant('RELATARPROB.F'),
          value: 2
        },
        {
          name: this.translateService.instant('RELATARPROB.E'),
          value: 0
        }
      ]
    },
    
  ];
  public emissoesChamadas = [
    {
      name: 'Fernando D.',
      value: 7.69
    },
    {
      name: 'Luciano',
      value: 23.07
    },
    {
      name: 'Hernanes',
      value: 15.38
    },
    {
      name: 'Simão',
      value: 30.76
    },
    {
      name: 'Sandra',
      value: 7.69
    },
    {
      name: 'Camilla Sotero',
      value: 15.38
    },
    
  ];
  showLegend = true;
  showLabels = true;
  isDoughnut = false;
  legendPosition = 'right';
  colorScheme = {
    domain: ['#d63333', '#a833d6', '#d68f33', '#d63395', '#d6d333', '#4633d6', '#87d633', '#338dd6', '#33d659', '#33d6bb']
  };

  constructor(
    private relAreaSegService: RelatorioAreaSegService,
    private router: Router,
    private translateService: TranslateService
  ) { }

  ngOnInit() {
    //this.problemasArea(this.codFesta);
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
