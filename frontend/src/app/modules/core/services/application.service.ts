import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {OidcSecurityService} from 'angular-auth-oidc-client';
import {SubscriptionAwareComponent} from '../subscription-aware.component';

@Injectable({
  providedIn: 'root'
})
export class ApplicationService extends SubscriptionAwareComponent {

  constructor(
    private readonly oidcSecurityService: OidcSecurityService,
    private readonly route: Router
  ) {
    super();
  }

  login(): void {
    this.oidcSecurityService.authorize();
  }

  logout(): void {
    this.oidcSecurityService.logoff().subscribe((): void => this.postLogout());
  }

  private postLogout(): void {
    sessionStorage.clear();
  }
}
