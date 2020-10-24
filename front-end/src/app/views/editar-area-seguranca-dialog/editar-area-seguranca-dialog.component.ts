import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material';
import { EditarAreaSegurancaService } from 'src/app/services/editar-area-seguranca/editar-area-seguranca.service';

@Component({
  selector: 'app-editar-area-seguranca-dialog',
  templateUrl: './editar-area-seguranca-dialog.component.html',
  styleUrls: ['./editar-area-seguranca-dialog.component.scss']
})
export class EditarAreaSegurancaDialogComponent implements OnInit {

  public codFesta: any;
  public area: any;
  public nomeEstoque: any;
  public form: FormGroup;
  public component: any;

  constructor(@Inject(MAT_DIALOG_DATA) data, public formBuilder: FormBuilder, public dialog: MatDialog,
              public editarAreaSeguranca: EditarAreaSegurancaService) {
    this.codFesta = data.codFesta;
    this.area = data.area;
    this.component = data.component;
  }

  ngOnInit() {
    this.gerarForm();
  }

  gerarForm() {
    this.form = this.formBuilder.group({
      nomeArea: new FormControl(this.area.nomeArea, Validators.required),
    });
  }

  get f() { return this.form.controls; }

  editAreaSeguranca(nomeArea) {
    const areaTO = {
      nomeArea,
      codFesta: this.codFesta,
      codArea: this.area.codArea
    };

    console.log(areaTO);

    this.editarAreaSeguranca.atualizarAreaSeguranca(areaTO).subscribe((resp: any) => {
      this.editarAreaSeguranca.setFarol(false);
      this.dialog.closeAll();
      this.component.ngOnInit();
    });
  }

}
