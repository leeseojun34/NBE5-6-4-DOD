export class WorkspaceDTO {

  constructor(data:Partial<WorkspaceDTO>) {
    Object.assign(this, data);
  }

  workspaceId?: number|null;
  url?: string|null;
  detail?: number|null;

}
