import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { GetFestaService } from 'src/app/services/get-festa/get-festa.service';
import { Router } from '@angular/router';
import { GetPermissoesService } from 'src/app/services/get-permissoes/get-permissoes.service';
import { GetGruposService } from 'src/app/services/get-grupos/get-grupos.service';
import { EditarGrupoService } from 'src/app/services/editar-grupo/editar-grupo.service';
import { SuccessDialogComponent } from '../success-dialog/success-dialog.component';
import { MatDialog } from '@angular/material';

@Component({
  selector: 'app-editar-grupo',
  templateUrl: './editar-grupo.component.html',
  styleUrls: ['./editar-grupo.component.scss']
})
export class EditarGrupoComponent implements OnInit {

  form: FormGroup;
  festa: any;
  grupo: any;
  permissoes = [];
  permissoesGrupo = [];

  constructor(public formBuilder: FormBuilder, public getFestaService: GetFestaService,
              public router: Router, public getPermissaoService: GetPermissoesService,
              public getGrupo: GetGruposService, public editGrupo: EditarGrupoService,
              public dialog: MatDialog) {
                this.form = this.formBuilder.group({
                  nomeGrupo: new FormControl('', Validators.required)
                });
              }

  ngOnInit() {
    this.resgatarFesta();
    this.resgatarPermissoes();
  }

  get f() { return this.form.controls; }

  resgatarFesta() {
    let idFesta = this.router.url;
    idFesta = idFesta.slice(idFesta.indexOf('&') + 1, idFesta.indexOf('/', idFesta.indexOf('&')));
    this.getFestaService.acessarFesta(idFesta).subscribe((resp: any) => {
      this.festa = resp;
      this.resgatarGrupo();
    });
  }

  resgatarGrupo() {
    let idgrupo = this.router.url;
    idgrupo = idgrupo.slice(idgrupo.indexOf('membros/') + 8, idgrupo.indexOf('/editar', idgrupo.indexOf('membros/')));
    this.getGrupo.getGrupoUnico(idgrupo).subscribe((resp: any) => {
      this.getGrupo.setFarol(false);
      this.grupo = resp;
      for (const permissao of resp.permissoesTO) {
        this.permissoesGrupo.push(permissao.codPermissao);
      }
      this.setFormValues();
    });
  }

  resgatarPermissoes() {
    this.getPermissaoService.getPermissoes().subscribe((resp: any) => {
      this.getPermissaoService.setFarol(false);
      this.permissoes = resp;
      this.buildForm();
    });
  }

  buildForm() {
    const group = {nomeGrupo: new FormControl('', Validators.required)};
    for (const permissao of this.permissoes) {
      group[permissao.descPermissao] = new FormControl(false);
    }
    this.form = this.formBuilder.group(group);
  }

  updateListaPermissao(permissao) {
    const field = this.form.get(permissao.descPermissao);
    if (!field.value) {
      this.permissoesGrupo.push(permissao.codPermissao);
    } else {
      const index = this.permissoesGrupo.indexOf(permissao.codPermissao);
      this.permissoesGrupo.splice(index, 1);
    }
  }

  editarGrupo(nomeGrupo) {
    const grupo = {
      codGrupo: this.grupo.codGrupo,
      codFesta: this.festa.codFesta,
      nomeGrupo,
      permissoes: this.permissoesGrupo
    };
    this.editGrupo.editarGrupo(grupo).subscribe((resp: any) => {
      this.editGrupo.setFarol(false);
      this.openDialogSuccess('GRUPOALT');
    });
  }

  openDialogSuccess(message: string) {
    this.dialog.open(SuccessDialogComponent, {
      width: '20rem',
      data: {message}
    });
  }

  setFormValues() {
    this.f.nomeGrupo.setValue(this.grupo.nomeGrupo);
    for (const permissao of this.permissoes) {
      if (this.permissoesGrupo.indexOf(permissao.codPermissao) !== -1) {
        this.form.get(permissao.descPermissao).setValue(true);
      }
    }
  }

  selecionarTudo(tipo) {
    for (const permissao of this.permissoes) {
      if (permissao.tipPermissao === tipo) {
        const field = this.form.get(permissao.descPermissao);
        if (!field.value) {
          field.setValue(true);
          this.permissoesGrupo.push(permissao.codPermissao);
        }
      }
    }
  }
}
