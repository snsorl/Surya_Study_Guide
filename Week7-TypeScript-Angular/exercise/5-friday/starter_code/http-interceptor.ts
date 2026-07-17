import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // TODO: Retrieve token from LocalStorage
    const token = null;

    if (token) {
      // TODO: Clone the incoming request to set the 'Authorization: Bearer <token>' header
      const clonedRequest = req;
      return next.handle(clonedRequest);
    }

    // Pass through request unmodified if no token exists
    return next.handle(req);
  }
}
