import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, NgForm, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { DadosBancariosService } from 'src/app/services/dados-bancarios/dados-bancarios.service';
import { GetFestaService } from 'src/app/services/get-festa/get-festa.service';
import { GetLoteService } from 'src/app/services/get-lote/get-lote.service';
import { textChangeRangeIsUnchanged } from 'typescript';
import { DeletarLoteDialogComponent } from '../deletar-lote-dialog/deletar-lote-dialog.component';
import bancos from './lista-bancos.json';

@Component({
  selector: 'app-dialog-dados-bancarios',
  templateUrl: './dialog-dados-bancarios.component.html',
  styleUrls: ['./dialog-dados-bancarios.component.scss']
})
export class DialogDadosBancariosComponent implements OnInit {

  constructor(
    public fb: FormBuilder,
    public dialog: MatDialog,
    public getFestaService: GetFestaService,
    public router: Router,
    public getLote: GetLoteService,
    private dadosBancariosService: DadosBancariosService
    ) {
    this.options = fb.group({
      bottom: 55,
      top: 0
    });
    this.form = fb.group({
      codBanco: new FormControl({value: '', disabled: true}, Validators.required),
      codAgencia: new FormControl({value: '', disabled: true}, Validators.required),
      codConta: new FormControl({value: '', disabled: true}, Validators.required)
    });
  }

  public festaNome: string;
  public lotes: any;
  options: FormGroup;
  public festa: any;
  public statusFesta: any;
  panelOpenState = false;
  public form: FormGroup;
  estoques: any;
  dataSources = [];
  subscription: Subscription;
  source: any;
  listaBancos: any;
  filteredList: any;
  contaMask: any;
  dadosBancEditavel: boolean;
  dadosBancariosTO: any;

  get f() { return this.form.controls; }

  ngOnInit() {
    this.source = null;
    this.lotes = [];
    let idFesta = this.router.url;
    this.dataSources = [];
    idFesta = idFesta.substring(idFesta.indexOf('&') + 1, idFesta.indexOf('/', idFesta.indexOf('&')));

    this.getFestaService.acessarFesta(idFesta).subscribe((resp: any) => {
      this.festa = resp;
      this.festaNome = resp.nomeFesta;
      this.statusFesta = resp.statusFesta;
      this.getDadosBancarios();
      this.listaBancos = bancos;
      this.filteredList = bancos;
      this.dadosBancEditavel = false;
      // this.resgatarListaBancos();
    });
  }

  editarDadosBancarios() {
    this.dadosBancEditavel = true;
    this.form.get('codBanco').enable();
    this.form.get('codAgencia').enable();
    this.form.get('codConta').enable();
  }


  salvarDadosBancarios() {

    this.dadosBancariosTO.codBanco = this.form.get('codBanco').value;
    this.dadosBancariosTO.codAgencia = this.form.get('codAgencia').value;
    this.dadosBancariosTO.codConta = this.form.get('codConta').value;

    this.dadosBancariosService.inserirDados(this.dadosBancariosTO).subscribe(
      (resp: any) => {
        this.dadosBancariosService.setFarol(false);
        this.dadosBancEditavel = false;
        this.form.get('codBanco').disable();
        this.form.get('codAgencia').disable();
        this.form.get('codConta').disable();
      });
  }

  getDadosBancarios() {
    this.dadosBancariosService
      .receberDado(this.festa.codFesta)
      .subscribe (
      (resp) => {
        this.dadosBancariosTO = resp;
        if (!this.dadosBancariosTO) {
          this.dadosBancariosTO = {
            codFesta: this.festa.codFesta,
            codBanco: null,
            codAgencia: null,
            codConta: null
          };
        } else {
          this.form.get('codBanco').setValue(this.dadosBancariosTO.codBanco);
          this.form.get('codAgencia').setValue(this.dadosBancariosTO.codAgencia);
          this.form.get('codConta').setValue(this.dadosBancariosTO.codConta);
        }
        this.dadosBancariosService.setFarol(false);
      });
  }

}
