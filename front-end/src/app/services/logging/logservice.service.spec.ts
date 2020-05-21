import { TestBed } from '@angular/core/testing';

import { LogService } from './log.service';

import { HttpClientModule } from '@angular/common/http';

describe('LogService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [
      HttpClientModule
    ],
    providers: [],
  }));

  it('should be created', () => {
    const service: LogService = TestBed.get(LogService);
    expect(service).toBeTruthy();
  });
});
