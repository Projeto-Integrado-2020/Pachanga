import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditGrupoMembroComponent } from './edit-grupo-membro.component';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CustomMaterialModule } from '../material/material.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule, ReactiveFormsModule, FormControl, Validators } from '@angular/forms';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClient } from '@angular/common/http';
import { LoginService } from 'src/app/services/loginService/login.service';
import { of } from 'rxjs';
import { GetGruposService } from 'src/app/services/get-grupos/get-grupos.service';
import { EditarMembroGrupoService } from 'src/app/services/editar-membro-grupo/editar-membro-grupo.service';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('EditGrupoMembroComponent', () => {
  let component: EditGrupoMembroComponent;
  let fixture: ComponentFixture<EditGrupoMembroComponent>;
  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open', 'closeAll']);
    TestBed.configureTestingModule({
      declarations: [ EditGrupoMembroComponent ],
      imports: [
      HttpClientTestingModule,
      CustomMaterialModule,
      BrowserAnimationsModule,
      FormsModule,
      ReactiveFormsModule,
      TranslateModule.forRoot({
        loader: {
          provide: TranslateLoader,
          useFactory: HttpLoaderFactory,
          deps: [HttpClient]
        }
      })],
      providers: [
        { provide: MAT_DIALOG_DATA, useValue: { codUsuario: '1', codFesta: '1', grupo: {codGrupo: '1', nomeGrupo: 'Salve'}} },
        { provide: MatDialog, useValue: dialogSpy },
        {provide: GetGruposService, useValue: {
          getGrupos: () => of([{codGrupo: 'teste'}]),
          setFarol: () => false,
        }},
        {provide: EditarMembroGrupoService, useValue: {
          editarMembroColaborador: () => of({}),
          setFarol: () => false,
        }}
    ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditGrupoMembroComponent);
    component = fixture.componentInstance;
    const service: LoginService = TestBed.get(LoginService);
    service.usuarioInfo = {codUsuario: '1'};
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should gerarForm', () => {
    component.gerarForm();
    expect(component.form).toBeTruthy();
    expect(component.form.get('grupoSelect')).toBeTruthy();
  });

  it('should resgatarGrupo', () => {
    spyOn(component.getGruposService, 'getGrupos')
    .and
    .callThrough();

    spyOn(component.getGruposService, 'setFarol')
    .and
    .callThrough();

    component.codFesta = 'testeFesta';
    component.resgatarGrupo();
    expect(component.getGruposService.getGrupos).toHaveBeenCalledWith('testeFesta');
    expect(component.getGruposService.setFarol).toHaveBeenCalledWith(false);
    expect(component.grupos).toEqual([{codGrupo: 'teste'}]);
  });

  it('should editarGrupo', () => {
    spyOn(component.editMembroService, 'editarMembroColaborador')
    .and
    .callThrough();

    spyOn(component.editMembroService, 'setFarol')
    .and
    .callThrough();

    component.component = {
      ngOnInit: () => true
    };

    spyOn(component.component, 'ngOnInit')
    .and
    .callThrough();

    component.codFesta = 'testeFesta';
    component.grupo = {codGrupo: 'testeGrupo'};
    component.codUsuario = 'testeFesta';
    component.editarGrupo(['teste']);
    expect(component.editMembroService.editarMembroColaborador).toHaveBeenCalledWith('testeFesta', 'testeGrupo', ['teste']);
    expect(component.editMembroService.setFarol).toHaveBeenCalledWith(false);
    expect(dialogSpy.closeAll).toHaveBeenCalled();
    expect(component.component.ngOnInit).toHaveBeenCalled();
  });

});
