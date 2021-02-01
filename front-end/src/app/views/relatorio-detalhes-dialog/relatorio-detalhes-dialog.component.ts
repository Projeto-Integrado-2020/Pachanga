import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material';
import { GetSegurancaService } from 'src/app/services/get-seguranca/get-seguranca.service';

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

  constructor(@Inject(MAT_DIALOG_DATA) data, public segurancaService: GetSegurancaService) {
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
    this.segurancaService.getAreaSeguranca(this.codFesta).subscribe((resp: any) => {
      for (const area of resp) {
        if (area.nomeArea === this.data.name) {
          this.areaProblema = area;
          break;
        }
      }
    });
  }

  relatorioProblemaUsuario() {
    this.segurancaService.getAreaSeguranca(this.codFesta).subscribe((resp: any) => {
      let codUsuario;
      for (const entry of this.chamadasUsuario) {
        if (entry.nomeUsuario === this.data.series) {
          codUsuario = entry.codUsuario;
          break;
        }
      }
      for (const area of resp) {
        for (const problema of area.problemasArea) {
          if (problema.codUsuarioResolv === codUsuario && problema.statusProblema === this.data.name.charAt(0)) {
            this.usuarioProblemas.push({
              nomeArea: area.nomeArea,
              problema: problema.descProblema
            });
          }
        }
      }
    });
  }
}
