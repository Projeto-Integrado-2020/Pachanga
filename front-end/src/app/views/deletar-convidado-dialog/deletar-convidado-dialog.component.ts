import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material';
import { DeletarConvidadoService } from '../../services/deletar-convidado/deletar-convidado.service';

@Component({
  selector: 'app-deletar-convidado-dialog',
  templateUrl: './deletar-convidado-dialog.component.html',
  styleUrls: ['./deletar-convidado-dialog.component.scss']
})
export class DeletarConvidadoDialogComponent implements OnInit {

  codConvidado: any;
  codGrupo: any;
  component: any;

  constructor(@Inject(MAT_DIALOG_DATA) data, public dialog: MatDialog,
              public delConvidadoService: DeletarConvidadoService) {
    this.codConvidado = data.codConvidado;
    this.codGrupo = data.codGrupo;
    this.component = data.component;
  }

  ngOnInit() {
  }

  deleteConvidadoGrupo() {
    this.delConvidadoService.deletarConvidado(this.codConvidado, this.codGrupo).subscribe((resp: any) => {
      this.delConvidadoService.setFarol(false);
      this.dialog.closeAll();
      this.component.ngOnInit();
    });
  }

}
