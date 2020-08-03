import { Component, OnInit } from '@angular/core';
import { GetFestaService } from 'src/app/services/get-festa/get-festa.service';
import { Router } from '@angular/router';
import { GetGruposService } from 'src/app/services/get-grupos/get-grupos.service';
import { MatTableDataSource, MatDialog } from '@angular/material';
import { DeleteMembroDialogComponent } from '../delete-membro-dialog/delete-membro-dialog.component';
import { EditGrupoMembroComponent } from '../edit-grupo-membro/edit-grupo-membro.component';
import { InviteDialogComponent } from '../invite-dialog/invite-dialog.component';

export interface TabelaMembros {
  membro: string;
  status: string;
  id: string;
}

@Component({
  selector: 'app-gerenciador-membros',
  templateUrl: './gerenciador-membros.component.html',
  styleUrls: ['./gerenciador-membros.component.scss']
})
export class GerenciadorMembrosComponent implements OnInit {

  festa: any;
  grupos: any;
  displayedColumns: string[] = ['membro', 'status', 'edit'];
  dataSources = [];

  constructor(public getFestaService: GetFestaService, public router: Router,
              public getGruposService: GetGruposService, public dialog: MatDialog) {
  }

  ngOnInit() {
    this.resgatarFesta();
  }

  resgatarFesta() {
    let idFesta = this.router.url;
    idFesta = idFesta.slice(idFesta.indexOf('&') + 1, idFesta.indexOf('/', idFesta.indexOf('&')));
    this.getFestaService.acessarFesta(idFesta).subscribe((resp: any) => {
      this.getFestaService.setFarol(false);
      this.festa = resp;
      this.resgatarGrupo();
    });
  }

  resgatarGrupo() {
    this.getGruposService.getGrupos(this.festa.codFesta).subscribe((resp: any) => {
      this.getGruposService.setFarol(false);
      this.grupos = resp;
      for (const grupo of resp) {
        const membros = [];
        for (const usuario of Object.keys(grupo.usuariosTO)) {
          membros.push({membro: grupo.usuariosTO[usuario].nomeUser, status: 'Aceito (HARDCODED)'});
        }
        this.dataSources.push(new MatTableDataSource<TabelaMembros>(membros));
      }
    });
  }

  openDialogInvite(grupo) {
    this.dialog.open(InviteDialogComponent, {
      width: '55rem',
      data: {
        idFesta: this.festa.codFesta,
        grupo
      }
    });
  }

  openDialogDelete(id, codGrupo, codFesta) {
    this.dialog.open(DeleteMembroDialogComponent, {
      width: '20rem',
      data: {
        id,
        codGrupo,
        codFesta
      }
    });
  }

  openDialogEdit(id, grupo, codFesta) {
    this.dialog.open(EditGrupoMembroComponent, {
      width: '20rem',
      data: {
        id,
        grupo,
        codFesta
      }
    });
  }

}
