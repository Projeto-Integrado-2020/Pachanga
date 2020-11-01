import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CriarLoteComponent } from './criar-lote.component';
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
import { CriarLoteService } from 'src/app/services/criar-lote/criar-lote.service';
import { LoginService } from 'src/app/services/loginService/login.service';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('CriarLoteComponent', () => {
  let component: CriarLoteComponent;
  let fixture: ComponentFixture<CriarLoteComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        CriarLoteComponent
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
        {provide: CriarLoteService, useValue: {
          novoLote: () => of({nomeLote: 'teste', codLote: '1'}),
          setFarol: () => false,
        }}
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CriarLoteComponent);
    component = fixture.componentInstance;
    const service: LoginService = TestBed.get(LoginService);
    service.usuarioInfo = {codUsuario: '1'};
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should callService when adicionarLote', () => {
    spyOn(component, 'addLote');
    component.addLote('teste', 'teste', 'teste', 'teste', 'teste', 'teste', 'teste', 'teste', 'teste');
    expect(component.addLote).toHaveBeenCalled();
  });

  it('should get f to get form controls', () => {
    expect(component.f).toBe(component.form.controls);
  });

  it('should buildForm', () => {
    expect(component.form.get('numeroLote')).toBeTruthy();
    expect(component.form.get('quantidade')).toBeTruthy();
    expect(component.form.get('preco')).toBeTruthy();
    expect(component.form.get('nomeLote')).toBeTruthy();
    expect(component.form.get('descLote')).toBeTruthy();
    expect(component.form.get('inicioData')).toBeTruthy();
    expect(component.form.get('dthrInicio')).toBeTruthy();
    expect(component.form.get('fimData')).toBeTruthy();
    expect(component.form.get('dthrFim')).toBeTruthy();
  });

  it('should addLote', () => {
    spyOn(component.loteAdd, 'novoLote')
    .and
    .callThrough();

    spyOn(component.loteAdd, 'setFarol')
    .and
    .callThrough();

    spyOn(component.router, 'navigate')
    .and
    .callThrough();

    component.festa = {
      codFesta: 'teste',
      nomeFesta: 'teste'
    };

    component.addLote('teste', 'teste', 'teste', 'teste', 'teste', 'teste', 'teste', 'teste', 'teste');

    expect(component.loteAdd.novoLote).toHaveBeenCalled();
    expect(component.loteAdd.setFarol).toHaveBeenCalledWith(false);
    expect(component.router.navigate).toHaveBeenCalledWith(['festas/teste&teste/ingressos/']);
  });

});
