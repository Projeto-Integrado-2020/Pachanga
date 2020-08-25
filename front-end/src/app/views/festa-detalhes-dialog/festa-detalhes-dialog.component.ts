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


  mensagem: string;
  codConvidado: string;
  idGrupo: string;

  festa = {nomeFesta: '', codEnderecoFesta: '', horarioFimFesta: '', horarioInicioFesta: '', nomeGrupo: ''};
  horarioInicioFesta: string;

  constructor(
              @Inject(MAT_DIALOG_DATA) data,
              public convService: ConvidadoService,
              public dialog: MatDialog,
              public router: Router
              ) {
                this.mensagem = data.mensagem;
               }

  ngOnInit() {
    const msg = this.mensagem;
    this.codConvidado = msg.slice(msg.indexOf('&') + 1);
    this.idGrupo = msg.slice(9, msg.indexOf('&'));
    this.getDetalhesFesta();
  }

  getDetalhesFesta() {
    this.convService.getDetalhesFesta(this.codConvidado, this.idGrupo).subscribe(
      (response: any) => {
        this.festa = response;
      }
    );
  }

  aceitarConvite() {
    this.convService.aceitarConvite(this.codConvidado, this.idGrupo).subscribe(
      (resp) => {
        this.dialog.closeAll();
        this.router.navigate(['/minhas-festas']);
      }
    );
  }

  recusarConvite() {
    this.convService.recusarConvite(this.codConvidado, this.idGrupo).subscribe();
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
