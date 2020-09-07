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
    const quantidadeTO = [
      null,
      null,
      null,
      quantidade
    ] ;

    this.perdaProdutoEstoque.perdaProdutoEstoque(this.estoque.codEstoque, element.dose, element.codProduto, quantidadeTO)
    .subscribe((resp: any) => {
      this.perdaProdutoEstoque.setFarol(false);
      this.component.quantidadesProdutos[this.indexEstoque][this.indexProduto].quantidadeAtual -= quantidade;
      this.dialog.closeAll();
    });
  }

  perdaProdutoDose(quantidade25, quantidade50, quantidade75, quantidade100, element) {
    const quantidadeTO = [
      !quantidade25 ? 0 : quantidade25 * element.quantDoses,
      !quantidade50 ? 0 : quantidade50 * element.quantDoses,
      !quantidade75 ? 0 : quantidade75 * element.quantDoses,
      !quantidade100 ? 0 : quantidade100 * element.quantDoses
    ];

    this.perdaProdutoEstoque.perdaProdutoEstoque
    (this.estoque.codEstoque, element.dose, element.codProduto, quantidadeTO).subscribe((resp: any) => {
      this.perdaProdutoEstoque.setFarol(false);
      this.component.quantidadesProdutos[this.indexEstoque][this.indexProduto].quantidadeAtual -=
      (Math.ceil(quantidadeTO[0] * 0.25) + Math.ceil(quantidadeTO[1] * 0.50) + Math.ceil(quantidadeTO[2] * 0.75) + quantidadeTO[3]);
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
