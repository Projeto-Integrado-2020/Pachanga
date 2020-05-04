import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NavbarComponent } from './navbar.component';

import {
  MatToolbarModule, 
  MatIconModule, 
  MatButtonModule,
  MatGridListModule
} from '@angular/material';

describe('NavbarComponent', () => {
  let component: NavbarComponent;
  let fixture: ComponentFixture<NavbarComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NavbarComponent ],
      imports: [
        MatToolbarModule,
        MatButtonModule,
        MatIconModule,
        MatGridListModule
      ],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NavbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
