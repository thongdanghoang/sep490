import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {SearchCriteriaDto, SearchResultDto} from '../../shared/models/models';
import {Observable} from 'rxjs';
import {AppRoutingConstants} from '../../../app-routing.constant';
import {PaymentDTO} from '../models/payment';
import {PaymentCriteria} from '../components/payment/payment.component';

@Injectable()
export class PaymentService {
  constructor(private readonly httpClient: HttpClient) {}
  public getPayments(
    criteria: SearchCriteriaDto<PaymentCriteria>
  ): Observable<SearchResultDto<PaymentDTO>> {
    return this.httpClient.post<SearchResultDto<PaymentDTO>>(
      `${AppRoutingConstants.ENTERPRISE_API_URL}/payment/search`,
      criteria
    );
  }
}
