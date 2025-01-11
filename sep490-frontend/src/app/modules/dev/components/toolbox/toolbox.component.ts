import {
  Component,
  EventEmitter,
  OnInit,
  TemplateRef,
  ViewChild,
} from '@angular/core';
import { MessageService } from 'primeng/api';
import { Observable, takeUntil } from 'rxjs';
import { ApplicationService } from '../../../core/services/application.service';
import { SubscriptionAwareComponent } from '../../../core/subscription-aware.component';
import { TableTemplateColumn } from '../../../shared/components/table-template/table-template.component';
import {
  SearchCriteriaDto,
  SearchResultDto,
} from '../../../shared/models/models';
import { ModalProvider } from '../../../shared/services/modal-provider';
import { DevService, Product } from '../../dev.service';

export interface ProductCriteria {
  criteria: string;
  // specific criteria such as name, category, etc.
}

@Component({
  selector: 'app-toolbox',
  templateUrl: './toolbox.component.html',
  styleUrl: './toolbox.component.scss',
})
export class ToolboxComponent
  extends SubscriptionAwareComponent
  implements OnInit
{
  @ViewChild('actionsTemplate', { static: true })
  actionsTemplate!: TemplateRef<any>;
  cols: TableTemplateColumn[] = [];
  productCriteria!: ProductCriteria;
  readonly searchEvent: EventEmitter<void> = new EventEmitter();
  selectedProducts!: Product[] | null;
  protected fetchProducts!: (
    criteria: SearchCriteriaDto<ProductCriteria>,
  ) => Observable<SearchResultDto<Product>>;

  constructor(
    private readonly modalProvider: ModalProvider,
    private readonly messageService: MessageService,
    private readonly devService: DevService,
    protected readonly applicationService: ApplicationService,
  ) {
    super();
  }

  ngOnInit(): void {
    this.fetchProducts = this.devService.getData.bind(this.devService);
    this.productCriteria = { criteria: '' };
    this.cols = [
      { field: 'code', header: 'Code', sortable: true },
      { field: 'name', header: 'Name', sortable: true },
      { field: 'category', header: 'Category' },
      {
        field: 'quantity',
        header: 'Quantity',
      },
      {
        field: 'actions',
        header: '',
        templateRef: this.actionsTemplate,
      },
    ];
  }

  search(): void {
    this.searchEvent.emit();
  }

  onSelectionChange(event: Product[]): void {
    this.selectedProducts = event;
  }

  confirmDelete(): void {
    this.modalProvider
      .showConfirm({
        message: 'Do you want to delete this product?',
        header: 'Delete Confirmation',
        icon: 'pi pi-info-circle',
        acceptButtonStyleClass: 'p-button-danger p-button-text',
        rejectButtonStyleClass: 'p-button-text p-button-text',
        acceptIcon: 'none',
        acceptLabel: 'Yes',
        rejectIcon: 'none',
        rejectLabel: 'No',
      })
      .pipe(takeUntil(this.destroy$))
      .subscribe((result: boolean): void => {
        this.showMessage(result);
      });
  }

  private showMessage(result: boolean): void {
    if (result) {
      this.messageService.add({
        severity: 'success',
        summary: 'Confirmed',
        detail: 'Product deleted',
      });
      return;
    }

    this.messageService.add({
      severity: 'error',
      summary: 'Rejected',
      detail: 'You have rejected',
    });
  }
}
