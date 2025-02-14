import {Injectable, OnDestroy} from '@angular/core';
import {
  AuthenticatedResult,
  OidcSecurityService
} from 'angular-auth-oidc-client';
import {SubscriptionAwareComponent} from '../subscription-aware.component';
import {BehaviorSubject, Observable, filter, map, switchMap} from 'rxjs';
import {JwtPayload} from 'jwt-decode';

interface UserInfoEmailScope {
  email: string;
  email_verified: boolean;
}

// eslint-disable-next-line @typescript-eslint/no-unused-vars
interface UserInfoPhoneScope {
  phone: string;
  phone_verified: boolean;
}

// eslint-disable-next-line @typescript-eslint/no-unused-vars
interface UserInfoAddressScope {
  address: {
    formatted: unknown;
  };
}

// eslint-disable-next-line @typescript-eslint/no-unused-vars
interface UserInfoProfileScope {
  birthdate: string;
  family_name: string;
  gender: string;
  given_name: string;
  locale: string;
  middle_name: string;
  name: string;
  nickname: string;
  picture: string;
  preferred_username: string;
  profile: string;
  updated_at: number;
  website: string;
  zoneinfo: string;
}
interface UserInfoData extends UserInfoEmailScope, JwtPayload {}

@Injectable()
export class ApplicationService
  extends SubscriptionAwareComponent
  implements OnDestroy
{
  public readonly userInfoSubject;

  constructor(private readonly oidcSecurityService: OidcSecurityService) {
    super();
    this.userInfoSubject = new BehaviorSubject<UserInfoData | null>(null);
    this.registerSubscription(
      this.oidcSecurityService.isAuthenticated$
        .pipe(
          filter((auth: AuthenticatedResult): boolean => auth.isAuthenticated),
          switchMap(() => this.oidcSecurityService.getUserData())
        )
        .subscribe((userInfo: UserInfoData): void =>
          this.userInfoSubject.next(userInfo)
        )
    );
  }

  override ngOnDestroy(): void {
    this.userInfoSubject.complete();
    super.ngOnDestroy();
  }

  isAuthenticated(): Observable<boolean> {
    return this.oidcSecurityService.isAuthenticated$.pipe(
      map((result: AuthenticatedResult): boolean => result.isAuthenticated)
    );
  }

  login(): void {
    this.oidcSecurityService.authorize();
  }

  isMobile(): boolean {
    return /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini|Mobile|mobile|CriOS/i.test(
      navigator.userAgent
    );
  }

  logout(): void {
    this.registerSubscription(
      this.oidcSecurityService.logoff().subscribe({
        complete: (): void => {
          this.postLogout();
        }
      })
    );
  }

  private postLogout(): void {
    sessionStorage.clear();
    localStorage.clear();
    this.userInfoSubject.next(null);
  }
}
