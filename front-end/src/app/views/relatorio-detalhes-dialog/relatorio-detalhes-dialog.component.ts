import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material';
import { GetHistoricoSegurancaService } from 'src/app/services/get-historico-seguranca/get-historico-seguranca.service';

@Component({
  selector: 'app-relatorio-detalhes-dialog',
  templateUrl: './relatorio-detalhes-dialog.component.html',
  styleUrls: ['./relatorio-detalhes-dialog.component.scss']
})
export class RelatorioDetalhesDialogComponent implements OnInit {

  data: any;
  codFesta: string;
  areaProblema = {problemasArea: []};
  usuarioProblemas = [];
  relatorio: string;
  chamadasUsuario: any;

  constructor(@Inject(MAT_DIALOG_DATA) data, public historicoSeguranca: GetHistoricoSegurancaService) {
    this.data = data.data;
    this.codFesta = data.codFesta;
    this.relatorio = data.relatorio;
    this.chamadasUsuario = data.chamadasUsuario;
  }

  ngOnInit() {
    if (this.relatorio === 'PROBAREA') {
      this.relatorioProblemaArea();
    } else if (this.relatorio === 'CONCSOL') {
      this.relatorioProblemaUsuario();
    }
  }

  relatorioProblemaArea() {
    this.historicoSeguranca.getHistorico(this.codFesta).subscribe((resp: any) => {
      const problemasArea = [];
      for (const historico of resp) {
        if (historico.nomeArea === this.data.name) {
          problemasArea.push({
            statusProblema: historico.statusProblema,
            descProblema: historico.descProblema
          });
        }
      }
      this.areaProblema = {
        problemasArea
      };
    });
  }

  relatorioProblemaUsuario() {
    this.historicoSeguranca.getHistorico(this.codFesta).subscribe((resp: any) => {
      let codUsuario;
      for (const entry of this.chamadasUsuario) {
        if (entry.nomeUsuario === this.data.series) {
          codUsuario = entry.codUsuario;
          break;
        }
      }
      for (const historico of resp) {
        if (historico.codUsuarioResolv === codUsuario && historico.statusProblema === this.data.name.charAt(0)) {
          this.usuarioProblemas.push({
            nomeArea: historico.nomeArea,
            problema: historico.descProblema
          });
        }
      }
    });
  }
}
