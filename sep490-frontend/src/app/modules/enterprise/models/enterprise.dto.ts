import {BaseDTO} from '../../shared/models/models';

export interface Building extends BaseDTO {
  name: string;
  address?: string;
  validFromInclusive?: Date;
  validToInclusive?: Date;
  activated?: boolean;
}

export interface BuildingDetails {
  name: string;
  numberOfDevices: number;
}

export interface CreditPackage extends BaseDTO {
  numberOfCredits: number;
  price: number;
}
