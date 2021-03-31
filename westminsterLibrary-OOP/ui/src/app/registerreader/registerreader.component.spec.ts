import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RegisterreaderComponent } from './registerreader.component';

describe('RegisterreaderComponent', () => {
  let component: RegisterreaderComponent;
  let fixture: ComponentFixture<RegisterreaderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RegisterreaderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RegisterreaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
