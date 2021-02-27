import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-index-carrocel',
  templateUrl: './index-carrocel.component.html',
  styleUrls: ['./index-carrocel.component.scss']
})


export class IndexCarrocelComponent implements OnInit {

  @Input() eventos: any;

  @Input() titulo: string;

  constructor(private router: Router) { }

  ngOnInit() {

  }

  redirectUrl(nomeFesta, codFesta) {
    nomeFesta = nomeFesta.toLowerCase().replace('-', '').replace('–', '')
                        .replace(/\s+/g, '-').replace('ç', 'c')
                        .replace('º', '').replace('ª', '')
                        .normalize('NFD').replace(/[\u0300-\u036f]/g, '')
                        .replace(/[`~!@#$%^&*()_|+\=?;:'",.<>\{\}\[\]\\\/]/gi, '');
    const url = '../' + nomeFesta + '&' + codFesta + '/venda-ingressos/';
    this.router.navigate([url]);
  }

}
