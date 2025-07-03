import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate } from 'react-router';
import { handleServerError } from 'app/common/utils';
import { EventUserDTO } from 'app/event-user/event-user-model';
import axios from 'axios';
import useDocumentTitle from 'app/common/use-document-title';


export default function EventUserList() {
  const { t } = useTranslation();
  useDocumentTitle(t('eventUser.list.headline'));

  const [eventUsers, setEventUsers] = useState<EventUserDTO[]>([]);
  const navigate = useNavigate();

  const getAllEventUsers = async () => {
    try {
      const response = await axios.get('/api/eventUsers');
      setEventUsers(response.data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  const confirmDelete = async (eventUserId: number) => {
    if (!confirm(t('delete.confirm'))) {
      return;
    }
    try {
      await axios.delete('/api/eventUsers/' + eventUserId);
      navigate('/eventUsers', {
            state: {
              msgInfo: t('eventUser.delete.success')
            }
          });
      getAllEventUsers();
    } catch (error: any) {
      if (error?.response?.data?.code === 'REFERENCED') {
        const messageParts = error.response.data.message.split(',');
        navigate('/eventUsers', {
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
    getAllEventUsers();
  }, []);

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('eventUser.list.headline')}</h1>
      <div>
        <Link to="/eventUsers/add" className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2">{t('eventUser.list.createNew')}</Link>
      </div>
    </div>
    {!eventUsers || eventUsers.length === 0 ? (
    <div>{t('eventUser.list.empty')}</div>
    ) : (
    <div className="overflow-x-auto">
      <table className="w-full">
        <thead>
          <tr>
            <th scope="col" className="text-left p-2">{t('eventUser.eventUserId.label')}</th>
            <th scope="col" className="text-left p-2">{t('eventUser.user.label')}</th>
            <th scope="col" className="text-left p-2">{t('eventUser.event.label')}</th>
            <th></th>
          </tr>
        </thead>
        <tbody className="border-t-2 border-black">
          {eventUsers.map((eventUser) => (
          <tr key={eventUser.eventUserId} className="odd:bg-gray-100">
            <td className="p-2">{eventUser.eventUserId}</td>
            <td className="p-2">{eventUser.user}</td>
            <td className="p-2">{eventUser.event}</td>
            <td className="p-2">
              <div className="float-right whitespace-nowrap">
                <Link to={'/eventUsers/edit/' + eventUser.eventUserId} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm">{t('eventUser.list.edit')}</Link>
                <span> </span>
                <button type="button" onClick={() => confirmDelete(eventUser.eventUserId!)} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm">{t('eventUser.list.delete')}</button>
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
