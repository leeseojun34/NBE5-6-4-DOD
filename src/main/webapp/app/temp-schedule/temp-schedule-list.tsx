import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate } from 'react-router';
import { handleServerError } from 'app/common/utils';
import { TempScheduleDTO } from 'app/temp-schedule/temp-schedule-model';
import axios from 'axios';
import useDocumentTitle from 'app/common/use-document-title';


export default function TempScheduleList() {
  const { t } = useTranslation();
  useDocumentTitle(t('tempSchedule.list.headline'));

  const [tempSchedules, setTempSchedules] = useState<TempScheduleDTO[]>([]);
  const navigate = useNavigate();

  const getAllTempSchedules = async () => {
    try {
      const response = await axios.get('/api/tempSchedules');
      setTempSchedules(response.data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  const confirmDelete = async (tempScheduleId: number) => {
    if (!confirm(t('delete.confirm'))) {
      return;
    }
    try {
      await axios.delete('/api/tempSchedules/' + tempScheduleId);
      navigate('/tempSchedules', {
            state: {
              msgInfo: t('tempSchedule.delete.success')
            }
          });
      getAllTempSchedules();
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  useEffect(() => {
    getAllTempSchedules();
  }, []);

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('tempSchedule.list.headline')}</h1>
      <div>
        <Link to="/tempSchedules/add" className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2">{t('tempSchedule.list.createNew')}</Link>
      </div>
    </div>
    {!tempSchedules || tempSchedules.length === 0 ? (
    <div>{t('tempSchedule.list.empty')}</div>
    ) : (
    <div className="overflow-x-auto">
      <table className="w-full">
        <thead>
          <tr>
            <th scope="col" className="text-left p-2">{t('tempSchedule.tempScheduleId.label')}</th>
            <th scope="col" className="text-left p-2">{t('tempSchedule.startTime.label')}</th>
            <th scope="col" className="text-left p-2">{t('tempSchedule.endTime.label')}</th>
            <th scope="col" className="text-left p-2">{t('tempSchedule.eventUser.label')}</th>
            <th></th>
          </tr>
        </thead>
        <tbody className="border-t-2 border-black">
          {tempSchedules.map((tempSchedule) => (
          <tr key={tempSchedule.tempScheduleId} className="odd:bg-gray-100">
            <td className="p-2">{tempSchedule.tempScheduleId}</td>
            <td className="p-2">{tempSchedule.startTime}</td>
            <td className="p-2">{tempSchedule.endTime}</td>
            <td className="p-2">{tempSchedule.eventUser}</td>
            <td className="p-2">
              <div className="float-right whitespace-nowrap">
                <Link to={'/tempSchedules/edit/' + tempSchedule.tempScheduleId} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm">{t('tempSchedule.list.edit')}</Link>
                <span> </span>
                <button type="button" onClick={() => confirmDelete(tempSchedule.tempScheduleId!)} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm">{t('tempSchedule.list.delete')}</button>
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
