import { TestBed } from '@angular/core/testing';

import { LogService } from './log.service';

import { HttpClientModule } from '@angular/common/http';

describe('LogService', () => {
  let service: LogService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientModule
      ],
      providers: [],
    });

    service = TestBed.get(LogService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
