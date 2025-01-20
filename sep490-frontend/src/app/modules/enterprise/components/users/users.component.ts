import {
  Component,
  EventEmitter,
  OnInit,
  TemplateRef,
  ViewChild
} from '@angular/core';
import {Observable, takeUntil} from 'rxjs';
import {ApplicationService} from '../../../core/services/application.service';
import {TableTemplateColumn} from '../../../shared/components/table-template/table-template.component';
import {EnterpriseUserDTO} from '../../../shared/models/business-model';
import {
  SearchCriteriaDto,
  SearchResultDto
} from '../../../shared/models/models';
import {UserService} from '../../services/user.service';
import {MessageService} from 'primeng/api';
import {ModalProvider} from '../../../shared/services/modal-provider';
import {SubscriptionAwareComponent} from '../../../core/subscription-aware.component';
import {TranslateService} from '@ngx-translate/core';

export interface UserCriteria {
  criteria: string;
}

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrl: './users.component.css'
})
export class UsersComponent
  extends SubscriptionAwareComponent
  implements OnInit
{
  @ViewChild('roleTemplate', {static: true}) roleTemplate!: TemplateRef<any>;
  @ViewChild('scopeTemplate', {static: true})
  scopeTemplate!: TemplateRef<any>;
  @ViewChild('actionsTemplate', {static: true})
  actionsTemplate!: TemplateRef<any>;
  protected fetchUsers!: (
    criteria: SearchCriteriaDto<UserCriteria>
  ) => Observable<SearchResultDto<EnterpriseUserDTO>>;
  protected cols: TableTemplateColumn[] = [];
  protected readonly searchEvent: EventEmitter<void> = new EventEmitter();
  protected selected: EnterpriseUserDTO[] = [];
  protected searchCriteria: UserCriteria = {criteria: ''};

  constructor(
    protected readonly applicationService: ApplicationService,
    private readonly userService: UserService,
    private readonly messageService: MessageService,
    private readonly modalProvider: ModalProvider,
    private readonly translate: TranslateService
  ) {
    super();
  }

  ngOnInit(): void {
    this.buildCols();
    this.fetchUsers = this.userService.getUsers.bind(this.userService);
  }

  buildCols(): void {
    this.cols.push({
      field: 'name',
      sortField: 'firstName',
      header: 'enterprise.Users.table.name',
      sortable: true
    });
    this.cols.push({
      field: 'email',
      header: 'enterprise.Users.table.email',
      sortable: true
    });
    this.cols.push({
      field: 'role',
      header: 'enterprise.Users.table.role',
      sortable: true,
      templateRef: this.roleTemplate
    });
    this.cols.push({
      field: 'scope',
      header: 'enterprise.Users.table.scope',
      sortable: true,
      templateRef: this.scopeTemplate
    });
    this.cols.push({
      field: 'actions',
      header: '',
      templateRef: this.actionsTemplate
    });
  }

  onSelectionChange(selectedUsers: EnterpriseUserDTO[]): void {
    this.selected = selectedUsers;
  }

  search(): void {
    this.searchEvent.emit();
  }

  confirmDelete(): void {
    this.modalProvider
      .showConfirm({
        message: this.translate.instant('common.defaultConfirmMessage'),
        header: this.translate.instant('common.confirmHeader'),
        icon: 'pi pi-info-circle',
        acceptButtonStyleClass: 'p-button-danger p-button-text min-w-20',
        rejectButtonStyleClass: 'p-button-contrast p-button-text min-w-20',
        acceptIcon: 'none',
        acceptLabel: this.translate.instant('common.accept'),
        rejectIcon: 'none',
        rejectLabel: this.translate.instant('common.reject')
      })
      .pipe(takeUntil(this.destroy$))
      .subscribe((result: boolean): void => {
        if (result) {
          this.deleteUsers();
        }
      });
  }

  private deleteUsers(): void {
    const userIds = this.selected.map(user => user.id);

    this.userService.deleteUsers(userIds).subscribe({
      next: () => {
        this.messageService.add({
          severity: 'success',
          summary: this.translate.instant(
            'enterprise.Users.message.success.summary'
          ),
          detail: this.translate.instant(
            'enterprise.Users.message.success.detail'
          )
        });
        this.selected = []; // Clear local selection
        this.searchEvent.emit(); // Refresh table
      },
      error: () => {
        this.messageService.add({
          severity: 'error',
          summary: this.translate.instant(
            'enterprise.Users.message.error.summary'
          ),
          detail: this.translate.instant(
            'enterprise.Users.message.error.detail'
          )
        });
      }
    });
  }

  // eslint-disable-next-line @typescript-eslint/member-ordering
  onDelete(rowData: EnterpriseUserDTO): void {
    this.selected = [rowData];
    this.confirmDelete();
  }

  // eslint-disable-next-line @typescript-eslint/explicit-function-return-type,@typescript-eslint/no-unused-vars,@typescript-eslint/member-ordering
  onEdit(rowData: TableTemplateColumn) {}
}
