import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { NavbarComponent, ConviteData } from '../navbar/navbar.component';
import { ConvidadoService } from 'src/app/services/convidado/convidado.service';

@Component({
  selector: 'app-festa-detalhes-dialog',
  templateUrl: './festa-detalhes-dialog.component.html',
  styleUrls: ['./festa-detalhes-dialog.component.scss']
})
export class FestaDetalhesDialogComponent implements OnInit {
  //CONVFEST?1&1

  mensagem: string;
  codConvidado: string;
  idGrupo: string;

  nomeFesta: string;
  horarioInicioFesta: string;



  constructor(
              @Inject(MAT_DIALOG_DATA) data,
              public convService: ConvidadoService
              ) {
                this.mensagem = data.mensagem;
               }

  ngOnInit() {
    const msg = this.mensagem;
    this.codConvidado = msg.slice(9, msg.indexOf('&'));
    this.idGrupo = msg.slice(msg.indexOf('&') + 1);
    this.getDetalhesFesta();
  }

  getDetalhesFesta() {
    this.convService.getDetalhesFesta(this.codConvidado, this.idGrupo).subscribe(
      (response: any) => {
        console.log(response);
        this.nomeFesta = response.nomeFesta;
      }
    );
  }

  aceitarConvite() {
    console.log(this.codConvidado);
    console.log(this.idGrupo);
    this.convService.aceitarConvite(this.codConvidado, this.idGrupo).subscribe();
  }

  recusarConvite() {
    console.log(this.codConvidado);
    console.log(this.idGrupo);
    this.convService.recusarConvite(this.codConvidado, this.idGrupo).subscribe();
  }

}
