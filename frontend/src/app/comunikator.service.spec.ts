import { TestBed } from '@angular/core/testing';

import { ComunikatorService } from './comunikator.service';

describe('ComunikatorService', () => {
  let service: ComunikatorService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ComunikatorService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
