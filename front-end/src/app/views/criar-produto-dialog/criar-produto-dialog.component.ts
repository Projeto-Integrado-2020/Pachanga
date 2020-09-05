import { Component, OnInit, Inject, ChangeDetectorRef } from '@angular/core';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material';
import { CriarProdutoService } from 'src/app/services/criar-produtos/criar-produto.service';
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-criar-produto-dialog',
  templateUrl: './criar-produto-dialog.component.html',
  styleUrls: ['./criar-produto-dialog.component.scss']
})
export class CriarProdutoDialogComponent implements OnInit {

  component: any;
  codFesta: any;
  form: FormGroup;
  indeterminate = false;
  showDosagem = false;

  constructor(@Inject(MAT_DIALOG_DATA) data, public criarService: CriarProdutoService,
              public dialog: MatDialog, public formBuilder: FormBuilder, private cd: ChangeDetectorRef) {
    this.codFesta = data.codFesta;
    this.component = data.component;
  }

  ngOnInit() {
    this.form = this.formBuilder.group({
      marca: new FormControl('', Validators.required),
      preco: new FormControl('', Validators.required),
      dosagem: new FormControl(''),
      checkbox: new FormControl('')
    }, {
      validator: this.dosagemValidator('dosagem', 'checkbox')
      });
  }

  get f() { return this.form.controls; }

  criarProduto(marca, precoMedio) {
    const produto = {
      marca,
      precoMedio,
      dose: this.form.get('checkbox').value,
      quantDoses: this.form.get('dosagem').value
    };

    this.criarService.adicionarProduto(produto, this.codFesta).subscribe((resp: any) => {
      this.criarService.setFarol(false);
      this.component.ngOnInit();
      this.dialog.closeAll();
    });
  }

  showHideDosagem() {
    this.showDosagem = !this.showDosagem;
    if (!this.showDosagem) {
      this.form.get('dosagem').setValue(null);
    }
    this.cd.detectChanges();
  }

  dosagemValidator(dosagem, checkbox) {
    return (formGroup: FormGroup) => {
      const checkboxInput = formGroup.get(checkbox);
      const dosagemInput = formGroup.get(dosagem);

      // set error on matchingControl if validation fails
      if (checkboxInput.value && !dosagemInput.value) {
        dosagemInput.setErrors({ dosagemTrue: true });
      } else {
        dosagemInput.setErrors(null);
      }
    };
  }
}
