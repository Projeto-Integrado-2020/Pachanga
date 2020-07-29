import { Component, OnInit } from '@angular/core';
import { GetFestaService } from 'src/app/services/get-festa/get-festa.service';
import { Router } from '@angular/router';
import { GetGruposService } from 'src/app/services/get-grupos/get-grupos.service';
import { MatTableDataSource, MatDialog } from '@angular/material';
import { InviteDialogComponent } from '../invite-dialog/invite-dialog.component';
import { DeletarGrupoComponent } from '../deletar-grupo/deletar-grupo.component';

export interface TabelaMembros {
  membro: string;
  status: string;
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
    this.resgatarGrupo();
    /*
    this.festa = {codFesta: '47'};
    this.grupos = [
      {codGrupo: '0', nomeGrupo: 'Grupo 1', quantMaxPessoas: '12', usuarios: {nomeUser: 'Andrey', status: 'Pendente'}},
      {codGrupo: '1', nomeGrupo: 'Grupo 2', quantMaxPessoas: '13', usuarios: {nomeUser: 'Luis', status: 'Pendente'}},
      {codGrupo: '2', nomeGrupo: 'Grupo 3', quantMaxPessoas: '14', usuarios: {nomeUser: 'Gustavo', status: 'Pendente'}}
    ];
    this.dataSources = [
      new MatTableDataSource<TabelaMembros>([{membro: 'Andrey', status: 'Pendente'}, {membro: 'Luis', status: 'Pendente'}]),
      new MatTableDataSource<TabelaMembros>([{membro: 'Luis', status: 'Pendente'}, {membro: 'Gustavo', status: 'Pendente'}]),
      new MatTableDataSource<TabelaMembros>([{membro: 'Gustavo', status: 'Pendente'}])
    ];
    */
  }

  resgatarFesta() {
    let idFesta = this.router.url;
    idFesta = idFesta.slice(idFesta.indexOf('&') + 1, idFesta.indexOf('/', idFesta.indexOf('&')));
    this.getFestaService.acessarFesta(idFesta).subscribe((resp: any) => {
      this.getFestaService.setFarol(false);
      this.festa = resp;
    });
  }

  resgatarGrupo() {
    this.getGruposService.getGrupos(this.festa.codFesta).subscribe((resp: any) => {
      this.getGruposService.setFarol(false);
      this.grupos = resp;
      for (const grupo of resp) {
        const membros = [];
        for (const usuario of Object.keys(grupo.usuarios)) {
          membros.push({membro: grupo.usuarios[usuario].nomeUser, status: grupo.usuarios[usuario].status});
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

  openDialogDelete(grupo) {
    this.dialog.open(DeletarGrupoComponent, {
      width: '20rem',
      data: {
        grupo,
        codFesta: this.festa.codFesta
      }
    });
  }

}
