import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material';
import { DeletarMembroGrupo } from '../../services/deletar-membro-grupo/deletar-membro-grupo.service';

@Component({
  selector: 'app-delete-membro-dialog',
  templateUrl: './delete-membro-dialog.component.html',
  styleUrls: ['./delete-membro-dialog.component.scss']
})
export class DeleteMembroDialogComponent implements OnInit {

  public codFesta: any;
  public codUsuario: any;
  public codGrupo: any;

  constructor(@Inject(MAT_DIALOG_DATA) data, public dialog: MatDialog,
              public membroExcluirService: DeletarMembroGrupo) {
    this.codFesta = data.codFesta;
    this.codUsuario = data.codUsuario;
    this.codGrupo = data.codGrupo;
  }

  ngOnInit() {
  }

  deleteMembroGrupo() {
    this.membroExcluirService.deletarMembroColaborador(this.codFesta, this.codUsuario, this.codGrupo).subscribe((resp: any) => {
      this.dialog.closeAll();
      this.membroExcluirService.setFarol(false);
      window.location.reload();
    });
  }

}
