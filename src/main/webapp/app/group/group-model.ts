export class GroupDTO {

  constructor(data:Partial<GroupDTO>) {
    Object.assign(this, data);
  }

  id?: number|null;
  name?: string|null;
  description?: string|null;
  isGrouped?: boolean|null;

}
