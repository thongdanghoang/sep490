import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AppRoutingConstants} from '../../app-routing.constant';
import {AuthorizationComponent} from './authorization.component';
import {EnterpriseUserDetailsComponent} from './components/create-user/enterprise-user-details.component';
import {UsersComponent} from './components/users/users.component';

const routes: Routes = [
  {
    path: '',
    component: AuthorizationComponent,
    children: [
      {path: AppRoutingConstants.USERS_PATH, component: UsersComponent},
      {
        path: `${AppRoutingConstants.USER_DETAILS}`,
        component: EnterpriseUserDetailsComponent
      },
      {
        path: `${AppRoutingConstants.USER_DETAILS}/:id`,
        component: EnterpriseUserDetailsComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthorizationRoutingModule {}
