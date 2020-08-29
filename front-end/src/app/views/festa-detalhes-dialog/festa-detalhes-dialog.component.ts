import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material';
import { ConvidadoService } from 'src/app/services/convidado/convidado.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-festa-detalhes-dialog',
  templateUrl: './festa-detalhes-dialog.component.html',
  styleUrls: ['./festa-detalhes-dialog.component.scss']
})
export class FestaDetalhesDialogComponent implements OnInit {


  alerta: any;
  idGrupo: string;

  constructor(
              @Inject(MAT_DIALOG_DATA) data,
              public convService: ConvidadoService,
              public dialog: MatDialog,
              public router: Router
              ) {
                this.alerta = data.alerta;
               }

  ngOnInit() {
    this.idGrupo =  this.alerta.mensagem.split('&')[0].split('?')[1];
  }


  aceitarConvite() {
    this.convService.aceitarConvite(this.alerta.codConvidado, this.idGrupo).subscribe(
      (resp) => {
        this.dialog.closeAll();
        this.router.navigate(['/minhas-festas']);
      }
    );
  }

  recusarConvite() {
    this.convService.recusarConvite(this.alerta.codConvidado, this.idGrupo).subscribe(
      (resp) => {
        this.dialog.closeAll();
      }
    );
  }

  formatDateTime(date) {
    if (date) {
      const conversao = date.split('T');
      const data = conversao[0].split('-');
      const hora = conversao[1];
      return data[2] + '/' + data[1] + '/' + data[0] + ' - ' + hora;
    }
    return '';
  }

}
