import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material';
import { SegurancaProblemasService } from 'src/app/services/seguranca-problemas/seguranca-problemas.service';

@Component({
  selector: 'app-relatar-problema-dialog',
  templateUrl: './relatar-problema-dialog.component.html',
  styleUrls: ['./relatar-problema-dialog.component.scss']
})
export class RelatarProblemaDialogComponent implements OnInit {
  public component: any;
  public codFesta: any;
  public area: any;

  mockProblemas: any = [
    {id: 1, nome: 'Briga'},
    {id: 2, nome: 'Incendio'},
    {id: 3, nome: 'Intruso'}
  ];

  constructor(
    @Inject(MAT_DIALOG_DATA) data,
    public dialog: MatDialog,
    private segurancaProblemaService: SegurancaProblemasService
    ) {
    this.codFesta = data.codFesta;
    this.area = data.area;
    this.component = data.component;
  }

  ngOnInit() {
    console.log(this.area);
  }

  relatarProblema() {

  }

}
