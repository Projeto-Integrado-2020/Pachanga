import { Component, OnInit } from '@angular/core';
import { GetFestaService } from 'src/app/services/get-festa/get-festa.service';
import { GetPermissoesService } from 'src/app/services/get-permissoes/get-permissoes.service';
import { GetGruposService } from 'src/app/services/get-grupos/get-grupos.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-distribuicao-permissoes',
  templateUrl: './distribuicao-permissoes.component.html',
  styleUrls: ['./distribuicao-permissoes.component.scss']
})
export class DistribuicaoPermissoesComponent implements OnInit {

  membros: any = [];
  gruposFesta: any = [];
  idFesta: string;

  constructor(public router: Router, public getFesta: GetFestaService, public getGrupos: GetGruposService) { }

  ngOnInit() {
    this.idFesta = this.router.url
      .slice(
        this.router.url.indexOf('&') + 1,
        this.router.url.indexOf('/',
        this.router.url.indexOf('&'))
      );
    
    this.gruposFesta = this.getGrupos.getGrupos(this.idFesta);
  }

}
