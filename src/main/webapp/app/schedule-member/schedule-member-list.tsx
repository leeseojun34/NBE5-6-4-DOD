import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate } from 'react-router';
import { handleServerError } from 'app/common/utils';
import { ScheduleMemberDTO } from 'app/schedule-member/schedule-member-model';
import axios from 'axios';
import useDocumentTitle from 'app/common/use-document-title';


export default function ScheduleMemberList() {
  const { t } = useTranslation();
  useDocumentTitle(t('scheduleMember.list.headline'));

  const [scheduleMembers, setScheduleMembers] = useState<ScheduleMemberDTO[]>([]);
  const navigate = useNavigate();

  const getAllScheduleMembers = async () => {
    try {
      const response = await axios.get('/api/scheduleMembers');
      setScheduleMembers(response.data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  const confirmDelete = async (id: number) => {
    if (!confirm(t('delete.confirm'))) {
      return;
    }
    try {
      await axios.delete('/api/scheduleMembers/' + id);
      navigate('/scheduleMembers', {
            state: {
              msgInfo: t('scheduleMember.delete.success')
            }
          });
      getAllScheduleMembers();
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  useEffect(() => {
    getAllScheduleMembers();
  }, []);

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('scheduleMember.list.headline')}</h1>
      <div>
        <Link to="/scheduleMembers/add" className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2">{t('scheduleMember.list.createNew')}</Link>
      </div>
    </div>
    {!scheduleMembers || scheduleMembers.length === 0 ? (
    <div>{t('scheduleMember.list.empty')}</div>
    ) : (
    <div className="overflow-x-auto">
      <table className="w-full">
        <thead>
          <tr>
            <th scope="col" className="text-left p-2">{t('scheduleMember.id.label')}</th>
            <th scope="col" className="text-left p-2">{t('scheduleMember.role.label')}</th>
            <th scope="col" className="text-left p-2">{t('scheduleMember.departLocationName.label')}</th>
            <th scope="col" className="text-left p-2">{t('scheduleMember.latitude.label')}</th>
            <th scope="col" className="text-left p-2">{t('scheduleMember.longitude.label')}</th>
            <th scope="col" className="text-left p-2">{t('scheduleMember.member.label')}</th>
            <th scope="col" className="text-left p-2">{t('scheduleMember.schedule.label')}</th>
            <th scope="col" className="text-left p-2">{t('scheduleMember.middleRegion.label')}</th>
            <th></th>
          </tr>
        </thead>
        <tbody className="border-t-2 border-black">
          {scheduleMembers.map((scheduleMember) => (
          <tr key={scheduleMember.id} className="odd:bg-gray-100">
            <td className="p-2">{scheduleMember.id}</td>
            <td className="p-2">{scheduleMember.role}</td>
            <td className="p-2">{scheduleMember.departLocationName}</td>
            <td className="p-2">{scheduleMember.latitude}</td>
            <td className="p-2">{scheduleMember.longitude}</td>
            <td className="p-2">{scheduleMember.member}</td>
            <td className="p-2">{scheduleMember.schedule}</td>
            <td className="p-2">{scheduleMember.middleRegion}</td>
            <td className="p-2">
              <div className="float-right whitespace-nowrap">
                <Link to={'/scheduleMembers/edit/' + scheduleMember.id} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm">{t('scheduleMember.list.edit')}</Link>
                <span> </span>
                <button type="button" onClick={() => confirmDelete(scheduleMember.id!)} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm">{t('scheduleMember.list.delete')}</button>
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
