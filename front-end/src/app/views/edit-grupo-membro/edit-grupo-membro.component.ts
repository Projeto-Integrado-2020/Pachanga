import { Component, OnInit, Inject } from '@angular/core';
import { GetGruposService } from 'src/app/services/get-grupos/get-grupos.service';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material';
import { EditarMembroGrupoService } from 'src/app/services/editar-membro-grupo/editar-membro-grupo.service';


@Component({
  selector: 'app-edit-grupo-membro',
  templateUrl: './edit-grupo-membro.component.html',
  styleUrls: ['./edit-grupo-membro.component.scss']
})
export class EditGrupoMembroComponent implements OnInit {

  public codFesta: any;
  public grupos: any;
  public form: FormGroup;
  public codUsuario: any;
  public grupo: any;
  component: any;

  constructor(public getGruposService: GetGruposService, public formBuilder: FormBuilder, @Inject(MAT_DIALOG_DATA) data,
              public editMembroService: EditarMembroGrupoService, public dialog: MatDialog) {
    this.codFesta = data.codFesta;
    this.codUsuario = data.codUsuario;
    this.grupo = data.grupo;
    this.component = data.component;
  }

  ngOnInit() {
    this.resgatarGrupo();
    /*this.grupos = [
      {codGrupo: '1', nomeGrupo: 'Salve'},
      {codGrupo: '2', nomeGrupo: 'Salve2'},
      {codGrupo: '3', nomeGrupo: 'Salve3'},
      {codGrupo: '4', nomeGrupo: 'Salve4'},
      {codGrupo: '5', nomeGrupo: 'Salve5'}
    ];*/
    this.gerarForm();
  }

  gerarForm() {
    this.form = this.formBuilder.group({
      grupoSelect: new FormControl('', Validators.required),
    });
  }

  get f() { return this.form.controls; }

  resgatarGrupo() {
    this.getGruposService.getGrupos(this.codFesta).subscribe((resp: any) => {
      this.getGruposService.setFarol(false);
      this.grupos = resp;
    });
  }

  editarGrupo(novosGrupos) {
    console.log(novosGrupos);
    this.editMembroService.editarMembroColaborador(this.codUsuario, this.grupo.codGrupo, novosGrupos).subscribe((resp: any) => {
      this.editMembroService.setFarol(false);
      this.dialog.closeAll();
      this.component.ngOnInit();
    });
  }

}
