import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material';
import { IngressosService } from 'src/app/services/ingressos/ingressos.service';
import { QrcodeDialogComponent } from '../qrcode-dialog/qrcode-dialog.component';

@Component({
  selector: 'app-meus-ingressos',
  templateUrl: './meus-ingressos.component.html',
  styleUrls: ['./meus-ingressos.component.scss']
})
export class MeusIngressosComponent implements OnInit {

  infoIngresso: any = [
    {
    endereco: 'Rua das Caneleiras, 27',
    data: '21:00, 28 de Janeiro de 2019'
    },
    {
      endereco: 'Rua Asasd, 237',
      data: '8:00, 16 de Janeiro de 2019'
    }
  ];
  constructor(
    private ingressosService: IngressosService,
    private dialog: MatDialog
    ) { }

  ngOnInit() {
    this.listarIngressos();
  }

  listarIngressos() {
    console.log('sasdas')
    this.ingressosService.listaIngressos().subscribe(
      (res) => {
        console.log('adajda');
        console.log(res);
      }
    );
  }

  abrirQRDialog() {
    this.dialog.open(QrcodeDialogComponent, {
      data: {
      }
    });
  }

}
