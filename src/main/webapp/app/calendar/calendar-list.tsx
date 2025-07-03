import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate } from 'react-router';
import { handleServerError } from 'app/common/utils';
import { CalendarDTO } from 'app/calendar/calendar-model';
import axios from 'axios';
import useDocumentTitle from 'app/common/use-document-title';


export default function CalendarList() {
  const { t } = useTranslation();
  useDocumentTitle(t('calendar.list.headline'));

  const [calendars, setCalendars] = useState<CalendarDTO[]>([]);
  const navigate = useNavigate();

  const getAllCalendars = async () => {
    try {
      const response = await axios.get('/api/calendars');
      setCalendars(response.data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  const confirmDelete = async (calendarId: number) => {
    if (!confirm(t('delete.confirm'))) {
      return;
    }
    try {
      await axios.delete('/api/calendars/' + calendarId);
      navigate('/calendars', {
            state: {
              msgInfo: t('calendar.delete.success')
            }
          });
      getAllCalendars();
    } catch (error: any) {
      if (error?.response?.data?.code === 'REFERENCED') {
        const messageParts = error.response.data.message.split(',');
        navigate('/calendars', {
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
    getAllCalendars();
  }, []);

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('calendar.list.headline')}</h1>
      <div>
        <Link to="/calendars/add" className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2">{t('calendar.list.createNew')}</Link>
      </div>
    </div>
    {!calendars || calendars.length === 0 ? (
    <div>{t('calendar.list.empty')}</div>
    ) : (
    <div className="overflow-x-auto">
      <table className="w-full">
        <thead>
          <tr>
            <th scope="col" className="text-left p-2">{t('calendar.calendarId.label')}</th>
            <th scope="col" className="text-left p-2">{t('calendar.calendarName.label')}</th>
            <th scope="col" className="text-left p-2">{t('calendar.synced.label')}</th>
            <th scope="col" className="text-left p-2">{t('calendar.syncedAt.label')}</th>
            <th scope="col" className="text-left p-2">{t('calendar.user.label')}</th>
            <th></th>
          </tr>
        </thead>
        <tbody className="border-t-2 border-black">
          {calendars.map((calendar) => (
          <tr key={calendar.calendarId} className="odd:bg-gray-100">
            <td className="p-2">{calendar.calendarId}</td>
            <td className="p-2">{calendar.calendarName}</td>
            <td className="p-2">{calendar.synced}</td>
            <td className="p-2">{calendar.syncedAt}</td>
            <td className="p-2">{calendar.user}</td>
            <td className="p-2">
              <div className="float-right whitespace-nowrap">
                <Link to={'/calendars/edit/' + calendar.calendarId} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm">{t('calendar.list.edit')}</Link>
                <span> </span>
                <button type="button" onClick={() => confirmDelete(calendar.calendarId!)} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm">{t('calendar.list.delete')}</button>
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
