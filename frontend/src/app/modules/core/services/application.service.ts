import {Injectable} from '@angular/core';
import {AuthenticatedResult, OidcSecurityService} from 'angular-auth-oidc-client';
import {SubscriptionAwareComponent} from '../subscription-aware.component';
import {map, Observable, Subject} from "rxjs";
import {JwtPayload} from 'jwt-decode';

interface UserData extends JwtPayload {
}

interface UserInfoData extends JwtPayload {
}

@Injectable()
export class ApplicationService extends SubscriptionAwareComponent {

  public readonly userDataSubject: Subject<UserData>;
  public readonly userInfoSubject: Subject<UserInfoData>;

  constructor(
    private readonly oidcSecurityService: OidcSecurityService,
  ) {
    super();
    this.userDataSubject = new Subject<UserData>();
    this.userInfoSubject = new Subject<UserInfoData>();
  }

  isAuthenticated(): Observable<boolean> {
    return this.oidcSecurityService.isAuthenticated$.pipe(map((result: AuthenticatedResult): boolean => result.isAuthenticated));
  }

  login(): void {
    this.oidcSecurityService.authorize();
    this.postLogin();
  }

  postLogin(): void {
    this.registerSubscriptions([
      this.oidcSecurityService.getPayloadFromAccessToken().subscribe((data: UserData): void => {
        console.log('userDataSubject', data);
        this.userDataSubject.next(data);
      }),
      this.oidcSecurityService.getUserData().subscribe((userInfo: UserInfoData): void => {
        console.log('userInfoSubject', userInfo);
        this.userInfoSubject.next(userInfo);
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
  }
}
