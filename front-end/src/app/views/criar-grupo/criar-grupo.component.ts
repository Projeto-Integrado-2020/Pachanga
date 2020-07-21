import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { GetFestaService } from 'src/app/services/get-festa/get-festa.service';
import { Router } from '@angular/router';
import { GetPermissoesService } from 'src/app/services/get-permissoes/get-permissoes.service';

@Component({
  selector: 'app-criar-grupo',
  templateUrl: './criar-grupo.component.html',
  styleUrls: ['./criar-grupo.component.scss']
})
export class CriarGrupoComponent implements OnInit {

  public form: FormGroup;
  public festa: any;

  public permissoes = [];
  public permissoesGrupo = [];

  constructor(public formBuilder: FormBuilder, public getFestaService: GetFestaService,
              public router: Router, public getPermissaoService: GetPermissoesService) { }

  ngOnInit() {
    this.resgatarFesta();
    this.resgatarPermissoes();
    this.buildForm();
  }

  get f() { return this.form.controls; }

  resgatarFesta() {
    let idFesta = this.router.url;
    idFesta = idFesta.slice(idFesta.indexOf('&') + 1, idFesta.indexOf('/', idFesta.indexOf('&')));
    this.callServiceGet(idFesta);
  }

  resgatarPermissoes() {
    this.getPermissaoService.getPermissoes().subscribe((resp: any) => {
      this.permissoes = resp;
    });
  }

  buildForm() {
    const group = {nomeGrupo: new FormControl('', Validators.required)};
    for (const permissao of this.permissoes) {
      group[permissao] = new FormControl(false);
    }
    this.form = this.formBuilder.group(group);
  }

  callServiceGet(idFesta) {
    this.getFestaService.acessarFesta(idFesta).subscribe((resp: any) => {
      this.festa = resp;
    });
  }

  updateListaPermissao(permissao) {
    const field = this.form.get(permissao);
    if (!field.value) {
      this.permissoesGrupo.push(permissao);
    } else {
      const index = this.permissoesGrupo.indexOf(permissao);
      this.permissoesGrupo.splice(index, 1);
    }
  }

}
