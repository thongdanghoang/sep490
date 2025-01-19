import {
  Component,
  EventEmitter,
  OnInit,
  TemplateRef,
  ViewChild
} from '@angular/core';
import {Observable} from 'rxjs';
import {ApplicationService} from '../../../core/services/application.service';
import {TableTemplateColumn} from '../../../shared/components/table-template/table-template.component';
import {EnterpriseUserDTO} from '../../../shared/models/business-model';
import {
  SearchCriteriaDto,
  SearchResultDto
} from '../../../shared/models/models';
import {UserService} from '../../services/user.service';

export interface UserCriteria {
  criteria: string;
}

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrl: './users.component.css'
})
export class UsersComponent implements OnInit {
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
    private readonly userService: UserService
  ) {}

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

  confirmDelete(): void {}

  // eslint-disable-next-line @typescript-eslint/no-unused-vars,@typescript-eslint/explicit-function-return-type
  onDelete(rowData: TableTemplateColumn) {}

  // eslint-disable-next-line @typescript-eslint/explicit-function-return-type,@typescript-eslint/no-unused-vars
  onEdit(rowData: TableTemplateColumn) {}
}
