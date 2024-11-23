import {Directive, OnInit} from '@angular/core';
import {SubscriptionAwareComponent} from '../../../core/subscription-aware.component';
import {AbstractControl, FormArray, FormBuilder, FormGroup} from '@angular/forms';
import {HttpClient} from '@angular/common/http';
import {MessageService} from 'primeng/api';
import {ModalProvider} from '../../services/modal-provider';
import {of, take} from 'rxjs';
import {BusinessErrorParam} from '../../models/models';

@Directive()
export abstract class AbstractFormComponent<T> extends SubscriptionAwareComponent implements OnInit {

  /**
   * the root form group of the form.
   */
  public formGroup!: FormGroup;

  /**
   * form controls of the form.
   */
  formControls!: { [key: string]: AbstractControl };

  /**
   * Flag in order to prevent double submit issue when the form is submitted. It is set to true when the submit() method is invoked and
   * set to false to release the lock if there's business error return from server via method submitFormResultError(). Other custom
   * handling can added depend use case.
   */
  disableSubmitButton: boolean = false;

  /**
   * Submitted flag, it is used to force to show validation error
   * when the form is submitted with validation error.
   */
  protected submitted: boolean = false;

  protected constructor(
    protected httpClient: HttpClient,
    protected formBuilder: FormBuilder,
    protected notificationService: MessageService,
    protected modalProvider: ModalProvider) {
    super();
  }

  ngOnInit(): void {
    this.formControls = this.initializeFormControls();
    this.formGroup = this.formBuilder.group(this.formControls);
    this.initializeData();
    this.updateFormControlsState(this.formGroup, [
      (ctr: AbstractControl): void => this.registerSubscription(ctr.valueChanges.pipe(take(1)).subscribe((): void => ctr.markAsTouched()))
    ]);
  }

  /**
   * Initialize list of controls for the form.
   */
  protected abstract initializeFormControls(): { [key: string]: AbstractControl };

  /**
   * Initialize the default value for the target entity.
   */
  protected abstract initializeData(): void;

  protected updateFormControlsState(formGroup: FormGroup, functions: ((formControl: AbstractControl) => void)[]): void {
    for (const control in formGroup.controls) {
      if (formGroup.controls[control]) {
        functions.forEach(fn => fn(formGroup.controls[control]));
        if (formGroup.get(control) instanceof FormGroup || formGroup.get(control) instanceof FormArray) {
          this.updateFormControlsState(formGroup.get(control) as FormGroup, functions);
        }
      }
    }
  }

  submit(): void {
    this.submitted = true;
    // Only perform valid check when formGroup is defined.
    this.disableSubmitBtn();
    this.prepareDataBeforeSubmit();
    this.updateFormControlsState(this.formGroup, [
      (ctr: AbstractControl): void => ctr.markAsTouched(),
      (ctr: AbstractControl): void => ctr.markAsDirty(),
      (ctr: AbstractControl): void => ctr.updateValueAndValidity()
    ]);
    this.formGroup.markAsTouched();
    if (this.formGroup.invalid) {
      this.enableSubmitBtn();
      this.showGenericErrorNotification();
      this.onSubmitFormRequestError({});
      return;
    }

    of(this.validateForm())
      .pipe(take(1))
      .subscribe((res: string[]): void => {
        if (!res || res.length === 0) {
          this.submitForm();
        } else {
          // UI feedback for specific form validation
          this.validateFormError(res);
          this.enableSubmitBtn();
        }
      });
  }

  /**
   * The actual submit logic of the child class.
   * This is only called when no validation errors found.
   */
  protected submitForm(data: T | null = null): void {
    if (this.submitFormDataUrl()) {
      this.disableSubmitBtn();
      this.httpClient.request(this.submitFormMethod(), this.submitFormDataUrl(), {body: data || this.getSubmitFormData()}).subscribe({
        next: r => {
          this.showSaveSuccessNotification(r);
          this.enableSubmitBtn();
          this.onSubmitFormDataSuccess(r);
        },
        error: error => {
          this.displayFormResultErrors(error.error);
          this.onSubmitFormRequestError(error);
        }
      });
    } else {
      this.enableSubmitBtn();
      this.onSubmitFormDataSuccess(this.getSubmitFormData());
    }
  }

  protected disableSubmitBtn(): void {
    this.disableSubmitButton = true;
  }

  /**
   * Perform additional processing before submit the form.
   */
  protected prepareDataBeforeSubmit(): void {
  }

  protected enableSubmitBtn(): void {
    this.disableSubmitButton = false;
  }

  protected onSubmitFormRequestError(_error: any): void {
  }

  /**
   * Validate form before submit
   */
  protected validateForm(): string[] {
    return [];
  }

  protected abstract submitFormDataUrl(): string;

  protected submitFormMethod(): string {
    return 'POST';
  }

  /**
   * Processing when addition validation logic failed.
   */
  protected validateFormError(res: string[]): void {
    this.onSubmitFormRequestError(res);
  }

  protected getSubmitFormData(): any {
    return this.formGroup.value;
  }

  protected showSaveSuccessNotification(_result: any): void {
    this.notificationService.add({
      severity: 'success',
      summary: 'common.success',
      detail: 'common.saveSuccess'
    });
  }

  protected displayFormResultErrors(result: any): void {
    if (result?.errorType === 'BUSINESS') {
      // Business error
      if (result?.field) {
        const formControl = this.formControls[result.field];
        if (formControl) {
          let businessError = {
            [result.i18nKey]: {}
          };
          if (result.args && result.args.length > 0) {
            result.args.forEach((arg: BusinessErrorParam) => {
              businessError[result.i18nKey] = {
                ...businessError[result.i18nKey],
                [arg.key]: arg.value
              };
            });
          }
          formControl.setErrors(businessError);
          formControl.markAsTouched();
          formControl.markAsDirty();
        }
      } else {
        this.notificationService.add({
          severity: 'error',
          summary: 'common.error',
          detail: `validation.${result.i18nKey}`
        });
      }
    }
  }

  protected abstract onSubmitFormDataSuccess(result: any): void;

  private showGenericErrorNotification(): void {
  }

}
