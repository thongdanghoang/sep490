import { Component, EventEmitter, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { SearchCriteriaDto, SearchResultDto } from '../../../shared/models/models';
import { ApplicationService } from '../../../core/services/application.service';
import { Observable } from 'rxjs';
import { EnterpriseUserDTO } from '../../../shared/models/business-model';
import { TableTemplateColumn } from '../../../shared/components/table-template/table-template.component';

@Component({
    selector: 'app-users',
    templateUrl: './users.component.html',
    styleUrl: './users.component.css'
})
export class UsersComponent implements OnInit {

    protected fetchUsers!: (criteria: SearchCriteriaDto<string>) => Observable<SearchResultDto<EnterpriseUserDTO>>;
    protected cols: TableTemplateColumn[] = [];
    protected readonly searchEvent: EventEmitter<void> = new EventEmitter();
    protected selected: EnterpriseUserDTO[] = [];
    protected searchCriteria: string = '';

    @ViewChild("roleTemplate", {static: true}) roleTemplate!: TemplateRef<any>;
    @ViewChild("scopeTemplate", {static: true}) scopeTemplate!: TemplateRef<any>;
    @ViewChild('actionsTemplate', { static: true }) actionsTemplate!: TemplateRef<any>;

    constructor(private readonly httpClient: HttpClient,
                protected readonly applicationService: ApplicationService) {
    }

    ngOnInit(): void {
        this.buildCols();
        this.fetchUsers = (criteria) => {
            return this.httpClient.post<SearchResultDto<EnterpriseUserDTO>>('http://localhost:8080/api/enterprise-user/search', criteria);
        };
    }

    buildCols(): void {
        this.cols.push({field: 'name', sortField: "firstName", header: 'enterprise.Users.table.name', sortable: true});
        this.cols.push({field: 'email', header: 'enterprise.Users.table.email', sortable: true});
        this.cols.push({field: 'role', header: 'enterprise.Users.table.role', sortable: true, templateRef: this.roleTemplate});
        this.cols.push({field: 'scope', header: 'enterprise.Users.table.scope', sortable: true, templateRef: this.scopeTemplate});
        this.cols.push({field: 'actions', header: '', templateRef: this.actionsTemplate})
    }

    onSelectionChange(selectedUsers: EnterpriseUserDTO[]): void {
        this.selected = selectedUsers;
    }

    search(): void {
        this.searchEvent.emit();
    }

    confirmDelete(): void {
        console.log(this.selected);
    }

    onDelete(rowData: TableTemplateColumn) {
        console.log(rowData);
    }

    onEdit(rowData: TableTemplateColumn) {

    }


}
