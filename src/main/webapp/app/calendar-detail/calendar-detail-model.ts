export class CalendarDetailDTO {

  constructor(data:Partial<CalendarDetailDTO>) {
    Object.assign(this, data);
  }

  id?: number|null;
  title?: string|null;
  startDatetime?: string|null;
  endDatetime?: string|null;
  syncedAt?: string|null;
  isAllDay?: boolean|null;
  externalEtag?: string|null;
  calendar?: number|null;

}
