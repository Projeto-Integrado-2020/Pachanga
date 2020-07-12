import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { Component, OnInit } from '@angular/core';
import { MatChipInputEvent } from '@angular/material/chips';

export interface Email {
  address: string;
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

  readonly separatorKeysCodes: number[] = [ENTER, COMMA];
  maillist: Email[] = [];
  // niveis de acesso:

  constructor() { }

  ngOnInit() {
  }

  add(event: MatChipInputEvent): void {
    const input = event.input;
    const value = event.value;

    // adicionar email
    if ((value || '').trim()) {
      const mails = value.split(',');
      for (const v of mails) {
        this.maillist.push({address: v.trim()});
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
  }
}
