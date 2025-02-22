import {NgModule, inject} from '@angular/core';
import {Router, RouterModule, Routes} from '@angular/router';
import {
  AutoLoginPartialRoutesGuard,
  OidcSecurityService
} from 'angular-auth-oidc-client';
import {Observable, map, of, switchMap, tap} from 'rxjs';
import {AppRoutingConstants} from './app-routing.constant';
import {DashboardComponent} from './components/dashboard/dashboard.component';
import {ForbiddenComponent} from './components/forbidden/forbidden.component';
import {HomeComponent} from './components/home/home.component';
import {NotFoundComponent} from './components/not-found/not-found.component';
import {UnauthorizedComponent} from './components/unauthorized/unauthorized.component';
import {ApplicationService} from './modules/core/services/application.service';
import {UserRole} from './modules/authorization/enums/role-names';

const authGuard: () => Observable<boolean> = () => {
  const authService = inject(OidcSecurityService);
  const applicationService = inject(ApplicationService);
  const router = inject(Router);

  return authService.checkAuth().pipe(
    tap(authData => {
      if (!authData.isAuthenticated) {
        applicationService.login();
      } else {
        applicationService.postLogin();
      }
    }),
    switchMap(authData =>
      authData.isAuthenticated
        ? applicationService.UserData.pipe(
            map(user => {
              if (
                user &&
                applicationService.includeRole(
                  user.authorities,
                  UserRole.ENTERPRISE_OWNER
                )
              ) {
                return true;
              }
              void router.navigate([AppRoutingConstants.FORBIDDEN]);
              return false;
            })
          )
        : of(false)
    )
  );
};

const AdminGuard: () => Observable<boolean> = () => {
  const authService = inject(OidcSecurityService);
  const applicationService = inject(ApplicationService);
  const router = inject(Router);

  return authService.checkAuth().pipe(
    tap(authData => {
      if (!authData.isAuthenticated) {
        applicationService.login();
      } else {
        applicationService.postLogin();
      }
    }),
    switchMap(authData =>
      authData.isAuthenticated
        ? applicationService.UserData.pipe(
            map(user => {
              if (
                user &&
                applicationService.includeRole(
                  user.authorities,
                  UserRole.SYSTEM_ADMIN
                )
              ) {
                return true;
              }
              void router.navigate([AppRoutingConstants.FORBIDDEN]);
              return false;
            })
          )
        : of(false)
    )
  );
};

const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    canActivate: [AutoLoginPartialRoutesGuard, authGuard]
  },
  {
    path: AppRoutingConstants.DASHBOARD_PATH,
    component: DashboardComponent,
    canActivate: [AutoLoginPartialRoutesGuard, authGuard]
  },
  {
    path: AppRoutingConstants.AUTH_PATH,
    loadChildren: () =>
      import('./modules/authorization/authorization.module').then(
        m => m.AuthorizationModule
      ),
    canActivate: [AutoLoginPartialRoutesGuard, authGuard]
  },
  {
    path: AppRoutingConstants.ADMIN_PATH,
    loadChildren: () =>
      import('./modules/admin/admin.module').then(m => m.AdminModule),
    canActivate: [AutoLoginPartialRoutesGuard, AdminGuard]
  },
  {
    path: AppRoutingConstants.ENTERPRISE_PATH,
    loadChildren: () =>
      import('./modules/enterprise/enterprise.module').then(
        m => m.EnterpriseModule
      ),
    canActivate: [AutoLoginPartialRoutesGuard, authGuard]
  },
  {
    path: AppRoutingConstants.EMISSIONS_PATH,
    loadChildren: () =>
      import('./modules/emissions/emissions.module').then(
        m => m.EmissionsModule
      ),
    canActivate: [AutoLoginPartialRoutesGuard, authGuard]
  },
  {
    path: AppRoutingConstants.FORBIDDEN,
    component: ForbiddenComponent
  },
  {
    path: AppRoutingConstants.UNAUTHORIZED,
    component: UnauthorizedComponent
  },
  {
    path: AppRoutingConstants.DEV_PATH,
    loadChildren: () =>
      import('./modules/dev/dev.module').then(m => m.DevModule)
  },
  {
    path: '**',
    component: NotFoundComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
