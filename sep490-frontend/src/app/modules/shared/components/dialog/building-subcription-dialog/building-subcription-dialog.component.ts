import {Component} from '@angular/core';
import {AbstractFormComponent} from '../../form/abstract-form-component';
import {
  Building,
  BuildingDetails,
  Subscription
} from '../../../../enterprise/models/enterprise.dto';
import {
  AbstractControl,
  FormBuilder,
  FormControl,
  Validators
} from '@angular/forms';
import {HttpClient} from '@angular/common/http';
import {MessageService} from 'primeng/api';
import {TranslateService} from '@ngx-translate/core';
import {ActivatedRoute, Router} from '@angular/router';
import {BuildingService} from '../../../../../services/building.service';
import {WalletService} from '../../../../enterprise/services/wallet.service';

@Component({
  selector: 'app-building-subcription-dialog',
  templateUrl: './building-subcription-dialog.component.html',
  styleUrl: './building-subcription-dialog.component.css'
})
export class BuildingSubcriptionDialogComponent extends AbstractFormComponent<Subscription> {
  selectedBuildingDetails: BuildingDetails | null = null;
  visible: boolean = false;
  balance: number = 0;
  totalCredit: number = 0;
  checked: boolean = false;
  protected readonly formStructure = {
    id: new FormControl(''),
    version: new FormControl(null),
    numberOfMonths: new FormControl(0, {
      nonNullable: true,
      validators: [Validators.min(1), Validators.max(100), Validators.required]
    }),
    numberOfDevices: new FormControl(0, {
      nonNullable: true,
      validators: [Validators.min(100), Validators.required]
    }),
    idBuilding: new FormControl('')
  };
  constructor(
    httpClient: HttpClient,
    formBuilder: FormBuilder,
    notificationService: MessageService,
    private readonly buildingService: BuildingService,
    private readonly walletService: WalletService,
    translate: TranslateService,
    private readonly router: Router,
    private readonly activatedRoute: ActivatedRoute
  ) {
    super(httpClient, formBuilder, notificationService, translate);
  }

  openDialog(building: Building): void {
    this.getBuildingDetails(building);
    this.getBalance();
    this.visible = true;
  }

  getBuildingDetails(building: Building): void {
    this.registerSubscription(
      this.buildingService.getBuildingDetails(building.id).subscribe({
        next: (details: BuildingDetails) => {
          this.selectedBuildingDetails = details;
        },
        error: () => {
          this.notificationService.add({
            severity: 'error',
            summary: 'Error',
            detail: 'Invalid location'
          });
        }
      })
    );
  }

  getBalance(): void {
    this.registerSubscription(
      this.walletService.getWalletBalance().subscribe(result => {
        this.balance = result;
      })
    );
  }

  closeDialog(): void {
    this.formGroup.reset();
    this.visible = false;
  }
  protected initializeData(): void {}

  protected initializeFormControls(): {[p: string]: AbstractControl} {
    return this.formStructure;
  }

  protected onSubmitFormDataSuccess(): void {}

  protected submitFormDataUrl(): string {
    return '';
  }
}
