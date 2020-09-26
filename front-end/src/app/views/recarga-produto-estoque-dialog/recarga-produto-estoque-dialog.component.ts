import { Component, OnInit, Inject } from '@angular/core';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { RecargaProdutoEstoqueService } from 'src/app/services/recarga-produto-estoque/recarga-produto-estoque.service';

@Component({
  selector: 'app-recarga-produto-estoque-dialog',
  templateUrl: './recarga-produto-estoque-dialog.component.html',
  styleUrls: ['./recarga-produto-estoque-dialog.component.scss']
})
export class RecargaProdutoEstoqueDialogComponent implements OnInit {

  public component: any;
  public estoque: any;
  public principal = '';
  public estoques: any;
  public quantidade: any;
  public form: FormGroup;
  public element: any;
  public indexEstoque: any;
  public indexProduto: any;

  constructor(@Inject(MAT_DIALOG_DATA) data, public formBuilder: FormBuilder, public dialog: MatDialog,
              public recargaProdutoEstoqueService: RecargaProdutoEstoqueService) {
    this.component = data.component;
    this.element = data.element;
    this.estoque = data.estoque;
    this.estoques = data.estoques;
    this.indexEstoque = data.indexEstoque;
    this.indexProduto = data.indexProduto;
  }

  ngOnInit() {
    const produtos = [];
    for (const estoque of this.estoques) {
      if (estoque.itemEstoque && this.estoque.codEstoque !== estoque.codEstoque) {
        for (const produtoEstoque of estoque.itemEstoque) {
          if (produtoEstoque.codProduto === this.element.codProduto) {
            if (estoque.principal) {
              this.principal = estoque.codEstoque;
            }
            produtos.push(estoque);
          }
        }
      }
    }
    this.estoques = produtos;
    this.gerarForm();
  }

  gerarForm() {
    this.form = this.formBuilder.group({
      quantidade: new FormControl(this.estoque.quantidade, Validators.required),
      estoqueOrigem: new FormControl(this.principal.toString()),
    });
  }

  get f() { return this.form.controls; }

  recargaProduto(quantidade, element) {
    if (element.dose) {
      quantidade *=  element.quantDoses;
    }
    let estoqueOrigem = this.form.get('estoqueOrigem').value;
    estoqueOrigem = estoqueOrigem ? estoqueOrigem : '';
    console.log(estoqueOrigem);
    this.recargaProdutoEstoqueService.recargaProdutoEstoque(quantidade, estoqueOrigem, element.codProduto, this.estoque.codEstoque)
    .subscribe((resp: any) => {
      this.recargaProdutoEstoqueService.setFarol(false);
      this.dialog.closeAll();
      this.component.quantidadesProdutos[this.indexEstoque][this.indexProduto].quantidadeAtual += Number(quantidade);
    });
  }

}
