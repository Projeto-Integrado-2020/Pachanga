import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material';
import { Router } from '@angular/router';
import { IngressosService } from 'src/app/services/ingressos/ingressos.service';
import { QrcodeDialogComponent } from '../qrcode-dialog/qrcode-dialog.component';

@Component({
  selector: 'app-meus-ingressos',
  templateUrl: './meus-ingressos.component.html',
  styleUrls: ['./meus-ingressos.component.scss']
})
export class MeusIngressosComponent implements OnInit {

  listaIngressos: any;
  ingressosAtivos: any[] = [];
  ingressosEncerrados: any[] = [];

  constructor(
    private ingressosService: IngressosService,
    private dialog: MatDialog,
    private router: Router
    ) { }

  ngOnInit() {
    this.listarIngressos();
  }

  listarIngressos() {
    this.ingressosService.listaIngressos().subscribe(
      (res) => {
        console.log(res);
        console.log(typeof res);
        this.listaIngressos = res;

        for (const ingresso of this.listaIngressos) {
          if (ingresso.festa.statusFesta === 'F') {
            this.ingressosEncerrados.push(ingresso);
          } else {
            this.ingressosAtivos.push(ingresso);
          }
        }
        //   console.log(ingresso.festa.horarioFimFesta);
        //   // if (this.datetimeVenceu(ingresso.festa.HorarioFimFesta)) {

        //   // }

        // }
      }
    );
  }

  abrirQRDialog() {
    this.dialog.open(QrcodeDialogComponent, {
      data: {
      }
    });
  }

  processardatetime(datetime) {
    const datetimeSplit = datetime.split(' ');
    const data = datetimeSplit[0].split('-').reverse().join('/');

    return data + ', ' + datetimeSplit[1];
  }

  navegarURL(rota) {
    this.router.navigateByUrl(rota);
  }


}
