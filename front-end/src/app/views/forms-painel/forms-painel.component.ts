import { Component, OnInit } from '@angular/core';
import { MatDialog, MatTableDataSource } from '@angular/material';
import { Router } from '@angular/router';
import { GetFormsService } from 'src/app/services/get-forms/get-forms.service';
import { AdicionarFormDialogComponent } from '../adicionar-form-dialog/adicionar-form-dialog.component';
import { DeletarFormDialogComponent } from '../deletar-form-dialog/deletar-form-dialog.component';
import { EditarFormDialogComponent } from '../editar-form-dialog/editar-form-dialog.component';

export interface TabelaForms {
  codQuestionario: any;
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

  constructor(public dialog: MatDialog, public router: Router,
              public getQuestionarios: GetFormsService) { }

  ngOnInit() {
    this.resgatarQuestionarios();
  }

  resgatarQuestionarios() {
    this.forms = [];
    const idFesta = this.router.url;
    this.codFesta = idFesta.substring(idFesta.indexOf('&') + 1, idFesta.indexOf('/', idFesta.indexOf('&')));
    this.getQuestionarios.getQuestionarios(this.codFesta).subscribe((resp: any) => {
      this.getQuestionarios.setFarol(false);
      for (const form of resp) {
        this.forms.push({
                            codQuestionario: form.codQuestionario,
                            nome: form.nomeQuestionario,
                            url: form.urlQuestionario
                          });
      }
      this.dataSource.data = this.forms.sort(this.nomeQuestionarioSort);
    });
  }

  nomeQuestionarioSort(a, b) {
    if (a.nome > b.nome) {
      return 1;
    } else {
      return -1;
    }
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
