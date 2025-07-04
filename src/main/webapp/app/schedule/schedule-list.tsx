import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate } from 'react-router';
import { handleServerError } from 'app/common/utils';
import { ScheduleDTO } from 'app/schedule/schedule-model';
import axios from 'axios';
import useDocumentTitle from 'app/common/use-document-title';


export default function ScheduleList() {
  const { t } = useTranslation();
  useDocumentTitle(t('schedule.list.headline'));

  const [schedules, setSchedules] = useState<ScheduleDTO[]>([]);
  const navigate = useNavigate();

  const getAllSchedules = async () => {
    try {
      const response = await axios.get('/api/schedules');
      setSchedules(response.data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  const confirmDelete = async (id: number) => {
    if (!confirm(t('delete.confirm'))) {
      return;
    }
    try {
      await axios.delete('/api/schedules/' + id);
      navigate('/schedules', {
            state: {
              msgInfo: t('schedule.delete.success')
            }
          });
      getAllSchedules();
    } catch (error: any) {
      if (error?.response?.data?.code === 'REFERENCED') {
        const messageParts = error.response.data.message.split(',');
        navigate('/schedules', {
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
    getAllSchedules();
  }, []);

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('schedule.list.headline')}</h1>
      <div>
        <Link to="/schedules/add" className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2">{t('schedule.list.createNew')}</Link>
      </div>
    </div>
    {!schedules || schedules.length === 0 ? (
    <div>{t('schedule.list.empty')}</div>
    ) : (
    <div className="overflow-x-auto">
      <table className="w-full">
        <thead>
          <tr>
            <th scope="col" className="text-left p-2">{t('schedule.id.label')}</th>
            <th scope="col" className="text-left p-2">{t('schedule.startTime.label')}</th>
            <th scope="col" className="text-left p-2">{t('schedule.endTime.label')}</th>
            <th scope="col" className="text-left p-2">{t('schedule.status.label')}</th>
            <th scope="col" className="text-left p-2">{t('schedule.location.label')}</th>
            <th scope="col" className="text-left p-2">{t('schedule.meetingPlatform.label')}</th>
            <th scope="col" className="text-left p-2">{t('schedule.specificLocation.label')}</th>
            <th scope="col" className="text-left p-2">{t('schedule.event.label')}</th>
            <th></th>
          </tr>
        </thead>
        <tbody className="border-t-2 border-black">
          {schedules.map((schedule) => (
          <tr key={schedule.id} className="odd:bg-gray-100">
            <td className="p-2">{schedule.id}</td>
            <td className="p-2">{schedule.startTime}</td>
            <td className="p-2">{schedule.endTime}</td>
            <td className="p-2">{schedule.status}</td>
            <td className="p-2">{schedule.location}</td>
            <td className="p-2">{schedule.meetingPlatform}</td>
            <td className="p-2">{schedule.specificLocation}</td>
            <td className="p-2">{schedule.event}</td>
            <td className="p-2">
              <div className="float-right whitespace-nowrap">
                <Link to={'/schedules/edit/' + schedule.id} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm">{t('schedule.list.edit')}</Link>
                <span> </span>
                <button type="button" onClick={() => confirmDelete(schedule.id!)} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm">{t('schedule.list.delete')}</button>
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
