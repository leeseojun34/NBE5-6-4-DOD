export class TempScheduleDTO {

  constructor(data:Partial<TempScheduleDTO>) {
    Object.assign(this, data);
  }

  id?: number|null;
  startTime?: string|null;
  endTime?: string|null;
  eventMember?: number|null;

}
