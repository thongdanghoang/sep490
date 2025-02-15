import {NgModule} from '@angular/core';
import {SharedModule} from '../shared/shared.module';
import {BuildingsComponent} from './components/buildings/buildings.component';
import {PaymentComponent} from './components/payment/payment.component';
import {SubscriptionComponent} from './components/subscription/subscription.component';
import {EnterpriseRoutingModule} from './enterprise-routing.module';
import {EnterpriseComponent} from './enterprise.component';
import {MarkerService} from './services/marker.service';
import {PaymentService} from './services/payment.service';
import {PopupService} from './services/popup.service';
import {RegionService} from './services/region.service';
import {WalletService} from './services/wallet.service';

@NgModule({
  declarations: [
    EnterpriseComponent,
    SubscriptionComponent,
    PaymentComponent,
    BuildingsComponent
  ],
  imports: [SharedModule, EnterpriseRoutingModule],
  providers: [
    MarkerService,
    PopupService,
    RegionService,
    PaymentService,
    WalletService
  ]
})
export class EnterpriseModule {}
