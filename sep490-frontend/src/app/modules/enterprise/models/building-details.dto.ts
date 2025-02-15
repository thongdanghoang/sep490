import {BaseDTO} from '../../shared/models/models';

export interface BuildingDetails extends BaseDTO {
  name: string;
  numberOfDevices: number;
}
