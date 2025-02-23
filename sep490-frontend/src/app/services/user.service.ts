import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {AppRoutingConstants} from '../app-routing.constant';
import {UserLanguage} from '../modules/shared/enums/user-language.enum';
import {UserConfigs} from '../modules/shared/models/user-configs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private readonly httpClient: HttpClient) {}

  get userConfigs(): Observable<UserConfigs> {
    return this.httpClient.get<UserConfigs>(
      `${AppRoutingConstants.IDP_API_URL}/user/configs`
    );
  }

  changeLanguage(language: UserLanguage): Observable<void> {
    return this.httpClient.put<void>(
      `${AppRoutingConstants.IDP_API_URL}/user/language/${language}`,
      {}
    );
  }

  changeTheme(theme: string): Observable<void> {
    return this.httpClient.put<void>(
      `${AppRoutingConstants.IDP_API_URL}/user/theme/${theme}`,
      {}
    );
  }
}
