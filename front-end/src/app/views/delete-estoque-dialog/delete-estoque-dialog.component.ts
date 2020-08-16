import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material';
import { DeletarEstoqueService } from 'src/app/services/deletar-estoque/deletar-estoque.service';


@Component({
  selector: 'app-delete-estoque-dialog',
  templateUrl: './delete-estoque-dialog.component.html',
  styleUrls: ['./delete-estoque-dialog.component.scss']
})
export class DeleteEstoqueDialogComponent implements OnInit {

  public codFesta: any;
  public codEstoque: any;
  public component: any;

  constructor(@Inject(MAT_DIALOG_DATA) data, public dialog: MatDialog,
              public deleteEstoqueService: DeletarEstoqueService) {
    this.codFesta = data.codFesta;
    this.codEstoque = data.codEstoque;
    this.component = data.component;
    }

  ngOnInit() {
  }

  deleteEstoque() {
    this.deleteEstoqueService.deleteEstoque(this.codFesta, this.codEstoque).subscribe((resp: any) => {
      this.dialog.closeAll();
      this.deleteEstoqueService.setFarol(false);
      this.component.ngOnInit();
    });
  }

}
