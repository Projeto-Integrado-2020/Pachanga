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
      precoDesconto: new FormControl('', Validators.required),
    });
  }

  get f() { return this.form.controls; }

  criarCupom(nomeCupom, precoDesconto) {
    const cupom = {
      nomeCupom,
      precoDesconto,
      codFesta: this.codFesta
    };

    console.log(cupom);

    this.addCupom.criarCupom(cupom).subscribe((resp: any) => {
      this.addCupom.setFarol(false);
      this.component.ngOnInit();
      this.dialog.closeAll();
    });
  }

}
