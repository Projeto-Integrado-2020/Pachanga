import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';
import { MatDialog, MatTableDataSource } from '@angular/material';
import { GetFestaService } from 'src/app/services/get-festa/get-festa.service';
import { Router } from '@angular/router';
import { StatusFestaService } from 'src/app/services/status-festa/status-festa.service';
import { GetEstoqueService } from 'src/app/services/get-estoque/get-estoque.service';
import { DeleteEstoqueDialogComponent } from '../delete-estoque-dialog/delete-estoque-dialog.component';
import { EditarEstoqueService } from 'src/app/services/editar-estoque/editar-estoque.service';
import { EditEstoqueDialogComponent } from '../edit-estoque-dialog/edit-estoque-dialog.component';
import { CriarEstoqueDialogComponent } from '../criar-estoque-dialog/criar-estoque-dialog.component';

@Component({
  selector: 'app-estoque-painel',
  templateUrl: './estoque-painel.component.html',
  styleUrls: ['./estoque-painel.component.scss']
})

export class EstoquePainelComponent implements OnInit {

  displayedColumns: string[] = ['nome', 'qtd', 'status', 'teste'];
  expandedElement: TabelaEstoque | null;

  public festaNome: string;
  options: FormGroup;
  public festa: any;
  public statusFesta: any;
  panelOpenState = false;
  public form: FormGroup;
  estoques: any;
  dataSources = [];

  constructor(public fb: FormBuilder, public dialog: MatDialog, public getFestaService: GetFestaService,
              public router: Router, public statusService: StatusFestaService, public getEstoque: GetEstoqueService) {
      this.options = fb.group({
        bottom: 55,
        top: 0
      });
  }

  gerarForm() {
    this.form = this.fb.group({
      grupoSelect: new FormControl('', Validators.required),
    });
  }

  get f() { return this.form.controls; }

  resgatarEstoquePanel() {
    this.getEstoque.getEstoque(this.festa.codFesta).subscribe((resp: any) => {
      this.getEstoque.setFarol(false);
      this.estoques = resp;
    });
  }

  ngOnInit() {
    let idFesta = this.router.url;
    idFesta = idFesta.substring(idFesta.indexOf('&') + 1, idFesta.indexOf('/', idFesta.indexOf('&')));
    this.getFestaService.acessarFesta(idFesta).subscribe((resp: any) => {
      this.getFestaService.setFarol(false);
      this.festa = resp;
      this.festaNome = resp.nomeFesta;
      this.statusFesta = resp.statusFesta;
      this.resgatarEstoquePanel();
    });
    this.gerarForm();
  }

  openDialogDelete(codEstoque) {
    this.dialog.open(DeleteEstoqueDialogComponent, {
      width: '20rem',
      data: {
        codFesta: this.festa.codFesta,
        codEstoque,
        component: this
      }
    });
  }

  openDialogEdit(estoque) {
    this.dialog.open(EditEstoqueDialogComponent, {
      width: '20rem',
      data: {
        codFesta: this.festa.codFesta,
        component: this,
        estoque
      }
    });
  }

  openDialogAdd() {
    this.dialog.open(CriarEstoqueDialogComponent, {
      width: '20rem',
      data: {
        codFesta: this.festa.codFesta,
        component: this
      }
    });
  }

}

export interface TabelaEstoque {
  nome: string;
  qtd: number;
  status: string;
  teste: any;
}

const ELEMENT_DATA: TabelaEstoque[] = [
  {
    nome: 'Skol 350ml',
    qtd: 1.0079,
    status: '',
    teste: '1'
  }, {
    nome: 'Dolly Limão 2L',
    qtd: 4.0026,
    status: '',
    teste: '1'
  }, {
    nome: 'Barrigudinha original 400ml',
    qtd: 6.941,
    status: '',
    teste: '1'
  },
];
