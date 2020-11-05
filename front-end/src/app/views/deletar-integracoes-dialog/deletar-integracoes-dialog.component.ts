import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material';
import { DeletarIntegracaoService } from 'src/app/services/deletar-integracao/deletar-integracao.service';
import { SuccessDialogComponent } from '../success-dialog/success-dialog.component';

@Component({
  selector: 'app-deletar-integracoes-dialog',
  templateUrl: './deletar-integracoes-dialog.component.html',
  styleUrls: ['./deletar-integracoes-dialog.component.scss']
})
export class DeletarIntegracoesDialogComponent implements OnInit {
  integracao: any;
  component: any;

  constructor(@Inject(MAT_DIALOG_DATA) data, public dialog: MatDialog, public deleteService: DeletarIntegracaoService) {
    this.integracao = data.integracao;
    this.component = data.component;
  }

  ngOnInit() {
  }

  deletarIntegracao() {
    this.deleteService.deleteIntegracao(this.integracao.codInfo).subscribe((resp: string) => {
      this.deleteService.setFarol(false);
      this.dialog.closeAll();
      this.component.ngOnInit();
      this.openDialogSuccess('INTDELE');
    });
  }

  openDialogSuccess(message: string) {
    this.dialog.open(SuccessDialogComponent, {
      width: '20rem',
      data: {message}
    });
  }

}
