export interface SearchPageDto {
  offset: number;
  limit: number;
}

export interface SortDto {
  colId: string;
  sort: string;
}

export interface SearchCriteriaDto<C> {
  page: SearchPageDto;
  criteria: C;
  sort: SortDto;
}

export interface SearchResultDto<R> {
  results: R[];
  total: number;
}

export interface KeyValue {
  [key: string]: any;
}

export interface DateRange<D = Date | string> {
  from: D;
  to: D;
}

export interface DropdownItem<V> {
  display: string;
  value: V;
  disabled?: boolean;
}

export interface BusinessErrorParam {
  key: string;
  value: string;
}

export interface BusinessErrorResponse {
  correlationId: string;
  field: string;
  i18nKey: string;
  args: BusinessErrorParam[];
}

export interface AutoCompleteDto<T> {
  value: T;
  label: string;
  disabled?: boolean;
}

export interface AutoCompleteSearchDto<T> {
  value: T;
  searchText: string;
}
