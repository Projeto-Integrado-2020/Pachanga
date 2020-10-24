import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material';
import { CriarAreaSegurancaService } from 'src/app/services/criar-area-seguranca/criar-area-seguranca.service';

@Component({
  selector: 'app-criar-area-seguranca-dialog',
  templateUrl: './criar-area-seguranca-dialog.component.html',
  styleUrls: ['./criar-area-seguranca-dialog.component.scss']
})
export class CriarAreaSegurancaDialogComponent implements OnInit {

  public codFesta: any;
  public nomeArea: any;
  public form: FormGroup;
  public component: any;

  constructor(@Inject(MAT_DIALOG_DATA) data, public formBuilder: FormBuilder, public dialog: MatDialog,
              public areaSegurancaAdd: CriarAreaSegurancaService) {
    this.codFesta = data.codFesta;
    this.component = data.component;
  }

  ngOnInit() {
    this.gerarForm();
  }

  gerarForm() {
    this.form = this.formBuilder.group({
      nomeArea: new FormControl('', Validators.required),
    });
  }

  get f() { return this.form.controls; }

  addAreaSeguranca(nomeArea) {
    const areaSegurancaTO = {
      nomeArea,
      codFesta: this.codFesta
    };

    this.areaSegurancaAdd.novaAreaSeguranca(areaSegurancaTO).subscribe((resp: any) => {
      this.areaSegurancaAdd.setFarol(false);
      this.dialog.closeAll();
      this.component.ngOnInit();
    });
  }

}
