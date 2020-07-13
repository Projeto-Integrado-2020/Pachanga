import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import { InviteDialogComponent } from '../invite-dialog/invite-dialog.component';
import { MatDialog } from '@angular/material';

@Component({
  selector: 'app-festa-painel-controle',
  templateUrl: './festa-painel-controle.component.html',
  styleUrls: ['./festa-painel-controle.component.scss']
})

export class FestaPainelControleComponent implements OnInit {

  options: FormGroup;

  displayedColumns: string[] = ['membro', 'status', 'permissao', 'edit'];
  dataSource = ELEMENT_DATA;

  constructor(fb: FormBuilder, public invite: MatDialog) {
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
  }

}

export interface PeriodicElement {
  membro: string;
  status: string;
}

const ELEMENT_DATA: PeriodicElement[] = [
  {membro: 'Andrey', status: 'Pendente'},
  {membro: 'Gustavo', status: 'Aceito'},
  {membro: 'Pedro', status: 'Recusado'}
];
