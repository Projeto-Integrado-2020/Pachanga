import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material';
import { GetFestaService } from 'src/app/services/get-festa/get-festa.service';
import { Router } from '@angular/router';
import { StatusFestaService } from 'src/app/services/status-festa/status-festa.service';

@Component({
  selector: 'app-estoque-painel',
  templateUrl: './estoque-painel.component.html',
  styleUrls: ['./estoque-painel.component.scss']
})

export class EstoquePainelComponent implements OnInit {

  dataSource = ELEMENT_DATA;
  displayedColumns: string[] = ['nome', 'qtd', 'status', 'teste'];
  expandedElement: PeriodicElement | null;

  public festaNome: string;
  options: FormGroup;
  public festa: any;
  public statusFesta: any;
  panelOpenState = false;
  public form: FormGroup;

  constructor(public fb: FormBuilder, public dialog: MatDialog, public getFestaService: GetFestaService,
              public router: Router, public statusService: StatusFestaService) {
      this.options = fb.group({
        bottom: 55,
        top: 0
      });
  }

  gerarForm() {
    this.form = this.fb.group({
      grupoSelect: new FormControl('', Validators.required),
    });
  }

  get f() { return this.form.controls; }



  ngOnInit() {
    let idFesta = this.router.url;
    idFesta = idFesta.substring(idFesta.indexOf('&') + 1, idFesta.indexOf('/', idFesta.indexOf('&')));
    this.getFestaService.acessarFesta(idFesta).subscribe((resp: any) => {
      this.getFestaService.setFarol(false);
      this.festa = resp;
      this.festaNome = resp.nomeFesta;
      this.statusFesta = resp.statusFesta;
    });
    this.gerarForm();
  }
}

export interface PeriodicElement {
  nome: string;
  qtd: number;
  status: string;
  teste: any;
}

const ELEMENT_DATA: PeriodicElement[] = [
  {
    nome: 'Skol 350ml',
    qtd: 1.0079,
    status: '',
    teste: '1'
  }, {
    nome: 'Dolly Limão 2L',
    qtd: 4.0026,
    status: '',
    teste: '1'
  }, {
    nome: 'Barrigudinha original 400ml',
    qtd: 6.941,
    status: '',
    teste: '1'
  },
];
