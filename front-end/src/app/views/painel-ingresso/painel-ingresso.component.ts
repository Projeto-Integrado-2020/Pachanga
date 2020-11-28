import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatDialog } from '@angular/material';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { GetFestaService } from 'src/app/services/get-festa/get-festa.service';
import { GetLoteService } from 'src/app/services/get-lote/get-lote.service';
import { textChangeRangeIsUnchanged } from 'typescript';
import { DeletarLoteDialogComponent } from '../deletar-lote-dialog/deletar-lote-dialog.component';
import bancos from './lista-bancos.json';


export interface TabelaLote {
  nome: string;
  preco: string;
  dthrInicio: any;
  dthrFim: any;
  quantidade: any;
}

@Component({
  selector: 'app-painel-ingresso',
  templateUrl: './painel-ingresso.component.html',
  styleUrls: ['./painel-ingresso.component.scss']
})
export class PainelIngressoComponent implements OnInit {

  constructor(public fb: FormBuilder, public dialog: MatDialog, public getFestaService: GetFestaService,
              public router: Router, public getLote: GetLoteService) {
    this.options = fb.group({
    bottom: 55,
    top: 0
    });
  }

  public festaNome: string;
  public lotes: any;
  options: FormGroup;
  public festa: any;
  public statusFesta: any;
  panelOpenState = false;
  public forms = {};
  estoques: any;
  dataSources = [];
  subscription: Subscription;
  source: any;
  listaBancos: any;
  contaMask: any;
  dadosBancEditavel: boolean;

  displayedColumns: string[] = ['nome', 'preco', 'numeroLote', 'quantidade', 'dthrInicio', 'dthrFim'];

  ngOnInit() {
    this.source = null;
    this.lotes = [];
    let idFesta = this.router.url;
    this.dataSources = [];
    idFesta = idFesta.substring(idFesta.indexOf('&') + 1, idFesta.indexOf('/', idFesta.indexOf('&')));

    this.getFestaService.acessarFesta(idFesta).subscribe((resp: any) => {
      this.getFestaService.setFarol(false);
      this.festa = resp;
      this.festaNome = resp.nomeFesta;
      this.statusFesta = resp.statusFesta;
      this.resgatarLote();
      this.listaBancos = bancos;
      this.dadosBancEditavel = false;
      //this.resgatarListaBancos();
    });
  }

  resgatarLote() {
    this.getLote.getLote(this.festa.codFesta).subscribe((resp: any) => {
      this.lotes = resp;
      this.getLote.setFarol(false);
    });
  }


  setFesta(festa) {
    this.festa = festa;
  }

  getDateFromDTF(date) {
    let conversion = date.split('T', 1);
    conversion = conversion[0].split('-');
    return conversion[2] + '/' + conversion[1] + '/' + conversion[0];
  }

  getTimeFromDTF(date) {
    return date.split('T')[1];
  }

  createUrlEditLote(codLote) {
    return '../ingressos/editar-lote/' + codLote;
  }

  openDialogDelete(lote, codLote) {
    this.dialog.open(DeletarLoteDialogComponent, {
      width: '20rem',
      data: {
        lote,
        codLote,
        component: this
      }
    });
  }

  editarDadosBancarios(){
    this.dadosBancEditavel = true;
  }

  salvarDadosBancarios(){
    this.dadosBancEditavel = false;
  }



}
