import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material';
import { FormBuilder, FormControl, Validators, FormGroup } from '@angular/forms';
import { PerdaProdutoEstoqueService } from 'src/app/services/perda-produto-estoque/perda-produto-estoque.service';

@Component({
  selector: 'app-perda-produto-dialog',
  templateUrl: './perda-produto-estoque-dialog.component.html',
  styleUrls: ['./perda-produto-estoque-dialog.component.scss']
})
export class PerdaProdutoEstoqueDialogComponent implements OnInit {

  public estoque: any;
  public component: any;
  public element: any;
  public form: any;
  public quebra: boolean;
  public indexEstoque: any;
  public indexProduto: any;

  constructor(@Inject(MAT_DIALOG_DATA) data, public formBuilder: FormBuilder, public dialog: MatDialog,
              public perdaProdutoEstoque: PerdaProdutoEstoqueService) {
    this.estoque = data.estoque;
    this.component = data.component;
    this.element = data.element;
    this.indexEstoque = data.indexEstoque;
    this.indexProduto = data.indexProduto;
  }

  ngOnInit() {
    this.gerarForm();
  }

  gerarForm() {
    if (this.element.dose) {
      this.form = this.formBuilder.group({
        quantidade100: new FormControl(''),
        quantidade75: new FormControl(''),
        quantidade50: new FormControl(''),
        quantidade25: new FormControl('')
      }, {
        validator: this.dosagemValidator('quantidade25', 'quantidade50', 'quantidade75', 'quantidade100')
        });
    } else {
      this.form = this.formBuilder.group({
        quantidade: new FormControl('', [Validators.required, Validators.min(1)]),
      });
    }
  }

  get f() { return this.form.controls; }

  perdaProduto(quantidade, element) {
    element = element.codProduto;
    element = element.codProduto;
    const quantidadeTO = {
      quantidade25: null,
      quantidade50: null,
      quantidade75: null,
      quantidade100: quantidade
    };

    this.perdaProdutoEstoque.perdaProdutoEstoque(quantidadeTO, element, this.estoque.codEstoque).subscribe((resp: any) => {
      this.perdaProdutoEstoque.setFarol(false);
      this.component.quantidadesProdutos[this.indexEstoque][this.indexProduto].quantidadeAtual -= quantidade;
      this.dialog.closeAll();
    });
  }

  perdaProdutoDose(quantidade25, quantidade50, quantidade75, quantidade100, element) {
    element = element.codProduto;
    const quantidadeTO = {
      quantidade25,
      quantidade50,
      quantidade75,
      quantidade100
    };

    if (quantidade25 == null) {
      quantidade25 = null;
    } else if (quantidade50 == null) {
      quantidade50 = null;
    } else if (quantidade75 == null) {
      quantidade75 = null;
    } else if (quantidade100 == null) {
      quantidade100 = null;
    }

    this.perdaProdutoEstoque.perdaProdutoEstoque(quantidadeTO, element, this.estoque.codEstoque).subscribe((resp: any) => {
      this.perdaProdutoEstoque.setFarol(false);
      this.component.quantidadesProdutos[this.indexEstoque][this.indexProduto].quantidadeAtual -=
      ((quantidade25 * 0.25) + (quantidade50 * 0.50) + (quantidade75 * 0.75) + quantidade100);
      this.dialog.closeAll();
    });
  }

  dosagemValidator(quantidade25, quantidade50, quantidade75, quantidade100) {
    return (formGroup: FormGroup) => {
      const quantidade25Input = formGroup.get(quantidade25);
      const quantidade50Input = formGroup.get(quantidade50);
      const quantidade75Input = formGroup.get(quantidade75);
      const quantidade100Input = formGroup.get(quantidade100);

      // set error on matchingControl if validation fails
      if (!quantidade25Input.value && !quantidade50Input.value && !quantidade75Input.value && !quantidade100Input.value) {
        quantidade25Input.setErrors({ requiredTrue: true });
        quantidade50Input.setErrors({ requiredTrue: true });
        quantidade75Input.setErrors({ requiredTrue: true });
        quantidade100Input.setErrors({ requiredTrue: true });
      } else {
        quantidade25Input.setErrors(null);
        quantidade50Input.setErrors(null);
        quantidade75Input.setErrors(null);
        quantidade100Input.setErrors(null);
      }
    };
  }

}
