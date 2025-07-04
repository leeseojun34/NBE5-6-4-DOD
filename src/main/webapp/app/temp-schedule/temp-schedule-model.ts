export class TempScheduleDTO {

  constructor(data:Partial<TempScheduleDTO>) {
    Object.assign(this, data);
  }

  id?: number|null;
  date?: string|null;
  timeBit?: number|null;
  eventMember?: number|null;

}
