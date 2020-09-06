import { Component, OnInit, Inject } from '@angular/core';
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material';
import { BaixaProdutoEstoqueService } from 'src/app/services/baixa-produto-estoque/baixa-produto-estoque.service';

@Component({
  selector: 'app-remover-unidade-dose-dialog',
  templateUrl: './remover-unidade-dose-dialog.component.html',
  styleUrls: ['./remover-unidade-dose-dialog.component.scss']
})
export class RemoverUnidadeDoseDialogComponent implements OnInit {

  public component: any;
  public estoque: any;
  public quantidade: any;
  public form: FormGroup;
  public element: any;
  public indexEstoque: any;
  public indexProduto: any;

  constructor(@Inject(MAT_DIALOG_DATA) data, public formBuilder: FormBuilder, public dialog: MatDialog,
              public baixaProdutoEstoqueDoseUnidade: BaixaProdutoEstoqueService) {
    this.component = data.component;
    this.element = data.element;
    this.estoque = data.estoque;
    this.indexEstoque = data.indexEstoque;
    this.indexProduto = data.indexProduto;
  }

  ngOnInit() {
    this.gerarForm();
  }

  get f() { return this.form.controls; }

  gerarForm() {
    this.form = this.formBuilder.group({
      quantidade: new FormControl(this.estoque.quantidade, Validators.required),
    });
  }

  removerProduto(quantidade) {
    quantidade *= this.element.quantDoses;
    this.baixaProdutoEstoqueDoseUnidade.baixaProdutoEstoque(quantidade, this.element.codProduto, this.estoque.codEstoque)
    .subscribe((resp: any) => {
      this.baixaProdutoEstoqueDoseUnidade.setFarol(false);
      this.component.quantidadesProdutos[this.indexEstoque][this.indexProduto].quantidadeAtual -= quantidade;
      this.dialog.closeAll();
    });
  }

}
