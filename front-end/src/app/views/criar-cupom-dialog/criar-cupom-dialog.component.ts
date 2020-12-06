import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material';
import { CriarCupomService } from 'src/app/services/criar-cupom/criar-cupom.service';

@Component({
  selector: 'app-criar-cupom-dialog',
  templateUrl: './criar-cupom-dialog.component.html',
  styleUrls: ['./criar-cupom-dialog.component.scss']
})
export class CriarCupomDialogComponent implements OnInit {

  component: any;
  codFesta: any;
  form: FormGroup;

  constructor(@Inject(MAT_DIALOG_DATA) data, public dialog: MatDialog, public formBuilder: FormBuilder,
              public addCupom: CriarCupomService) {
    this.codFesta = data.codFesta;
    this.component = data.component;
  }

  ngOnInit() {
    this.form = this.formBuilder.group({
      nomeCupom: new FormControl('', Validators.required),
      tipoDesconto: new FormControl('', Validators.required),
      porcentagemDesc: new FormControl(0, [Validators.max(100), Validators.min(1)]),
      precoDesconto: new FormControl(0, [Validators.min(1)])
    }, {
        validator: this.tipoDescontoValidation('tipoDesconto', 'porcentagemDesc', 'precoDesconto')
      });
  }

  get f() { return this.form.controls; }

  criarCupom() {
    const tipoDesconto = this.form.get('tipoDesconto').value;
    const precoDesconto = this.form.get('precoDesconto').value;
    const porcentagemDesc = this.form.get('porcentagemDesc').value;

    const cupom = {
      nomeCupom: this.form.get('nomeCupom').value,
      tipoDesconto,
      precoDesconto: tipoDesconto === 'V' ? precoDesconto : null,
      porcentagemDesc: tipoDesconto === 'P' ? porcentagemDesc : null,
      codFesta: this.codFesta
    };

    this.addCupom.criarCupom(cupom).subscribe((resp: any) => {
      this.addCupom.setFarol(false);
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
