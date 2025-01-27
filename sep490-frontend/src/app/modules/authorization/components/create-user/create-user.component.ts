import {HttpClient} from '@angular/common/http';
import {Component} from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormControl,
  ValidationErrors,
  ValidatorFn,
  Validators
} from '@angular/forms';
import {Router} from '@angular/router';
import {TranslateService} from '@ngx-translate/core';
import {MessageService} from 'primeng/api';
import {filter, takeUntil} from 'rxjs';
import {v4 as uuidv4} from 'uuid';
import {AppRoutingConstants} from '../../../../app-routing.constant';
import {AbstractFormComponent} from '../../../shared/components/form/abstract-form-component';
import {
  BuildingDTO,
  NewEnterpriseUserDTO,
  PermissionRole,
  UserScope
} from '../../../shared/models/business-model';
import {SelectableItem} from '../../../shared/models/models';
import {UserService} from '../../services/user.service';

@Component({
  selector: 'app-create-user',
  templateUrl: './create-user.component.html',
  styleUrl: './create-user.component.css'
})
export class CreateUserComponent extends AbstractFormComponent<NewEnterpriseUserDTO> {
  protected readonly UserScope = UserScope;
  protected permissionRoleOptions: SelectableItem<string>[] = [
    {
      disabled: false,
      value: PermissionRole[PermissionRole.MANAGER],
      label: 'enum.permissionRole.MANAGER'
    },
    {
      disabled: false,
      value: PermissionRole[PermissionRole.STAFF],
      label: 'enum.permissionRole.STAFF'
    },
    {
      disabled: false,
      value: PermissionRole[PermissionRole.AUDITOR],
      label: 'enum.permissionRole.AUDITOR'
    }
  ];
  protected readonly enterpriseUserStructure = {
    email: new FormControl<string>('', [Validators.required, Validators.email]),
    firstName: new FormControl<string>('', [Validators.required]),
    lastName: new FormControl<string>('', [Validators.required]),
    buildingPermissionRole: new FormControl<string>('', [Validators.required]),
    scope: new FormControl<string>('', [Validators.required]),
    buildings: new FormControl<string[]>(
      [],
      [this.buildingValidator().bind(this)]
    )
  };

  protected scopeOptions: SelectableItem<string>[] = [
    {
      disabled: false,
      value: UserScope[UserScope.ENTERPRISE],
      label: 'enum.scope.ENTERPRISE'
    },
    {
      disabled: false,
      value: UserScope[UserScope.BUILDING],
      label: 'enum.scope.BUILDING'
    }
  ];

  protected mockBuildings: BuildingDTO[] = [
    {id: uuidv4(), name: 'Building 1'},
    {id: uuidv4(), name: 'Building 2'},
    {id: uuidv4(), name: 'Building 3'}
  ];

  constructor(
    httpClient: HttpClient,
    formBuilder: FormBuilder,
    notificationService: MessageService,
    translate: TranslateService,
    protected userService: UserService,
    private readonly router: Router
  ) {
    super(httpClient, formBuilder, notificationService, translate);
  }

  protected initializeData(): void {}

  protected initializeFormControls(): {[p: string]: AbstractControl} {
    this.enterpriseUserStructure.scope.valueChanges
      .pipe(
        filter(
          (): boolean =>
            !(
              this.enterpriseUserStructure.scope.untouched &&
              this.enterpriseUserStructure.scope.pristine
            )
        ),
        takeUntil(this.destroy$)
      )
      .subscribe(value => {
        if (value === UserScope[UserScope.ENTERPRISE]) {
          this.enterpriseUserStructure.buildings.setValue([]);
        }
      });
    return this.enterpriseUserStructure;
  }

  protected override prepareDataBeforeSubmit(): void {}

  protected submitFormDataUrl(): string {
    return this.userService.createUserURL();
  }

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  protected onSubmitFormDataSuccess(_result: any): void {
    void this.router.navigate([
      '/',
      AppRoutingConstants.AUTH_PATH,
      AppRoutingConstants.USERS_PATH
    ]);
  }

  private buildingValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      if (!this.enterpriseUserStructure) {
        return null;
      }
      if (
        this.enterpriseUserStructure.scope.value ===
        UserScope[UserScope.BUILDING]
      ) {
        if (control.value?.length === 0) {
          return {required: true};
        }
      }
      return null;
    };
  }
}
