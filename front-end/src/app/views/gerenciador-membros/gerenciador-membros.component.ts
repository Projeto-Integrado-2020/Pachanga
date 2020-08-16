import { Component, OnInit } from '@angular/core';
import { GetFestaService } from 'src/app/services/get-festa/get-festa.service';
import { Router } from '@angular/router';
import { GetGruposService } from 'src/app/services/get-grupos/get-grupos.service';
import { MatTableDataSource, MatDialog } from '@angular/material';
import { DeleteMembroDialogComponent } from '../delete-membro-dialog/delete-membro-dialog.component';
import { EditGrupoMembroComponent } from '../edit-grupo-membro/edit-grupo-membro.component';
import { InviteDialogComponent } from '../invite-dialog/invite-dialog.component';
import { DeletarGrupoComponent } from '../deletar-grupo/deletar-grupo.component';
import { DeletarConvidadoDialogComponent } from '../deletar-convidado-dialog/deletar-convidado-dialog.component';

export interface TabelaMembros {
  membro: string;
  status: string;
  id: string;
  convidado: boolean;
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
    this.dataSources = [];
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
          membros.push({
            membro: grupo.usuariosTO[usuario].nomeUser,
            status: 'PAINELCONTROLE.ACEITO',
            id: grupo.usuariosTO[usuario].codUsuario,
            convidado: false
          });
        }
        for (const convidado of Object.keys(grupo.convidadosTO)) {
          membros.push({
            membro: grupo.convidadosTO[convidado].email,
            status: 'PAINELCONTROLE.PENDENTE',
            id: grupo.convidadosTO[convidado].codConvidado,
            convidado: true
          });
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
        grupo,
        component: this
      }
    });
  }

  openDialogDeleteMembro(element, codGrupo) {
    if (!element.convidado) {
      this.dialog.open(DeleteMembroDialogComponent, {
        width: '20rem',
        data: {
          codUsuario: element.id,
          codGrupo,
          component: this
        }
      });
    } else {
      this.dialog.open(DeletarConvidadoDialogComponent, {
        width: '20rem',
        data: {
          codConvidado: element.id,
          codGrupo,
          component: this
        }
      });
    }
  }

  openDialogDeleteGrupo(grupo) {
    this.dialog.open(DeletarGrupoComponent, {
      width: '20rem',
      data: {
        grupo,
        codFesta: this.festa.codFesta,
        component: this
      }
    });
  }

  openDialogEdit(codUsuario, grupo, codFesta) {
    this.dialog.open(EditGrupoMembroComponent, {
      width: '20rem',
      data: {
        codUsuario,
        grupo,
        codFesta,
        component: this
      }
    });
  }

}
