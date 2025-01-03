import {NgModule} from '@angular/core';

import {EnterpriseRoutingModule} from './enterprise-routing.module';
import {EnterpriseComponent} from './enterprise.component';
import {SharedModule} from '../shared/shared.module';
import {MarkerService} from './services/marker.service';
import {PopupService} from './services/popup.service';
import {RegionService} from './services/region.service';
import {UsersComponent} from './components/users/users.component';
import {PlanComponent} from './components/plan/plan.component';
import {PaymentComponent} from './components/payment/payment.component';
import {BuildingsComponent} from './components/buildings/buildings.component';

@NgModule({
  declarations: [
    EnterpriseComponent,
    UsersComponent,
    PlanComponent,
    PaymentComponent,
    BuildingsComponent
  ],
  imports: [SharedModule, EnterpriseRoutingModule],
  providers: [MarkerService, PopupService, RegionService]
})
export class EnterpriseModule {}
