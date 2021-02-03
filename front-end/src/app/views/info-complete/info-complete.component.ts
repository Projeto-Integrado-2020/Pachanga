import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material';
import { LoginService } from '../../services/loginService/login.service';
import { PerfilDialogComponent } from '../perfil-dialog/perfil-dialog.component';

@Component({
  selector: 'app-info-complete',
  templateUrl: './info-complete.component.html',
  styleUrls: ['./info-complete.component.scss']
})
export class InfoCompleteComponent implements OnInit {

  constructor(public loginService: LoginService, public dialog: MatDialog) { }

  public mensagem: boolean;

  ngOnInit() {
    this.mensagem = true;
  }

  fecharMensagem() {
    this.mensagem = false;
  }

  openDialogPerfil(): void {
    this.dialog.open(PerfilDialogComponent, {
      width: '25rem',
    });
  }

}
