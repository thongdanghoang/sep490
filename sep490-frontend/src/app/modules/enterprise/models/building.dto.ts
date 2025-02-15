import {BaseDTO} from '../../shared/models/models';

export interface Building extends BaseDTO {
  name: string;
  address?: string;
  validFromInclusive?: Date;
  validToInclusive?: Date;
  activated?: boolean;
}
