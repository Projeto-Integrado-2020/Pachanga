import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { Component, OnInit, Inject } from '@angular/core';
import { MatChipInputEvent } from '@angular/material/chips';
import { ConviteMembroService } from 'src/app/services/convite-membro/convite-membro.service';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material';
import { SuccessDialogComponent } from '../success-dialog/success-dialog.component';

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

  readonly separatorKeysCodes: number[] = [ENTER, COMMA];
  maillist: Email[] = [];
  errors = 0;
  // niveis de acesso:

  constructor(public conviteService: ConviteMembroService, @Inject(MAT_DIALOG_DATA) data,
              public modal: MatDialog) {
    this.idFesta = data.idFesta;
    this.grupo = data.grupo;
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
    const expression = /(?!.*\.{2})^([a-z\d!#$%&'*+\-\/=?^_`//{|}~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+(\.[a-z\d!#$%&'*+\-\/=?^_`{|}~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+)*|"((([ \t]*\r\n)?[ \t]+)?([\x01-\x08\x0b\x0c\x0e-\x1f\x7f\x21\x23-\x5b\x5d-\x7e\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|\\[\x01-\x09\x0b\x0c\x0d-\x7f\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))*(([ \t]*\r\n)?[ \t]+)?")@(([a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|[a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF][a-z\d\-._~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]*[a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])\.)+([a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|[a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF][a-z\d\-._~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]*[a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])\.?$/i;
    /* tslint:enable */
    return expression.test(String(email).toLowerCase());
  }

  adicionarMembros() {
    const emails = [];
    for (const email of this.maillist) {
      emails.push(email.address);
    }
    this.conviteService.convidarMembro(this.idFesta, this.grupo, emails).subscribe((resp: any) => {
      this.conviteService.setFarol(false);
      this.modal.closeAll();
      this.openDialogSuccess('MEMBROAD');
    });
  }

  openDialogSuccess(message: string) {
    this.modal.open(SuccessDialogComponent, {
      width: '20rem',
      data: {message}
    });
  }
}
