export class EventDTO {

  constructor(data:Partial<EventDTO>) {
    Object.assign(this, data);
  }

  id?: number|null;
  title?: string|null;
  description?: string|null;
  meetingType?: string|null;
  maxMember?: number|null;
  group?: number|null;

}
