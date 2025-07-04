export class EventMemberDTO {

  constructor(data:Partial<EventMemberDTO>) {
    Object.assign(this, data);
  }

  id?: number|null;
  role?: string|null;
  member?: string|null;
  event?: number|null;

}
