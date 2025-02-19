import {Injectable} from '@angular/core';
import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest
} from '@angular/common/http';
import {TranslateService} from '@ngx-translate/core';
import {Observable} from 'rxjs';

@Injectable({providedIn: 'root'})
export class LanguageInterceptor implements HttpInterceptor {
  constructor(private readonly languageService: TranslateService) {}

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    const language = this.languageService.currentLang ?? 'vi';
    const modifiedReq = req.clone({
      setHeaders: {'Accept-Language': language}
    });
    return next.handle(modifiedReq);
  }
}
