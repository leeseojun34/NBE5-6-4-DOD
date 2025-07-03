import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate } from 'react-router';
import { handleServerError } from 'app/common/utils';
import { ScheduleUserDTO } from 'app/schedule-user/schedule-user-model';
import axios from 'axios';
import useDocumentTitle from 'app/common/use-document-title';


export default function ScheduleUserList() {
  const { t } = useTranslation();
  useDocumentTitle(t('scheduleUser.list.headline'));

  const [scheduleUsers, setScheduleUsers] = useState<ScheduleUserDTO[]>([]);
  const navigate = useNavigate();

  const getAllScheduleUsers = async () => {
    try {
      const response = await axios.get('/api/scheduleUsers');
      setScheduleUsers(response.data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  const confirmDelete = async (scheduleUserId: number) => {
    if (!confirm(t('delete.confirm'))) {
      return;
    }
    try {
      await axios.delete('/api/scheduleUsers/' + scheduleUserId);
      navigate('/scheduleUsers', {
            state: {
              msgInfo: t('scheduleUser.delete.success')
            }
          });
      getAllScheduleUsers();
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  useEffect(() => {
    getAllScheduleUsers();
  }, []);

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('scheduleUser.list.headline')}</h1>
      <div>
        <Link to="/scheduleUsers/add" className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2">{t('scheduleUser.list.createNew')}</Link>
      </div>
    </div>
    {!scheduleUsers || scheduleUsers.length === 0 ? (
    <div>{t('scheduleUser.list.empty')}</div>
    ) : (
    <div className="overflow-x-auto">
      <table className="w-full">
        <thead>
          <tr>
            <th scope="col" className="text-left p-2">{t('scheduleUser.scheduleUserId.label')}</th>
            <th scope="col" className="text-left p-2">{t('scheduleUser.role.label')}</th>
            <th scope="col" className="text-left p-2">{t('scheduleUser.user.label')}</th>
            <th scope="col" className="text-left p-2">{t('scheduleUser.schedule.label')}</th>
            <th></th>
          </tr>
        </thead>
        <tbody className="border-t-2 border-black">
          {scheduleUsers.map((scheduleUser) => (
          <tr key={scheduleUser.scheduleUserId} className="odd:bg-gray-100">
            <td className="p-2">{scheduleUser.scheduleUserId}</td>
            <td className="p-2">{scheduleUser.role}</td>
            <td className="p-2">{scheduleUser.user}</td>
            <td className="p-2">{scheduleUser.schedule}</td>
            <td className="p-2">
              <div className="float-right whitespace-nowrap">
                <Link to={'/scheduleUsers/edit/' + scheduleUser.scheduleUserId} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm">{t('scheduleUser.list.edit')}</Link>
                <span> </span>
                <button type="button" onClick={() => confirmDelete(scheduleUser.scheduleUserId!)} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm">{t('scheduleUser.list.delete')}</button>
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
