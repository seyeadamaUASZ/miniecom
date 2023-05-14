import { ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot } from '@angular/router';
import { IProduct } from '../../product/product.model';
import { EMPTY, Observable, of } from 'rxjs';
import { Injectable } from '@angular/core';
import { ProductService } from '../../product/service/product.service';
import { mergeMap } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class OrderProductResolveService implements Resolve<IProduct | null> {
  constructor(protected service: ProductService, protected router: Router) {}

  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<IProduct | null> | Promise<IProduct | null> | IProduct | null {
    const idProduct = route.params['id'];
    if (idProduct) {
      return this.service.find(idProduct).pipe(
        mergeMap((product: HttpResponse<IProduct>) => {
          if (product.body) {
            return of(product.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
