export class CalendarDetailDTO {

  constructor(data:Partial<CalendarDetailDTO>) {
    Object.assign(this, data);
  }

  calendarDetailId?: number|null;
  title?: string|null;
  startDatetime?: string|null;
  endDatetime?: string|null;
  syncedAt?: string|null;
  calendar?: number|null;

}
