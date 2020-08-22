import { Component, OnInit, Inject } from '@angular/core';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material';
import { DeletarProdutoEstoqueService } from 'src/app/services/deletar-produto-estoque/deletar-produto-estoque.service';
import { SuccessDialogComponent } from '../success-dialog/success-dialog.component';

@Component({
  selector: 'app-deletar-produto-estoque-dialog',
  templateUrl: './deletar-produto-estoque-dialog.component.html',
  styleUrls: ['./deletar-produto-estoque-dialog.component.scss']
})
export class DeletarProdutoEstoqueDialogComponent implements OnInit {

  public estoque: any;
  public component: any;
  public produto: any;

  constructor(@Inject(MAT_DIALOG_DATA) data, public dialog: MatDialog,
              public deleteProdEstoqueService: DeletarProdutoEstoqueService) {
    this.estoque = data.estoque;
    this.produto = data.produto;
    this.component = data.component;
  }

  ngOnInit() {
  }

  deleteProdutoEstoque() {
    this.deleteProdEstoqueService.deletarProdutoEstoque(this.produto.codProduto, this.estoque.codEstoque).subscribe((resp: any) => {
      this.dialog.closeAll();
      this.deleteProdEstoqueService.setFarol(false);
      this.component.ngOnInit();
    });
  }

  openDialogSuccess(message: string) {
    this.dialog.open(SuccessDialogComponent, {
      width: '20rem',
      data: {message}
    });
  }

}
