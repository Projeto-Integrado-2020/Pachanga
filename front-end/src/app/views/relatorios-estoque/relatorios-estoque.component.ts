import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RelatorioEstoqueService } from 'src/app/services/relatorios/relatorio-estoque.service';

@Component({
  selector: 'app-relatorios-estoque',
  templateUrl: './relatorios-estoque.component.html',
  styleUrls: ['./relatorios-estoque.component.scss']
})
export class RelatoriosEstoqueComponent implements OnInit {

  codFesta: string;
  quantidadeItemValores = [];

  constructor(public relEstoqueService: RelatorioEstoqueService, public router: Router) { }

  ngOnInit() {
    let idFesta = this.router.url;
    this.codFesta = idFesta.substring(idFesta.indexOf('&') + 1, idFesta.indexOf('/', idFesta.indexOf('&')));
    this.consumoItemEstoque();
    this.quantidadeItemEstoque();
    this.perdaItemEstoque();
  }

  // relatorios estoque
  consumoItemEstoque() {
    this.relEstoqueService.consumoItemEstoque(this.codFesta).subscribe((resp: any) => {
      console.log('consumoItemEstoque');
      console.log(resp);
    });
  }
  perdaItemEstoque() {
    this.relEstoqueService.perdaItemEstoque(this.codFesta).subscribe((resp: any) => {
      console.log('perdaItemEstoque');
      console.log(resp);
    });
  }
  quantidadeItemEstoque() {
    this.relEstoqueService.quantidadeItemEstoque(this.codFesta).subscribe((resp: any) => {
      console.log('quantidadeItemEstoque');
      console.log(resp);
    });
  }

}
