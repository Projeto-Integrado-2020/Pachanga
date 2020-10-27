import { Component, OnInit, Inject } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { GetEstoqueService } from 'src/app/services/get-estoque/get-estoque.service';
import { GetProdutosService } from 'src/app/services/get-produtos/get-produtos.service';
import { RecargaProdutoEstoqueService } from 'src/app/services/recarga-produto-estoque/recarga-produto-estoque.service';

@Component({
  selector: 'app-alerta-estoque',
  templateUrl: './alerta-estoque.component.html',
  styleUrls: ['./alerta-estoque.component.scss']
})
export class AlertaEstoqueComponent implements OnInit {

  notificacao: any;
  estoques: any;
  form: FormGroup;
  principal = '';
  produto: any;

  constructor(@Inject(MAT_DIALOG_DATA) data, public getEstoque: GetEstoqueService, public getProduto: GetProdutosService,
              public formBuilder: FormBuilder, public recargaProdutoEstoqueService: RecargaProdutoEstoqueService,
              private dialogRef: MatDialogRef<AlertaEstoqueComponent>) {
    this.notificacao = data.alerta;
  }

  ngOnInit() {
    this.gerarForm();
    this.resgatarEstoques();
    this.resgatarProduto();
  }

  resgatarProduto() {
    const codFesta = this.notificacao.mensagem.split('&')[0].split('?')[1];
    const codProduto = this.notificacao.mensagem.split('&')[2];
    this.getProduto.getProdutos(codFesta).subscribe((resp: any) => {
      this.getProduto.setFarol(false);
      for (const produto of resp) {
        if (produto.codProduto.toString() === codProduto.toString()) {
          this.produto = produto;
        }
      }
    });
  }

  resgatarEstoques() {
    const codFesta = this.notificacao.mensagem.split('&')[0].split('?')[1];
    const codEstoque = this.notificacao.mensagem.split('&')[1];
    const codProduto = this.notificacao.mensagem.split('&')[2];
    this.getEstoque.getEstoque(codFesta).subscribe((resp: any) => {
      const produtos = [];
      for (const estoque of resp) {
        if (estoque.itemEstoque && codEstoque.toString() !== estoque.codEstoque.toString()) {
          for (const produtoEstoque of estoque.itemEstoque) {
            if (produtoEstoque.codProduto.toString() === codProduto.toString()) {
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
    });
  }

  get f() { return this.form.controls; }

  gerarForm() {
    this.form = this.formBuilder.group({
      quantidade: new FormControl(Validators.required),
      estoqueOrigem: new FormControl(this.principal.toString()),
    });
  }

  recargaProduto(quantidade) {
    const codEstoque = this.notificacao.mensagem.split('&')[1];
    const codProduto = this.notificacao.mensagem.split('&')[2];
    if (this.produto.dose) {
      quantidade *=  this.produto.quantDoses;
    }
    let estoqueOrigem = this.form.get('estoqueOrigem').value;
    estoqueOrigem = estoqueOrigem ? estoqueOrigem : '';
    this.recargaProdutoEstoqueService.recargaProdutoEstoque(quantidade, estoqueOrigem, codProduto, codEstoque)
    .subscribe((resp: any) => {
      this.recargaProdutoEstoqueService.setFarol(false);
      this.dialogRef.close();
    });
  }

  getUrlFesta() {
    const codFesta = this.notificacao.mensagem.split('&')[0].split('?')[1];
    const nomeFesta = this.notificacao.notificacaoEstoque.nomeFesta.toLowerCase().replace('-', '').replace('–', '')
                        .replace(/\s+/g, '-').replace('ç', 'c')
                        .replace('º', '').replace('ª', '')
                        .normalize('NFD').replace(/[\u0300-\u036f]/g, '')
                        .replace(/[`~!@#$%^&*()_|+\=?;:'",.<>\{\}\[\]\\\/]/gi, '');
    const url = '../festas/' + nomeFesta + '&' + codFesta + '/estoque';
    return url;
  }

}
