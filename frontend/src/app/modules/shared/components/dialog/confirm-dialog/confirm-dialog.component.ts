import {Component, EventEmitter, Output} from '@angular/core';
import {DynamicDialogConfig, DynamicDialogRef} from 'primeng/dynamicdialog';
import {Confirmation} from 'primeng/api';
import {Nullable} from 'primeng/ts-helpers';

/**
 * Type of the confirm event.
 */
export enum ConfirmEventType {
  ACCEPT,
  REJECT,
  CANCEL
}

@Component({
  selector: 'app-confirm-dialog',
  templateUrl: './confirm-dialog.component.html',
  styleUrl: './confirm-dialog.component.css'
})
export class ConfirmDialogComponent {

  confirmationOptions: Nullable<Confirmation>;

  /**
   * Callback to invoke when dialog is hidden.
   * @param {ConfirmEventType} enum - Custom confirm event.
   * @group Emits
   */
  @Output() onHide: EventEmitter<ConfirmEventType> = new EventEmitter<ConfirmEventType>();

  constructor(private readonly configs: DynamicDialogConfig,
              private readonly ref: DynamicDialogRef) {
    this.confirmationOptions = this.configs.data;
  }

  accept() {
    if (this.confirmationOptions && this.confirmationOptions.acceptEvent) {
      this.confirmationOptions.acceptEvent.emit();
    }

    this.hide(ConfirmEventType.ACCEPT);
    this.ref.close(true);
  }

  reject() {
    if (this.confirmationOptions && this.confirmationOptions.rejectEvent) {
      this.confirmationOptions.rejectEvent.emit(ConfirmEventType.REJECT);
    }

    this.hide(ConfirmEventType.REJECT);
    this.ref.close(false);
  }

  get acceptButtonLabel(): string {
    return this.option('acceptLabel') || "TranslationKeys.ACCEPT";
  }

  get rejectButtonLabel(): string {
    return this.option('rejectLabel') || "TranslationKeys.REJECT";
  }

  option(name: string): any {
    const source: { [key: string]: any } = this.confirmationOptions || this;
    if (source.hasOwnProperty(name)) {
      return source[name];
    }
    return undefined;
  }

  private hide(type?: ConfirmEventType) {
    this.onHide.emit(type);
    this.confirmationOptions = null;
  }
}
