import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material';
import { EditarFormService } from 'src/app/services/editar-form/editar-form.service';

@Component({
  selector: 'app-editar-form-dialog',
  templateUrl: './editar-form-dialog.component.html',
  styleUrls: ['./editar-form-dialog.component.scss']
})
export class EditarFormDialogComponent implements OnInit {

  component: any;
  codFesta: any;
  googleForm: any;
  form: FormGroup;

  constructor(@Inject(MAT_DIALOG_DATA) data, public editarService: EditarFormService,
              public dialog: MatDialog, public formBuilder: FormBuilder) {
    this.googleForm = data.form;
    this.codFesta = data.codFesta;
    this.component = data.component;
  }

  ngOnInit() {
    this.form = this.formBuilder.group({
      nome: new FormControl(this.googleForm.nome, Validators.required),
      /* tslint:disable */
      url: new FormControl(this.googleForm.url, [Validators.required, Validators.pattern(/^[A-Za-z][A-Za-z\d.+-]*:\/*(?:\w+(?::\w+)?@)?[^\s/]+(?::\d+)?(?:\/[\w#!:.?+=&%@\-/]*)?$/)]),
      /* tslint:enable */
    });
  }

  get f() { return this.form.controls; }

  editarForm(nomeQuestionario, urlQuestionario) {
    const formEditado = {
      codQuestionario: this.googleForm.codQuestionario,
      codFesta: this.codFesta,
      nomeQuestionario,
      urlQuestionario
    };

    this.editarService.editarQuestionario(formEditado).subscribe((resp: any) => {
      this.editarService.setFarol(false);
      this.component.ngOnInit();
      this.dialog.closeAll();
    });
  }

}
