import {NgModule} from '@angular/core';
import {SharedModule} from '../shared/shared.module';

import {AuthorizationRoutingModule} from './authorization-routing.module';
import {AuthorizationComponent} from './authorization.component';
import {CreateUserComponent} from './components/create-user/create-user.component';
import {UpdateUserComponent} from './components/update-user/update-user.component';
import {UsersComponent} from './components/users/users.component';
import {UserService} from './services/user.service';

@NgModule({
  declarations: [
    AuthorizationComponent,
    CreateUserComponent,
    UsersComponent,
    UpdateUserComponent
  ],
  imports: [SharedModule, AuthorizationRoutingModule],
  providers: [UserService]
})
export class AuthorizationModule {}
