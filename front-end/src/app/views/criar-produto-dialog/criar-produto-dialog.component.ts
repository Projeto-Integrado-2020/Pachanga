import { Component, OnInit, Inject } from '@angular/core';
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

  constructor(@Inject(MAT_DIALOG_DATA) data, public criarService: CriarProdutoService,
              public dialog: MatDialog, public formBuilder: FormBuilder) {
    this.codFesta = data.codFesta;
    this.component = data.component;
  }

  ngOnInit() {
    this.form = this.formBuilder.group({
      marca: new FormControl('', Validators.required),
      preco: new FormControl('', Validators.required)
    });
  }

  get f() { return this.form.controls; }

  criarProduto(marca, precoMedio) {
    const produto = {
      marca,
      precoMedio
    };

    this.criarService.adicionarProduto(produto, this.codFesta).subscribe((resp: any) => {
      this.criarService.setFarol(false);
      this.component.ngOnInit();
      this.dialog.closeAll();
    });
  }

}
