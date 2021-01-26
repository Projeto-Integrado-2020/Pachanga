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

  constructor(@Inject(MAT_DIALOG_DATA) data, public segurancaService: GetSegurancaService) {
    this.data = data.data;
    this.codFesta = data.codFesta;
  }

  ngOnInit() {
    this.segurancaService.getAreaSeguranca(this.codFesta).subscribe((resp: any) => {
      for (const area of resp) {
        if (area.nomeArea === this.data.name) {
          this.areaProblema = area;
          break;
        }
      }
    });
  }
}
