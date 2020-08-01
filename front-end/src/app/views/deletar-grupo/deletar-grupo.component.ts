import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material';
import { DeleterGrupoService } from '../../services/deletar-grupo/deleter-grupo.service';
import { SuccessDialogComponent } from '../success-dialog/success-dialog.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-deletar-grupo',
  templateUrl: './deletar-grupo.component.html',
  styleUrls: ['./deletar-grupo.component.scss']
})
export class DeletarGrupoComponent implements OnInit {

  grupo: any;
  codFesta: any;

  constructor(@Inject(MAT_DIALOG_DATA) data, public deleteService: DeleterGrupoService,
              public dialog: MatDialog, public router: Router) {
    this.grupo = data.grupo;
    this.codFesta = data.codFesta;
  }

  ngOnInit() {
  }

  deletarGrupo() {
    this.deleteService.deleteGrupo(this.grupo.codGrupo, this.codFesta).subscribe((resp: string) => {
      this.dialog.closeAll();
      window.location.reload();
      this.openDialogSuccess(resp);
    });
  }

  openDialogSuccess(message: string) {
    this.dialog.open(SuccessDialogComponent, {
      width: '20rem',
      data: {message}
    });
  }

}
