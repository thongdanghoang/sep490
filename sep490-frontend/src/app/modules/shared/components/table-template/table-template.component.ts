import {
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output,
  TemplateRef,
  ViewChild
} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {MessageService, SortEvent} from 'primeng/api';
import {PaginatorState} from 'primeng/paginator';
import {Table} from 'primeng/table';
import {takeUntil} from 'rxjs';
import {ApplicationConstant} from '../../../../application.constant';
import {SearchResultDto, SortDto} from '../../models/models';
import {AbstractSearchComponent} from '../abstract-search';

export interface TableTemplateColumn {
  field: string;
  header: string;
  sortable?: boolean;
  templateRef?: TemplateRef<any>;
}

@Component({
  selector: 'table-template',
  templateUrl: './table-template.component.html',
  styleUrl: './table-template.component.scss'
})
export class TableTemplateComponent<
    C,
    R,
    W extends SearchResultDto<R> = SearchResultDto<R>
  >
  extends AbstractSearchComponent<C, R, W>
  implements OnInit
{
  @Input() captionTemplateRef: TemplateRef<any> | undefined;
  @Input({required: true}) columns!: TableTemplateColumn[];
  @Input() sort: SortDto | undefined;
  @ViewChild('tableTemplateComponent') tableTemplateComponent!: Table;
  isSorted: boolean | null | undefined;
  @Input() checkbox: boolean = false;
  @Input() searchOnInit: boolean = true;
  @Input() triggerSearch: EventEmitter<void> | undefined;
  @Output() readonly selectionChange: EventEmitter<R[]> = new EventEmitter();
  selected: R[] | undefined;

  protected readonly ApplicationConstant = ApplicationConstant;

  constructor(translate: TranslateService, messageService: MessageService) {
    super(translate, messageService);
  }

  override ngOnInit(): void {
    super.ngOnInit();
    if (this.searchOnInit) {
      this.submit();
    }
    if (this.triggerSearch) {
      this.triggerSearch
        .pipe(takeUntil(this.destroy$))
        .subscribe((): void => this.submit());
    }
  }

  override initSearchDto(): void {
    this.searchCriteria = {
      page: {
        offset: 0,
        limit: ApplicationConstant.DEFAULT_PAGE_SIZE
      },
      criteria: this.criteria,
      sort: this.sort
    };
  }

  onSortChange(event: SortEvent): void {
    if (event.field && event.order) {
      this.sort = {
        colId: event.field,
        sort: event.order === 1 ? 'ASC' : 'DESC'
      };
    }
    if (
      this.sort &&
      (this.searchCriteria.sort?.colId !== this.sort.colId ||
        this.searchCriteria.sort?.sort !== this.sort.sort)
    ) {
      if (this.isSorted == null) {
        this.isSorted = true;
        this.searchCriteria.sort = this.sort;
      } else if (this.isSorted) {
        this.isSorted = false;
        this.searchCriteria.sort = this.sort;
      } else if (!this.isSorted) {
        this.isSorted = null;
        this.searchCriteria.sort = undefined;
        this.tableTemplateComponent.reset();
      }
      this.search();
    }
  }

  onPageChange(value: PaginatorState): void {
    if (value.first !== undefined && value.rows !== undefined) {
      this.searchCriteria.page = {
        offset: value.first,
        limit: value.rows
      };
      this.search();
    }
  }

  onSelectionChange(event: R[]): void {
    this.selectionChange.emit(event);
  }
}
