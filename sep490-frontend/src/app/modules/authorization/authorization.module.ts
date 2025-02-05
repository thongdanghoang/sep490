import {NgModule} from '@angular/core';
import {SharedModule} from '../shared/shared.module';

import {AuthorizationRoutingModule} from './authorization-routing.module';
import {AuthorizationComponent} from './authorization.component';
import {EnterpriseUserDetailsComponent} from './components/create-user/enterprise-user-details.component';
import {UsersComponent} from './components/users/users.component';
import {UserService} from './services/user.service';
import {Message} from 'primeng/message';

@NgModule({
  declarations: [
    AuthorizationComponent,
    EnterpriseUserDetailsComponent,
    UsersComponent
  ],
  imports: [SharedModule, AuthorizationRoutingModule, Message],
  providers: [UserService]
})
export class AuthorizationModule {}
