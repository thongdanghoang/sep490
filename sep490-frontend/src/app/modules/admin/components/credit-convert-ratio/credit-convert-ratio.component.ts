import {Component, OnInit, inject} from '@angular/core';
import {SubscriptionAwareComponent} from '../../../core/subscription-aware.component';
import {ModalProvider} from '../../../shared/services/modal-provider';
import {TranslateService} from '@ngx-translate/core';
import {CreditConvertRatioService} from '../../services/credit-convert-ratio.service';
import {CreditConvertRatio} from '../../../enterprise/models/enterprise.dto';
import {MessageService} from 'primeng/api';
import {Router} from '@angular/router';
import {AppRoutingConstants} from '../../../../app-routing.constant';

@Component({
  selector: 'app-credit-covert-ratio',
  templateUrl: './credit-convert-ratio.component.html',
  styleUrl: './credit-convert-ratio.component.css'
})
export class CreditConvertRatioComponent
  extends SubscriptionAwareComponent
  implements OnInit
{
  creditConvertRatios: CreditConvertRatio[] = [];
  private readonly router = inject(Router);
  constructor(
    private readonly creditConvertRatioService: CreditConvertRatioService,
    private readonly modalProvider: ModalProvider,
    private readonly translate: TranslateService,
    private readonly messageService: MessageService
  ) {
    super();
  }
  ngOnInit(): void {
    this.getCreditConvertRatios();
  }

  getCreditConvertRatios(): void {
    this.registerSubscription(
      this.creditConvertRatioService
        .getAllCreditConvertRatio()
        .subscribe(rs => {
          this.creditConvertRatios = rs;
        })
    );
  }

  control(ratio: CreditConvertRatio): void {
    void this.router.navigate([
      `/${AppRoutingConstants.ADMIN_PATH}/${AppRoutingConstants.CREDIT_CONVERT_RATIO_DETAILS}`,
      ratio.id
    ]);
  }
}
