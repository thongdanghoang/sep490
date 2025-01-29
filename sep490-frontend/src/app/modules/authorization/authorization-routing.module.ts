import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AppRoutingConstants} from '../../app-routing.constant';
import {AuthorizationComponent} from './authorization.component';
import {CreateUserComponent} from './components/create-user/create-user.component';
import {UpdateUserComponent} from './components/update-user/update-user.component';
import {UsersComponent} from './components/users/users.component';

const routes: Routes = [
  {
    path: '',
    component: AuthorizationComponent,
    children: [
      {path: AppRoutingConstants.USERS_PATH, component: UsersComponent},
      {
        path: `${AppRoutingConstants.UPDATE_USER_PATH}/:id`,
        component: UpdateUserComponent
      },
      {
        path: AppRoutingConstants.USER_DETAILS,
        component: CreateUserComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthorizationRoutingModule {}
