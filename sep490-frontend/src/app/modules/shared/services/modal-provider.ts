import {Injectable, OnDestroy} from '@angular/core';
import {Confirmation} from 'primeng/api';
import {DialogService, DynamicDialogRef} from 'primeng/dynamicdialog';
import {SubscriptionAwareComponent} from '../../core/subscription-aware.component';
import {ConfirmDialogComponent} from '../components/dialog/confirm-dialog/confirm-dialog.component';
import {Observable} from 'rxjs';

@Injectable()
export class ModalProvider
  extends SubscriptionAwareComponent
  implements OnDestroy
{
  ref: DynamicDialogRef | undefined;

  constructor(private readonly dialogService: DialogService) {
    super();
  }

  showConfirm(params: Confirmation): Observable<boolean> {
    this.ref = this.dialogService.open(ConfirmDialogComponent, {
      header: params.header,
      modal: true,
      data: params
    });
    return this.ref.onClose;
  }

  showError(): void {
    // TODO: Implement error dialog
  }

  showDirtyCheckConfirm(): void {
    // TODO: Implement dirty check confirmation
  }

  override ngOnDestroy(): void {
    if (this.ref) {
      this.ref.close();
    }
    super.ngOnDestroy();
  }
}
