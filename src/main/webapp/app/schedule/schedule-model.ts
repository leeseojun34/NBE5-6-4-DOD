export class ScheduleDTO {

  constructor(data:Partial<ScheduleDTO>) {
    Object.assign(this, data);
  }

  id?: number|null;
  startTime?: string|null;
  endTime?: string|null;
  status?: string|null;
  location?: string|null;
  description?: string|null;
  meetingPlatform?: string|null;
  platformUrl?: string|null;
  specificLocation?: string|null;
  event?: number|null;

}
