import { Component, OnInit } from '@angular/core';
import { GetGruposService } from 'src/app/services/get-grupos/get-grupos.service';
import { Router } from '@angular/router';
import { GetFestaService } from 'src/app/services/get-festa/get-festa.service';
import { MatTableDataSource } from '@angular/material';

@Component({
  selector: 'app-distribuicao-permissoes',
  templateUrl: './distribuicao-permissoes.component.html',
  styleUrls: ['./distribuicao-permissoes.component.scss']
})
export class DistribuicaoPermissoesComponent implements OnInit {

  urlFesta: string;
  festa: any;

  // dataSources: [];

  nomesGrupos = [];
  nomesMembros = [];

  grupos: any;



  constructor(public router: Router, public getGrupos: GetGruposService, public getFesta: GetFestaService) { }

  ngOnInit() {
    this.urlFesta = this.router.url;

    this.festa = {codFesta: '47',
      membros: [
        {nomeUser: 'Andrey', status: 'Pendente'},
        {nomeUser: 'Gustavo', status: 'Pendente'},
        {nomeUser: 'Luis', status: 'Pendente'}
      ]
    };

    this.grupos = [
      {codGrupo: '0', nomeGrupo: 'Grupo 1', quantMaxPessoas: '12', usuarios: {nomeUser: 'Andrey', status: 'Pendente'}},
      {codGrupo: '1', nomeGrupo: 'Grupo 2', quantMaxPessoas: '13', usuarios: {nomeUser: 'Luis', status: 'Pendente'}},
      {codGrupo: '2', nomeGrupo: 'Grupo 3', quantMaxPessoas: '14', usuarios: {nomeUser: 'Gustavo', status: 'Pendente'}}
    ];

    for (const grupo of this.grupos) {
      this.nomesGrupos.push(grupo.nomeGrupo);
    }

    for (const membro of this.festa.membros) {
      this.nomesMembros.push(membro.nomeUser);
    }
  }

  resgatarFesta() {
    let idFesta = this.router.url;
    idFesta = idFesta.slice(idFesta.indexOf('&') + 1, idFesta.indexOf('/', idFesta.indexOf('&')));
    this.getFesta.acessarFesta(idFesta).subscribe((resp: any) => {
      this.getFesta.setFarol(false);
      this.festa = resp;
    });
  }


/*
  resgatarGrupo() {
    this.getGrupos.getGrupos(this.festa.codFesta).subscribe((resp: any) => {
      this.getGrupos.setFarol(false);
      this.grupos = resp;
      for (const grupo of resp) {
        const membros = [];
        for (const usuario of Object.keys(grupo.usuarios)) {
          membros.push({membro: grupo.usuarios[usuario].nomeUser, status: grupo.usuarios[usuario].status});
        }
        //this.dataSources.push(new MatTableDataSource<TabelaMembros>(membros));
      }
    });
  }
*/
}
