export class MeetingDTO {

  constructor(data:Partial<MeetingDTO>) {
    Object.assign(this, data);
  }

  meetingId?: number|null;
  meetingPlatform?: string|null;
  platformUrl?: string|null;
  schedule?: number|null;

}
