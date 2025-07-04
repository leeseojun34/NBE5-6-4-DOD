import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate } from 'react-router';
import { handleServerError } from 'app/common/utils';
import { GroupMemberDTO } from 'app/group-member/group-member-model';
import axios from 'axios';
import useDocumentTitle from 'app/common/use-document-title';


export default function GroupMemberList() {
  const { t } = useTranslation();
  useDocumentTitle(t('groupMember.list.headline'));

  const [groupMembers, setGroupMembers] = useState<GroupMemberDTO[]>([]);
  const navigate = useNavigate();

  const getAllGroupMembers = async () => {
    try {
      const response = await axios.get('/api/groupMembers');
      setGroupMembers(response.data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  const confirmDelete = async (id: number) => {
    if (!confirm(t('delete.confirm'))) {
      return;
    }
    try {
      await axios.delete('/api/groupMembers/' + id);
      navigate('/groupMembers', {
            state: {
              msgInfo: t('groupMember.delete.success')
            }
          });
      getAllGroupMembers();
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  useEffect(() => {
    getAllGroupMembers();
  }, []);

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('groupMember.list.headline')}</h1>
      <div>
        <Link to="/groupMembers/add" className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2">{t('groupMember.list.createNew')}</Link>
      </div>
    </div>
    {!groupMembers || groupMembers.length === 0 ? (
    <div>{t('groupMember.list.empty')}</div>
    ) : (
    <div className="overflow-x-auto">
      <table className="w-full">
        <thead>
          <tr>
            <th scope="col" className="text-left p-2">{t('groupMember.id.label')}</th>
            <th scope="col" className="text-left p-2">{t('groupMember.role.label')}</th>
            <th scope="col" className="text-left p-2">{t('groupMember.member.label')}</th>
            <th scope="col" className="text-left p-2">{t('groupMember.group.label')}</th>
            <th></th>
          </tr>
        </thead>
        <tbody className="border-t-2 border-black">
          {groupMembers.map((groupMember) => (
          <tr key={groupMember.id} className="odd:bg-gray-100">
            <td className="p-2">{groupMember.id}</td>
            <td className="p-2">{groupMember.role}</td>
            <td className="p-2">{groupMember.member}</td>
            <td className="p-2">{groupMember.group}</td>
            <td className="p-2">
              <div className="float-right whitespace-nowrap">
                <Link to={'/groupMembers/edit/' + groupMember.id} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm">{t('groupMember.list.edit')}</Link>
                <span> </span>
                <button type="button" onClick={() => confirmDelete(groupMember.id!)} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm">{t('groupMember.list.delete')}</button>
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
