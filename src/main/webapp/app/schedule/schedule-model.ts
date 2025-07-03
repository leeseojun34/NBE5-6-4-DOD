export class ScheduleDTO {

  constructor(data:Partial<ScheduleDTO>) {
    Object.assign(this, data);
  }

  scheduleId?: number|null;
  startTime?: string|null;
  endTime?: string|null;
  status?: string|null;
  event?: number|null;

}
