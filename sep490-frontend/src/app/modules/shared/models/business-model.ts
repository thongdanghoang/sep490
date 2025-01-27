import {BaseDTO} from './models';
import {Role} from './role-names.constant';

export interface EnterpriseUserDTO extends BaseDTO {
  email: string;
  name: string;
  role: Role;
  scope: UserScope;
}

export interface EnterpriseUserDetailDTO {
  id: string;
  email: string;
  firstName: string;
  lastName: string;
  permissionRole: string;
  scope: string;
  createdDate: Date;
  buildingIds: string[];
  version: number;
}

export enum UserScope {
  BUILDING,
  ENTERPRISE
}

export enum PermissionRole {
  MANAGER,
  AUDITOR,
  STAFF
}

export interface NewEnterpriseUserDTO {
  email: string;
  firstName: string;
  lastName: string;
  permissionRole: string;
  scope: string;
  buildingIds: string[];
}

export interface BuildingDTO {
  id: string;
  name: string;
}
