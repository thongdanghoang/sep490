import {BaseDTO} from '../../shared/models/models';
import {Role} from '../../shared/models/role-names.constant';
import {PermissionRole} from '../enums/building-permission-role.enum';
import {UserScope} from '../enums/user-scope.enum';

export interface NewEnterpriseUserDTO {
  email: string;
  firstName: string;
  lastName: string;
  buildingPermissionRole: string;
  scope: string;
  buildingIds: string[];
}

export interface BuildingDTO {
  id: string;
  name: string;
}

export interface EnterpriseUserDTO extends BaseDTO {
  email: string;
  name: string;
  role: Role;
  scope: UserScope;
}

export interface EnterpriseUserDetailDTO extends BaseDTO {
  email: string;
  firstName: string;
  lastName: string;
  permissionRole: PermissionRole;
  scope: UserScope;
  createdDate: Date;
  buildingIds: string[];
}
