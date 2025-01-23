import {HttpClient} from '@angular/common/http';
import {Component} from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormControl,
  Validators
} from '@angular/forms';
import {Router} from '@angular/router';
import {TranslateService} from '@ngx-translate/core';
import {MessageService} from 'primeng/api';
import {AppRoutingConstants} from '../../../../app-routing.constant';
import {AbstractFormComponent} from '../../../shared/components/form/abstract-form-component';
import {
  BuildingDTO,
  NewEnterpriseUserDTO,
  PermissionRole,
  UserScope
} from '../../../shared/models/business-model';
import {EnumOptions} from '../../../shared/models/models';
import {ModalProvider} from '../../../shared/services/modal-provider';
import {v4 as uuidv4} from 'uuid';
import {UserService} from '../../services/user.service';

@Component({
  selector: 'app-create-user',
  templateUrl: './create-user.component.html',
  styleUrl: './create-user.component.css'
})
export class CreateUserComponent extends AbstractFormComponent<NewEnterpriseUserDTO> {
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
    email: new FormControl<string>('', [Validators.required, Validators.email]),
    firstName: new FormControl<string>('', [Validators.required]),
    lastName: new FormControl<string>('', [Validators.required]),
    permissionRole: new FormControl<string>('', [Validators.required]),
    scope: new FormControl<string>('', [Validators.required]),
    buildings: new FormControl<string[]>([], [Validators.required])
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
    private readonly router: Router
  ) {
    super(
      httpClient,
      formBuilder,
      notificationService,
      modalProvider,
      translate
    );
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

  onLangChange(): void {
    this.updateOptionsLabel();
    this.enterpriseUserForm.permissionRole.setValue('');
    this.enterpriseUserForm.permissionRole.setErrors(null);
    this.enterpriseUserForm.scope.setValue('');
    this.enterpriseUserForm.scope.setErrors(null);
    this.enterpriseUserForm.buildings.setValue([]);
  }

  updateOptionsLabel(): void {
    this.permissionRoleOptions.forEach(
      x => (x.name = this.translate.instant(x.i18nCode))
    );
    this.scopeOptions.forEach(
      x => (x.name = this.translate.instant(x.i18nCode))
    );
  }

  protected initializeData(): void {
    this.updateOptionsLabel();
    this.registerSubscription(
      this.translate.onLangChange.subscribe(() => this.onLangChange())
    );
  }

  protected initializeFormControls(): {[p: string]: AbstractControl} {
    return this.enterpriseUserForm;
  }

  protected override prepareDataBeforeSubmit(): void {
    if (
      this.enterpriseUserForm.scope.value === UserScope[UserScope.ENTERPRISE]
    ) {
      this.enterpriseUserForm.buildings.setValue([]);
    }
  }

  protected submitFormDataUrl(): string {
    return this.userService.createUserURL();
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
}
