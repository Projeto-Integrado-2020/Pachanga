import { Component, Inject, Injectable, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-problema-dialog',
  templateUrl: './problema-dialog.component.html',
  styleUrls: ['./problema-dialog.component.scss']
})

@Injectable({
  providedIn: 'root',
})

export class ProblemaDialogComponent implements OnInit {

   public data: [];

  constructor(
    @Inject(MAT_DIALOG_DATA) data) {
      this.data = data;
    }

  ngOnInit() {
  }

}
