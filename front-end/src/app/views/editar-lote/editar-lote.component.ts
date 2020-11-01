import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material';
import { Router } from '@angular/router';
import { EditarLoteService } from 'src/app/services/editar-lote/editar-lote.service';
import { GetLoteUnicoService } from 'src/app/services/get-lote-unico/get-lote-unico.service';
import { SuccessDialogComponent } from '../success-dialog/success-dialog.component';

@Component({
  selector: 'app-editar-lote',
  templateUrl: './editar-lote.component.html',
  styleUrls: ['./editar-lote.component.scss']
})
export class EditarLoteComponent implements OnInit {

  options: FormGroup;
  public festa: any;
  source: any;
  public form: FormGroup;
  minDate = new Date();
  public lote: any;

  constructor(public formBuilder: FormBuilder, public dialog: MatDialog, public loteUnico: GetLoteUnicoService,
              public loteEdit: EditarLoteService, public router: Router) {
  }

  ngOnInit() {
    this.gerarForm();
    this.getLoteUnico();
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

  getLoteUnico() {
    let idLote = this.router.url;
    idLote = idLote.substring(idLote.indexOf('editar-lote/') + 12);
    this.loteUnico.getLoteUnico(idLote).subscribe((resp: any) => {
      this.loteUnico.setFarol(false);
      this.lote = resp;
      this.setFormValues();
    });
  }

  editLote(numeroLote, nomeLote, quantidade, preco, descLote, dataInicio, dthrInicio, dataFim, dthrFim) {
    const loteTO = {
      codFesta: this.lote.codFesta,
      codLote: this.lote.codLote,
      numeroLote,
      quantidade,
      preco,
      nomeLote,
      descLote,
      horarioInicio: dataInicio.slice(6, 10) + '-' + dataInicio.slice(3, 5) + '-' + dataInicio.slice(0, 2) + 'T' + dthrInicio,
      horarioFim: dataFim.slice(6, 10) + '-' + dataFim.slice(3, 5) + '-' + dataFim.slice(0, 2) + 'T' + dthrFim,
    };

    this.loteEdit.editarLote(loteTO).subscribe((resp: any) => {
      this.loteEdit.setFarol(false);
      this.openDialogSuccess('LOTEALT');
    });
  }

  setFormValues() {
    this.f.numeroLote.setValue(this.lote.numeroLote);
    this.f.quantidade.setValue(this.lote.quantidade);
    this.f.preco.setValue(this.lote.preco);
    this.f.nomeLote.setValue(this.lote.nomeLote);
    this.f.descLote.setValue(this.lote.descLote);
    this.f.inicioData.setValue(new Date(this.lote.horarioInicio));
    this.f.fimData.setValue(new Date(this.lote.horarioFim));
    this.f.dthrInicio.setValue(this.getTimeFromDTF(this.lote.horarioInicio));
    this.f.dthrFim.setValue(this.getTimeFromDTF(this.lote.horarioFim));
    if (new Date() > new Date(this.lote.horarioInicio)) {
      this.minDate = new Date(this.lote.horarioInicio);
    }
  }

  openDialogSuccess(message: string) {
    this.dialog.open(SuccessDialogComponent, {
      width: '20rem',
      data: {message}
    });
  }

  getDateFromDTF(date) {
    let conversion = date.split('T', 1);
    conversion = conversion[0].split('-');
    return conversion[2] + '/' + conversion[1] + '/' + conversion[0];
  }

  getTimeFromDTF(date) {
    return date.split('T')[1].slice(0, 5);
  }


}
