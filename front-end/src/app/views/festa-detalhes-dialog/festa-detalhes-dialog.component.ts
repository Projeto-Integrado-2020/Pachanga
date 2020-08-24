import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialog } from '@angular/material';
import { NavbarComponent, ConviteData } from '../navbar/navbar.component';
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

  nomeFesta: string;
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
        this.nomeFesta = response.nomeFesta;
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

}
