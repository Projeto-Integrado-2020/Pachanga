import { Component, OnInit, Inject } from '@angular/core';
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

  constructor(@Inject(MAT_DIALOG_DATA) data, public editarService: EditarProdutoService,
              public dialog: MatDialog, public formBuilder: FormBuilder) {
    this.produto = data.produto;
    this.codFesta = data.codFesta;
    this.component = data.component;
  }

  ngOnInit() {
    this.form = this.formBuilder.group({
      marca: new FormControl(this.produto.marca, Validators.required),
      preco: new FormControl(this.produto.preco, Validators.required)
    });
  }

  get f() { return this.form.controls; }

  editarProduto(marca, precoMedio) {
    const produtoEditado = {
      codProduto: this.produto.codProduto,
      codFesta: this.codFesta,
      marca,
      precoMedio
    };

    this.editarService.editarProduto(produtoEditado).subscribe((resp: any) => {
      this.editarService.setFarol(false);
      this.component.ngOnInit();
      this.dialog.closeAll();
    });
  }

}
