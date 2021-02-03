import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { LoginService } from 'src/app/services/loginService/login.service';
import { SegurancaProblemasService } from 'src/app/services/seguranca-problemas/seguranca-problemas.service';
import { DatePipe } from '@angular/common';
import { Router } from '@angular/router';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { FileValidator } from 'ngx-material-file-input';
import { ProcessingDialogComponent } from '../processing-dialog/processing-dialog.component';

@Component({
  selector: 'app-relatar-problema-dialog',
  templateUrl: './relatar-problema-dialog.component.html',
  styleUrls: ['./relatar-problema-dialog.component.scss']
})
export class RelatarProblemaDialogComponent implements OnInit {
  public component: any;
  public codFesta: any;
  public area: any;
  public problemaTO: any = {};
  public date: any;
  public form: FormGroup;
  public urlNoImage = 'https://xtremebike.com.br/website/images/product/1.jpg';
  public imagem = this.urlNoImage;
  listaProblemas: any;

  constructor(
    public router: Router,
    public dialogRef: MatDialogRef<RelatarProblemaDialogComponent>,
    @Inject(MAT_DIALOG_DATA) data,
    public dialog: MatDialog,
    public segurancaProblemaService: SegurancaProblemasService,
    private loginService: LoginService,
    private datePipe: DatePipe,
    public formBuilder: FormBuilder,
    ) {
    this.codFesta = data.codFesta;
    this.area = data.area;
    this.component = data.component;
  }

  ngOnInit() {
    console.log(this.area);
    this.buildForm();
    this.segurancaProblemaService.listarProblemas().subscribe(
      (resp: any) => {
        // console.log(resp);
        this.listaProblemas = resp;
        console.log(this.listaProblemas);
        this.segurancaProblemaService.setFarol(false);
      }
    );
  }
  // {{'CATEGORIA.' + categoria.nomeCategoria | translate}}

  openDialogProcessing() {
    this.dialog.open(ProcessingDialogComponent, {
        width: '20rem',
        disableClose: true,
        data: {
            tipo: 'RELATPROBLM'
        }
    });
  }

  relatarProblm(codProblema, descProblema) {
    this.date = new Date();
    this.openDialogProcessing();
    const problemaTO = {
      codProblema,
      descProblema,
      horarioInicio: this.datePipe.transform(this.date, 'yyyy-MM-ddThh:mm:ss'),
      observacaoSolucao: '',
      codFesta: this.codFesta,
      codAreaSeguranca: this.area.codArea,
      codUsuarioEmissor: this.loginService.getusuarioInfo().codUsuario,
    };
    const imagem = this.form.get('imagemProblema').value;

    this.segurancaProblemaService.adicionarProblema(problemaTO, imagem).subscribe((resp: any) => {
      this.dialogRef.close();
      this.component.ngOnInit();
      this.segurancaProblemaService.setFarol(false);
      this.dialog.closeAll();
    });
  }

  get f() { return this.form.controls; }

  buildForm() {
    this.form = this.formBuilder.group({
      codProblema: new FormControl(''),
      descProblema: new FormControl(''),
      imagemProblema: new FormControl(null, FileValidator.maxContentSize(2097152))
    });
  }

  excluirInputImagem() {
    this.form.get('imagemProblema').setValue(null);
    this.imagem = this.urlNoImage;
    const fileInput = document.
    querySelector('ngx-mat-file-input[formcontrolname="imagemProblema"] input[type="file"]')  as HTMLInputElement;
    fileInput.value = null;
  }

  alterarPreview() {
    if (this.form.get('imagemProblema').value) {
      const arquivo = this.form.get('imagemProblema').value._files[0];
      const reader = new FileReader();
      reader.readAsDataURL(arquivo);

      reader.onload = (event: any) => { // called once readAsDataURL is completed
        this.imagem = event.target.result;
      };
    } else {
      this.imagem = this.urlNoImage;
    }
  }

}
