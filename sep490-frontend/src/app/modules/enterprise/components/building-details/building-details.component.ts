import {Location} from '@angular/common';
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
import {filter, map, switchMap, takeUntil, tap} from 'rxjs';
import {validate} from 'uuid';
import {UUID} from '../../../../../types/uuid';
import {AppRoutingConstants} from '../../../../app-routing.constant';
import {BuildingService} from '../../../../services/building.service';
import {GeocodingService} from '../../../../services/geocoding.service';
import {AbstractFormComponent} from '../../../shared/components/form/abstract-form-component';
import {BuildingDetails} from '../../models/enterprise.dto';
import {MapLocation} from '../buildings/buildings.component';

@Component({
  selector: 'app-building-detail',
  templateUrl: './building-details.component.html',
  styleUrl: './building-details.component.css'
})
export class BuildingDetailsComponent extends AbstractFormComponent<BuildingDetails> {
  protected readonly buildingDetailsStructure = {
    id: new FormControl(''),
    version: new FormControl(null),
    name: new FormControl(null, Validators.required),
    numberOfDevices: new FormControl(0, {
      nonNullable: true,
      validators: [Validators.min(1), Validators.required]
    }),
    latitude: new FormControl<number | null>(null, [
      Validators.required,
      Validators.min(-90),
      Validators.max(90)
    ]),
    longitude: new FormControl<number | null>(null, [
      Validators.required,
      Validators.min(-180),
      Validators.max(180)
    ]),
    address: new FormControl<string | null>(null, Validators.required)
  };

  constructor(
    httpClient: HttpClient,
    formBuilder: FormBuilder,
    notificationService: MessageService,
    translate: TranslateService,
    private readonly activatedRoute: ActivatedRoute,
    private readonly buildingService: BuildingService,
    private readonly geocodingService: GeocodingService,
    private readonly location: Location,
    private readonly router: Router
  ) {
    super(httpClient, formBuilder, notificationService, translate);
  }

  back(): void {
    this.location.back();
  }

  get isEdit(): boolean {
    return !!this.buildingDetailsStructure.id.value;
  }

  override reset(): void {
    if (this.isEdit) {
      return this.initializeData();
    }
    super.reset();
  }

  protected initializeData(): void {
    this.fetchBuildingDetails();
    this.handleLocationChange();
  }

  protected initializeFormControls(): {[p: string]: AbstractControl} {
    return this.buildingDetailsStructure;
  }

  protected onSubmitFormDataSuccess(): void {
    void this.router.navigate([
      '/',
      AppRoutingConstants.ENTERPRISE_PATH,
      AppRoutingConstants.BUILDING_PATH
    ]);
  }

  protected submitFormDataUrl(): string {
    return this.buildingService.createBuildingUrl;
  }

  private fetchBuildingDetails(): void {
    this.activatedRoute.paramMap
      .pipe(
        takeUntil(this.destroy$),
        map(params => params.get('id')),
        filter((idParam): idParam is string => !!idParam),
        filter(id => validate(id)),
        tap(id => this.buildingDetailsStructure.id.setValue(id)),
        switchMap(id => this.buildingService.getBuildingDetails(id as UUID))
      )
      .subscribe(building => {
        this.formGroup.patchValue(building);
      });
  }

  private handleLocationChange(): void {
    this.activatedRoute.queryParams
      .pipe(
        takeUntil(this.destroy$),
        filter((params): params is MapLocation => !!params),
        switchMap(location => {
          if (!!location.latitude && !!location.longitude) {
            this.buildingDetailsStructure.latitude.setValue(location.latitude);
            this.buildingDetailsStructure.longitude.setValue(
              location.longitude
            );
            return this.geocodingService.reverse(
              location.latitude,
              location.longitude
            );
          } else if (!this.isEdit) {
            // only check when creating a new building
            this.notificationService.add({
              severity: 'error',
              summary: 'Error',
              detail: 'Invalid location'
            });
            return [];
          }
          return [];
        })
      )
      .subscribe(address => {
        if (address) {
          this.buildingDetailsStructure.address.setValue(address.displayName);
        }
      });
  }
}
