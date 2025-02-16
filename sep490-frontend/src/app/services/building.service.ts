import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable, map} from 'rxjs';
import {UUID} from '../../types/uuid';
import {AppRoutingConstants} from '../app-routing.constant';
import {
  Building,
  BuildingDetails
} from '../modules/enterprise/models/enterprise.dto';
import {
  SearchCriteriaDto,
  SearchResultDto
} from '../modules/shared/models/models';

@Injectable({
  providedIn: 'root'
})
export class BuildingService {
  constructor(private readonly httpClient: HttpClient) {}

  get createBuildingUrl(): string {
    return `${AppRoutingConstants.ENTERPRISE_API_URL}/${AppRoutingConstants.BUILDING_PATH}`;
  }

  getBuildingDetails(id: UUID): Observable<BuildingDetails> {
    return this.httpClient.get<BuildingDetails>(
      `${AppRoutingConstants.ENTERPRISE_API_URL}/${AppRoutingConstants.BUILDING_PATH}/${id}`
    );
  }

  searchBuildings(
    searchCriteria: SearchCriteriaDto<void>
  ): Observable<SearchResultDto<Building>> {
    return this.httpClient
      .post<
        SearchResultDto<Building>
      >(`${AppRoutingConstants.ENTERPRISE_API_URL}/${AppRoutingConstants.BUILDING_PATH}/search`, searchCriteria)
      .pipe(
        map(result => {
          return {
            totalElements: result.totalElements,
            results: result.results.map(building => {
              return {
                ...building,
                address: 'Avenue Louis-CASAÏ 18, 1209 Genève',
                validFromInclusive: new Date(),
                validToInclusive: new Date(
                  new Date().getTime() + Math.random() * 1e12
                )
              };
            })
          };
        })
      );
  }
}
