import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material';
import { Router } from '@angular/router';
import { CriarLoteService } from 'src/app/services/criar-lote/criar-lote.service';
import { GetFestaService } from 'src/app/services/get-festa/get-festa.service';

@Component({
  selector: 'app-criar-lote',
  templateUrl: './criar-lote.component.html',
  styleUrls: ['./criar-lote.component.scss']
})
export class CriarLoteComponent implements OnInit {

  public festaNome: string;
  public festa: any;
  public form: FormGroup;
  minDate = new Date();

  constructor(public formBuilder: FormBuilder, public dialog: MatDialog,
              public loteAdd: CriarLoteService, public getFestaService: GetFestaService, public router: Router) {
  }

  ngOnInit() {
    let idFesta = this.router.url;
    idFesta = idFesta.substring(idFesta.indexOf('&') + 1, idFesta.indexOf('/', idFesta.indexOf('&')));
    this.getFestaService.acessarFesta(idFesta).subscribe((resp: any) => {
      this.getFestaService.setFarol(false);
      this.festa = resp;
      this.festaNome = resp.nomeFesta;
    });
    this.gerarForm();
  }

  gerarForm() {
    this.form = this.formBuilder.group({
      numeroLote: new FormControl('', Validators.required),
      quantidade: new FormControl('', [Validators.required, Validators.min(1)]),
      preco: new FormControl('', Validators.required),
      nomeLote: new FormControl('', [Validators.required, Validators.min(1)]),
      descLote: new FormControl('', Validators.required),
      inicioData: new FormControl('', Validators.required),
      dthrInicio: new FormControl('', Validators.required),
      fimData: new FormControl('', Validators.required),
      dthrFim: new FormControl('', Validators.required)
    });
  }

  get f() { return this.form.controls; }

  addLote(numeroLote, nomeLote, quantidade, preco, descLote, dataInicio, dthrInicio, dataFim, dthrFim) {
    const loteTO = {
      codFesta: this.festa.codFesta,
      codLote: null,
      numeroLote,
      quantidade,
      preco,
      nomeLote,
      descLote,
      horarioInicio: dataInicio.slice(6, 10) + '-' + dataInicio.slice(3, 5) + '-' + dataInicio.slice(0, 2) + 'T' + dthrInicio,
      horarioFim: dataFim.slice(6, 10) + '-' + dataFim.slice(3, 5) + '-' + dataFim.slice(0, 2) + 'T' + dthrFim,
    };

    this.loteAdd.novoLote(loteTO).subscribe((resp: any) => {
      this.loteAdd.setFarol(false);
      this.router.navigate(['festas/' + this.festa.nomeFesta   + '&' + this.festa.codFesta + '/ingressos/']);
    });
  }

}
