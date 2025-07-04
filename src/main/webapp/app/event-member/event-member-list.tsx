import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate } from 'react-router';
import { handleServerError } from 'app/common/utils';
import { EventMemberDTO } from 'app/event-member/event-member-model';
import axios from 'axios';
import useDocumentTitle from 'app/common/use-document-title';


export default function EventMemberList() {
  const { t } = useTranslation();
  useDocumentTitle(t('eventMember.list.headline'));

  const [eventMembers, setEventMembers] = useState<EventMemberDTO[]>([]);
  const navigate = useNavigate();

  const getAllEventMembers = async () => {
    try {
      const response = await axios.get('/api/eventMembers');
      setEventMembers(response.data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  const confirmDelete = async (id: number) => {
    if (!confirm(t('delete.confirm'))) {
      return;
    }
    try {
      await axios.delete('/api/eventMembers/' + id);
      navigate('/eventMembers', {
            state: {
              msgInfo: t('eventMember.delete.success')
            }
          });
      getAllEventMembers();
    } catch (error: any) {
      if (error?.response?.data?.code === 'REFERENCED') {
        const messageParts = error.response.data.message.split(',');
        navigate('/eventMembers', {
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
    getAllEventMembers();
  }, []);

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('eventMember.list.headline')}</h1>
      <div>
        <Link to="/eventMembers/add" className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2">{t('eventMember.list.createNew')}</Link>
      </div>
    </div>
    {!eventMembers || eventMembers.length === 0 ? (
    <div>{t('eventMember.list.empty')}</div>
    ) : (
    <div className="overflow-x-auto">
      <table className="w-full">
        <thead>
          <tr>
            <th scope="col" className="text-left p-2">{t('eventMember.id.label')}</th>
            <th scope="col" className="text-left p-2">{t('eventMember.role.label')}</th>
            <th scope="col" className="text-left p-2">{t('eventMember.member.label')}</th>
            <th scope="col" className="text-left p-2">{t('eventMember.event.label')}</th>
            <th></th>
          </tr>
        </thead>
        <tbody className="border-t-2 border-black">
          {eventMembers.map((eventMember) => (
          <tr key={eventMember.id} className="odd:bg-gray-100">
            <td className="p-2">{eventMember.id}</td>
            <td className="p-2">{eventMember.role}</td>
            <td className="p-2">{eventMember.member}</td>
            <td className="p-2">{eventMember.event}</td>
            <td className="p-2">
              <div className="float-right whitespace-nowrap">
                <Link to={'/eventMembers/edit/' + eventMember.id} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm">{t('eventMember.list.edit')}</Link>
                <span> </span>
                <button type="button" onClick={() => confirmDelete(eventMember.id!)} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm">{t('eventMember.list.delete')}</button>
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
