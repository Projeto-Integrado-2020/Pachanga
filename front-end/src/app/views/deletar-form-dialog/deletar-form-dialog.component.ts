import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material';
import { DeletarFormService } from 'src/app/services/deletar-form/deletar-form.service';
import { SuccessDialogComponent } from '../success-dialog/success-dialog.component';

@Component({
  selector: 'app-deletar-form-dialog',
  templateUrl: './deletar-form-dialog.component.html',
  styleUrls: ['./deletar-form-dialog.component.scss']
})
export class DeletarFormDialogComponent implements OnInit {

  form: any;
  codFesta: any;
  component: any;

  constructor(@Inject(MAT_DIALOG_DATA) data, public dialog: MatDialog, public deleteService: DeletarFormService) {
    this.form = data.form;
    this.codFesta = data.codFesta;
    this.component = data.component;
  }

  ngOnInit() {
  }

  deletarForm() {
    this.deleteService.deleteQuestionario(this.form.codQuestionario).subscribe((resp: string) => {
      this.deleteService.setFarol(false);
      this.dialog.closeAll();
      this.component.ngOnInit();
      this.openDialogSuccess('FORMDELE');
    });
  }

  openDialogSuccess(message: string) {
    this.dialog.open(SuccessDialogComponent, {
      width: '20rem',
      data: {message}
    });
  }

}
