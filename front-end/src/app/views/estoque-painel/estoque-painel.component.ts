import { Component, OnInit, OnDestroy } from '@angular/core';
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
import { BaixaProdutoEstoqueService } from 'src/app/services/baixa-produto-estoque/baixa-produto-estoque.service';
import { RecargaProdutoEstoqueService } from 'src/app/services/recarga-produto-estoque/recarga-produto-estoque.service';
import { RecargaProdutoEstoqueDialogComponent } from '../recarga-produto-estoque-dialog/recarga-produto-estoque-dialog.component';
import { interval, Observable, Subscription } from 'rxjs';
import { PerdaProdutoEstoqueDialogComponent } from '../perda-produto-estoque-dialog/perda-produto-estoque-dialog.component';
import { RemoverUnidadeDoseDialogComponent } from '../remover-unidade-dose-dialog/remover-unidade-dose-dialog.component';

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
  styleUrls: ['./estoque-painel.component.scss',
  '../../../../node_modules/font-awesome/css/font-awesome.css']
})

export class EstoquePainelComponent implements OnInit, OnDestroy {

  displayedColumns: string[] = ['nome', 'quantidadeMax', 'porcentagemMin', 'quantidadeAtual', 'actions1', 'actions2'];

  public festaNome: string;
  options: FormGroup;
  public festa: any;
  public statusFesta: any;
  panelOpenState = false;
  public forms = {};
  estoques: any;
  dataSources = [];
  quantidadesProdutos = [];
  subscription: Subscription;
  source: any;

  constructor(public fb: FormBuilder, public dialog: MatDialog, public getFestaService: GetFestaService,
              public router: Router, public statusService: StatusFestaService, public getEstoque: GetEstoqueService,
              public baixaProdutoEstoque: BaixaProdutoEstoqueService, public recargaProdutoEstoque: RecargaProdutoEstoqueService) {
      this.options = fb.group({
        bottom: 55,
        top: 0
      });
  }

  gerarForm() {
    this.forms = [];
    for (const estoque of this.estoques) {
      if (estoque.itemEstoque) {
        for (const produtoEstoque of Object.keys(estoque.itemEstoque)) {
          const form = this.fb.group({
            quantidade: new FormControl('', [Validators.min(1), Validators.required]),
          });
          this.forms[
            estoque.codEstoque + '' + estoque.itemEstoque[produtoEstoque].codProduto
          ] = form;
        }
      }
    }
  }

  f(key) {
    return this.forms[key].controls;
  }

  resgatarEstoquePanel() {
    this.getEstoque.getEstoque(this.festa.codFesta).subscribe((resp: any) => {
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
              marca: estoque.itemEstoque[produtoEstoque].produto.marca,
              dose: estoque.itemEstoque[produtoEstoque].produto.dose,
              quantDoses: estoque.itemEstoque[produtoEstoque].produto.quantDoses
            });
          }
        }
        this.quantidadesProdutos.push(produtos);
        this.dataSources.push(new MatTableDataSource<TabelaProdutos>(produtos));
      }
      this.gerarForm();
    });
  }

  ngOnInit() {
    this.dataSources = [];
    this.estoques = [];
    let idFesta = this.router.url;
    idFesta = idFesta.substring(idFesta.indexOf('&') + 1, idFesta.indexOf('/', idFesta.indexOf('&')));
    this.getFestaService.acessarFesta(idFesta).subscribe((resp: any) => {
      this.getFestaService.setFarol(false);
      this.festa = resp;
      this.festaNome = resp.nomeFesta;
      this.statusFesta = resp.statusFesta;
      this.resgatarEstoquePanel();
      this.updateQuantidades();
    });
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
        estoque,
        component: this
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

  openDialogRemoverItens() {
    this.dialog.open(DeletarProdutoEstoqueDialogComponent, {
      width: '20rem',
      data: {

      }
    });

  }

  removerProduto(quantidade, element, codEstoque, indexEstoque, indexProduto) {
    element = element.codProduto;
    this.baixaProdutoEstoque.baixaProdutoEstoque(quantidade, element, codEstoque).subscribe((resp: any) => {
      this.baixaProdutoEstoque.setFarol(false);
      this.quantidadesProdutos[indexEstoque][indexProduto].quantidadeAtual -= quantidade;
    });
  }

  recargaProduto(estoque, element, indexEstoque, indexProduto) {
    this.dialog.open(RecargaProdutoEstoqueDialogComponent, {
      width: '20rem',
      data: {
        component: this,
        estoque,
        element,
        indexEstoque,
        indexProduto
      }
    });
  }

  updateQuantidades() {
    this.source = interval(1000);
    this.subscription = this.source.subscribe(
      () => {
        this.getQtdsAtualizadas(this.getEstoque.getEstoque(this.festa.codFesta));
      }
    );
  }

  openDialogPerda(estoque, element, indexEstoque, indexProduto) {
    this.dialog.open(PerdaProdutoEstoqueDialogComponent, {
      width: '20rem',
      data: {
        component: this,
        element,
        estoque,
        indexEstoque,
        indexProduto
      }
    });
  }

  openDialogBaixaDoseUnidade(estoque, element, indexEstoque, indexProduto) {
    this.dialog.open(RemoverUnidadeDoseDialogComponent, {
      width: '20rem',
      data: {
        component: this,
        element,
        estoque,
        indexEstoque,
        indexProduto
      }
    });
  }

  getQtdsAtualizadas(observavel: Observable<object>) {
    return observavel.subscribe(
      (resp: any) => {
        this.quantidadesProdutos = [];
        for (const estoque of this.estoques) {
          for (const estoqueAtualizado of resp) {
            if (estoqueAtualizado.codEstoque === estoque.codEstoque) {
              estoque.itemEstoque = estoqueAtualizado.itemEstoque;
            }
          }
        }
        for (const estoque of this.estoques) {
          const produtos = [];
          if (estoque.itemEstoque) {
            for (const produtoEstoque of Object.keys(estoque.itemEstoque)) {
              produtos.push({
                codProduto: estoque.itemEstoque[produtoEstoque].codProduto,
                quantidadeMax: estoque.itemEstoque[produtoEstoque].quantidadeMax,
                quantidadeAtual: estoque.itemEstoque[produtoEstoque].quantidadeAtual,
                porcentagemMin: estoque.itemEstoque[produtoEstoque].porcentagemMin,
                marca: estoque.itemEstoque[produtoEstoque].produto.marca,
                dose: estoque.itemEstoque[produtoEstoque].produto.dose,
                quantDoses: estoque.itemEstoque[produtoEstoque].produto.quantDoses
              });
            }
          }
          this.quantidadesProdutos.push(produtos);
        }
      }
    );
  }

  calcularUnidades(quantidade, doses) {
    return Math.ceil(quantidade / doses);
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
    this.source = null;
  }

}
