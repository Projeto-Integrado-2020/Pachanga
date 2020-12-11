import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material';
import { Router } from '@angular/router';
import { IngressosService } from 'src/app/services/ingressos/ingressos.service';
import { DialogIngressosMesmaFestaComponent } from '../dialog-ingressos-mesma-festa/dialog-ingressos-mesma-festa.component';
import { QrcodeDialogComponent } from '../qrcode-dialog/qrcode-dialog.component';

@Component({
  selector: 'app-meus-ingressos',
  templateUrl: './meus-ingressos.component.html',
  styleUrls: ['./meus-ingressos.component.scss']
})
export class MeusIngressosComponent implements OnInit {


  listaIngressos: any;
  listaIngressosUnicos: any;
  ingressosAtivos: any[] = [];
  ingressosEncerrados: any[] = [];
  dicionarioIngressos = {};

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

        this.listaIngressos = res;
        let listaIngressosUnicos = this.getArrayUnica(this.listaIngressos);
        console.log(listaIngressosUnicos);

        for (const ingresso of listaIngressosUnicos) {
          Object.assign(ingresso, {Qtde: 0});
          ingresso.Qtde = this.listaIngressos.filter(x => x.festa.codFesta === ingresso.festa.codFesta).length;
          if (ingresso.festa.statusFesta === 'F') {
            this.ingressosEncerrados.push(ingresso);
          } else {
            this.ingressosAtivos.push(ingresso);
          }
        }

        console.log(this.listaIngressos);
        //   console.log(ingresso.festa.horarioFimFesta);
        //   // if (this.datetimeVenceu(ingresso.festa.HorarioFimFesta)) {

        //   // }

        // }
      }
    );
  }

  getArrayUnica(ingressos){
    const arrayUnica = [];

    for(let ingresso of ingressos) {
      if(!arrayUnica.find(ingr => ingr.festa.codFesta === ingresso.festa.codFesta)){
        arrayUnica.push(ingresso);
      }
    }

    return arrayUnica;
  }

  botaoImprimirBoleto(ingresso){
    if(ingresso.Qtde > 1) {
      this.abrirBoletoIngressosMultiplos(ingresso.festa.codFesta);
    } else{
      console.log("abrir url do boleto");
    }
  }


  abrirBoletoIngressosMultiplos(codFesta){
    let ingressos = this.listaIngressos.filter(x => x.festa.codFesta === codFesta);
    const boletosUnicos = []

    for(let ingresso of ingressos) {
      if(!boletosUnicos.find(ingr => ingr.codBoleto === ingresso.codBoleto)){
        boletosUnicos.push(ingresso);
      }
    }

    if(boletosUnicos.length > 1){
      this.dialog.open(DialogIngressosMesmaFestaComponent, {
        data: {
          ingressos: boletosUnicos
        }
      })
    } else {
      console.log("abrir url do boleto: " + boletosUnicos[0].codBoleto);
    }
  }

  abrirQRDialog(ingresso) {
    this.dialog.open(QrcodeDialogComponent, {
      height: '255px',
      width: '255px',
      data: {
        link: ingresso.codIngresso
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
