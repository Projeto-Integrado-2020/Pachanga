import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material';
import { EditarCupomService } from 'src/app/services/editar-cupom/editar-cupom.service';

@Component({
  selector: 'app-editar-cupom-dialog',
  templateUrl: './editar-cupom-dialog.component.html',
  styleUrls: ['./editar-cupom-dialog.component.scss']
})
export class EditarCupomDialogComponent implements OnInit {

  public cupom: any;
  public codFesta: any;
  form: FormGroup;
  public component: any;
  minDate = new Date();

  constructor(@Inject(MAT_DIALOG_DATA) data, public dialog: MatDialog, public formBuilder: FormBuilder,
              public editCupom: EditarCupomService) {
    this.cupom = data.cupom;
    this.codFesta = data.codFesta;
    this.component = data.component;
  }

  ngOnInit() {
    this.form = this.formBuilder.group({
      nomeCupom: new FormControl(this.cupom.nomeCupom, Validators.required),
      tipoDesconto: new FormControl(this.cupom.tipoDesconto, Validators.required),
      precoDesconto: new FormControl(this.cupom.precoDesconto, [Validators.max(100), Validators.min(1)]),
      porcentagemDesc: new FormControl(this.cupom.porcentagemDesc, [Validators.min(1)]),
      inicioData: new FormControl(this.cupom.dataIniDesconto, Validators.required),
      fimData: new FormControl(this.cupom.dataFimDesconto, Validators.required)
    }, {
      validator: this.tipoDescontoValidation('tipoDesconto', 'porcentagemDesc', 'precoDesconto')
    });
  }

  get f() { return this.form.controls; }

  editarCupom(dataInicio, dataFim) {
    const tipoDesconto = this.form.get('tipoDesconto').value;
    const precoDesconto = this.form.get('precoDesconto').value;
    const porcentagemDesc = this.form.get('porcentagemDesc').value;

    const cupom = {
      codCupom: this.cupom.codCupom,
      nomeCupom: this.form.get('nomeCupom').value,
      tipoDesconto,
      precoDesconto: tipoDesconto === 'V' ? precoDesconto : null,
      porcentagemDesc: tipoDesconto === 'P' ? porcentagemDesc : null,
      codFesta: this.codFesta,
      dataIniDesconto: dataInicio.slice(6, 10) + '-' + dataInicio.slice(3, 5) + '-' + dataInicio.slice(0, 2) + 'T00:00:00',
      dataFimDesconto: dataFim.slice(6, 10) + '-' + dataFim.slice(3, 5) + '-' + dataFim.slice(0, 2) + 'T23:59:59'
    };

    console.log(cupom);

    this.editCupom.editarCupom(cupom).subscribe((resp: any) => {
      this.editCupom.setFarol(false);
      this.component.ngOnInit();
      this.dialog.closeAll();
    });
  }

  tipoDescontoValidation(tipoDesconto, porcentagemDesc, precoDesconto) {
    return (formGroup: FormGroup) => {
      const tipoDescontoInput = formGroup.get(tipoDesconto);
      const porcentagemDescInput = formGroup.get(porcentagemDesc);
      const precoDescontoInput = formGroup.get(precoDesconto);

      // set error on matchingControl if validation fails
      if (tipoDescontoInput.value === 'P' && !porcentagemDescInput.value) {
        porcentagemDescInput.setErrors({ required: true });
      } else {
        if (tipoDescontoInput.value === 'V' && !precoDescontoInput.value) {
          precoDescontoInput.setErrors({ required: true });
        } else {
          porcentagemDescInput.setErrors(null);
          precoDescontoInput.setErrors(null);
        }
      }
    };
  }

}
