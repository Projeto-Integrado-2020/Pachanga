import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RelatorioAreaSegService } from 'src/app/services/relatorios/relatorio-area-seg.service';

@Component({
  selector: 'app-relatorios-seguranca',
  templateUrl: './relatorios-seguranca.component.html',
  styleUrls: ['./relatorios-seguranca.component.scss']
})
export class RelatoriosSegurancaComponent implements OnInit {


 codFesta = this.router.url.substring(this.router.url.indexOf('&') + 1, this.router.url.indexOf('/', this.router.url.indexOf('&')));
  
  constructor(
    private relAreaSegService: RelatorioAreaSegService,
    private router: Router
  ) { }

  ngOnInit() {
    //this.problemasArea(this.codFesta)
  }

  problemasArea(codFesta) {
    this.relAreaSegService.problemasArea(codFesta).subscribe((resp: any) => {
        console.log('problemasArea');
        console.log(resp);
      });
  }

  /*
  chamadasUsuario() {
    this.relAreaSegService.chamadasUsuario().subscribe((resp: any) => {
      console.log('chamadasUsuario');
      console.log(resp);
    });
  }
  usuarioSolucionador() {
    this.relAreaSegService.usuarioSolucionador().subscribe((resp: any) => {
      console.log('usuarioSolucionador');
      console.log(resp);
    });
  }*/

}
