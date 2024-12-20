export interface SearchPageDto {
  offset: number;
  limit: number;
}

export interface SortDto {
  colId: string;
  sort: 'ASC' | 'DESC';
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

export interface KeyValue<T = any> {
  [key: string]: T;
}

export interface DateRange<D = Date | string> {
  from: D;
  to: D;
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

export interface AutoCompleteSearchDto<T> {
  value: T;
  searchText: string;
}

export interface SelectableItem<V> {
  label: string;
  value: V;
  disabled?: boolean;
}

export type AutoCompleteDto<V> = SelectableItem<V>;
export type DropdownItem<V> = SelectableItem<V>;
