import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProductService } from '../../product/service/product.service';
import { OrderService } from '../service/order.service';
import { IOrder } from '../order.model';
import { IProduct } from '../../product/product.model';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import dayjs from 'dayjs/esm';
import { Observable } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { finalize } from 'rxjs/operators';
import { DATE_TIME_FORMAT } from '../../../config/input.constants';

@Component({
  selector: 'jhi-saveorder',
  templateUrl: './saveorder.component.html',
  styleUrls: ['./saveorder.component.scss'],
})
export class SaveorderComponent implements OnInit {
  product: IProduct | null = null;
  orderForm!: FormGroup;
  isSaving = false;

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected productService: ProductService,
    protected orderService: OrderService,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    let currentTime: dayjs.Dayjs;
    currentTime = dayjs();
    this.activatedRoute.data.subscribe(({ product }) => {
      this.product = product;
      if (product) {
        //console.log("product retrieving ",JSON.stringify(product))
        this.orderForm = this.fb.group({
          quantityOrder: ['', Validators.required],
          date: [currentTime, Validators.required],
        });
      }

      //this.loadRelationshipsOptions();
    });
  }

  save() {
    //console.log('save..');
    console.log('the order form ' + JSON.stringify(this.orderForm.value));
    const data = {
      quantity: this.orderForm.value.quantityOrder,
      idProduct: this.product?.id,
      date: dayjs(this.orderForm.value.date, DATE_TIME_FORMAT),
    };
    this.subscribeToSaveResponse(this.orderService.createOrderByUser(data));
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrder>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }
  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  previousState() {
    window.history.back();
  }
}
