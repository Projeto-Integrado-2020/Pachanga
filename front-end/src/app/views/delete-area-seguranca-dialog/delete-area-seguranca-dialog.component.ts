import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material';
import { DeletarAreaSegurancaService } from 'src/app/services/deletar-area-seguranca/deletar-area-seguranca.service';
import { SuccessDialogComponent } from '../success-dialog/success-dialog.component';

@Component({
  selector: 'app-delete-area-seguranca-dialog',
  templateUrl: './delete-area-seguranca-dialog.component.html',
  styleUrls: ['./delete-area-seguranca-dialog.component.scss']
})
export class DeleteAreaSegurancaDialogComponent implements OnInit {

  public codFesta: any;
  public component: any;
  public codArea: any;

  constructor(@Inject(MAT_DIALOG_DATA) data, public dialog: MatDialog,
              public deleteAreaSegurancaService: DeletarAreaSegurancaService) {
    this.codFesta = data.codFesta;
    this.codArea = data.codArea;
    this.component = data.component;
  }

  ngOnInit() {
  }

  deleteAreaSeguranca() {
    this.deleteAreaSegurancaService.deletarAreaSeguranca(this.codArea).subscribe((resp: any) => {
      this.dialog.closeAll();
      this.deleteAreaSegurancaService.setFarol(false);
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
