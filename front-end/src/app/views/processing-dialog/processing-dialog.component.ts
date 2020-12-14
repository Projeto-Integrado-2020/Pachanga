import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-processing-dialog',
  templateUrl: './processing-dialog.component.html',
  styleUrls: ['./processing-dialog.component.scss']
})
export class ProcessingDialogComponent implements OnInit {

  public tipo: any;

  constructor(
    @Inject(MAT_DIALOG_DATA) data) {
    this.tipo = data.tipo;
  }

  ngOnInit() {
  }

}
