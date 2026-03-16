import { TestBed } from '@angular/core/testing';

import { Promotionalservice } from './promotionalservice';

describe('Promotionalservice', () => {
  let service: Promotionalservice;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Promotionalservice);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
