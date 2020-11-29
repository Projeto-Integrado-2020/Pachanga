import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material';
import { DeletarCupomService } from 'src/app/services/deletar-cupom/deletar-cupom.service';
import { SuccessDialogComponent } from '../success-dialog/success-dialog.component';

@Component({
  selector: 'app-deletar-cupom-dialog',
  templateUrl: './deletar-cupom-dialog.component.html',
  styleUrls: ['./deletar-cupom-dialog.component.scss']
})
export class DeletarCupomDialogComponent implements OnInit {

  cupom: any;
  codFesta: any;
  component: any;

  constructor(@Inject(MAT_DIALOG_DATA) data, public dialog: MatDialog, public deletarCupom: DeletarCupomService) {
    this.cupom = data.cupom;
    this.codFesta = data.codFesta;
    this.component = data.component;
  }

  ngOnInit() {
  }

  deleteCupom() {
    this.deletarCupom.deleteCupom(this.cupom.codCupom).subscribe((resp: string) => {
      this.deletarCupom.setFarol(false);
      this.dialog.closeAll();
      this.component.ngOnInit();
      this.openDialogSuccess('CUPOMDEL');
    });
  }

  openDialogSuccess(message: string) {
    this.dialog.open(SuccessDialogComponent, {
      width: '20rem',
      data: {message}
    });
  }

}
