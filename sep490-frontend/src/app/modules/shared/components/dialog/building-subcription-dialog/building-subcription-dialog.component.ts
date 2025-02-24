import {Component} from '@angular/core';
import {AbstractFormComponent} from '../../form/abstract-form-component';
import {Subscription} from '../../../../enterprise/models/enterprise.dto';
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
import {DynamicDialogConfig, DynamicDialogRef} from 'primeng/dynamicdialog';

@Component({
  selector: 'app-building-subcription-dialog',
  templateUrl: './building-subcription-dialog.component.html',
  styleUrl: './building-subcription-dialog.component.css'
})
export class BuildingSubcriptionDialogComponent extends AbstractFormComponent<Subscription> {
  data: any;
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
    translate: TranslateService,
    private readonly router: Router,
    private readonly activatedRoute: ActivatedRoute,
    public ref: DynamicDialogRef,
    public config: DynamicDialogConfig
  ) {
    super(httpClient, formBuilder, notificationService, translate);
    this.data = config.data;
  }

  closeDialog(): void {
    this.ref.close();
  }
  buy(): void {
    // Handle the buy logic here before closing, if necessary.
    this.ref.close('buy');
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
