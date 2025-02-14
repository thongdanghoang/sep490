import {HttpClient} from '@angular/common/http';
import {Component} from '@angular/core';
import {
  AbstractControl,
  FormArray,
  FormBuilder,
  FormControl,
  ValidationErrors,
  ValidatorFn,
  Validators
} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {TranslateService} from '@ngx-translate/core';
import {MessageService} from 'primeng/api';
import {filter, map, switchMap, takeUntil, tap} from 'rxjs';
import {UUID} from '../../../../../types/uuid';
import {AppRoutingConstants} from '../../../../app-routing.constant';
import {AbstractFormComponent} from '../../../shared/components/form/abstract-form-component';
import {SelectableItem} from '../../../shared/models/models';
import {BuildingPermissionRole} from '../../enums/building-permission-role';
import {UserRole} from '../../enums/role-names';
import {UserScope} from '../../enums/user-scope';
import {
  Building,
  BuildingPermission,
  EnterpriseUserDetails
} from '../../models/enterprise-user';
import {UserService} from '../../services/user.service';
import {MultiSelectChangeEvent} from 'primeng/multiselect';
import {SelectChangeEvent} from 'primeng/select';

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
    role: new FormControl(UserRole[UserRole.ENTERPRISE_EMPLOYEE], {
      nonNullable: true
    }),
    scope: new FormControl<string>('', [Validators.required]),
    buildingPermissions: new FormArray([]),
    selectedBuildingIds: new FormControl<UUID[]>(
      [],
      [this.selectedBuildingIdsValidator().bind(this)]
    ),
    enterprisePermission: new FormControl<BuildingPermissionRole | null>(null, [
      this.enterprisePermissionValidator().bind(this)
    ])
  };
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
  protected buildingPermissionScopeEnterprise: SelectableItem<string>[] = [
    {
      disabled: false,
      value: BuildingPermissionRole[
        BuildingPermissionRole.MANAGER
      ] as keyof typeof BuildingPermissionRole,
      label: 'enum.permissionRole.MANAGER'
    },
    {
      disabled: false,
      value: BuildingPermissionRole[
        BuildingPermissionRole.STAFF
      ] as keyof typeof BuildingPermissionRole,
      label: 'enum.permissionRole.STAFF'
    },
    {
      disabled: false,
      value: BuildingPermissionRole[
        BuildingPermissionRole.AUDITOR
      ] as keyof typeof BuildingPermissionRole,
      label: 'enum.permissionRole.AUDITOR'
    }
  ];
  protected scopeOptions: SelectableItem<keyof typeof UserScope>[] = [
    {
      disabled: false,
      value: UserScope[UserScope.ENTERPRISE] as keyof typeof UserScope,
      label: 'enum.scope.ENTERPRISE'
    },
    {
      disabled: false,
      value: UserScope[UserScope.BUILDING] as keyof typeof UserScope,
      label: 'enum.scope.BUILDING'
    }
  ];
  // TODO: [thongdanghoang] integrate with enterprise use case
  protected mockBuildings: Building[] = [
    {id: 'c42cfe00-f32b-42cc-8b6c-9b495ad1f726' as UUID, name: 'Building 1'},
    {id: 'e1a5f984-8fb4-4a4a-847f-e73fcd139829' as UUID, name: 'Building 2'},
    {id: '901e46c3-ec95-45b5-b124-6945a6f8fe83' as UUID, name: 'Building 3'}
  ];
  protected selectableBuildings: SelectableItem<any>[] = this.mockBuildings.map(
    building => ({
      disabled: false,
      value: building.id,
      label: building.name
    })
  );

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

  onBuildingSelect(event: MultiSelectChangeEvent): void {
    const selectedBuildingIds = event.value;
    const currentBuildingIds = this.buildingPermissions.controls.map(
      control => control.value.buildingId
    );
    const buildingIdsToAdd = selectedBuildingIds.filter(
      (id: UUID): boolean => !currentBuildingIds.includes(id)
    );
    const buildingIdsToRemove = currentBuildingIds.filter(
      (id: UUID): boolean => !selectedBuildingIds.includes(id)
    );
    buildingIdsToRemove.forEach(buildingId => {
      const index = this.buildingPermissions.controls.findIndex(
        control => control.value.buildingId === buildingId
      );
      if (index !== -1) {
        this.removeBuildingPermission(index);
      }
    });
    buildingIdsToAdd.forEach((buildingId: UUID): void => {
      this.addBuildingPermission(buildingId);
    });
  }

  onEnterprisePermissionChange(event: SelectChangeEvent): void {
    this.enterpriseUserStructure.buildingPermissions.clear();
    this.addBuildingPermission(null, event.value);
  }

  onScopeChange(): void {
    this.buildingPermissions.clear();
    this.enterpriseUserStructure.selectedBuildingIds.setValue([]);
    this.enterpriseUserStructure.enterprisePermission.setValue(null);
  }

  get isEdit(): boolean {
    return !!this.enterpriseUserStructure.id.value;
  }

  get scopeBuildings(): boolean {
    return (
      this.enterpriseUserStructure.scope.value === UserScope[UserScope.BUILDING]
    );
  }

  get scopeEnterprise(): boolean {
    return (
      this.enterpriseUserStructure.scope.value ===
      UserScope[UserScope.ENTERPRISE]
    );
  }

  get roleEmployee(): boolean {
    return (
      this.enterpriseUserStructure.role.value ===
      UserRole[UserRole.ENTERPRISE_EMPLOYEE]
    );
  }

  get buildingPermissions(): FormArray {
    return this.formGroup.get('buildingPermissions') as FormArray;
  }

  getBuildingName(control: AbstractControl): string {
    const building = this.mockBuildings.find(
      b => b.id === control.value.buildingId
    );
    return building?.name ?? '';
  }

  addBuildingPermission(
    buildingId: UUID | null,
    role?: keyof typeof BuildingPermissionRole
  ): void {
    this.buildingPermissions.controls.push(
      this.formBuilder.group({
        buildingId: new FormControl(buildingId),
        role: new FormControl(role, [Validators.required])
      })
    );
  }

  removeBuildingPermission(index: number): void {
    this.buildingPermissions.removeAt(index);
  }

  override reset(): void {
    if (this.isEdit) {
      return this.initializeData();
    }
    this.buildingPermissions.clear();
    super.reset();
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
      .subscribe(user => {
        this.formGroup.patchValue(user);
        this.initializeBuildingPermissions(user.buildingPermissions);
        this.initializeSelectedBuildingIds();
        this.initializeEnterprisePermission();
      });
  }

  protected initializeFormControls(): {[p: string]: AbstractControl} {
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

  private initializeBuildingPermissions(
    buildingPermissions: BuildingPermission[]
  ): void {
    // Clear the existing FormArray
    this.buildingPermissions.clear();

    // Add a FormGroup for each building permission
    buildingPermissions.forEach(permission => {
      this.buildingPermissions.push(
        this.formBuilder.group({
          buildingId: new FormControl(permission.buildingId),
          role: new FormControl(permission.role)
        })
      );
    });
  }

  private initializeSelectedBuildingIds(): void {
    if (this.scopeBuildings) {
      this.enterpriseUserStructure.selectedBuildingIds.setValue(
        this.buildingPermissions.controls.map(
          control => control.value.buildingId
        )
      );
    }
  }

  private initializeEnterprisePermission(): void {
    if (
      this.scopeEnterprise &&
      this.roleEmployee &&
      this.buildingPermissions.controls.length > 0
    ) {
      this.enterpriseUserStructure.enterprisePermission.setValue(
        this.buildingPermissions.controls[0].value.role
      );
    }
  }

  private enterprisePermissionValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      if (!this.enterpriseUserStructure) {
        return null;
      }
      if (this.scopeEnterprise) {
        return control.value ? null : {required: true};
      }
      return null;
    };
  }

  private selectedBuildingIdsValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      if (!this.enterpriseUserStructure) {
        return null;
      }
      if (this.scopeBuildings) {
        return control.value.length > 0 ? null : {required: true};
      }
      return null;
    };
  }
}
