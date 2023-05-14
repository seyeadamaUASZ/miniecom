import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SaveorderComponent } from './saveorder.component';

describe('SaveorderComponent', () => {
  let component: SaveorderComponent;
  let fixture: ComponentFixture<SaveorderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SaveorderComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(SaveorderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
