import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RelatorioAreaSegService } from 'src/app/services/relatorios/relatorio-area-seg.service';

@Component({
  selector: 'app-relatorios-seguranca',
  templateUrl: './relatorios-seguranca.component.html',
  styleUrls: ['./relatorios-seguranca.component.scss']
})
export class RelatoriosSegurancaComponent implements OnInit {



  codFesta = this.router.url.substring(this.router.url.indexOf('&') + 1, this.router.url.indexOf('/', this.router.url.indexOf('&')));
  public problemasPorArea = []
  public resolucoesPorUsuario = []
  public emissoesChamadas = []
  showLegend: boolean = true;
  showLabels: boolean = true;
  isDoughnut: boolean = false;
  legendPosition: string = 'below';
  colorScheme = {
    domain: ['#5AA454', '#A10A28', '#C7B42C', '#AAAAAA']
  };

  constructor(
    private relAreaSegService: RelatorioAreaSegService,
    private router: Router
  ) { }

  ngOnInit() {
    this.problemasArea(this.codFesta)
  }

  problemasArea(codFesta) {
    this.relAreaSegService.problemasArea(codFesta).subscribe((resp: any) => {

        let entries = Object.entries(resp.problemasArea)
        let dataset = []
        for(const [prop, val] of entries){
          dataset.push(
            {
              name: prop,
              value: val
            }
          )
        }

        this.problemasPorArea = dataset

        this.chamadasUsuario(codFesta)
      });
  }


  chamadasUsuario(codFesta) {
    this.relAreaSegService.chamadasUsuario(codFesta).subscribe((resp: any) => {
      console.log('chamadasUsuario');
      console.log(resp);
      let chamadasEmitidas = resp.chamadasEmitidasFuncionario
      let dataset = []

      for(let username in chamadasEmitidas){
        let entry = Object.entries(chamadasEmitidas[username])
        let data = {
          name: username,
          series: [
            {
              name: "Finalizado",
              value: parseInt(Object.keys(chamadasEmitidas[username])[0])
            },
            {
              name: "Engano",
              value: Object.values(chamadasEmitidas[username])[0]
            }
          ]
        }
        dataset.push(data)
      }

      this.resolucoesPorUsuario = dataset;
      console.log(this.resolucoesPorUsuario)
      this.usuariosPorEmissao(codFesta);
    });
  }

  usuariosPorEmissao(codFesta) {
    this.relAreaSegService.usuarioSolucionador(codFesta).subscribe((resp: any) => {
      let dataset = []
      let solucionadorAlertasSeguranca = resp.solucionadorAlertasSeguranca
      for(let solucionador in solucionadorAlertasSeguranca){
        dataset.push(
          {
            name: solucionador,
            value: solucionadorAlertasSeguranca[solucionador]
          }
        )
      }
      
      console.log('usuariosPorEmissao');
      console.log(resp);
      this.emissoesChamadas = dataset;
      console.log(dataset)
    });
  }

  
  onSelect(data): void {
    console.log('Item clicked', JSON.parse(JSON.stringify(data)));
  }

  onActivate(data): void {
    console.log('Activate', JSON.parse(JSON.stringify(data)));
  }

  onDeactivate(data): void {
    console.log('Deactivate', JSON.parse(JSON.stringify(data)));
  }

}
