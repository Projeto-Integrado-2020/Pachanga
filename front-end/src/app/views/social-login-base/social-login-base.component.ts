import { Component, OnInit } from '@angular/core';
import { AuthService } from 'angular4-social-login';
import { SocialUser } from 'angular4-social-login';
import { LoginService } from 'src/app/services/loginService/login.service';
import { FormGroup, FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material';
import { Router } from '@angular/router';

@Component({
  selector: 'app-social-login-base',
  templateUrl: './social-login-base.component.html',
  styleUrls: ['./social-login-base.component.scss']
})
export class SocialLoginBaseComponent implements OnInit {

  public user: SocialUser;

  public form: FormGroup;

  constructor(public authService: AuthService, public loginService: LoginService,
              public formBuilder: FormBuilder, public modal: MatDialog, public router: Router) { }

  get f() { return this.form.controls; }

  signOut(): void {
    this.router.navigate(['/']);
    this.loginService.finalizarSessao();
    this.loginService.setUsuarioAutenticado(false);
  }

  ngOnInit() {

  }

  autenticar(user) {
    this.loginService.logar(user).subscribe(resp => {
      this.loginService.setUsuarioAutenticado(true);
      this.loginService.setusuarioInfo(resp);
      this.modal.closeAll();
    });
  }

  cadastrar_se(user) {
    this.loginService.cadastrar(user).subscribe(resp => {
      this.autenticar(user);
    });
  }

}
