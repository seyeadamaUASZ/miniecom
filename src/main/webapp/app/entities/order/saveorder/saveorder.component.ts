import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProductService } from '../../product/service/product.service';
import { OrderService } from '../service/order.service';
import { IOrder } from '../order.model';
import { IProduct } from '../../product/product.model';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import dayjs from 'dayjs/esm';

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
    console.log('save..');
  }

  previousState() {
    console.log('previous...');
  }
}
