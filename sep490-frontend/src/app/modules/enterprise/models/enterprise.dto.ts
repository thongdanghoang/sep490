import {BaseDTO} from '../../shared/models/models';
import {UUID} from '../../../../types/uuid';

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

export enum CreditConvertType {
  MONTH,
  DEVICE
}

export interface CreditConvertRatio extends BaseDTO {
  ratio: number;
  convertType: keyof typeof CreditConvertType;
}

export interface SubscribeRequest extends BaseDTO {
  buildingId: UUID;
  months: number;
  numberOfDevices: number;
  monthRatio: number;
  deviceRatio: number;
  type: keyof typeof CreditConvertType;
}

export interface Subscription extends BaseDTO {
  numberOfMonths: number;
  numberOfDevices: number;
  idBuilding: UUID;
}
