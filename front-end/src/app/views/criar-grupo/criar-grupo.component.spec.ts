import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClient } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CustomMaterialModule } from '../../views/material/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CriarGrupoComponent } from './criar-grupo.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule } from '@angular/router';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('CriarGrupoComponent', () => {
  let component: CriarGrupoComponent;
  let fixture: ComponentFixture<CriarGrupoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CriarGrupoComponent ],
      imports: [
        HttpClientTestingModule,
        CustomMaterialModule,
        FormsModule,
        ReactiveFormsModule,
        BrowserAnimationsModule,
        TranslateModule.forRoot({
          loader: {
            provide: TranslateLoader,
            useFactory: HttpLoaderFactory,
            deps: [HttpClient]
          }
        }),
        RouterModule.forRoot([])
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CriarGrupoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should get f to get form controls', () => {
    expect(component.f).toBe(component.form.controls);
  });

  it('should callServiceGet when resgatarFesta', () => {
    spyOn(component, 'callServiceGet');
    component.resgatarFesta();
    expect(component.callServiceGet).toHaveBeenCalled();
  });

  it('should updateListaPermissao', () => {
    const group = {nomeGrupo: new FormControl('', Validators.required)};
    component.permissoes = ['TESTE1', 'TESTE2', 'TESTE3', 'TESTE4', 'TESTE5'];

    for (const permissao of component.permissoes) {
      group[permissao] = new FormControl(false);
    }
    component.form = component.formBuilder.group(group);
    component.updateListaPermissao('TESTE1');
    expect(component.permissoesGrupo).toEqual(['TESTE1']);
    component.updateListaPermissao('TESTE2');
    expect(component.permissoesGrupo).toEqual(['TESTE1', 'TESTE2']);
    component.form.get('TESTE1').setValue(true);
    component.updateListaPermissao('TESTE1');
    expect(component.permissoesGrupo).toEqual(['TESTE2']);
  });
});
