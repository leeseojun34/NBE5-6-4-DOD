export class EventUserDTO {

  constructor(data:Partial<EventUserDTO>) {
    Object.assign(this, data);
  }

  eventUserId?: number|null;
  user?: string|null;
  event?: number|null;

}
