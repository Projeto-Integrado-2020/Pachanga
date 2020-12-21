import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material';
import { Router } from '@angular/router';
import { Columns, Img, PdfMakeWrapper, QR, Table, Txt } from 'pdfmake-wrapper';
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
    public ingressosService: IngressosService,
    public dialog: MatDialog,
    public router: Router
    ) { }

  ngOnInit() {
    this.listarIngressos();
  }

  listarIngressos() {
    this.ingressosService.listaIngressos().subscribe(
      (res) => {

        this.listaIngressos = res;
        const listaIngressosUnicos = this.getArrayUnica(this.listaIngressos);

        for (const ingresso of listaIngressosUnicos) {
          Object.assign(ingresso, {Qtde: 0});
          Object.assign(ingresso, {listaUnidades: []});
          ingresso.listaUnidades = this.listaIngressos
          .filter(x => x.festa.codFesta === ingresso.festa.codFesta && x.statusCompra === ingresso.statusCompra);
          ingresso.Qtde = ingresso.listaUnidades.length;
          if (ingresso.festa.statusFesta === 'F') {
            this.ingressosEncerrados.push(ingresso);
          } else {
            this.ingressosAtivos.push(ingresso);
          }
        }
      }
    );
  }

  getArrayUnica(ingressos) {
    const arrayUnica = [];

    for (const ingresso of ingressos) {
      if (!arrayUnica
        .find(ingr => ingr.festa.codFesta === ingresso.festa.codFesta && ingr.statusCompra === ingresso.statusCompra)) {
        arrayUnica.push(ingresso);
      }
    }

    return arrayUnica;
  }

  botaoImprimirBoleto(ingresso) {
    if (ingresso.Qtde > 1) {
      this.abrirBoletoIngressosMultiplos(ingresso);
    } else {
      this.router.navigate([]).then(() => {  window.open(ingresso.urlBoleto, '_blank'); });
    }
  }


  abrirBoletoIngressosMultiplos(ingresso) {
    const ingressos = this.listaIngressos.filter(x => x.festa.codFesta === ingresso.festa.codFesta);
    const boletosUnicos = [];

    for (const i of ingressos) {
      if (!boletosUnicos.find(ingr => ingr.urlBoleto === i.urlBoleto)) {
        boletosUnicos.push(i);
      }
    }

    if (boletosUnicos.length > 1) {
      this.dialog.open(DialogIngressosMesmaFestaComponent, {
        data: {
          ingressos: boletosUnicos
        }
      });
    } else {
      this.router.navigate([]).then(() => {  window.open(ingresso.urlBoleto, '_blank'); });
    }
  }

  abrirQRDialog(ingresso) {

    const ingressos = this.listaIngressos.
      filter(x => x.festa.codFesta === ingresso.festa.codFesta && x.statusCompra === ingresso.statusCompra);
    this.dialog.open(QrcodeDialogComponent, {
      data: {
        ingressos
      }
    });
  }

  processardatetime(datetime) {
    const datetimeSplit = datetime.split(' ');
    const data = datetimeSplit[0].split('-').reverse().join('/');

    return data + ', ' + datetimeSplit[1].slice(0, 5);
  }

  navegarURL(rota) {
    this.router.navigateByUrl(rota);
  }

  gerarIngressoPDF(ingressos) {
    const pdf = new PdfMakeWrapper();
    pdf.info({
      title: 'Ingresso Pachanga',
      author: 'EventManager',
      subject: 'Ingresso Pachanga'
    });

    let nomePDF;
    for (const [index, ingresso] of ingressos.entries()) {
      this.criarPaginaPDF(pdf, ingresso);
      if (index === 0) {
        nomePDF = ingresso.codIngresso;
      }
      if (index < ingressos.length - 1) {
        pdf.add(new Txt('').pageBreak('after').end);
      }
    }
    pdf.create().download(nomePDF + '.pdf');
  }

  processarDataCompra(datetime) {
    const datetimeSplit = datetime.split('T');
    const data = datetimeSplit[0].split('-').reverse().join('/');

    return data + ', ' + datetimeSplit[1].slice(0, 5);
  }

  criarPaginaPDF(pdf, ingresso) {
    pdf.styles({
      title: {
        color: '#6b219c',
        bold: true,
        fontSize: 25,
        margin: [5, 5, 5, 15]
      },
      textosLow: {
        fontSize: 10,
        margin: 5
      },
      tableHeader: {
        margin: 15
      },
      textos: {
        bold: true,
        fontSize: 12,
        color: '#9c2152',
        margin: 5
      },
      subTitles: {
        bold: true,
        fontSize: 15,
        color: '#6b219c',
        margin: 5
      },
      tableTexto: {
        margin: [15, 0, 0, 5]
      },
      qrcode: {
        margin: [0, 15, 0, 15]
      },
      QRCodeTable: {
        margin: [0, 0, 0, 5]
      },
      header: {
        color: '#9c2152',
        bold: true,
        fontSize: 20,
      }
    });

    const layout = {
      hLineWidth(i, node) {
        if (i === 0 || i === node.table.body.length) {
          return 1;
        }
        return (i === node.table.headerRows) ? 0 : 0;
      }
    };
    pdf.add(new Txt('Pachanga').alignment('center').style('header').end);
    pdf.add(new Txt('https://pachanga.herokuapp.com/').alignment('center').style('textosLow').end);
    pdf.add(new Table([
      [
        new Txt(ingresso.festa.nomeFesta).style('title').end
      ],
      [
        new Txt('Horário da festa: ' + this.processardatetime(ingresso.festa.horarioInicioFesta) + ' - ' +
            this.processardatetime(ingresso.festa.horarioFimFesta)).style('textosLow').end
      ],
      [
        new Txt('Local: ' + ingresso.festa.codEnderecoFesta).style('textosLow').end
      ]
    ]).widths([ 500, 10 ]).style('tableHeader').end);

    pdf.add(new Columns([
      [
        new Table([
          [
            new Txt('Ingresso').style('subTitles').end
          ],
          [
            new Txt('Lote: ' + ingresso.nomeLote).style('textos').end
          ],
          [
            new Txt('Valor pago: R$' + ingresso.preco.toFixed(2)).style('textos').end
          ],
          [
            new Txt('Comprado dia ' + this.processarDataCompra(ingresso.dataCompra)).alignment('right').style('textosLow').end
          ]
        ]).widths([ 300, 10 ]).layout(layout).style('tableTexto').end,
        new Table([
          [
            new Txt('Participante').style('subTitles').end
          ],
          [
            new Txt('Nome: ' + ingresso.nomeTitular).style('textos').end
          ],
          [
            new Txt('E-mail: ' + ingresso.emailTitular).style('textos').end
          ]
        ]).widths([ 300, 10 ]).layout(layout).style('tableTexto').end
      ],
      [
        new Table([
          [
            new QR(ingresso.codIngresso).alignment('center').fit(150).style('qrcode').end
          ],
          [
            new Txt(ingresso.codIngresso).alignment('center').style('textosLow').end
          ]
        ]).layout(layout).widths([ 170, 10 ]).style(['QRCodeTable']).end
      ]
    ]).columnGap(1).end);
  }


}
