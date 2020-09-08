import { Component, OnInit, Inject, ChangeDetectorRef } from '@angular/core';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material';
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';
import { EditarProdutoService } from 'src/app/services/editar-produto/editar-produto.service';

@Component({
  selector: 'app-editar-produto-dialog',
  templateUrl: './editar-produto-dialog.component.html',
  styleUrls: ['./editar-produto-dialog.component.scss']
})
export class EditarProdutoDialogComponent implements OnInit {

  component: any;
  codFesta: any;
  produto: any;
  form: FormGroup;
  showDosagem: boolean;

  constructor(@Inject(MAT_DIALOG_DATA) data, public editarService: EditarProdutoService,
              public dialog: MatDialog, public formBuilder: FormBuilder, private cd: ChangeDetectorRef) {
    this.produto = data.produto;
    this.codFesta = data.codFesta;
    this.component = data.component;
  }

  ngOnInit() {
    this.showDosagem = this.produto.dose;
    this.form = this.formBuilder.group({
      marca: new FormControl(this.produto.marca, Validators.required),
      preco: new FormControl(this.produto.preco, Validators.required),
      dosagem: new FormControl(this.produto.quantDose),
      checkbox: new FormControl(this.produto.dose),
    }, {
      validator: this.dosagemValidator('dosagem', 'checkbox')
      });
  }

  get f() { return this.form.controls; }

  editarProduto(marca, precoMedio) {
    const produtoEditado = {
      codProduto: this.produto.codProduto,
      codFesta: this.codFesta,
      marca,
      precoMedio,
      dose: this.form.get('checkbox').value,
      quantDoses: this.form.get('dosagem').value
    };

    this.editarService.editarProduto(produtoEditado).subscribe((resp: any) => {
      this.editarService.setFarol(false);
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
