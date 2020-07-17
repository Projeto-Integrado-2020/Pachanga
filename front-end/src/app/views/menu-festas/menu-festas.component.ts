import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { MenuFestasService } from '../../services/menu-festa/menu-festas.service';
import { LoginService } from '../../services/loginService/login.service';
import {PageEvent} from '@angular/material/paginator';

@Component({
  selector: 'app-menu-festas',
  templateUrl: './menu-festas.component.html',
  styleUrls: ['./menu-festas.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class MenuFestasComponent implements OnInit {

  festas: any = [];
  length = 100;
  pageSize = 10;
  pageSizeOptions: number[] = [5, 10, 25, 100];

  //MatPaginator Output
  pageEvent: PageEvent;

  constructor(public menuFestasService: MenuFestasService, public loginService: LoginService) { }

  ngOnInit() {
    this.menuFestasService.getFestas(this.loginService.usuarioInfo.codUsuario).subscribe((resp: any) => {
      this.menuFestasService.setFarol(false);
      this.festas = resp;
      console.log(resp);
    });
  }

  isAdmin(festa) {
    return festa.funcionalidade === 'ORGANIZADOR';
  }

  getDateFromDTF(date) {
    let conversion = date.split('T', 1);
    console.log(conversion);
    conversion = conversion[0].split('-');
    console.log(conversion);
    return conversion[2] + '/' + conversion[1] + '/' + conversion[0];
  }

  getTimeFromDTF(date) {
    return date.split('T')[1];
  }


  setPageSizeOptions(setPageSizeOptionsInput: string) {
    if (setPageSizeOptionsInput) {
      this.pageSizeOptions = setPageSizeOptionsInput.split(',').map(str => +str);
    }
  }

  createUrl(nomeFesta) {
    return nomeFesta.toLowerCase().replace('-', '').replace('–', '')
                    .replace(/\s+/g, '-').replace('ç', 'c')
                    .replace('º', '').replace('ª', '')
                    .normalize('NFD').replace(/[\u0300-\u036f]/g, '')
                    .replace(/[`~!@#$%^&*()_|+\=?;:'",.<>\{\}\[\]\\\/]/gi, '');
  }

}
