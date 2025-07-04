export class GroupMemberDTO {

  constructor(data:Partial<GroupMemberDTO>) {
    Object.assign(this, data);
  }

  id?: number|null;
  role?: string|null;
  member?: string|null;
  group?: number|null;

}
