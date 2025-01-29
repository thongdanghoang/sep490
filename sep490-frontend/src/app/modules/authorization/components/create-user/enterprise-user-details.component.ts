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
import {ActivatedRoute, Router} from '@angular/router';
import {TranslateService} from '@ngx-translate/core';
import {MessageService} from 'primeng/api';
import {SelectChangeEvent} from 'primeng/select';
import {filter, map, switchMap, takeUntil, tap} from 'rxjs';
import {v4 as uuidv4} from 'uuid';
import {UUID} from '../../../../../types/uuid';
import {AppRoutingConstants} from '../../../../app-routing.constant';
import {AbstractFormComponent} from '../../../shared/components/form/abstract-form-component';
import {SelectableItem} from '../../../shared/models/models';
import {BuildingPermissionRole} from '../../enums/building-permission-role';
import {UserScope} from '../../enums/user-scope';
import {
  Building,
  BuildingPermission,
  EnterpriseUserDetails
} from '../../models/enterprise-user';
import {UserService} from '../../services/user.service';

@Component({
  selector: 'app-create-user',
  templateUrl: './enterprise-user-details.component.html',
  styleUrl: './enterprise-user-details.component.css'
})
export class EnterpriseUserDetailsComponent extends AbstractFormComponent<EnterpriseUserDetails> {
  protected readonly enterpriseUserStructure = {
    id: new FormControl(''),
    version: new FormControl(null),
    email: new FormControl<string>('', [Validators.required, Validators.email]),
    firstName: new FormControl<string>('', [Validators.required]),
    lastName: new FormControl<string>('', [Validators.required]),
    scope: new FormControl<string>('', [Validators.required]),
    buildingPermissions: new FormControl<BuildingPermission[]>(
      [],
      [this.buildingValidator().bind(this)]
    )
  };
  protected readonly UserScope = UserScope;
  protected permissionRoleOptions: SelectableItem<string>[] = [
    {
      disabled: false,
      value: BuildingPermissionRole[BuildingPermissionRole.MANAGER],
      label: 'enum.permissionRole.MANAGER'
    },
    {
      disabled: false,
      value: BuildingPermissionRole[BuildingPermissionRole.STAFF],
      label: 'enum.permissionRole.STAFF'
    },
    {
      disabled: false,
      value: BuildingPermissionRole[BuildingPermissionRole.AUDITOR],
      label: 'enum.permissionRole.AUDITOR'
    }
  ];
  protected buildingPermissionScopeEnterprise: SelectableItem<
    BuildingPermission[]
  >[] = [
    {
      disabled: false,
      value: [
        {
          role: BuildingPermissionRole[
            BuildingPermissionRole.MANAGER
          ] as keyof typeof BuildingPermissionRole
        }
      ],
      label: 'enum.permissionRole.MANAGER'
    },
    {
      disabled: false,
      value: [
        {
          role: BuildingPermissionRole[
            BuildingPermissionRole.STAFF
          ] as keyof typeof BuildingPermissionRole
        }
      ],
      label: 'enum.permissionRole.STAFF'
    },
    {
      disabled: false,
      value: [
        {
          role: BuildingPermissionRole[
            BuildingPermissionRole.AUDITOR
          ] as keyof typeof BuildingPermissionRole
        }
      ],
      label: 'enum.permissionRole.AUDITOR'
    }
  ];
  protected scopeOptions: SelectableItem<
    keyof typeof BuildingPermissionRole
  >[] = [
    {
      disabled: false,
      value: UserScope[
        UserScope.ENTERPRISE
      ] as keyof typeof BuildingPermissionRole,
      label: 'enum.scope.ENTERPRISE'
    },
    {
      disabled: false,
      value: UserScope[
        UserScope.BUILDING
      ] as keyof typeof BuildingPermissionRole,
      label: 'enum.scope.BUILDING'
    }
  ];

  protected mockBuildings: Building[] = [
    {id: uuidv4() as UUID, name: 'Building 1'},
    {id: uuidv4() as UUID, name: 'Building 2'},
    {id: uuidv4() as UUID, name: 'Building 3'}
  ];
  protected selectedBuilding: Building[] = [];

  constructor(
    httpClient: HttpClient,
    formBuilder: FormBuilder,
    notificationService: MessageService,
    translate: TranslateService,
    protected userService: UserService,
    private readonly router: Router,
    private readonly activatedRoute: ActivatedRoute
  ) {
    super(httpClient, formBuilder, notificationService, translate);
  }

  onBuildingPermissionChange(buildingId: UUID, event: SelectChangeEvent): void {
    const role = event.value as keyof typeof BuildingPermissionRole;
    const buildingPermission =
      this.enterpriseUserStructure.buildingPermissions.value?.find(
        permission => permission.buildingId === buildingId
      );
    if (buildingPermission) {
      buildingPermission.role = role;
    } else {
      if (this.enterpriseUserStructure.buildingPermissions.value) {
        this.enterpriseUserStructure.buildingPermissions.value?.push({
          buildingId,
          role
        });
      } else {
        this.enterpriseUserStructure.buildingPermissions.setValue([
          {
            buildingId,
            role
          }
        ]);
      }
    }
  }

  protected initializeData(): void {
    this.activatedRoute.paramMap
      .pipe(
        takeUntil(this.destroy$),
        map(params => params.get('id')),
        filter((idParam): idParam is string => !!idParam),
        tap(id => this.enterpriseUserStructure.id.setValue(id)),
        switchMap(id => this.userService.getUserById(id))
      )
      .subscribe(user => this.formGroup.patchValue(user));
  }

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
          this.enterpriseUserStructure.buildingPermissions.setValue([]);
          this.selectedBuilding = [];
        }
      });
    return this.enterpriseUserStructure;
  }

  protected override prepareDataBeforeSubmit(): void {}

  protected submitFormDataUrl(): string {
    return this.userService.createOrUpdateUserURL;
  }

  protected onSubmitFormDataSuccess(): void {
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
