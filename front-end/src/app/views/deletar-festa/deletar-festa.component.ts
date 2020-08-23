import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material';
import { DeletarFestaService } from '../../services/deletar-festa/deletar-festa.service';
import { SuccessDialogComponent } from '../success-dialog/success-dialog.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-deletar-festa',
  templateUrl: './deletar-festa.component.html',
  styleUrls: ['./deletar-festa.component.scss']
})
export class DeletarFestaComponent implements OnInit {

  public festa: any;

  constructor(@Inject(MAT_DIALOG_DATA) data, public deleteService: DeletarFestaService,
              public dialog: MatDialog, public router: Router) {
    this.festa = data.festa;
  }

  ngOnInit() {
  }

  deletarFesta() {
    this.deleteService.deleteFesta(this.festa.codFesta).subscribe((resp: string) => {
      this.dialog.closeAll();
      this.deleteService.setFarol(false);
      this.router.navigate(['minhas-festas']);
      this.openDialogSuccess(resp);
    });
  }

  openDialogSuccess(message: string) {
    this.dialog.open(SuccessDialogComponent, {
      width: '20rem',
      data: {message}
    });
  }

}
