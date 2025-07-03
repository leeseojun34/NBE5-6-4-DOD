export class ScheduleUserDTO {

  constructor(data:Partial<ScheduleUserDTO>) {
    Object.assign(this, data);
  }

  scheduleUserId?: number|null;
  role?: string|null;
  user?: string|null;
  schedule?: number|null;

}
