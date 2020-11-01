import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MatDialog, MAT_DIALOG_DATA } from '@angular/material';
import { CustomMaterialModule } from '../material/material.module';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { HttpClient } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { of } from 'rxjs';
import { DeletarLoteDialogComponent } from './deletar-lote-dialog.component';
import { DeletarLoteService } from 'src/app/services/deletar-lote/deletar-lote.service';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('DeletarLoteDialogComponent', () => {
  let component: DeletarLoteDialogComponent;
  let fixture: ComponentFixture<DeletarLoteDialogComponent>;
  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open', 'closeAll']);

    TestBed.configureTestingModule({
      declarations: [
        DeletarLoteDialogComponent,
      ],
      imports: [
        CustomMaterialModule,
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
        { provide: MAT_DIALOG_DATA, useValue: {lote: {codLote: '1', codFesta: '1', nomeLote: 'teste' }} },
        { provide: MatDialog, useValue: dialogSpy },
        {provide: DeletarLoteService, useValue: {
          deleteLote: () => of({}),
          setFarol: () => false,
        }}
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DeletarLoteDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should deleteLote', () => {
    spyOn(component.deleteLoteService, 'deleteLote')
    .and
    .callThrough();

    spyOn(component.deleteLoteService, 'setFarol')
    .and
    .callThrough();

    component.component = {
      ngOnInit: () => true
    };
    spyOn(component.component, 'ngOnInit')
    .and
    .callThrough();

    component.codLote = 'teste';
    component.deletarLote();
    expect(component.deleteLoteService.deleteLote).toHaveBeenCalledWith('teste');
    expect(component.deleteLoteService.setFarol).toHaveBeenCalledWith(false);
    expect(dialogSpy.closeAll).toHaveBeenCalled();
    expect(component.component.ngOnInit).toHaveBeenCalled();
  });
});
