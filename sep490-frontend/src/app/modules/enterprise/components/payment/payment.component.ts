import {Component, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {SubscriptionAwareComponent} from '../../../core/subscription-aware.component';
import {TableTemplateColumn} from '../../../shared/components/table-template/table-template.component';
import {
  SearchCriteriaDto,
  SearchResultDto
} from '../../../shared/models/models';
import {Observable} from 'rxjs';
import {ApplicationService} from '../../../core/services/application.service';
import {PaymentDTO} from '../../models/payment';
import {PaymentService} from '../../services/payment.service';
import {WalletService} from '../../services/wallet.service';

export interface PaymentCriteria {
  criteria: string;
  // specific criteria such as name, category, etc.
}

@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrl: './payment.component.css'
})
export class PaymentComponent
  extends SubscriptionAwareComponent
  implements OnInit
{
  @ViewChild('statusTemplate', {static: true})
  statusTemplate!: TemplateRef<any>;
  @ViewChild('amountTemplate', {static: true})
  amountTemplate!: TemplateRef<any>;
  @ViewChild('actionsTemplate', {static: true})
  actionsTemplate!: TemplateRef<any>;
  cols: TableTemplateColumn[] = [];
  paymentCriteria!: PaymentCriteria;
  balance: number = 0;
  protected fetchData!: (
    criteria: SearchCriteriaDto<PaymentCriteria>
  ) => Observable<SearchResultDto<PaymentDTO>>;

  constructor(
    protected readonly applicationService: ApplicationService,
    private readonly paymentService: PaymentService,
    private readonly walletService: WalletService
  ) {
    super();
  }

  ngOnInit(): void {
    this.fetchData = this.paymentService.getPayments.bind(this.paymentService);
    this.paymentCriteria = {criteria: ''};
    this.buildCols();
    this.getBalance();
  }

  buildCols(): void {
    this.cols.push({
      field: 'createdDate',
      header: 'payment.history.table.date',
      sortable: true
    });
    this.cols.push({
      field: 'status',
      header: 'payment.history.table.status',
      templateRef: this.statusTemplate
    });
    this.cols.push({
      field: 'amount',
      header: 'payment.history.table.amount',
      sortable: true,
      templateRef: this.amountTemplate
    });
    this.cols.push({
      field: 'actions',
      header: '',
      templateRef: this.actionsTemplate
    });
  }

  getStatusClass(status: string): string {
    return status === 'SUCCESS'
      ? 'bg-[#91C896] text-white'
      : 'bg-red-500 text-white';
  }

  getBalance(): void {
    this.registerSubscription(
      this.walletService.getWalletBalance().subscribe(result => {
        this.balance = result;
      })
    );
  }
}
