import { Component, OnInit } from '@angular/core';
import { GetFestaService } from 'src/app/services/get-festa/get-festa.service';
import { GetPermissoesService } from 'src/app/services/get-permissoes/get-permissoes.service';

@Component({
  selector: 'app-distribuicao-permissoes',
  templateUrl: './distribuicao-permissoes.component.html',
  styleUrls: ['./distribuicao-permissoes.component.scss']
})
export class DistribuicaoPermissoesComponent implements OnInit {

  membros: any = [];
  perfisPermissao: any = [];
  constructor(public getFesta: GetFestaService, public getPermissoes: GetPermissoesService) { }

  ngOnInit() {
  }

}
