import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CriarFestaComponent } from './criar-festa.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClient } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';

import { CustomMaterialModule } from '../material/material.module';
import { NgxMaterialTimepickerModule } from 'ngx-material-timepicker';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { RouterModule } from '@angular/router';
import { of } from 'rxjs';
import { GetCategoriasService } from 'src/app/services/get-categorias/get-categorias.service';
import { CadastrarFestaService } from 'src/app/services/cadastro-festa/cadastrar-festa.service';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}


describe('CriarFestaComponent', () => {
  let component: CriarFestaComponent;
  let fixture: ComponentFixture<CriarFestaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        CriarFestaComponent
      ],
      imports: [
        CustomMaterialModule,
        NgxMaterialTimepickerModule,
        FormsModule,
        ReactiveFormsModule,
        HttpClientTestingModule,
        BrowserAnimationsModule,
        TranslateModule.forRoot({
          loader: {
            provide: TranslateLoader,
            useFactory: HttpLoaderFactory,
            deps: [HttpClient]
          }
        }),
        RouterModule.forRoot([])
      ],
      providers: [
        {provide: GetCategoriasService, useValue: {
          getCategorias: () => of([ {categoria: 'Teste1'}, {categoria: 'Teste2'} ]),
          setFarol: () => false,
        }},
        {provide: CadastrarFestaService, useValue: {
          cadastrarFesta: () => of({nomeFesta: 'TesteMix', codFesta: '87'}),
          setFarol: () => false,
        }}
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CriarFestaComponent);
    component = fixture.componentInstance;
    component.loginService.usuarioInfo = {codUsuario: '1', nomeUser: 'Teste', nomesexo: null, dtNasc: null};
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should callService when adicionarFesta', () => {
    spyOn(component, 'callService');
    component.adicionarFesta('teste', 'teste', 'teste', 'teste', 'teste', 'teste', 'teste', 'teste', 'teste', 'teste', 'teste');
    expect(component.callService).toHaveBeenCalled();
  });

  it('should get f to get form controls', () => {
    expect(component.f).toBe(component.form.controls);
  });

  it('should buildForm', () => {
    expect(component.form.get('nomeFesta')).toBeTruthy();
    expect(component.form.get('descFesta')).toBeTruthy();
    expect(component.form.get('endereco')).toBeTruthy();
    expect(component.form.get('inicioData')).toBeTruthy();
    expect(component.form.get('fimData')).toBeTruthy();
    expect(component.form.get('inicioHora')).toBeTruthy();
    expect(component.form.get('fimHora')).toBeTruthy();
    expect(component.form.get('categoriaPrincipal')).toBeTruthy();
    expect(component.form.get('categoriaSecundaria')).toBeTruthy();
    expect(component.form.get('organizador')).toBeTruthy();
    expect(component.form.get('descOrganizador')).toBeTruthy();
  });

  it('should resgatarCategorias', () => {
    spyOn(component.getCategoria, 'getCategorias')
    .and
    .callThrough();

    spyOn(component.getCategoria, 'setFarol')
    .and
    .callThrough();

    component.resgatarCategorias();

    expect(component.getCategoria.getCategorias).toHaveBeenCalled();
    expect(component.getCategoria.setFarol).toHaveBeenCalledWith(false);
    expect(component.categorias).toEqual([{categoria: 'Teste1'}, {categoria: 'Teste2'}]);
  });

  it('should callService', () => {
    spyOn(component.festaService, 'cadastrarFesta')
    .and
    .callThrough();

    spyOn(component.festaService, 'setFarol')
    .and
    .callThrough();

    spyOn(component.router, 'navigate')
    .and
    .callThrough();

    component.callService({});

    expect(component.festaService.cadastrarFesta).toHaveBeenCalled();
    expect(component.festaService.setFarol).toHaveBeenCalledWith(false);
    expect(component.categorias).toEqual([{categoria: 'Teste1'}, {categoria: 'Teste2'}]);
    expect(component.router.navigate).toHaveBeenCalledWith(['festas/testemix&87/painel/']);
  });

});
