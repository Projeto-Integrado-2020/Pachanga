/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { InviteDialogComponent } from './invite-dialog.component';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClient } from '@angular/common/http';
import { CustomMaterialModule } from '../material/material.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MAT_DIALOG_DATA } from '@angular/material';
import { MatDialog } from '@angular/material';
import { ConviteMembroService } from 'src/app/services/convite-membro/convite-membro.service';
import { of } from 'rxjs';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('InviteDialogComponent', () => {
  let dialogSpy: MatDialog;
  let component: InviteDialogComponent;
  let fixture: ComponentFixture<InviteDialogComponent>;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open', 'closeAll']);
    TestBed.configureTestingModule({
      declarations: [ InviteDialogComponent ],
      imports: [
        CustomMaterialModule,
        BrowserAnimationsModule,
        HttpClientTestingModule,
        TranslateModule.forRoot({
          loader: {
            provide: TranslateLoader,
            useFactory: HttpLoaderFactory,
            deps: [HttpClient]
          }
        })
      ],
      providers: [
        { provide: MAT_DIALOG_DATA, useValue: {idFesta: '0', grupo: 'teste'} },
        { provide: MatDialog, useValue: dialogSpy },
        {provide: ConviteMembroService, useValue: {
          convidarMembro: () => of({}),
          setFarol: () => false,
        }}
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InviteDialogComponent);
    component = fixture.componentInstance;
    component.component = {
      ngOnInit: () => {
        return true;
      }
    };
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should add e-mail', () => {
    component.add({input: null, value: 'teste@teste.com'});
    expect(component.maillist).toEqual([{address: 'teste@teste.com', valid: true}]);
  });

  it('should remove e-mail', () => {
    component.add({input: null, value: 'teste@teste.com'});
    component.remove(component.maillist[0]);
    expect(component.maillist).toEqual([]);
  });

  it('should open a dialog through a method', () => {
    component.openDialogSuccess('teste');
    expect(dialogSpy.open).toHaveBeenCalled();
  });

  it('should validate e-mail', () => {
    let result = component.validate('teste@teste.com');
    expect(result).toBeTruthy();
    result = component.validate('testeteste');
    expect(result).toBeFalsy();
  });

  it('should adicionarMembros', () => {
    spyOn(component.conviteService, 'convidarMembro')
    .and
    .callThrough();

    spyOn(component.conviteService, 'setFarol')
    .and
    .callThrough();

    spyOn(component, 'openDialogSuccess')
    .and
    .callThrough();

    component.idFesta = 'teste';
    component.grupo = 'teste';
    component.maillist = [
      {address: 'email@email.com', valid: true},
      {address: 'teste@teste.com', valid: true}
    ];
    component.adicionarMembros();
    expect(component.conviteService.convidarMembro).toHaveBeenCalledWith('teste', 'teste', ['email@email.com', 'teste@teste.com']);
    expect(component.conviteService.setFarol).toHaveBeenCalledWith(false);
    expect(dialogSpy.closeAll).toHaveBeenCalled();
  });
});
