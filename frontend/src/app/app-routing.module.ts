import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AutoLoginPartialRoutesGuard} from 'angular-auth-oidc-client';
import {AppRoutingConstants} from './app-routing.constant';
import {ForbiddenComponent} from './components/forbidden/forbidden.component';
import {UnauthorizedComponent} from './components/unauthorized/unauthorized.component';
import {NotFoundComponent} from './components/not-found/not-found.component';

const routes: Routes = [
  {
    path: AppRoutingConstants.DEV_PATH,
    loadChildren: () => import('./modules/dev/dev.module').then(m => m.DevModule),
    canActivate: [AutoLoginPartialRoutesGuard]
  },
  {
    path: AppRoutingConstants.FORBIDDEN,
    component: ForbiddenComponent,
  },
  {
    path: AppRoutingConstants.UNAUTHORIZED,
    component: UnauthorizedComponent,
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
export class AppRoutingModule {
}
