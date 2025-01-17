import {Role} from './role-names.constant';

export interface EnterpriseUserDTO {
  id: string;
  email: string;
  name: string;
  role: Role;
  scope: UserScope;
}

export enum UserScope {
  BUILDING = 'building',
  ENTERPRISE = 'enterprise'
}
