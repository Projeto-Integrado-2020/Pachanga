import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { DeletarFestaComponent } from '../deletar-festa/deletar-festa.component';
import { MatDialog, MatTableDataSource } from '@angular/material';
import { GetFestaService } from '../../services/get-festa/get-festa.service';
import { Router } from '@angular/router';
import { StatusFestaService } from '../../services/status-festa/status-festa.service';
import { StatusDialogComponent } from '../status-dialog/status-dialog.component';

export interface TabelaMembros {
  membro: string;
  status: string;
}

@Component({
  selector: 'app-festa-painel-controle',
  templateUrl: './festa-painel-controle.component.html',
  styleUrls: ['./festa-painel-controle.component.scss']
})

export class FestaPainelControleComponent implements OnInit {

  public festaNome: string;
  options: FormGroup;
  public festa: any;
  public statusFesta: any;

  membros: TabelaMembros[] = [];
  displayedColumns: string[] = ['membro', 'status', 'edit'];
  dataSource = new MatTableDataSource<TabelaMembros>(this.membros);

  constructor(fb: FormBuilder, public dialog: MatDialog, public getFestaService: GetFestaService,
              public router: Router, public statusService: StatusFestaService) {
    this.options = fb.group({
      bottom: 55,
      top: 0
    });
  }

  ngOnInit() {
    this.membros = [];
    let idFesta = this.router.url;
    idFesta = idFesta.substring(idFesta.indexOf('&') + 1, idFesta.indexOf('/', idFesta.indexOf('&')));
    this.getFestaService.acessarFesta(idFesta).subscribe((resp: any) => {
      this.getFestaService.setFarol(false);
      this.festa = resp;
      this.festaNome = resp.nomeFesta;
      this.statusFesta = resp.statusFesta;
      for (const usuario of Object.keys(resp.usuarios)) {
        this.membros.push({membro: resp.usuarios[usuario].nomeUser, status: 'PAINELCONTROLE.ACEITO'});
      }
      for (const convidado of Object.keys(resp.convidados)) {
        this.membros.push({membro: resp.convidados[convidado].email, status: 'PAINELCONTROLE.PENDENTE'});
      }
      this.dataSource.data = this.membros;
    });
  }

  openDialogDelete() {
    this.dialog.open(DeletarFestaComponent, {
      width: '20rem',
      data: {festa: this.festa}
    });
  }

  openDialogStatus(status) {
    this.dialog.open(StatusDialogComponent, {
      width: '20rem',
      data: {codFesta: this.festa.codFesta, status, painel: this}
    });
  }

  setFesta(festa) {
    this.festa = festa;
  }

}
