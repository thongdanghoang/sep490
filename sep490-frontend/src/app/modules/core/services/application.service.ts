import {Injectable} from '@angular/core';
import {AuthenticatedResult, OidcSecurityService} from 'angular-auth-oidc-client';
import {SubscriptionAwareComponent} from '../subscription-aware.component';
import {BehaviorSubject, map, Observable} from "rxjs";
import {JwtPayload} from 'jwt-decode';

interface UserData extends JwtPayload {
}

interface UserInfoData extends JwtPayload {
}

@Injectable()
export class ApplicationService extends SubscriptionAwareComponent {

  public readonly userDataSubject;
  public readonly userInfoSubject;

  constructor(
    private readonly oidcSecurityService: OidcSecurityService,
  ) {
    super();
    this.userDataSubject = new BehaviorSubject<UserData | null>(null);
    this.userInfoSubject = new BehaviorSubject<UserInfoData | null>(null);
  }

  override ngOnDestroy(): void {
    this.userDataSubject.complete();
    this.userInfoSubject.complete();
    super.ngOnDestroy();
  }

  isAuthenticated(): Observable<boolean> {
    return this.oidcSecurityService.isAuthenticated$.pipe(map((result: AuthenticatedResult): boolean => result.isAuthenticated));
  }

  login(): void {
    this.oidcSecurityService.authorize();
    this.registerSubscription(
      this.oidcSecurityService.isAuthenticated$.subscribe(
        (auth: AuthenticatedResult) => {
          if (auth.isAuthenticated) {
            this.postLogin();
          }
        }
      )
    );
  }

  postLogin(): void {
    this.registerSubscriptions([
      this.oidcSecurityService.getPayloadFromAccessToken().subscribe({
        next: (data: UserData): void => this.userDataSubject.next(data),
        error: (error) => {
          console.error('Failed to get access token payload:', error);
          this.userDataSubject.next(null);
        }
      }),
      this.oidcSecurityService.getUserData().subscribe({
        next: (userInfo: UserInfoData): void => this.userInfoSubject.next(userInfo),
        error: (error) => {
          console.error('Failed to get user info:', error);
          this.userInfoSubject.next(null);
        }
      })
    ]);
  }

  logout(): void {
    this.registerSubscription(
      this.oidcSecurityService.logoff().subscribe({
        complete: () => {
          this.postLogout();
        }
      })
    );
  }

  private postLogout(): void {
    sessionStorage.clear();
    localStorage.clear();
    this.userDataSubject.next(null);
    this.userInfoSubject.next(null);
  }
}
