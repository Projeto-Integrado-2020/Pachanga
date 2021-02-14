import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { Component, OnInit, Inject } from '@angular/core';
import { MatChipInputEvent } from '@angular/material/chips';
import { ConviteMembroService } from 'src/app/services/convite-membro/convite-membro.service';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material';
import { SuccessDialogComponent } from '../success-dialog/success-dialog.component';
import { ProcessingDialogComponent } from '../processing-dialog/processing-dialog.component';

export interface Email {
  address: string;
  valid: boolean;
}

@Component({
  selector: 'app-invite-dialog',
  templateUrl: './invite-dialog.component.html',
  styleUrls: ['./invite-dialog.component.scss']
})
export class InviteDialogComponent implements OnInit {
  visible = true;
  selectable = true;
  removable = true;
  addOnBlur = true;
  idFesta: string;
  grupo: string;
  component: any;

  readonly separatorKeysCodes: number[] = [ENTER, COMMA];
  maillist: Email[] = [];
  errors = 0;
  // niveis de acesso:

  constructor(public conviteService: ConviteMembroService, @Inject(MAT_DIALOG_DATA) data,
              public modal: MatDialog) {
    this.idFesta = data.idFesta;
    this.grupo = data.grupo;
    this.component = data.component;
  }

  ngOnInit() {
  }

  add(event: MatChipInputEvent): void {
    const input = event.input;
    const value = event.value;

    // adicionar email
    if ((value || '').trim()) {
      const mails = value.split(/[,;]+/);
      for (const v of mails) {
        const validation = this.validate(v);
        this.maillist.push({address: v.trim(), valid: validation});
        if (!validation) {
          this.errors += 1;
          console.log(this.errors);
        }
      }
    }

    // resetar o valir do input
    if (input) {
      input.value = '';
    }
  }

  remove(email: Email): void {
    const index = this.maillist.indexOf(email);

    if (index >= 0) {
      this.maillist.splice(index, 1);
    }
    if (!email.valid) {
      this.errors -= 1;
    }
  }

  validate(email) {
    /* tslint:disable */
    const expression = /^(([^<>()\[\]\\.,;:\s@+"]+(\.[^<>()\[\]\\.,;:\s@+"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    /* tslint:enable */
    return expression.test(String(email).toLowerCase());
  }

  adicionarMembros() {
    this.openDialogProcessing();
    const emails = [];
    for (const email of this.maillist) {
      emails.push(email.address);
    }
    this.conviteService.convidarMembro(this.idFesta, this.grupo, emails).subscribe((resp: any) => {
      this.conviteService.setFarol(false);
      this.modal.closeAll();
      this.component.ngOnInit();
      this.openDialogSuccess('MEMBROAD');
    });
  }

  openDialogSuccess(message: string) {
    this.modal.open(SuccessDialogComponent, {
      width: '20rem',
      data: {message}
    });
  }

  openDialogProcessing() {
    this.modal.open(ProcessingDialogComponent, {
        width: '20rem',
        disableClose: true,
        data: {
            tipo: 'INVITE'
        }
    });
}
}
