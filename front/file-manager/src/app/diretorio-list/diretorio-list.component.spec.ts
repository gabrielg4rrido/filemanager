import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DiretorioListComponent } from './diretorio-list.component';

describe('DiretorioListComponent', () => {
  let component: DiretorioListComponent;
  let fixture: ComponentFixture<DiretorioListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DiretorioListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DiretorioListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
