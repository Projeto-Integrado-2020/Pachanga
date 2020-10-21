import { Component, OnInit } from '@angular/core';
import { MatDialog, MatTableDataSource } from '@angular/material';
import { Router } from '@angular/router';
import { AdicionarFormDialogComponent } from '../adicionar-form-dialog/adicionar-form-dialog.component';
import { DeletarFormDialogComponent } from '../deletar-form-dialog/deletar-form-dialog.component';
import { EditarFormDialogComponent } from '../editar-form-dialog/editar-form-dialog.component';

export interface TabelaForms {
  nome: string;
  url: string;
}

@Component({
  selector: 'app-forms-painel',
  templateUrl: './forms-painel.component.html',
  styleUrls: ['./forms-painel.component.scss']
})
export class FormsPainelComponent implements OnInit {

  codFesta: string;
  forms: TabelaForms[] = [];
  displayedColumns: string[] = ['nome', 'url', 'actions'];
  dataSource = new MatTableDataSource<TabelaForms>(this.forms);

  constructor(public dialog: MatDialog, public router: Router) { }

  ngOnInit() {
    const idFesta = this.router.url;
    this.codFesta = idFesta.substring(idFesta.indexOf('&') + 1, idFesta.indexOf('/', idFesta.indexOf('&')));

    this.forms = [
      {
        nome: 'Questionário de Pesquisa de Satisfação',
        url: 'https://app.zenhub.com/workspaces/pachanga-5e6bad5f567b4e735fea6f87/board?repos=247109099'
      },
      {nome: 'wadas', url: 'awdasd9'}
    ];
    this.dataSource.data = this.forms;
  }

  openDialogDelete(form) {
    this.dialog.open(DeletarFormDialogComponent, {
      width: '20rem',
      data: {
        form,
        codFesta: this.codFesta,
        component: this
      }
    });
  }

  openDialogEdit(form) {
    this.dialog.open(EditarFormDialogComponent, {
      width: '20rem',
      data: {
        form,
        codFesta: this.codFesta,
        component: this
      }
    });
  }

  openDialogAdd() {
    this.dialog.open(AdicionarFormDialogComponent, {
      width: '20rem',
      data: {
        codFesta: this.codFesta,
        component: this
      }
    });
  }

}
