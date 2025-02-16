import {Component, OnInit} from '@angular/core';
import {UUID} from '../../../../../types/uuid';
import {SubscriptionAwareComponent} from '../../../core/subscription-aware.component';
import {CreditPackage} from '../../models/enterprise.dto';
import {CreditPackageService} from '../../services/credit-package.service';
import {WalletService} from '../../services/wallet.service';

@Component({
  selector: 'app-subscriptions',
  templateUrl: './plan.component.html',
  styleUrl: './plan.component.css'
})
export class PlanComponent
  extends SubscriptionAwareComponent
  implements OnInit
{
  tabs = [
    {title: 'purchaseCredit.about', content: 'Content 1', value: '0'},
    {title: 'purchaseCredit.termService', content: 'Content 2', value: '1'}
  ];
  creditPackages: CreditPackage[] = [];
  selectedPackageId: UUID | null = null;
  balance: number = 0;

  constructor(
    private readonly walletService: WalletService,
    private readonly creditPackageService: CreditPackageService
  ) {
    super();
  }

  ngOnInit(): void {
    this.getBalance();
    this.getCreditPackages();
  }

  selectPackage(id: UUID): void {
    this.selectedPackageId = id;
  }

  onBuyCredit(): void {
    if (this.selectedPackageId) {
      // Handle purchase logic
    }
  }

  getBalance(): void {
    this.registerSubscription(
      this.walletService.getWalletBalance().subscribe(result => {
        this.balance = result;
      })
    );
  }

  getCreditPackages(): void {
    this.registerSubscription(
      this.creditPackageService.getAllCreditPackages().subscribe(rs => {
        this.creditPackages = rs;
      })
    );
  }
}
