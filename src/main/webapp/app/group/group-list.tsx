import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate } from 'react-router';
import { handleServerError } from 'app/common/utils';
import { GroupDTO } from 'app/group/group-model';
import axios from 'axios';
import useDocumentTitle from 'app/common/use-document-title';


export default function GroupList() {
  const { t } = useTranslation();
  useDocumentTitle(t('group.list.headline'));

  const [groups, setGroups] = useState<GroupDTO[]>([]);
  const navigate = useNavigate();

  const getAllGroups = async () => {
    try {
      const response = await axios.get('/api/groups');
      setGroups(response.data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  const confirmDelete = async (id: number) => {
    if (!confirm(t('delete.confirm'))) {
      return;
    }
    try {
      await axios.delete('/api/groups/' + id);
      navigate('/groups', {
            state: {
              msgInfo: t('group.delete.success')
            }
          });
      getAllGroups();
    } catch (error: any) {
      if (error?.response?.data?.code === 'REFERENCED') {
        const messageParts = error.response.data.message.split(',');
        navigate('/groups', {
              state: {
                msgError: t(messageParts[0]!, { id: messageParts[1]! })
              }
            });
        return;
      }
      handleServerError(error, navigate);
    }
  };

  useEffect(() => {
    getAllGroups();
  }, []);

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('group.list.headline')}</h1>
      <div>
        <Link to="/groups/add" className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2">{t('group.list.createNew')}</Link>
      </div>
    </div>
    {!groups || groups.length === 0 ? (
    <div>{t('group.list.empty')}</div>
    ) : (
    <div className="overflow-x-auto">
      <table className="w-full">
        <thead>
          <tr>
            <th scope="col" className="text-left p-2">{t('group.id.label')}</th>
            <th scope="col" className="text-left p-2">{t('group.name.label')}</th>
            <th scope="col" className="text-left p-2">{t('group.description.label')}</th>
            <th scope="col" className="text-left p-2">{t('group.isGrouped.label')}</th>
            <th></th>
          </tr>
        </thead>
        <tbody className="border-t-2 border-black">
          {groups.map((group) => (
          <tr key={group.id} className="odd:bg-gray-100">
            <td className="p-2">{group.id}</td>
            <td className="p-2">{group.name}</td>
            <td className="p-2">{group.description}</td>
            <td className="p-2">{group.isGrouped?.toString()}</td>
            <td className="p-2">
              <div className="float-right whitespace-nowrap">
                <Link to={'/groups/edit/' + group.id} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm">{t('group.list.edit')}</Link>
                <span> </span>
                <button type="button" onClick={() => confirmDelete(group.id!)} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm">{t('group.list.delete')}</button>
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
