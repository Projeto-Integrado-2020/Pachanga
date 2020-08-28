import { Component, OnInit, Inject } from '@angular/core';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material';
import { EditarEstoqueService } from 'src/app/services/editar-estoque/editar-estoque.service';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { RecargaProdutoEstoqueService } from 'src/app/services/recarga-produto-estoque/recarga-produto-estoque.service';

@Component({
  selector: 'app-recarga-produto-estoque-dialog',
  templateUrl: './recarga-produto-estoque-dialog.component.html',
  styleUrls: ['./recarga-produto-estoque-dialog.component.scss']
})
export class RecargaProdutoEstoqueDialogComponent implements OnInit {

  public component: any;
  public estoque: any;
  public quantidade: any;
  public form: FormGroup;
  public element: any;

  constructor(@Inject(MAT_DIALOG_DATA) data, public formBuilder: FormBuilder, public dialog: MatDialog,
              public recargaProdutoEstoqueService: RecargaProdutoEstoqueService) {
    this.component = data.component;
    this.element = data.element;
    this.estoque = data.estoque;
  }

  ngOnInit() {
    this.gerarForm();
  }

  gerarForm() {
    this.form = this.formBuilder.group({
      quantidade: new FormControl(this.estoque.quantidade, Validators.required),
    });
  }

  get f() { return this.form.controls; }

  recargaProduto(quantidade, element) {
    element = element.codProduto;
    console.log(quantidade, element, this.estoque.codEstoque);

    this.recargaProdutoEstoqueService.recargaProdutoEstoque(quantidade, element, this.estoque.codEstoque).subscribe((resp: any) => {
      this.recargaProdutoEstoqueService.setFarol(false);
      this.dialog.closeAll();
      this.component.ngOnInit();
    });
  }

}
