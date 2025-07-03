import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate } from 'react-router';
import { handleServerError } from 'app/common/utils';
import { WorkspaceDTO } from 'app/workspace/workspace-model';
import axios from 'axios';
import useDocumentTitle from 'app/common/use-document-title';


export default function WorkspaceList() {
  const { t } = useTranslation();
  useDocumentTitle(t('workspace.list.headline'));

  const [workspaces, setWorkspaces] = useState<WorkspaceDTO[]>([]);
  const navigate = useNavigate();

  const getAllWorkspaces = async () => {
    try {
      const response = await axios.get('/api/workspaces');
      setWorkspaces(response.data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  const confirmDelete = async (workspaceId: number) => {
    if (!confirm(t('delete.confirm'))) {
      return;
    }
    try {
      await axios.delete('/api/workspaces/' + workspaceId);
      navigate('/workspaces', {
            state: {
              msgInfo: t('workspace.delete.success')
            }
          });
      getAllWorkspaces();
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  useEffect(() => {
    getAllWorkspaces();
  }, []);

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('workspace.list.headline')}</h1>
      <div>
        <Link to="/workspaces/add" className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2">{t('workspace.list.createNew')}</Link>
      </div>
    </div>
    {!workspaces || workspaces.length === 0 ? (
    <div>{t('workspace.list.empty')}</div>
    ) : (
    <div className="overflow-x-auto">
      <table className="w-full">
        <thead>
          <tr>
            <th scope="col" className="text-left p-2">{t('workspace.workspaceId.label')}</th>
            <th scope="col" className="text-left p-2">{t('workspace.url.label')}</th>
            <th scope="col" className="text-left p-2">{t('workspace.detail.label')}</th>
            <th></th>
          </tr>
        </thead>
        <tbody className="border-t-2 border-black">
          {workspaces.map((workspace) => (
          <tr key={workspace.workspaceId} className="odd:bg-gray-100">
            <td className="p-2">{workspace.workspaceId}</td>
            <td className="p-2">{workspace.url}</td>
            <td className="p-2">{workspace.detail}</td>
            <td className="p-2">
              <div className="float-right whitespace-nowrap">
                <Link to={'/workspaces/edit/' + workspace.workspaceId} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm">{t('workspace.list.edit')}</Link>
                <span> </span>
                <button type="button" onClick={() => confirmDelete(workspace.workspaceId!)} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm">{t('workspace.list.delete')}</button>
              </div>
            </td>
          </tr>
          ))}
        </tbody>
      </table>
    </div>
    )}
  </>);
}
