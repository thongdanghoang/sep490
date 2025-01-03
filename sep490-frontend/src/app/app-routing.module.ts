import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AppRoutingConstants} from './app-routing.constant';
import {DashboardComponent} from './components/dashboard/dashboard.component';
import {ForbiddenComponent} from './components/forbidden/forbidden.component';
import {UnauthorizedComponent} from './components/unauthorized/unauthorized.component';
import {NotFoundComponent} from './components/not-found/not-found.component';
import {AutoLoginPartialRoutesGuard} from 'angular-auth-oidc-client';
import {HomeComponent} from './components/home/home.component';

const routes: Routes = [
  {
    path: '',
    component: HomeComponent
  },
  {
    path: AppRoutingConstants.DASHBOARD_PATH,
    component: DashboardComponent
  },
  {
    path: AppRoutingConstants.DEV_PATH,
    loadChildren: () =>
      import('./modules/dev/dev.module').then(m => m.DevModule)
  },
  {
    path: AppRoutingConstants.ENTERPRISE_PATH,
    loadChildren: () =>
      import('./modules/enterprise/enterprise.module').then(
        m => m.EnterpriseModule
      ),
    canActivate: [AutoLoginPartialRoutesGuard]
  },
  {
    path: AppRoutingConstants.EMISSIONS_PATH,
    loadChildren: () =>
      import('./modules/emissions/emissions.module').then(
        m => m.EmissionsModule
      ),
    canActivate: [AutoLoginPartialRoutesGuard]
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
    path: '**',
    component: NotFoundComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
