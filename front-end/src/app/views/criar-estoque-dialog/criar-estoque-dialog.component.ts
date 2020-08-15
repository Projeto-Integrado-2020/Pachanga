import { Component, OnInit, Inject } from '@angular/core';
import { CriarEstoqueService } from 'src/app/services/criar-estoque/criar-estoque.service';
import { FormBuilder, FormControl, Validators, FormGroup } from '@angular/forms';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-criar-estoque-dialog',
  templateUrl: './criar-estoque-dialog.component.html',
  styleUrls: ['./criar-estoque-dialog.component.scss']
})
export class CriarEstoqueDialogComponent implements OnInit {

  public codFesta: any;
  public nomeEstoque: any;
  public form: FormGroup;
  public component: any;

  constructor(@Inject(MAT_DIALOG_DATA) data, public formBuilder: FormBuilder, public dialog: MatDialog,
              public criarEstoque: CriarEstoqueService) {
    this.codFesta = data.codFesta;
    this.component = data.component;
  }

  ngOnInit() {
    this.gerarForm();
  }

  gerarForm() {
    this.form = this.formBuilder.group({
      nomeEstoque: new FormControl('', Validators.required),
    });
  }

  get f() { return this.form.controls; }

  addEstoque(nomeEstoque) {
    const estoqueTO = {
      nomeEstoque
    };

    this.criarEstoque.novoEstoque(this.codFesta, estoqueTO).subscribe((resp: any) => {
      this.criarEstoque.setFarol(false);
      this.dialog.closeAll();
      this.component.ngOnInit();
    });
  }

}
