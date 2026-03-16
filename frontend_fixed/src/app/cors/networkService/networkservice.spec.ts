import { TestBed } from '@angular/core/testing';

import { Networkservice } from './networkservice';

describe('Networkservice', () => {
  let service: Networkservice;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Networkservice);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
