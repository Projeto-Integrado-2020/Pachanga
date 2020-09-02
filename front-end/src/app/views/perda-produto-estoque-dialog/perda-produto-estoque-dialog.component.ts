import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material';
import { FormBuilder, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-perda-produto-dialog',
  templateUrl: './perda-produto-estoque-dialog.component.html',
  styleUrls: ['./perda-produto-estoque-dialog.component.scss']
})
export class PerdaProdutoEstoqueDialogComponent implements OnInit {

  public estoque: any;
  public component: any;
  public produto: any;
  public form: any;
  public festa: any;

  constructor(@Inject(MAT_DIALOG_DATA) data, public formBuilder: FormBuilder, public dialog: MatDialog) {
    this.produto = data.produto;
    this.estoque = data.estoque;
    this.component = data.component;
    this.festa = data.festa;
  }

  ngOnInit() {
    this.gerarForm();
  }

  gerarForm() {
    this.form = this.formBuilder.group({
      quantidade: new FormControl('', [Validators.required, Validators.min(1)]),
    });
  }

  get f() { return this.form.controls; }

  perdaProduto() {
    
  }

}
