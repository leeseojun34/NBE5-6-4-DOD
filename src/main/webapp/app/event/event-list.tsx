import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate } from 'react-router';
import { handleServerError } from 'app/common/utils';
import { EventDTO } from 'app/event/event-model';
import axios from 'axios';
import useDocumentTitle from 'app/common/use-document-title';


export default function EventList() {
  const { t } = useTranslation();
  useDocumentTitle(t('event.list.headline'));

  const [events, setEvents] = useState<EventDTO[]>([]);
  const navigate = useNavigate();

  const getAllEvents = async () => {
    try {
      const response = await axios.get('/api/events');
      setEvents(response.data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  const confirmDelete = async (eventId: number) => {
    if (!confirm(t('delete.confirm'))) {
      return;
    }
    try {
      await axios.delete('/api/events/' + eventId);
      navigate('/events', {
            state: {
              msgInfo: t('event.delete.success')
            }
          });
      getAllEvents();
    } catch (error: any) {
      if (error?.response?.data?.code === 'REFERENCED') {
        const messageParts = error.response.data.message.split(',');
        navigate('/events', {
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
    getAllEvents();
  }, []);

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('event.list.headline')}</h1>
      <div>
        <Link to="/events/add" className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2">{t('event.list.createNew')}</Link>
      </div>
    </div>
    {!events || events.length === 0 ? (
    <div>{t('event.list.empty')}</div>
    ) : (
    <div className="overflow-x-auto">
      <table className="w-full">
        <thead>
          <tr>
            <th scope="col" className="text-left p-2">{t('event.eventId.label')}</th>
            <th scope="col" className="text-left p-2">{t('event.title.label')}</th>
            <th scope="col" className="text-left p-2">{t('event.creator.label')}</th>
            <th scope="col" className="text-left p-2">{t('event.meetingType.label')}</th>
            <th scope="col" className="text-left p-2">{t('event.maxMember.label')}</th>
            <th scope="col" className="text-left p-2">{t('event.group.label')}</th>
            <th></th>
          </tr>
        </thead>
        <tbody className="border-t-2 border-black">
          {events.map((event) => (
          <tr key={event.eventId} className="odd:bg-gray-100">
            <td className="p-2">{event.eventId}</td>
            <td className="p-2">{event.title}</td>
            <td className="p-2">{event.creator}</td>
            <td className="p-2">{event.meetingType}</td>
            <td className="p-2">{event.maxMember}</td>
            <td className="p-2">{event.group}</td>
            <td className="p-2">
              <div className="float-right whitespace-nowrap">
                <Link to={'/events/edit/' + event.eventId} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm">{t('event.list.edit')}</Link>
                <span> </span>
                <button type="button" onClick={() => confirmDelete(event.eventId!)} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm">{t('event.list.delete')}</button>
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
