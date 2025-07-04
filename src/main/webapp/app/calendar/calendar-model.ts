export class CalendarDTO {

  constructor(data:Partial<CalendarDTO>) {
    Object.assign(this, data);
  }

  id?: number|null;
  name?: string|null;
  synced?: boolean|null;
  syncedAt?: string|null;
  member?: string|null;

}
