import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material';
import { DeletarProdutoService } from 'src/app/services/deletar-produto/deletar-produto.service';
import { SuccessDialogComponent } from '../success-dialog/success-dialog.component';

@Component({
  selector: 'app-deletar-produto-dialog',
  templateUrl: './deletar-produto-dialog.component.html',
  styleUrls: ['./deletar-produto-dialog.component.scss']
})
export class DeletarProdutoDialogComponent implements OnInit {

  produto: any;
  codFesta: any;
  component: any;

  constructor(@Inject(MAT_DIALOG_DATA) data, public deleteService: DeletarProdutoService,
              public dialog: MatDialog) {
    this.produto = data.grupo;
    this.codFesta = data.codFesta;
    this.component = data.component;
  }

  ngOnInit() {
  }

  deletarProduto() {
    this.deleteService.deleteProduto(this.produto.codProduto, this.codFesta).subscribe((resp: string) => {
      this.dialog.closeAll();
      this.component.ngOnInit();
      this.openDialogSuccess('PRODDELE');
    });
  }

  openDialogSuccess(message: string) {
    this.dialog.open(SuccessDialogComponent, {
      width: '20rem',
      data: {message}
    });
  }

}
