import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AppRoutingConstants} from '../../app-routing.constant';
import {BuildingsComponent} from './components/buildings/buildings.component';
import {PaymentComponent} from './components/payment/payment.component';
import {PlanComponent} from './components/plan/plan.component';
import {UsersComponent} from './components/users/users.component';
import {EnterpriseComponent} from './enterprise.component';

const routes: Routes = [
  {
    path: '',
    component: EnterpriseComponent,
    children: [
      {path: AppRoutingConstants.BUILDING_PATH, component: BuildingsComponent},
      {path: AppRoutingConstants.USERS_PATH, component: UsersComponent},
      {
        path: AppRoutingConstants.PLAN_PATH,
        component: PlanComponent
      },
      {path: AppRoutingConstants.PAYMENT_PATH, component: PaymentComponent}
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EnterpriseRoutingModule {}
