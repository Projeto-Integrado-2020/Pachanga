import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { EditarProdutoEstoqueService } from 'src/app/services/editar-produto-estoque/editar-produto-estoque.service';

@Component({
  selector: 'app-editar-produto-estoque-dialog',
  templateUrl: './editar-produto-estoque-dialog.component.html',
  styleUrls: ['./editar-produto-estoque-dialog.component.scss']
})
export class EditarProdutoEstoqueDialogComponent implements OnInit {

  public estoque: any;
  public component: any;
  public produto: any;
  public form: any;
  public festa: any;

  constructor(@Inject(MAT_DIALOG_DATA) data, public formBuilder: FormBuilder, public dialog: MatDialog,
              public editProdutoEstoque: EditarProdutoEstoqueService) {
    this.produto = data.produto;
    this.estoque = data.estoque;
    this.component = data.component;
    this.festa = data.festa;
  }

  ngOnInit() {
    this.gerarForm();
  }

  gerarForm() {
    this.form = this.formBuilder.group({
      quantidadeMax: new FormControl(this.produto.quantidadeMax, [Validators.required, Validators.min(1)]),
      quantidadeAtual: new FormControl(this.produto.quantidadeAtual, [Validators.required, Validators.min(1)]),
      porcentagemMin: new FormControl(this.produto.porcentagemMin, [Validators.required, Validators.min(0), Validators.max(100)]),
    });
  }

  get f() { return this.form.controls; }

  editarProdutoEstoque(quantidadeMax, quantidadeAtual, porcentagemMin) {
    const itemEstoqueTO = {
      codProduto: this.produto.codProduto,
      codFesta: this.festa.codFesta,
      codEstoque: this.estoque.codEstoque,
      quantidadeMax,
      quantidadeAtual,
      porcentagemMin
    };

    this.editProdutoEstoque.editarProdutoEstoque(itemEstoqueTO).subscribe((resp: any) => {
      this.editProdutoEstoque.setFarol(false);
      this.dialog.closeAll();
      this.component.ngOnInit();
    });
  }

}
