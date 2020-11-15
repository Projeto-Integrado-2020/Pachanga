import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { LoginService } from 'src/app/services/loginService/login.service';
import { SegurancaProblemasService } from 'src/app/services/seguranca-problemas/seguranca-problemas.service';
import { DatePipe } from '@angular/common';
import { Router } from '@angular/router';
import { PainelSegurancaComponent } from '../painel-seguranca/painel-seguranca.component';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-relatar-problema-dialog',
  templateUrl: './relatar-problema-dialog.component.html',
  styleUrls: ['./relatar-problema-dialog.component.scss']
})
export class RelatarProblemaDialogComponent implements OnInit {
  public component: PainelSegurancaComponent;
  public codFesta: any;
  public area: any;
  public problemaTO: any = {};
  public date: any;

  listaProblemas: any;

  constructor(
    public router: Router,
    public dialogRef: MatDialogRef<RelatarProblemaDialogComponent>,
    @Inject(MAT_DIALOG_DATA) data,
    public dialog: MatDialog,
    private segurancaProblemaService: SegurancaProblemasService,
    private loginService: LoginService,
    private datePipe: DatePipe
    ) {
    this.codFesta = data.codFesta;
    this.area = data.area;
    this.component = data.component;
  }

  ngOnInit() {
    console.log(this.area);
    this.segurancaProblemaService.listarProblemas().subscribe(
      (resp: any) => {
        // console.log(resp);
        this.listaProblemas = resp;
        console.log(this.listaProblemas);
        this.segurancaProblemaService.setFarol(false);
      }
    );
    // console.log(this.listaProblemas);
  }
  // {{'CATEGORIA.' + categoria.nomeCategoria | translate}}

  relatarProblema(model: NgForm) {


    this.date = new Date();

    Object.assign(
      this.problemaTO, // codFesta, descProblema
      {
        codProblema: model.value.codProblema,
        descProblema: model.value.descProblema,
        codFesta: this.codFesta,
        codAreaSeguranca: this.area.codArea,
        codUsuarioEmissor: this.loginService.getusuarioInfo().codUsuario,
        horarioInicio: this.datePipe.transform(this.date, 'yyyy-MM-ddThh:mm:ss'),
        observacaoSolucao: ''
      }
    );

    console.log(this.problemaTO);

    this.segurancaProblemaService.adicionarProblema(this.problemaTO).subscribe((resp: any) => {
      this.dialogRef.close();
      this.component.ngOnInit();
      this.segurancaProblemaService.setFarol(false);
    });
  }

}
