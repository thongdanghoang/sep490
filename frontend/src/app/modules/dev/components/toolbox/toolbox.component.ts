import {Component} from '@angular/core';
import {ModalProvider} from '../../../shared/services/modal-provider';

@Component({
  selector: 'app-toolbox',
  templateUrl: './toolbox.component.html',
  styleUrl: './toolbox.component.scss'
})
export class ToolboxComponent {

  constructor(private readonly modalProvider: ModalProvider) {
  }

  confirm(): void {
    this.modalProvider.showConfirm({
      message: 'Are you sure that you want to proceed?',
      header: 'Confirmation',
      icon: 'pi pi-exclamation-triangle',
      acceptIcon: "none",
      acceptLabel: 'Accept',
      rejectIcon: "none",
      rejectButtonStyleClass: "p-button-text",
      rejectLabel: 'Reject',
      acceptToastDetail: 'You have accepted',
      acceptToastSeverity: 'info',
      acceptToastSummary: 'Confirmed',
      rejectToastDetail: 'You have rejected',
      rejectToastSeverity: 'error',
      rejectToastSummary: 'Rejected'
    });
  }

  confirmDelete() {
    this.modalProvider.showConfirm({
      message: 'Do you want to delete this record?',
      header: 'Delete Confirmation',
      icon: 'pi pi-info-circle',
      acceptButtonStyleClass: "p-button-danger p-button-text",
      rejectButtonStyleClass: "p-button-text p-button-text",
      acceptIcon: "none",
      acceptLabel: 'Yes',
      rejectIcon: "none",
      rejectLabel: 'No',
      acceptToastDetail: 'Record deleted',
      acceptToastSeverity: 'info',
      acceptToastSummary: 'Confirmed',
      rejectToastDetail: 'You have rejected',
      rejectToastSeverity: 'error',
      rejectToastSummary: 'Rejected'
    })
  }
}
