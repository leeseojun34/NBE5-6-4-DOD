export class GroupUserDTO {

  constructor(data:Partial<GroupUserDTO>) {
    Object.assign(this, data);
  }

  groupUserId?: number|null;
  groupRole?: string|null;
  user?: string|null;
  group?: number|null;

}
