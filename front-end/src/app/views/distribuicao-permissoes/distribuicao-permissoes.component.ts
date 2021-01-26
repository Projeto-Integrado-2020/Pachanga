import { Component, OnInit } from '@angular/core';
import { GetGruposService } from 'src/app/services/get-grupos/get-grupos.service';
import { Router } from '@angular/router';
import { GetFestaService } from 'src/app/services/get-festa/get-festa.service';
import { FormBuilder, FormControl } from '@angular/forms';
import { MatDialog } from '@angular/material';
import { DistribuicaoDialogComponent } from '../distribuicao-dialog/distribuicao-dialog.component';

@Component({
  selector: 'app-distribuicao-permissoes',
  templateUrl: './distribuicao-permissoes.component.html',
  styleUrls: ['./distribuicao-permissoes.component.scss']
})
export class DistribuicaoPermissoesComponent implements OnInit {

  form = this.formBuilder.group({});
  festa = { usuarios: [], codFesta: null };
  grupos: any;
  relacaoGrupoMembros = [];

  // configuracao do accordion

  isOpen: boolean;

  constructor(public router: Router, public getGrupos: GetGruposService, public getFesta: GetFestaService,
              public formBuilder: FormBuilder, public dialog: MatDialog) { }

  ngOnInit() {
    this.resgatarFesta();
  }

  resgatarFesta() {
    let idFesta = this.router.url;
    idFesta = idFesta.slice(idFesta.indexOf('&') + 1, idFesta.indexOf('/', idFesta.indexOf('&')));
    this.getFesta.acessarFesta(idFesta).subscribe((resp: any) => {
      this.festa = resp;
      this.resgatarGrupo();
    });
  }

  resgatarGrupo() {
    let idFesta = this.router.url;
    idFesta = idFesta.slice(idFesta.indexOf('&') + 1, idFesta.indexOf('/', idFesta.indexOf('&')));
    this.getGrupos.getGrupos(idFesta).subscribe((resp: any) => {
      this.getGrupos.setFarol(false);
      this.grupos = resp;
      this.buildForm();
    });
  }

  buildForm() {
    const group = {};
    for (const grupo of this.grupos) {
      for (const usuario of this.festa.usuarios) {
        group[grupo.codGrupo.toString() + usuario.codUsuario.toString()] = new FormControl(false);
      }
    }
    this.form = this.formBuilder.group(group);
    this.gerarRelacao();
  }

  gerarRelacao() {
    for (const grupo of this.grupos) {
      const listUser = [];
      for (const usuario of grupo.usuariosTO) {
        listUser.push(usuario.codUsuario);
        this.form.get(grupo.codGrupo + '' + usuario.codUsuario).setValue(true);
      }
      this.relacaoGrupoMembros.push(listUser);
    }
  }

  updateRelacao(index, codUsuario) {
    if (this.relacaoGrupoMembros[index].indexOf(codUsuario) === -1) {
      this.relacaoGrupoMembros[index].push(codUsuario);
    } else {
      const i = this.relacaoGrupoMembros[index].indexOf(codUsuario);
      this.relacaoGrupoMembros[index].splice(i, 1);
    }
  }

  openAssingDialog(index, grupo) {
    this.dialog.open(DistribuicaoDialogComponent, {
      width: '20rem',
      data: {
        grupo,
        codFesta: this.festa.codFesta,
        listaUser: this.relacaoGrupoMembros[index]
      }
    });
  }
}
