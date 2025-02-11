import {Component, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {TableTemplateColumn} from '../../../shared/components/table-template/table-template.component';
import {DevService, Product} from '../../../dev/dev.service';
import {
  SearchCriteriaDto,
  SearchResultDto
} from '../../../shared/models/models';
import {Observable} from 'rxjs';
import {ApplicationService} from '../../../core/services/application.service';

export interface ProductCriteria {
  criteria: string;
  // specific criteria such as name, category, etc.
}

@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrl: './payment.component.css'
})
export class PaymentComponent implements OnInit {
  @ViewChild('statusTemplate', {static: true})
  statusTemplate!: TemplateRef<any>;
  @ViewChild('actionsTemplate', {static: true})
  actionsTemplate!: TemplateRef<any>;
  cols: TableTemplateColumn[] = [];
  productCriteria!: ProductCriteria;
  balance: number = 0;
  protected fetchProducts!: (
    criteria: SearchCriteriaDto<ProductCriteria>
  ) => Observable<SearchResultDto<Product>>;
  constructor(
    protected readonly applicationService: ApplicationService,
    private readonly devService: DevService
  ) {}

  ngOnInit(): void {
    this.fetchProducts = this.devService.getData.bind(this.devService);
    this.productCriteria = {criteria: ''};
    this.buildCols();
    this.getBalance();
  }

  buildCols(): void {
    this.cols.push({
      field: 'date',
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
      sortable: true
    });
    this.cols.push({
      field: 'actions',
      header: '',
      templateRef: this.actionsTemplate
    });
  }

  getStatusClass(status: string): string {
    return status === 'success'
      ? 'bg-[#91C896] text-white'
      : 'bg-red-500 text-white';
  }

  getBalance(): void {
    this.balance = 1500;
  }
}
