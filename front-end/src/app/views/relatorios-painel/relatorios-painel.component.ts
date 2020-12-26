import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { GetFestaService } from 'src/app/services/get-festa/get-festa.service';
import { RelatorioAreaSegService } from 'src/app/services/relatorios/relatorio-area-seg.service';
import { RelatorioEstoqueService } from 'src/app/services/relatorios/relatorio-estoque.service';

@Component({
  selector: 'app-relatorios-painel',
  templateUrl: './relatorios-painel.component.html',
  styleUrls: ['./relatorios-painel.component.scss']
})
export class RelatoriosPainelComponent implements OnInit {

  options: FormGroup;
  festa: any;
  festaNome: string;

  constructor(
    private fb: FormBuilder,
    private getFestaService: GetFestaService,
    private router: Router,
    private relEstoqueService: RelatorioEstoqueService,
    private relAreaSegService: RelatorioAreaSegService
    ) {
    this.options = fb.group({
      bottom: 55,
      top: 0
    });
  }

  ngOnInit() {
    let idFesta = this.router.url;
    idFesta = idFesta.substring(idFesta.indexOf('&') + 1, idFesta.indexOf('/', idFesta.indexOf('&')));
    this.getFestaService.acessarFesta(idFesta).subscribe((resp: any) => {
      this.getFestaService.setFarol(false);
      this.festa = resp;
      this.festaNome = resp.nomeFesta;
      
      this.problemasArea();
      this.chamadasUsuario();
      this.usuarioSolucionador();
      this.consumoItemEstoque();
      this.perdaItemEstoque();
      this.quantidadeItemEstoque();
    });
  }
  // relatorios areaseg
  problemasArea(){
    this.relAreaSegService.problemasArea().subscribe((resp:any) => {
        console.log("problemasArea");
        console.log(resp)
      });
  }
  chamadasUsuario(){
    this.relAreaSegService.chamadasUsuario().subscribe((resp:any) => {
      console.log("chamadasUsuario");
      console.log(resp)
    });
  }
  usuarioSolucionador(){
    this.relAreaSegService.usuarioSolucionador().subscribe((resp:any) => {
      console.log("usuarioSolucionador");
      console.log(resp)
    });
  }

  // relatorios estoque
  consumoItemEstoque(){
    this.relEstoqueService.consumoItemEstoque().subscribe((resp:any) => {
      console.log("consumoItemEstoque");
      console.log(resp)
    });
  }
  perdaItemEstoque(){
    this.relEstoqueService.perdaItemEstoque().subscribe((resp:any) => {
      console.log("perdaItemEstoque");
      console.log(resp)
    });
  }
  quantidadeItemEstoque(){
    this.relEstoqueService.quantidadeItemEstoque().subscribe((resp:any) => {
      console.log("quantidadeItemEstoque");
      console.log(resp)
    });
  }

}
