import {Component, Injector} from '@angular/core';
import {ModalProvider} from '../../../shared/services/modal-provider';
import {MessageService} from 'primeng/api';

@Component({
  selector: 'app-toolbox',
  templateUrl: './toolbox.component.html',
  styleUrl: './toolbox.component.scss'
})
export class ToolboxComponent {

  private readonly modalProvider;
  private readonly messageService;

  constructor(private readonly injector: Injector) {
    this.modalProvider = this.injector.get(ModalProvider);
    this.messageService = this.injector.get(MessageService);
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
      rejectLabel: 'Reject'
    }).subscribe((result: boolean) => {
      if (result) {
        this.messageService.add({severity: 'success', summary: 'Confirmed', detail: 'You have accepted'});
      } else {
        this.messageService.add({severity: 'error', summary: 'Rejected', detail: 'You have rejected'});
      }
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
      rejectLabel: 'No'
    }).subscribe((result: boolean) => {
      if (result) {
        this.messageService.add({severity: 'success', summary: 'Confirmed', detail: 'Record deleted'});
      } else {
        this.messageService.add({severity: 'error', summary: 'Rejected', detail: 'You have rejected'});
      }
    });
  }
}
