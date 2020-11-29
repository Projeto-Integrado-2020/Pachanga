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
      precoDesconto: new FormControl(this.cupom.precoDesconto, Validators.required),
    });
  }

  get f() { return this.form.controls; }

  editarCupom(nomeCupom, precoDesconto) {
    const cupom = {
      codCupom: this.cupom.codCupom,
      nomeCupom,
      precoDesconto,
      codFesta: this.codFesta
    };

    this.editCupom.editarCupom(cupom).subscribe((resp: any) => {
      this.editCupom.setFarol(false);
      this.component.ngOnInit();
      this.dialog.closeAll();
    });
  }

}
