/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { CUSTOM_ELEMENTS_SCHEMA, DebugElement } from '@angular/core';

import { RelatoriosSegurancaComponent } from './relatorios-seguranca.component';
import { CustomMaterialModule } from '../material/material.module';
import { ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { HttpLoaderFactory } from 'src/app/app.module';
import { HttpClient } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { LoginService } from 'src/app/services/loginService/login.service';
import { RelatorioAreaSegService } from 'src/app/services/relatorios/relatorio-area-seg.service';
import { of } from 'rxjs';
import { MatDialog } from '@angular/material';

describe('RelatoriosSegurancaComponent', () => {
  let component: RelatoriosSegurancaComponent;
  let fixture: ComponentFixture<RelatoriosSegurancaComponent>;
  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open', 'closeAll']);

    TestBed.configureTestingModule({
      imports: [
        CustomMaterialModule,
        ReactiveFormsModule,
        BrowserAnimationsModule,
        HttpClientTestingModule,
        TranslateModule.forRoot({
          loader: {
            provide: TranslateLoader,
            useFactory: HttpLoaderFactory,
            deps: [HttpClient]
          }
        }),
        RouterModule.forRoot([])
      ],
      declarations: [ RelatoriosSegurancaComponent ],
      schemas: [ CUSTOM_ELEMENTS_SCHEMA ],
      providers: [
        {provide: RelatorioAreaSegService, useValue: {
          problemasArea: () => of({problemasArea: {}, chamadasEmitidasFuncionario: [], solucionadorAlertasSeguranca: []}),
          chamadasUsuario: () => of({problemasArea: {}, chamadasEmitidasFuncionario: [], solucionadorAlertasSeguranca: []}),
          usuarioSolucionador: () => of({problemasArea: {}, chamadasEmitidasFuncionario: [], solucionadorAlertasSeguranca: []})
        }},
        { provide: MatDialog, useValue: dialogSpy },
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RelatoriosSegurancaComponent);
    component = fixture.componentInstance;
    const service: LoginService = TestBed.get(LoginService);
    service.usuarioInfo = {codUsuario: '1'};
    const token = {
      timeToken: '2020-09-21T01:14:04.028+0000',
      token: 'teste'
    };
    localStorage.setItem('token', JSON.stringify(token));
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should get problemasArea', () => {
    spyOn(component.relAreaSegService, 'problemasArea')
    .and
    .callThrough();

    component.problemasArea('teste');
    expect(component.relAreaSegService.problemasArea).toHaveBeenCalled();
  });

  it('should get chamadasUsuario', () => {
    spyOn(component.relAreaSegService, 'chamadasUsuario')
    .and
    .callThrough();

    component.chamadasUsuario('teste');
    expect(component.relAreaSegService.chamadasUsuario).toHaveBeenCalled();
  });

  it('should get usuariosPorEmissao', () => {
    spyOn(component.relAreaSegService, 'usuarioSolucionador')
    .and
    .callThrough();

    component.usuariosPorEmissao('teste');
    expect(component.relAreaSegService.usuarioSolucionador).toHaveBeenCalled();
  });

  it('should open dialog through a method', () => {
    component.onSelect('teste');
    expect(dialogSpy.open).toHaveBeenCalled();
  });
});
