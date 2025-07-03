export class TempScheduleDTO {

  constructor(data:Partial<TempScheduleDTO>) {
    Object.assign(this, data);
  }

  tempScheduleId?: number|null;
  startTime?: string|null;
  endTime?: string|null;
  eventUser?: number|null;

}
