import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-detalhes-problema-dialog',
  templateUrl: './detalhes-problema-dialog.component.html',
  styleUrls: ['./detalhes-problema-dialog.component.scss']
})
export class DetalhesProblemaDialogComponent implements OnInit {

  public detalhes: any;
  public dia: string;
  public hora: string;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data,
    public modal: MatDialog,
    public translate: TranslateService
    ) {
      this.detalhes = data.problema;
     }

  ngOnInit() {
    this.detalhes = this.data;
    console.log(this.detalhes);
    console.log(this.dataHorarioProblema());
  }

  dataHorarioProblema() {

    let str = this.detalhes.problema.horarioInicio;
    str = str.split('T');
    const dia = str[0].split('-').reverse().join('/');
    const hora = str[1].slice(0, 8);
    this.dia = dia;
    this.hora = hora;
  }

}
