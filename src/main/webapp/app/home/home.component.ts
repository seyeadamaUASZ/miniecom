import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { IProduct } from '../entities/product/product.model';

import { ProductService } from '../entities/product/service/product.service';

import { DataUtils } from '../core/util/data-util.service';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit, OnDestroy {
  account: Account | null = null;
  products?: IProduct[];

  private readonly destroy$;
  constructor(
    private accountService: AccountService,
    protected productService: ProductService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    protected dataUtils: DataUtils
  ) {
    this.destroy$ = new Subject<void>();
  }

  ngOnInit(): void {
    this.load();
    this.accountService
      .getAuthenticationState()
      .pipe(takeUntil(this.destroy$))
      .subscribe(account => (this.account = account));
  }

  login(): void {
    this.router.navigate(['/login']);
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  load(): void {
    /* this.loadFromBackendWithRouteInformations().subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onResponseSuccess(res);
      },
    });*/
    this.allProduct();
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    return this.dataUtils.openFile(base64String, contentType);
  }

  allProduct(): void {
    this.productService.queryAll().subscribe({
      next: (res: any) => {
        this.products = res;
        //console.log("all products ",JSON.stringify(this.products))
      },
      error(error: Error) {
        console.log('error ', error);
      },
    });
  }
}
