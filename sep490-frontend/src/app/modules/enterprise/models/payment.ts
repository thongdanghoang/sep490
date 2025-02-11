import {BaseDTO} from '../../shared/models/models';
import {PaymentStatus} from '../enums/payment-status';

export interface PaymentDTO extends BaseDTO {
  createdDate: Date;
  status: PaymentStatus;
  amount: bigint;
}
