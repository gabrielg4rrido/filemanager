import { TestBed } from '@angular/core/testing';

import { DiretorioService } from './diretorio.service';

describe('DiretorioService', () => {
  let service: DiretorioService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DiretorioService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
