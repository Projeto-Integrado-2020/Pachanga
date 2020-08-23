import { Component, OnInit, Inject } from '@angular/core';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material';
import { AtribuicaoProdutoEstoqueService } from 'src/app/services/atribuicao-produto-estoque/atribuicao-produto-estoque.service';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { GetProdutosService } from 'src/app/services/get-produtos/get-produtos.service';

@Component({
  selector: 'app-criar-produto-estoque-dialog',
  templateUrl: './criar-produto-estoque-dialog.component.html',
  styleUrls: ['./criar-produto-estoque-dialog.component.scss']
})
export class CriarProdutoEstoqueDialogComponent implements OnInit {

  public codFesta: any;
  public estoque: any;
  public form: FormGroup;
  public component: any;
  public produtos: any;

  constructor(@Inject(MAT_DIALOG_DATA) data, public formBuilder: FormBuilder, public dialog: MatDialog,
              public addProdEstoqueService: AtribuicaoProdutoEstoqueService, public getProdutosService: GetProdutosService) {
    this.codFesta = data.codFesta;
    this.estoque = data.estoque;
    this.component = data.component;
  }

  ngOnInit() {
    this.gerarForm();
    this.resgatarProdutos();
  }

  gerarForm() {
    this.form = this.formBuilder.group({
      codProduto: new FormControl('', Validators.required),
      quantidadeMax: new FormControl('', [Validators.required, Validators.min(1)]),
      quantidadeAtual: new FormControl('', [Validators.required, Validators.min(1)]),
      porcentagemMin: new FormControl('', [Validators.required, Validators.min(0), Validators.max(100)]),
    });
  }

  get f() { return this.form.controls; }

  resgatarProdutos() {
    this.getProdutosService.getProdutos(this.codFesta).subscribe((resp: any) => {
      this.getProdutosService.setFarol(false);
      this.produtos = resp;
    });
  }

  addProdutoEstoque(codProduto, quantidadeMax, quantidadeAtual, porcentagemMin) {
    const itemEstoqueTO = {
      codProduto,
      codFesta: this.codFesta,
      codEstoque: this.estoque.codEstoque,
      quantidadeMax,
      quantidadeAtual,
      porcentagemMin
    };

    this.addProdEstoqueService.addProdutoEstoque(itemEstoqueTO, this.estoque.codEstoque).subscribe((resp: any) => {
      this.addProdEstoqueService.setFarol(false);
      this.dialog.closeAll();
      this.component.ngOnInit();
    });
  }

}
