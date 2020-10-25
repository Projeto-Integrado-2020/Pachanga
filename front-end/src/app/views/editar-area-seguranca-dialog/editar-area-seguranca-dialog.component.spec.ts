import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditarAreaSegurancaDialogComponent } from './editar-area-seguranca-dialog.component';
import { CustomMaterialModule } from '../material/material.module';
import { NgxMaterialTimepickerModule } from 'ngx-material-timepicker';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { HttpClient } from '@angular/common/http';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { LoginService } from 'src/app/services/loginService/login.service';
import { of } from 'rxjs';
import { EditarEstoqueService } from 'src/app/services/editar-estoque/editar-estoque.service';
import { EditarAreaSegurancaService } from 'src/app/services/editar-area-seguranca/editar-area-seguranca.service';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('EditarAreaSegurancaDialogComponent', () => {
  let component: EditarAreaSegurancaDialogComponent;
  let fixture: ComponentFixture<EditarAreaSegurancaDialogComponent>;
  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open', 'closeAll']);
    TestBed.configureTestingModule({
      declarations: [ EditarAreaSegurancaDialogComponent ],
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
        })],
        providers: [
          { provide: MAT_DIALOG_DATA, useValue: { codFesta: '1', area: { codArea: '1', codFesta: '1', nomeArea: 'Nome'} } },
          { provide: MatDialog, useValue: dialogSpy },
          {provide: EditarAreaSegurancaService, useValue: {
            atualizarAreaSeguranca: () => of({}),
            setFarol: () => false,
          }}
      ]
      })
      .compileComponents();
    }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditarAreaSegurancaDialogComponent);
    component = fixture.componentInstance;
    const service: LoginService = TestBed.get(LoginService);
    service.usuarioInfo = {codUsuario: '1'};
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should editAreaSeguranca', () => {
    spyOn(component.editarAreaSeguranca, 'atualizarAreaSeguranca')
    .and
    .callThrough();

    spyOn(component.editarAreaSeguranca, 'setFarol')
    .and
    .callThrough();

    component.component = {
      ngOnInit: () => true
    };

    spyOn(component.component, 'ngOnInit')
    .and
    .callThrough();

    const areaTO = {
      nomeArea: 'testeNome',
      codFesta: '1',
      codArea: 'testeCodA'
    };

    component.area = {
      nomeArea: 'testeNome',
      codFesta: '1',
      codArea: 'testeCodA'
    };

    component.editAreaSeguranca('testeNome');
    expect(component.editarAreaSeguranca.atualizarAreaSeguranca).toHaveBeenCalledWith(areaTO);
    expect(component.editarAreaSeguranca.setFarol).toHaveBeenCalledWith(false);
    expect(component.component.ngOnInit).toHaveBeenCalled();
  });
});
