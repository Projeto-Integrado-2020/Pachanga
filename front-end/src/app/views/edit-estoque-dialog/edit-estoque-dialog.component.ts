import { Component, OnInit, Inject } from '@angular/core';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material';
import { EditarEstoqueService } from 'src/app/services/editar-estoque/editar-estoque.service';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-edit-estoque-dialog',
  templateUrl: './edit-estoque-dialog.component.html',
  styleUrls: ['./edit-estoque-dialog.component.scss']
})
export class EditEstoqueDialogComponent implements OnInit {

  public codFesta: any;
  public estoque: any;
  public nomeEstoque: any;
  public form: FormGroup;
  public component: any;

  constructor(@Inject(MAT_DIALOG_DATA) data, public formBuilder: FormBuilder, public dialog: MatDialog,
              public editarEstoque: EditarEstoqueService) {
    this.codFesta = data.codFesta;
    this.estoque = data.estoque;
    this.component = data.component;
  }

  ngOnInit() {
    this.gerarForm();
  }

  gerarForm() {
    this.form = this.formBuilder.group({
      nomeEstoque: new FormControl(this.estoque.nomeEstoque, Validators.required),
    });
  }

  get f() { return this.form.controls; }

  editEstoque(nomeEstoque) {
    const estoqueTO = {
      codEstoque: this.estoque.codEstoque,
      nomeEstoque
    };

    this.editarEstoque.atualizarEstoque(this.codFesta, estoqueTO).subscribe((resp: any) => {
      this.editarEstoque.setFarol(false);
      this.dialog.closeAll();
      this.component.ngOnInit();
    });
  }

}
