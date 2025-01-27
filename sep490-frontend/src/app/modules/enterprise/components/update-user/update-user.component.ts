import {HttpClient} from '@angular/common/http';
import {Component} from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormControl,
  Validators
} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {TranslateService} from '@ngx-translate/core';
import {MessageService} from 'primeng/api';
import {AbstractFormComponent} from '../../../shared/components/form/abstract-form-component';
import {
  BuildingDTO,
  EnterpriseUserDetailDTO,
  PermissionRole,
  UserScope
} from '../../../shared/models/business-model';
import {EnumOptions} from '../../../shared/models/models';
import {ModalProvider} from '../../../shared/services/modal-provider';
import {UserService} from '../../services/user.service';
import {v4 as uuidv4} from 'uuid';
import {AppRoutingConstants} from '../../../../app-routing.constant';

@Component({
  selector: 'app-update-user',
  templateUrl: './update-user.component.html',
  styleUrl: './update-user.component.css'
})
export class UpdateUserComponent extends AbstractFormComponent<EnterpriseUserDetailDTO> {
  userId: string;
  protected readonly UserScope = UserScope;
  protected permissionRoleOptions: EnumOptions[] = [
    {
      name: '',
      code: PermissionRole[PermissionRole.MANAGER],
      i18nCode: 'enum.permissionRole.MANAGER'
    },
    {
      name: '',
      code: PermissionRole[PermissionRole.STAFF],
      i18nCode: 'enum.permissionRole.STAFF'
    },
    {
      name: '',
      code: PermissionRole[PermissionRole.AUDITOR],
      i18nCode: 'enum.permissionRole.AUDITOR'
    }
  ];
  protected readonly enterpriseUserForm = {
    id: new FormControl<string>(''),
    createdDate: new FormControl<Date>(new Date()),
    email: new FormControl<string>(''),
    firstName: new FormControl<string>(''),
    lastName: new FormControl<string>(''),
    permissionRole: new FormControl<string>(''),
    scope: new FormControl<string>(''),
    buildings: new FormControl<string[]>([]),
    version: new FormControl<number>(0)
  };

  protected scopeOptions: EnumOptions[] = [
    {
      name: '',
      code: UserScope[UserScope.ENTERPRISE],
      i18nCode: 'enum.scope.ENTERPRISE'
    },
    {
      name: '',
      code: UserScope[UserScope.BUILDING],
      i18nCode: 'enum.scope.BUILDING'
    }
  ];

  protected buildings: BuildingDTO[] = [
    {id: uuidv4(), name: 'Building 1'},
    {id: uuidv4(), name: 'Building 2'},
    {id: uuidv4(), name: 'Building 3'}
  ];
  constructor(
    httpClient: HttpClient,
    formBuilder: FormBuilder,
    notificationService: MessageService,
    modalProvider: ModalProvider,
    translate: TranslateService,
    protected userService: UserService,
    private readonly router: Router,
    private readonly activatedRoute: ActivatedRoute
  ) {
    super(
      httpClient,
      formBuilder,
      notificationService,
      modalProvider,
      translate
    );
    this.userId = '';
  }
  onRevert(): void {
    this.formGroup.reset();
  }

  onScopeChange(event: any): void {
    if (event.value === UserScope[UserScope.BUILDING]) {
      this.enterpriseUserForm.buildings.addValidators(Validators.required);
      this.enterpriseUserForm.buildings.markAsUntouched();
    } else {
      this.enterpriseUserForm.buildings.removeValidators(Validators.required);
      this.enterpriseUserForm.buildings.markAsUntouched();
    }
  }

  permissionRoleUser(): string {
    return this.enterpriseUserForm.permissionRole.value?.toString() as string;
  }

  protected updateOptionsLabel(): void {
    this.permissionRoleOptions.forEach(
      x => (x.name = this.translate.instant(x.i18nCode))
    );
    this.scopeOptions.forEach(
      x => (x.name = this.translate.instant(x.i18nCode))
    );
  }
  protected initializeFormControls(): {[key: string]: AbstractControl} {
    return this.enterpriseUserForm;
  }

  protected override prepareDataBeforeSubmit(): void {
    this.enterpriseUserForm.id.enable();
    this.enterpriseUserForm.createdDate.enable();
    this.enterpriseUserForm.permissionRole.enable();
    if (
      this.enterpriseUserForm.scope.value === UserScope[UserScope.ENTERPRISE]
    ) {
      this.enterpriseUserForm.buildings.setValue([]);
    }
  }

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  protected onSubmitFormDataSuccess(_result: any): void {
    void this.router.navigate([
      '/',
      AppRoutingConstants.ENTERPRISE_PATH,
      AppRoutingConstants.USERS_PATH
    ]);
  }
  protected override onSubmitFormRequestError(error: any): void {
    if (error.error.errorType === 'BUSINESS') {
      this.enableSubmitBtn();
    }
  }

  protected override submitFormMethod(): string {
    return 'PUT';
  }

  protected initializeData(): void {
    this.loadUserData();
    this.submitFormMethod();
    this.updateOptionsLabel();
    this.registerSubscription(
      this.translate.onLangChange.subscribe(() => this.onLangChange())
    );
  }

  protected submitFormDataUrl(): string {
    return this.userService.updateUserURL(this.userId);
  }

  private loadUserData(): void {
    this.activatedRoute.paramMap.subscribe(params => {
      this.userId = String(params.get('id'));
    });
    this.userService.getUserById(this.userId).subscribe({
      next: (user: EnterpriseUserDetailDTO) => {
        this.enterpriseUserForm.id.setValue(user.id);
        this.enterpriseUserForm.id.disable();
        this.enterpriseUserForm.email.setValue(user.email);
        this.enterpriseUserForm.firstName.setValue(user.firstName);
        this.enterpriseUserForm.lastName.setValue(user.lastName);
        this.enterpriseUserForm.permissionRole.setValue(user.permissionRole);
        this.enterpriseUserForm.permissionRole.disable();
        this.enterpriseUserForm.scope.setValue(user.scope);
        this.enterpriseUserForm.createdDate.setValue(user.createdDate);
        this.enterpriseUserForm.createdDate.disable();
        this.enterpriseUserForm.buildings.setValue(user.buildingIds);
        this.enterpriseUserForm.version.setValue(user.version);
      },
      error: () => {
        this.notificationService.add({
          severity: 'error',
          summary: 'Error',
          detail: this.translate.instant('enterprise.Users.loadError')
        });
      }
    });
  }

  private onLangChange(): void {
    this.updateOptionsLabel();
    this.enterpriseUserForm.permissionRole.setErrors(null);
    this.enterpriseUserForm.scope.setErrors(null);
  }
}
