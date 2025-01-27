import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {UUID} from '../../../../types/uuid';
import {AppRoutingConstants} from '../../../app-routing.constant';
import {EnterpriseUserDTO} from '../../shared/models/business-model';
import {SearchCriteriaDto, SearchResultDto} from '../../shared/models/models';
import {UserCriteria} from '../components/users/users.component';

@Injectable()
export class UserService {
  constructor(private readonly httpClient: HttpClient) {}

  public getUsers(
    criteria: SearchCriteriaDto<UserCriteria>
  ): Observable<SearchResultDto<EnterpriseUserDTO>> {
    return this.httpClient.post<SearchResultDto<EnterpriseUserDTO>>(
      `${AppRoutingConstants.IDP_API_URL}/enterprise-user/search`,
      criteria
    );
  }

  public createUserURL(): string {
    return `${AppRoutingConstants.IDP_API_URL}/enterprise-user/create`;
  }

  public deleteUsers(userIds: UUID[]): Observable<void> {
    return this.httpClient.post<void>(
      `${AppRoutingConstants.IDP_API_URL}/enterprise-user/delete`,
      userIds
    );
  }
}
