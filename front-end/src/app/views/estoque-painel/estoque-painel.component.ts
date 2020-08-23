import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';
import { MatDialog, MatTableDataSource } from '@angular/material';
import { GetFestaService } from 'src/app/services/get-festa/get-festa.service';
import { Router } from '@angular/router';
import { StatusFestaService } from 'src/app/services/status-festa/status-festa.service';
import { GetEstoqueService } from 'src/app/services/get-estoque/get-estoque.service';
import { DeleteEstoqueDialogComponent } from '../delete-estoque-dialog/delete-estoque-dialog.component';
import { EditEstoqueDialogComponent } from '../edit-estoque-dialog/edit-estoque-dialog.component';
import { CriarEstoqueDialogComponent } from '../criar-estoque-dialog/criar-estoque-dialog.component';
import { CriarProdutoEstoqueDialogComponent } from '../criar-produto-estoque-dialog/criar-produto-estoque-dialog.component';
import { DeletarProdutoEstoqueDialogComponent } from '../deletar-produto-estoque-dialog/deletar-produto-estoque-dialog.component';
import { EditarProdutoEstoqueDialogComponent } from '../editar-produto-estoque-dialog/editar-produto-estoque-dialog.component';

export interface TabelaProdutos {
  codEstoque: string;
  quantidadeMax: string;
  quantidadeMin: string;
  porcentagemAtual: string;
  marca: string;
}

@Component({
  selector: 'app-estoque-painel',
  templateUrl: './estoque-painel.component.html',
  styleUrls: ['./estoque-painel.component.scss']
})

export class EstoquePainelComponent implements OnInit {

  displayedColumns: string[] = ['nome', 'quantidadeAtual', 'actions'];

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
      for (const estoque of resp) {
        const produtos = [];
        if (estoque.itemEstoque) {
          for (const produtoEstoque of Object.keys(estoque.itemEstoque)) {
            produtos.push({
              codProduto: estoque.itemEstoque[produtoEstoque].codProduto,
              quantidadeMax: estoque.itemEstoque[produtoEstoque].quantidadeMax,
              quantidadeAtual: estoque.itemEstoque[produtoEstoque].quantidadeAtual,
              porcentagemMin: estoque.itemEstoque[produtoEstoque].porcentagemMin,
              marca: estoque.itemEstoque[produtoEstoque].produto.marca
            });
          }
        }
        this.dataSources.push(new MatTableDataSource<TabelaProdutos>(produtos));
      }
    });
  }

  ngOnInit() {
    this.dataSources = [];
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

  openDialogDeleteProdEstoque(estoque, produto) {
    this.dialog.open(DeletarProdutoEstoqueDialogComponent, {
      width: '20rem',
      data: {
        component: this,
        estoque,
        produto
      }
    });
  }

  openDialogAddProdEstoque(estoque) {
    this.dialog.open(CriarProdutoEstoqueDialogComponent, {
      width: '20rem',
      data: {
        codFesta: this.festa.codFesta,
        component: this,
        estoque
      }
    });
  }

  openDialogEditProdEstoque(estoque, produto) {
    this.dialog.open(EditarProdutoEstoqueDialogComponent, {
      width: '20rem',
      data: {
        component: this,
        produto,
        estoque,
        festa: this.festa
      }
    });
  }

}
