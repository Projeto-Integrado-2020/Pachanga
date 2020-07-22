import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { InviteDialogComponent } from '../invite-dialog/invite-dialog.component';
import { DeletarFestaComponent } from '../deletar-festa/deletar-festa.component';
import { MatDialog, MatTableDataSource } from '@angular/material';
import { TranslateService } from '@ngx-translate/core';
import { GetFestaService } from '../../services/get-festa/get-festa.service';
import { Router } from '@angular/router';

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

  membros: TabelaMembros[] = [];
  displayedColumns: string[] = ['membro', 'status', 'permissao', 'edit'];
  dataSource = new MatTableDataSource<TabelaMembros>(this.membros);

  constructor(fb: FormBuilder, public dialog: MatDialog, private translate: TranslateService, public getFestaService: GetFestaService,
              public router: Router) {
    this.options = fb.group({
      bottom: 55,
      top: 0
    });
  }

  openDialogInvite() {
    this.dialog.open(InviteDialogComponent, {
      width: '50rem',
      data: {
        idFesta: this.festa.codFesta,
        grupo: ''
      }
    });
  }

  ngOnInit() {
    let idFesta = this.router.url;
    idFesta = idFesta.substring(idFesta.indexOf('&') + 1, idFesta.indexOf('/', idFesta.indexOf('&')));
    this.getFestaService.acessarFesta(idFesta).subscribe((resp: any) => {
      this.getFestaService.setFarol(false);
      this.festa = resp;
      this.festaNome = resp.nomeFesta;
      let usuario: any;
      for (usuario of Object.keys(resp.usuarios)) {
        this.membros.push({membro: resp.usuarios[usuario].nomeUser, status: 'Pendente'});
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

}
