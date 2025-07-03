import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate } from 'react-router';
import { handleServerError } from 'app/common/utils';
import { GroupUserDTO } from 'app/group-user/group-user-model';
import axios from 'axios';
import useDocumentTitle from 'app/common/use-document-title';


export default function GroupUserList() {
  const { t } = useTranslation();
  useDocumentTitle(t('groupUser.list.headline'));

  const [groupUsers, setGroupUsers] = useState<GroupUserDTO[]>([]);
  const navigate = useNavigate();

  const getAllGroupUsers = async () => {
    try {
      const response = await axios.get('/api/groupUsers');
      setGroupUsers(response.data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  const confirmDelete = async (groupUserId: number) => {
    if (!confirm(t('delete.confirm'))) {
      return;
    }
    try {
      await axios.delete('/api/groupUsers/' + groupUserId);
      navigate('/groupUsers', {
            state: {
              msgInfo: t('groupUser.delete.success')
            }
          });
      getAllGroupUsers();
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  useEffect(() => {
    getAllGroupUsers();
  }, []);

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('groupUser.list.headline')}</h1>
      <div>
        <Link to="/groupUsers/add" className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2">{t('groupUser.list.createNew')}</Link>
      </div>
    </div>
    {!groupUsers || groupUsers.length === 0 ? (
    <div>{t('groupUser.list.empty')}</div>
    ) : (
    <div className="overflow-x-auto">
      <table className="w-full">
        <thead>
          <tr>
            <th scope="col" className="text-left p-2">{t('groupUser.groupUserId.label')}</th>
            <th scope="col" className="text-left p-2">{t('groupUser.groupRole.label')}</th>
            <th scope="col" className="text-left p-2">{t('groupUser.user.label')}</th>
            <th scope="col" className="text-left p-2">{t('groupUser.group.label')}</th>
            <th></th>
          </tr>
        </thead>
        <tbody className="border-t-2 border-black">
          {groupUsers.map((groupUser) => (
          <tr key={groupUser.groupUserId} className="odd:bg-gray-100">
            <td className="p-2">{groupUser.groupUserId}</td>
            <td className="p-2">{groupUser.groupRole}</td>
            <td className="p-2">{groupUser.user}</td>
            <td className="p-2">{groupUser.group}</td>
            <td className="p-2">
              <div className="float-right whitespace-nowrap">
                <Link to={'/groupUsers/edit/' + groupUser.groupUserId} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm">{t('groupUser.list.edit')}</Link>
                <span> </span>
                <button type="button" onClick={() => confirmDelete(groupUser.groupUserId!)} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm">{t('groupUser.list.delete')}</button>
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
