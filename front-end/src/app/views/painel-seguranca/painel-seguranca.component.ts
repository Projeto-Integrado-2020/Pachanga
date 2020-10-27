import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatDialog, MatTableDataSource } from '@angular/material';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { GetFestaService } from 'src/app/services/get-festa/get-festa.service';
import { GetSegurancaService } from 'src/app/services/get-seguranca/get-seguranca.service';
import { CriarAreaSegurancaDialogComponent } from '../criar-area-seguranca-dialog/criar-area-seguranca-dialog.component';
import { DeleteAreaSegurancaDialogComponent } from '../delete-area-seguranca-dialog/delete-area-seguranca-dialog.component';
import { EditarAreaSegurancaDialogComponent } from '../editar-area-seguranca-dialog/editar-area-seguranca-dialog.component';
import { RelatarProblemaDialogComponent } from '../relatar-problema-dialog/relatar-problema-dialog.component';

export interface TabelaSeguranca {
  nome: string;
  status: string;
}

@Component({
  selector: 'app-painel-seguranca',
  templateUrl: './painel-seguranca.component.html',
  styleUrls: ['./painel-seguranca.component.scss']
})

export class PainelSegurancaComponent implements OnInit {

  constructor(public fb: FormBuilder, public dialog: MatDialog, public getFestaService: GetFestaService,
              public router: Router, public getSeguranca: GetSegurancaService) {
    this.options = fb.group({
        bottom: 55,
        top: 0
      });
  }

  public festaNome: string;
  public areas: any;
  options: FormGroup;
  public festa: any;
  public statusFesta: any;
  panelOpenState = false;
  public forms = {};
  estoques: any;
  dataSources = [];
  subscription: Subscription;
  source: any;

  displayedColumns: string[] = ['nome', 'status'];

  resgatarEstoquePanel() {
    this.getSeguranca.getAreaSeguranca(this.festa.codFesta).subscribe((resp: any) => {
      this.areas = resp;
      this.getSeguranca.setFarol(false);
    });
  }

  ngOnInit() {
    this.source = null;
    this.areas = [];
    let idFesta = this.router.url;
    this.dataSources = [];
    idFesta = idFesta.substring(idFesta.indexOf('&') + 1, idFesta.indexOf('/', idFesta.indexOf('&')));

    this.getFestaService.acessarFesta(idFesta).subscribe((resp: any) => {
      this.getFestaService.setFarol(false);
      this.festa = resp;
      this.festaNome = resp.nomeFesta;
      this.statusFesta = resp.statusFesta;
      this.resgatarEstoquePanel();
    });
  }

  setFesta(festa) {
    this.festa = festa;
  }

  openDialogRelatarProblema(area) {
    this.dialog.open(RelatarProblemaDialogComponent, {
      width: '20rem',
      data: {
        codFesta: this.festa.codFesta,
        component: this,
        area
      }
    });
  }

  openDialogCriarArea() {
    this.dialog.open(CriarAreaSegurancaDialogComponent, {
      width: '20rem',
      data: {
        codFesta: this.festa.codFesta,
        component: this
      }
    });
  }

  openDialogDelete(codArea) {
    this.dialog.open(DeleteAreaSegurancaDialogComponent, {
      width: '20rem',
      data: {
        codArea,
        component: this
      }
    });
  }

  openDialogEdit(area) {
    this.dialog.open(EditarAreaSegurancaDialogComponent, {
      width: '20rem',
      data: {
        codFesta: this.festa.codFesta,
        area,
        component: this
      }
    });
  }

}
