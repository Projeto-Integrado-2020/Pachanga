import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { MenuFestasService } from '../../services/menu-festa/menu-festas.service';
import { FestaPainelControleComponent } from '../festa-painel-controle/festa-painel-controle.component';

@Component({
  selector: 'app-menu-festas',
  templateUrl: './menu-festas.component.html',
  styleUrls: ['./menu-festas.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class MenuFestasComponent implements OnInit {

  festas: any = [];

  constructor(public menuFestasService: MenuFestasService) { }

  ngOnInit() {
    this.menuFestasService.getFestas().subscribe((resp: any) => {
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


}
