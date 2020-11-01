import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material';
import { DeletarLoteService } from 'src/app/services/deletar-lote/deletar-lote.service';
import { SuccessDialogComponent } from '../success-dialog/success-dialog.component';

@Component({
  selector: 'app-deletar-lote-dialog',
  templateUrl: './deletar-lote-dialog.component.html',
  styleUrls: ['./deletar-lote-dialog.component.scss']
})
export class DeletarLoteDialogComponent implements OnInit {

  public lote: any;
  public codLote: any;
  public component: any;

  constructor(@Inject(MAT_DIALOG_DATA) data, public deleteLoteService: DeletarLoteService,
              public dialog: MatDialog) {
    this.lote = data.lote;
    this.codLote = data.codLote;
    this.component = data.component;
  }

  ngOnInit() {
  }

  deletarLote() {
    this.deleteLoteService.deleteLote(this.codLote).subscribe((resp: string) => {
      this.dialog.closeAll();
      this.deleteLoteService.setFarol(false);
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
