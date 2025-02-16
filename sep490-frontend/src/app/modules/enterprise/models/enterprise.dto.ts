import {BaseDTO} from '../../shared/models/models';

export interface Building extends BaseDTO {
  name: string;
  address?: string;
  validFromInclusive?: Date;
  validToInclusive?: Date;
  activated?: boolean;
  latitude: number;
  longitude: number;
}

export interface BuildingDetails extends BaseDTO {
  name: string;
  numberOfDevices: number;
  latitude: number;
  longitude: number;
}

export interface CreditPackage extends BaseDTO {
  numberOfCredits: number;
  price: number;
}
