import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BeerEditComponent } from './beer-edit.component';

describe('BeerEditComponent', () => {
  let component: BeerEditComponent;
  let fixture: ComponentFixture<BeerEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BeerEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BeerEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
