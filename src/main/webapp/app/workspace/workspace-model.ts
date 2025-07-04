export class WorkspaceDTO {

  constructor(data:Partial<WorkspaceDTO>) {
    Object.assign(this, data);
  }

  id?: number|null;
  url?: string|null;
  schedule?: number|null;

}
