import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material';
import { StatusFestaService } from 'src/app/services/status-festa/status-festa.service';
import { FestaPainelControleComponent } from '../festa-painel-controle/festa-painel-controle.component';

@Component({
  selector: 'app-status-dialog',
  templateUrl: './status-dialog.component.html',
  styleUrls: ['./status-dialog.component.scss']
})
export class StatusDialogComponent implements OnInit {

  public codFesta: any;
  public status: any;
  public painel: FestaPainelControleComponent;

  constructor(@Inject(MAT_DIALOG_DATA) data, public dialog: MatDialog,
              public statusService: StatusFestaService) {
    this.codFesta = data.codFesta;
    this.status = data.status;
    this.painel = data.painel;
  }

  ngOnInit() {
  }

  setStatusFesta() {
    this.statusService.mudarStatusFesta(this.codFesta, this.status).subscribe((resp: any) => {
      this.statusService.setFarol(false);
      this.painel.setFesta(resp);
      this.painel.ngOnInit();
      this.dialog.closeAll();
    });
  }

}
