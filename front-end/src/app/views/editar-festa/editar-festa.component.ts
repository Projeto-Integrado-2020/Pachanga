import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { EditarFestaService } from 'src/app/services/editar-festa/editar-festa.service';
import { GetFestaService } from 'src/app/services/get-festa/get-festa.service';
import { SuccessDialogComponent } from '../../views/success-dialog/success-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';

@Component({
  selector: 'app-editar-festa',
  templateUrl: './editar-festa.component.html',
  styleUrls: ['./editar-festa.component.scss']
})
export class EditarFestaComponent implements OnInit {

  constructor(public formBuilder: FormBuilder, public festaService: EditarFestaService,
              public router: Router, public dialog: MatDialog, public getFestaService: GetFestaService) { }

  public form: FormGroup;
  minDate: Date;
  festa: any;

  ngOnInit() {
    this.resgatarFesta();
    this.gerarForm();
  }

  gerarForm() {
    this.form = this.formBuilder.group({
      nomeFesta: new FormControl('', Validators.required),
      descFesta: new FormControl('', Validators.required),
      endereco: new FormControl('', Validators.required),
      inicioData: new FormControl('', Validators.required),
      fimData: new FormControl('', Validators.required),
      inicioHora: new FormControl('', Validators.required),
      fimHora: new FormControl('', Validators.required),
      organizador: new FormControl('', Validators.required),
      descOrganizador: new FormControl('', Validators.required),
    });
  }

  get f() { return this.form.controls; }

  resgatarFesta() {
    let idFesta = this.router.url;
    idFesta = idFesta.slice(idFesta.indexOf('&') + 1, idFesta.indexOf('/', idFesta.indexOf('&')));
    this.callServiceGet(idFesta);
  }

  atualizarFesta(nomeFesta, descricaoFesta, codEnderecoFesta, dataInicio, horaInicio, dataFim, horaFim, organizador, descOrganizador) {
    const dadosFesta = {
      codFesta: this.festa.codFesta,
      nomeFesta,
      statusFesta: null,
      organizador,
      horarioInicioFesta: dataInicio.slice(6, 10) + '-' + dataInicio.slice(3, 5) + '-' + dataInicio.slice(0, 2) + 'T' + horaInicio,
      horarioFimFesta: dataFim.slice(6, 10) + '-' + dataFim.slice(3, 5) + '-' + dataFim.slice(0, 2) + 'T' + horaFim,
      descricaoFesta,
      codEnderecoFesta,
      descOrganizador
    };
    this.callServiceAtualizacao(dadosFesta);
  }

  callServiceAtualizacao(dadosFesta) {
    this.festaService.atualizarFesta(dadosFesta).subscribe((resp: any) => {
      this.openDialogSuccess('FESTAALT');
    });
  }

  callServiceGet(idFesta) {
    this.getFestaService.acessarFesta(idFesta).subscribe((resp: any) => {
      this.festa = resp;
      this.setFormValues();
    });
  }

  openDialogSuccess(message: string) {
    this.dialog.open(SuccessDialogComponent, {
      width: '20rem',
      data: {message}
    });
  }

  getTimeFromDTF(date) {
    return date.split('T')[1].slice(0, 5);
  }

  setFormValues() {
    this.f.nomeFesta.setValue(this.festa.nomeFesta);
    this.f.descFesta.setValue(this.festa.descricaoFesta);
    this.f.endereco.setValue(this.festa.codEnderecoFesta);
    this.f.organizador.setValue(this.festa.organizador);
    this.f.descOrganizador.setValue(this.festa.descOrganizador);
    this.f.inicioData.setValue(new Date(this.festa.horarioInicioFesta));
    this.f.fimData.setValue(new Date(this.festa.horarioFimFesta));
    this.f.inicioHora.setValue(this.getTimeFromDTF(this.festa.horarioInicioFesta));
    this.f.fimHora.setValue(this.getTimeFromDTF(this.festa.horarioFimFesta));
    if (new Date() > new Date(this.festa.horarioInicioFesta)) {
      this.minDate = new Date(this.festa.horarioInicioFesta);
    }
  }

}
