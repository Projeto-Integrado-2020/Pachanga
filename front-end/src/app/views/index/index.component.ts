import { Component, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { GetFestaIndexService } from 'src/app/services/get-festa-index/get-festa-index.service';
import { MenuFestasService } from 'src/app/services/menu-festa/menu-festas.service';

export interface Tile {
  cols: number;
  rows: number;
  text: string;
 }


@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.scss']
})
export class IndexComponent implements OnInit {

  title: string = this.translate.instant('INDEX.OLA');
  public festas: any;
  festasMostradas = [];
  length;
  rows: 2;
  nenhumaFesta = false;

  constructor(private translate: TranslateService, public getFestas: GetFestaIndexService) {
  }

  tiles: Tile[] = [
    {text: this.title, cols: 3, rows: 1, },
    {text: null, cols: 1, rows: 1}
  ];

  ngOnInit() {
    this.getFestas.getFestasLista().subscribe((resp: any) => {
      this.getFestas.setFarol(false);
      this.festas = resp;

      if (this.festas.length === 0) {
        this.nenhumaFesta = true;
      }

      this.length = this.festas.length;
      this.festasMostradas = this.festas.slice(0, 5);
    });
  }

  getDateFromDTF(date) {
    let conversion = date.split('T', 1);
    conversion = conversion[0].split('-');
    return conversion[2] + '/' + conversion[1] + '/' + conversion[0];
  }

  getTimeFromDTF(date) {
    return date.split('T')[1];
  }

  createUrl(nomeFesta, codFesta) {
    nomeFesta = nomeFesta.toLowerCase().replace('-', '').replace('–', '')
                        .replace(/\s+/g, '-').replace('ç', 'c')
                        .replace('º', '').replace('ª', '')
                        .normalize('NFD').replace(/[\u0300-\u036f]/g, '')
                        .replace(/[`~!@#$%^&*()_|+\=?;:'",.<>\{\}\[\]\\\/]/gi, '');
    const url = '../' + nomeFesta + '&' + codFesta + '/venda-ingressos';
    return url;
  }

}
