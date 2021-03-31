import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BurrowdvdComponent } from './burrowdvd.component';

describe('BurrowdvdComponent', () => {
  let component: BurrowdvdComponent;
  let fixture: ComponentFixture<BurrowdvdComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BurrowdvdComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BurrowdvdComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
