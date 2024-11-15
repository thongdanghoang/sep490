import {Injectable} from '@angular/core';
import {Confirmation, MessageService} from 'primeng/api';
import {DialogService, DynamicDialogRef} from 'primeng/dynamicdialog';
import {SubscriptionAwareComponent} from '../../core/subscription-aware.component';
import {ConfirmDialogComponent} from '../components/confirm-dialog/confirm-dialog.component';

@Injectable()
export class ModalProvider extends SubscriptionAwareComponent {

  ref: DynamicDialogRef | undefined;

  constructor(private readonly dialogService: DialogService,
              private readonly messageService: MessageService) {
    super();
  }

  showConfirm(params: Confirmation & {
    acceptToastSeverity: "info" | "success" | "warn" | "error" | "secondary" | "contrast";
    acceptToastSummary: string;
    acceptToastDetail: string;
    rejectToastSeverity: "info" | "success" | "warn" | "error" | "secondary" | "contrast";
    rejectToastSummary: string;
    rejectToastDetail: string;
  }): void {
    this.ref = this.dialogService.open(ConfirmDialogComponent, {
      header: params.header,
      modal: true,
      data: params,
    });
    this.registerSubscription(this.ref.onClose.subscribe((result: any): void => {
      if (result) {
        this.messageService.add({
          severity: params.acceptToastSeverity,
          summary: params.acceptToastSummary,
          detail: params.acceptToastDetail
        });
      } else {
        this.messageService.add({
          severity: params.rejectToastSeverity,
          summary: params.rejectToastSummary,
          detail: params.rejectToastDetail
        });
      }
    }));
  }

  showError(): void {
  }

  showDirtyCheckConfirm(): void {
  }

  override ngOnDestroy(): void {
    if (this.ref) {
      this.ref.close();
    }
    super.ngOnDestroy();
  }
}
