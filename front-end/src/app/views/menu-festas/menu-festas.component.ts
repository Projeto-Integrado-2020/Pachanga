import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { MenuFestasService } from '../../services/menu-festa/menu-festas.service';
import { LoginService } from '../../services/loginService/login.service';
import {PageEvent} from '@angular/material/paginator';

@Component({
  selector: 'app-menu-festas',
  templateUrl: './menu-festas.component.html',
  styleUrls: ['./menu-festas.component.scss']
})
export class MenuFestasComponent implements OnInit {

  festas: any = [];
  length;
  pageSize = 5;
  festasMostradas = [];
  pageSizeOptions: number[] = [5, 10, 25, 100];
  filtro = {admin: true};
  nenhumaFesta = false;
  buscaPorNome: any;
  buscaPorAdmin = false;
  loading = false;

  // MatPaginator Output
  pageEvent: PageEvent;

  constructor(public menuFestasService: MenuFestasService, public loginService: LoginService) { }

  ngOnInit() {
    this.menuFestasService.getFestas(this.loginService.usuarioInfo.codUsuario).subscribe((resp: any) => {
      this.loading = true;
      this.menuFestasService.setFarol(false);
      this.festas = resp.sort(this.nomeFestaSort);

      if (this.festas.length === 0) {
        this.nenhumaFesta = true;
      }
      this.length = this.festas.length;
      this.festasMostradas = this.festas.slice(0, 5);
    });
    this.loading = false;

  }

  nomeFestaSort(a, b) {
    if (a.nomeFesta > b.nomeFesta) {
      return 1;
    } else {
      return -1;
    }
  }

  isAdmin(festa) {
    return festa.isOrganizador === true;
  }

  getDateFromDTF(date) {
    let conversion = date.split(' ', 1);
    conversion = conversion[0].split('-');
    return conversion[2] + '/' + conversion[1] + '/' + conversion[0];
  }

  getTimeFromDTF(date) {
    return date.split(' ')[1];
  }

  onPageChange(event: PageEvent) {
    const startIndex = event.pageIndex * event.pageSize;
    let endIndex = startIndex + event.pageSize;
    if (endIndex > this.festas.length) {
      endIndex = this.festas.length;
    }
    this.festasMostradas = this.festas.slice(startIndex, endIndex);
  }


  setPageSizeOptions(setPageSizeOptionsInput: string) {
    if (setPageSizeOptionsInput) {
      this.pageSizeOptions = setPageSizeOptionsInput.split(',').map(str => +str);
    }
  }

  createUrl(nomeFesta, codFesta) {
    nomeFesta = nomeFesta.toLowerCase().replace('-', '').replace('–', '')
                        .replace(/\s+/g, '-').replace('ç', 'c')
                        .replace('º', '').replace('ª', '')
                        .normalize('NFD').replace(/[\u0300-\u036f]/g, '')
                        .replace(/[`~!@#$%^&*()_|+\=?;:'",.<>\{\}\[\]\\\/]/gi, '');
    const url = '../festas/' + nomeFesta + '&' + codFesta + '/painel';
    return url;
  }

}
