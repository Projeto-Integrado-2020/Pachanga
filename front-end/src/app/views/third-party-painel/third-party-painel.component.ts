import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatDialog } from '@angular/material';
import { Router } from '@angular/router';
import { GetFestaService } from 'src/app/services/get-festa/get-festa.service';
import { GetIntegracaoService } from 'src/app/services/get-integracao/get-integracao.service';
import { AdicionarIntegracoesDialogComponent } from '../adicionar-integracoes-dialog/adicionar-integracoes-dialog.component';
import { DeletarIntegracoesDialogComponent } from '../deletar-integracoes-dialog/deletar-integracoes-dialog.component';
import { EditarIntegracoesDialogComponent } from '../editar-integracoes-dialog/editar-integracoes-dialog.component';

@Component({
  selector: 'app-third-party-painel',
  templateUrl: './third-party-painel.component.html',
  styleUrls: ['./third-party-painel.component.scss']
})
export class ThirdPartyPainelComponent implements OnInit {

  integracoes: any;
  festa: any;
  festaNome: string;
  options: FormGroup;

  constructor(public fb: FormBuilder, public router: Router, public getFestaService: GetFestaService,
              public getIntegracoes: GetIntegracaoService, public dialog: MatDialog) {
    this.options = fb.group({
      bottom: 55,
      top: 0
      });
  }

  ngOnInit() {
    this.integracoes = [];
    this.resgatarFesta();
    this.resgatarIntegracoes();
  }

  resgatarFesta() {
    let idFesta = this.router.url;
    idFesta = idFesta.substring(idFesta.indexOf('&') + 1, idFesta.indexOf('/', idFesta.indexOf('&')));

    this.getFestaService.acessarFesta(idFesta).subscribe((resp: any) => {
      this.festa = resp;
      this.festaNome = resp.nomeFesta;
    });
  }

  resgatarIntegracoes() {
    let idFesta = this.router.url;
    idFesta = idFesta.substring(idFesta.indexOf('&') + 1, idFesta.indexOf('/', idFesta.indexOf('&')));

    this.getIntegracoes.getIntegracoes(idFesta).subscribe((resp: any) => {
      this.getIntegracoes.setFarol(false);
      this.integracoes = resp;
    });
  }

  openDialogDelete(integracao) {
    this.dialog.open(DeletarIntegracoesDialogComponent, {
      width: '20rem',
      data: {
        integracao,
        component: this
      }
    });
  }

  openDialogEdit(integracao) {
    this.dialog.open(EditarIntegracoesDialogComponent, {
      width: '25rem',
      data: {
        integracao,
        codFesta: this.festa.codFesta,
        component: this
      }
    });
  }

  openDialogAdd() {
    this.dialog.open(AdicionarIntegracoesDialogComponent, {
      width: '25rem',
      data: {
        codFesta: this.festa.codFesta,
        component: this
      }
    });
  }

}
