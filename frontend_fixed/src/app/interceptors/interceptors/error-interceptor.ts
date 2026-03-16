import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';

export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  const router = inject(Router);

  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {

      if (error.status === 401) {
        router.navigate(['/login']);
      }

      const message =
        typeof error.error === 'string'
          ? error.error
          : error.error?.message || 'Something went wrong';

      console.error('API Error:', message);

      return throwError(() => new Error(message));
    })
  );
};
