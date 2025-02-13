import {NgModule} from '@angular/core';
import {SharedModule} from '../shared/shared.module';
import {BuildingsComponent} from './components/buildings/buildings.component';
import {PaymentComponent} from './components/payment/payment.component';
import {PlanComponent} from './components/plan/plan.component';
import {EnterpriseRoutingModule} from './enterprise-routing.module';
import {EnterpriseComponent} from './enterprise.component';
import {MarkerService} from './services/marker.service';
import {PopupService} from './services/popup.service';
import {RegionService} from './services/region.service';
import {PaymentService} from './services/payment.service';
import {Tag} from 'primeng/tag';
import {
  Accordion,
  AccordionContent,
  AccordionHeader,
  AccordionPanel
} from 'primeng/accordion';

@NgModule({
  declarations: [
    EnterpriseComponent,
    PlanComponent,
    PaymentComponent,
    BuildingsComponent
  ],
  imports: [
    SharedModule,
    EnterpriseRoutingModule,
    Tag,
    Accordion,
    AccordionPanel,
    AccordionHeader,
    AccordionContent
  ],
  providers: [MarkerService, PopupService, RegionService, PaymentService]
})
export class EnterpriseModule {}
