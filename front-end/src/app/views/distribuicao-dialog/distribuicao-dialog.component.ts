import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material';
import { SuccessDialogComponent } from '../success-dialog/success-dialog.component';
import { Router } from '@angular/router';
import { AtribuicaoGrupoService } from 'src/app/services/atribuicao-grupo/atribuicao-grupo.service';

@Component({
  selector: 'app-distribuicao-dialog',
  templateUrl: './distribuicao-dialog.component.html',
  styleUrls: ['./distribuicao-dialog.component.scss']
})
export class DistribuicaoDialogComponent implements OnInit {

  grupo;
  codFesta;
  listaUser;

  constructor(@Inject(MAT_DIALOG_DATA) data, public assignService: AtribuicaoGrupoService,
              public dialog: MatDialog) {
    this.grupo = data.grupo;
    this.codFesta = data.codFesta;
    this.listaUser = data.listaUser;
  }

  ngOnInit() {
  }

  atribuirPermissoes() {
    this.assignService.atribuirMembros(this.listaUser, this.grupo.codGrupo).subscribe((resp: string) => {
      this.assignService.setFarol(false);
      this.dialog.closeAll();
      this.openDialogSuccess('ATRIBSUC');
    });
  }

  openDialogSuccess(message: string) {
    this.dialog.closeAll();
    this.dialog.open(SuccessDialogComponent, {
      width: '20rem',
      data: {message}
    });
  }

}
