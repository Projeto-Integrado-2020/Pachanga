import { Component, CUSTOM_ELEMENTS_SCHEMA, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { GetCategoriasService } from 'src/app/services/get-categorias/get-categorias.service';
import { GetFestaIndexService } from 'src/app/services/get-festa-index/get-festa-index.service';

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
  loading = false;
  festas: any;
  festasMostradas = [];
  length;
  rows: 2;
  nenhumaFesta = false;
  categorias = [];

  constructor(
    private translate: TranslateService,
    public getFestas: GetFestaIndexService,
    public getCategorias: GetCategoriasService
    ) {
  }

  tiles: Tile[] = [
    {text: this.title, cols: 3, rows: 1, },
    {text: null, cols: 1, rows: 1}
  ];

  ngOnInit() {
    this.loading = true;
    this.getFestas.getFestasLista().subscribe((resp: any) => {
      this.getFestas.setFarol(false);
      this.festas = resp;

      if (this.festas.length === 0) {
        this.nenhumaFesta = true;
      }

      this.length = this.festas.length;
      for (const festa of this.festas) {
        if (!festa.urlImagem) {
          festa.urlImagem = 'https://res.cloudinary.com/htctb0zmi/image/upload/v1611352783/pachanga-logo_tikwrw.png';
        }

        if (festa.categoriaSecundaria === null) {
          Object.assign(festa, {categoriaSecundaria: {
            codCategoria: null,
            nomeCategoria: null
          }});
        }
      }
      this.festasMostradas = this.festas;
    });
    this.loading = false;
    this.getCategorias.getCategorias().subscribe((resp: any) => {
      this.categorias = resp.sort((a, b) => {
        if (a.nomeCategoria < b.nomeCategoria) { return -1; }
        if (a.nomeCategoria > b.nomeCategoria) { return 1; }
        return 0;
      });
    });

  }

  getDateFromDTF(date) {
    let conversion = date.split(' ', 1);
    conversion = conversion[0].split('-');
    return conversion[2] + '/' + conversion[1] + '/' + conversion[0];
  }

  getTimeFromDTF(date) {
    return date.split(' ')[1];
  }

  filtrarGenero(genero, eventos) {
    return eventos.filter(evento => evento.categoriaPrimaria.nomeCategoria === genero ||
                          evento.categoriaSecundaria.nomeCategoria === genero);
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
