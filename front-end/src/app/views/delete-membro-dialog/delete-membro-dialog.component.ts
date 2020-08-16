import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material';
import { DeletarMembroGrupo } from '../../services/deletar-membro-grupo/deletar-membro-grupo.service';

@Component({
  selector: 'app-delete-membro-dialog',
  templateUrl: './delete-membro-dialog.component.html',
  styleUrls: ['./delete-membro-dialog.component.scss']
})
export class DeleteMembroDialogComponent implements OnInit {

  public codUsuario: any;
  public codGrupo: any;
  component: any;

  constructor(@Inject(MAT_DIALOG_DATA) data, public dialog: MatDialog,
              public membroExcluirService: DeletarMembroGrupo) {
    this.codUsuario = data.codUsuario;
    this.codGrupo = data.codGrupo;
    this.component = data.component;
  }

  ngOnInit() {
  }

  deleteMembroGrupo() {
    this.membroExcluirService.deletarMembroColaborador(this.codUsuario, this.codGrupo).subscribe((resp: any) => {
      this.membroExcluirService.setFarol(false);
      this.dialog.closeAll();
      this.component.ngOnInit();
    });
  }

}
