import { Component, OnDestroy, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material';
import { Router } from '@angular/router';
import { interval, Subscription } from 'rxjs';
import { GetSegurancaService } from 'src/app/services/get-seguranca/get-seguranca.service';
import { DetalhesProblemaDialogComponent } from '../detalhes-problema-dialog/detalhes-problema-dialog.component';

@Component({
  selector: 'app-painel-seguranca-geral',
  templateUrl: './painel-seguranca-geral.component.html',
  styleUrls: ['./painel-seguranca-geral.component.scss']
})
export class PainelSegurancaGeralComponent implements OnInit, OnDestroy {

  idFesta: any;
  visaoGeral: any = [];
  subscription: Subscription;
  source: any;

  constructor(public router: Router, public getSeguranca: GetSegurancaService,
              public dialog: MatDialog) { }

  ngOnInit() {
    this.ngOnDestroy();
    const url = this.router.url;
    this.idFesta = url.substring(url.indexOf('&') + 1, url.indexOf('/', url.indexOf('&')));
    this.resgatarAreaSeguranca();
    this.updateSeguranca();
  }

  resgatarAreaSeguranca() {
    this.getSeguranca.getAreaSeguranca(this.idFesta).subscribe((resp: any) => {
      for (const areaResp of resp) {
        for (const problema of areaResp.problemasArea) {
          let flag = 0;
          for (const problemasVisao of this.visaoGeral) {
            if (problema.codAreaProblema === problemasVisao.codAreaProblema) {
              flag = 1;
              problemasVisao.nomeArea = areaResp.nomeArea;
              problemasVisao.statusProblema = problema.statusProblema;
              problemasVisao.problemaCompleto = problema;
              break;
            }
          }
          if (flag === 0) {
            this.visaoGeral.push({
              codArea: areaResp.codArea,
              nomeArea: areaResp.nomeArea,
              codAreaProblema: problema.codAreaProblema,
              descProblema: problema.descProblema,
              statusProblema: problema.statusProblema,
              horarioInicio: problema.horarioInicio,
              problemaCompleto:  problema
            });
          }
        }
      }
      this.visaoGeral.sort(this.comparator);
    });
  }

  comparator(a, b) {
    if (a.statusProblema === 'A' && b.statusProblema !== 'A') {
      return -1;
    }
    if (a.statusProblema === 'A' && b.statusProblema === 'A' || a.statusProblema !== 'A' && b.statusProblema !== 'A') {
      if (a.horarioInicio > b.horarioInicio) {
        return -1;
      } else if (a.horarioInicio < b.horarioInicio) {
        return 1;
      } else {
        if (a.descProblema > b.descProblema) {
          return 1;
        } else if (a.descProblema < b.descProblema) {
          return -1;
        } else {
          if (a.nomeArea > b.nomeArea) {
            return 1;
          } else if (a.nomeArea < b.nomeArea) {
            return -1;
          }
        }
      }
    }
    return 0;
  }

  updateSeguranca() {
    this.source = interval(1000);
    this.subscription = this.source.subscribe(
      () => {
        this.resgatarAreaSeguranca();
      }
    );
  }

  processarDataCompra(datetime) {
    const datetimeSplit = datetime.split('T');
    const data = datetimeSplit[0].split('-').reverse().join('/');

    return data + ' - ' + datetimeSplit[1].slice(0, 8);
  }

  verDetalhesProblema(problema) {
    this.dialog.open(DetalhesProblemaDialogComponent, {
      width: '30rem',
      data: {
        problema
      }
    });
  }

  ngOnDestroy() {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
    this.source = null;
  }

}
