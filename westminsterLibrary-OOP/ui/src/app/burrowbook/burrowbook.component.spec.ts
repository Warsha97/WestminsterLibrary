import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BurrowbookComponent } from './burrowbook.component';

describe('BurrowbookComponent', () => {
  let component: BurrowbookComponent;
  let fixture: ComponentFixture<BurrowbookComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BurrowbookComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BurrowbookComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
