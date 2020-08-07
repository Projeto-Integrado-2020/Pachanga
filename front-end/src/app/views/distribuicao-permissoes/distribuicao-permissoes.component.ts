import { Component, OnInit } from '@angular/core';
import { GetGruposService } from 'src/app/services/get-grupos/get-grupos.service';
import { Router } from '@angular/router';
import { GetFestaService } from 'src/app/services/get-festa/get-festa.service';
import { FormControl } from '@angular/forms';
import { Observable } from 'rxjs';
import { startWith, map } from 'rxjs/operators';

@Component({
  selector: 'app-distribuicao-permissoes',
  templateUrl: './distribuicao-permissoes.component.html',
  styleUrls: ['./distribuicao-permissoes.component.scss']
})
export class DistribuicaoPermissoesComponent implements OnInit {

  festa = { usuarios: [] };

  myControl = new FormControl();
  nomesGrupos = [];
  nomesMembros = [];
  filteredMembros: Observable<string[]>;

  grupos: any;

  // configuracao do accordion

  isOpen: boolean;

  constructor(public router: Router, public getGrupos: GetGruposService, public getFesta: GetFestaService) { }

  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();

    return this.nomesMembros.filter(membro => membro.toLowerCase().includes(filterValue));
  }

  ngOnInit() {
    this.resgatarFesta();
    this.resgatarGrupo();
    this.filteredMembros = this.myControl.valueChanges
      .pipe(
        startWith(''),
        map(value => this._filter(value))
      );
  }

  resgatarFesta() {
    let idFesta = this.router.url;
    idFesta = idFesta.slice(idFesta.indexOf('&') + 1, idFesta.indexOf('/', idFesta.indexOf('&')));
    this.getFesta.acessarFesta(idFesta).subscribe((resp: any) => {
      this.getFesta.setFarol(false);
      this.festa = resp;
    });
  }

  resgatarGrupo() {
    let idFesta = this.router.url;
    idFesta = idFesta.slice(idFesta.indexOf('&') + 1, idFesta.indexOf('/', idFesta.indexOf('&')));
    this.getGrupos.getGrupos(idFesta).subscribe((resp: any) => {
      this.getGrupos.setFarol(false);
      this.grupos = resp;
      for (const grupo of this.grupos) {
        this.nomesGrupos.push(grupo.usuariosTO);
      }
    });
  }
}
