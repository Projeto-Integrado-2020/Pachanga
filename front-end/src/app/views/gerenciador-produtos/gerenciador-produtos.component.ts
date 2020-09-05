import { Component, OnInit } from '@angular/core';
import { MatDialog, MatTableDataSource } from '@angular/material';
import { Router } from '@angular/router';
import { GetProdutosService } from 'src/app/services/get-produtos/get-produtos.service';
import { DeletarProdutoDialogComponent } from '../deletar-produto-dialog/deletar-produto-dialog.component';
import { EditarProdutoDialogComponent } from '../editar-produto-dialog/editar-produto-dialog.component';
import { CriarProdutoDialogComponent } from '../criar-produto-dialog/criar-produto-dialog.component';

export interface TabelaProdutos {
  codProduto: string;
  marca: string;
  preco: string;
  quantDose: string;
  dose: boolean;
}

@Component({
  selector: 'app-gerenciador-produtos',
  templateUrl: './gerenciador-produtos.component.html',
  styleUrls: ['./gerenciador-produtos.component.scss']
})
export class GerenciadorProdutosComponent implements OnInit {

  produtos: TabelaProdutos[] = [];
  codFesta: string;
  displayedColumns: string[] = ['marca', 'preco', 'dose', 'quantDose', 'actions'];
  dataSource = new MatTableDataSource<TabelaProdutos>(this.produtos);

  constructor(public dialog: MatDialog, public router: Router, public getProdutos: GetProdutosService) { }

  ngOnInit() {
    this.resgatarProdutos();
  }

  resgatarProdutos() {
    this.produtos = [];
    const idFesta = this.router.url;
    this.codFesta = idFesta.substring(idFesta.indexOf('&') + 1, idFesta.indexOf('/', idFesta.indexOf('&')));
    this.getProdutos.getProdutos(this.codFesta).subscribe((resp: any) => {
      this.getProdutos.setFarol(false);
      for (const produto of resp) {
        this.produtos.push({codProduto: produto.codProduto,
                            marca: produto.marca,
                            dose: produto.dose,
                            quantDose: produto.quantDoses,
                            preco: parseFloat(produto.precoMedio).toFixed(2)
                          });
      }
      this.dataSource.data = this.produtos;
    });
  }

  openDialogDelete(produto) {
    this.dialog.open(DeletarProdutoDialogComponent, {
      width: '20rem',
      data: {
        produto,
        codFesta: this.codFesta,
        component: this
      }
    });
  }

  openDialogEdit(produto) {
    this.dialog.open(EditarProdutoDialogComponent, {
      width: '20rem',
      data: {
        produto,
        codFesta: this.codFesta,
        component: this
      }
    });
  }

  openDialogAdd() {
    this.dialog.open(CriarProdutoDialogComponent, {
      width: '20rem',
      data: {
        codFesta: this.codFesta,
        component: this
      }
    });
  }

}
