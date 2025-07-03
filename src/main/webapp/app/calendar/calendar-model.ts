export class CalendarDTO {

  constructor(data:Partial<CalendarDTO>) {
    Object.assign(this, data);
  }

  calendarId?: number|null;
  calendarName?: string|null;
  synced?: string|null;
  syncedAt?: string|null;
  user?: string|null;

}
