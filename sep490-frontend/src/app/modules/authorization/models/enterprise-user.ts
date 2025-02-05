import {UUID} from '../../../../types/uuid';
import {BaseDTO} from '../../shared/models/models';
import {BuildingPermissionRole} from '../enums/building-permission-role';
import {UserRole} from '../enums/role-names';
import {UserScope} from '../enums/user-scope';

export interface EnterpriseUserDetails extends BaseDTO {
  email: string;
  firstName: string;
  lastName: string;
  role: keyof typeof UserRole;
  scope: keyof typeof UserScope;
  buildingPermissions: BuildingPermission[];
}

export interface EnterpriseUser extends BaseDTO {
  email: string;
  name: string;
  role: UserRole;
  scope: UserScope;
  buildingPermissions: BuildingPermission[];
}

export interface Building {
  id: UUID;
  name: string;
}

export interface BuildingPermission {
  buildingId?: UUID;
  role: keyof typeof BuildingPermissionRole;
}
