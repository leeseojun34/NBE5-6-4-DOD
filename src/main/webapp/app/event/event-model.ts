export class EventDTO {

  constructor(data:Partial<EventDTO>) {
    Object.assign(this, data);
  }

  eventId?: number|null;
  title?: string|null;
  description?: string|null;
  creator?: number|null;
  meetingType?: string|null;
  maxMember?: number|null;
  group?: number|null;

}
