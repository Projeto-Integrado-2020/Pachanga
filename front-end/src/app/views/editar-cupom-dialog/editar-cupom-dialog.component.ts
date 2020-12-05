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
      porcentagemDesc: new FormControl(this.cupom.porcentagemDesc, [Validators.min(1)])
    }, {
      validator: this.tipoDescontoValidation('tipoDesconto', 'porcentagemDesc', 'precoDesconto')
    });
  }

  get f() { return this.form.controls; }

  editarCupom() {
    const tipoDesconto = this.form.get('tipoDesconto').value;
    const precoDesconto = this.form.get('precoDesconto').value;
    const porcentagemDesc = this.form.get('porcentagemDesc').value;

    const cupom = {
      codCupom: this.cupom.codCupom,
      nomeCupom: this.form.get('nomeCupom').value,
      tipoDesconto,
      precoDesconto: tipoDesconto === 'V' ? precoDesconto : null,
      porcentagemDesc: tipoDesconto === 'P' ? porcentagemDesc : null,
      codFesta: this.codFesta
    };

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
