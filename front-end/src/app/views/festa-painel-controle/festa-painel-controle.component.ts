import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import { InviteDialogComponent } from '../invite-dialog/invite-dialog.component';
import { MatDialog } from '@angular/material';
import {TranslateService} from '@ngx-translate/core';
import { PainelControleService } from '../../services/painel-controle/painel-controle.service'
import { Router } from '@angular/router';

@Component({
  selector: 'app-festa-painel-controle',
  templateUrl: './festa-painel-controle.component.html',
  styleUrls: ['./festa-painel-controle.component.scss']
})

export class FestaPainelControleComponent implements OnInit {

  options: FormGroup;
  private festa: any;

  displayedColumns: string[] = ['membro', 'status', 'permissao', 'edit'];
  dataSource = ELEMENT_DATA;

  constructor(fb: FormBuilder, public invite: MatDialog, private translate: TranslateService, public painelService: PainelControleService, public router: Router) {
    this.options = fb.group({
      bottom: 55,
      top: 0
    });
  }

  openDialogInvite() {
    this.invite.open(InviteDialogComponent, {
      width: '20rem',
    });
  }

  ngOnInit() {
    let idFesta = this.router.url;
    idFesta = idFesta.substring(idFesta.indexOf("&") + 1, idFesta.indexOf("/"));

    this.painelService.acessarFesta(idFesta).subscribe((resp: any) => {
      this.painelService.setFarol(false);
      this.festa = resp;
    });
  }

}

export interface TabelaMembros {
  membro: string;
  status: string;
} 

const ELEMENT_DATA: TabelaMembros[] = [
  {membro: 'Andrey', status: 'a'},
  {membro: 'Gustavo', status: 'a'},
  {membro: 'Pedro', status: 'a'}
];

/*const ELEMENT_DATA: TabelaMembros[] = [
  {membro: 'Andrey', status: 'this.membro1'},
  {membro: 'Gustavo', status: 'this.translate.instant('PAINELCONTROLE.RECUSADO')'},
  {membro: 'Pedro', status: 'this.translate.instant('PAINELCONTROLE.PENDENTE')'}
];*/