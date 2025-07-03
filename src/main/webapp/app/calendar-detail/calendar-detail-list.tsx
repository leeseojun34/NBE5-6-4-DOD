import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate } from 'react-router';
import { handleServerError } from 'app/common/utils';
import { CalendarDetailDTO } from 'app/calendar-detail/calendar-detail-model';
import axios from 'axios';
import useDocumentTitle from 'app/common/use-document-title';


export default function CalendarDetailList() {
  const { t } = useTranslation();
  useDocumentTitle(t('calendarDetail.list.headline'));

  const [calendarDetails, setCalendarDetails] = useState<CalendarDetailDTO[]>([]);
  const navigate = useNavigate();

  const getAllCalendarDetails = async () => {
    try {
      const response = await axios.get('/api/calendarDetails');
      setCalendarDetails(response.data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  const confirmDelete = async (calendarDetailId: number) => {
    if (!confirm(t('delete.confirm'))) {
      return;
    }
    try {
      await axios.delete('/api/calendarDetails/' + calendarDetailId);
      navigate('/calendarDetails', {
            state: {
              msgInfo: t('calendarDetail.delete.success')
            }
          });
      getAllCalendarDetails();
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  useEffect(() => {
    getAllCalendarDetails();
  }, []);

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('calendarDetail.list.headline')}</h1>
      <div>
        <Link to="/calendarDetails/add" className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2">{t('calendarDetail.list.createNew')}</Link>
      </div>
    </div>
    {!calendarDetails || calendarDetails.length === 0 ? (
    <div>{t('calendarDetail.list.empty')}</div>
    ) : (
    <div className="overflow-x-auto">
      <table className="w-full">
        <thead>
          <tr>
            <th scope="col" className="text-left p-2">{t('calendarDetail.calendarDetailId.label')}</th>
            <th scope="col" className="text-left p-2">{t('calendarDetail.title.label')}</th>
            <th scope="col" className="text-left p-2">{t('calendarDetail.startDatetime.label')}</th>
            <th scope="col" className="text-left p-2">{t('calendarDetail.endDatetime.label')}</th>
            <th scope="col" className="text-left p-2">{t('calendarDetail.syncedAt.label')}</th>
            <th scope="col" className="text-left p-2">{t('calendarDetail.calendar.label')}</th>
            <th></th>
          </tr>
        </thead>
        <tbody className="border-t-2 border-black">
          {calendarDetails.map((calendarDetail) => (
          <tr key={calendarDetail.calendarDetailId} className="odd:bg-gray-100">
            <td className="p-2">{calendarDetail.calendarDetailId}</td>
            <td className="p-2">{calendarDetail.title}</td>
            <td className="p-2">{calendarDetail.startDatetime}</td>
            <td className="p-2">{calendarDetail.endDatetime}</td>
            <td className="p-2">{calendarDetail.syncedAt}</td>
            <td className="p-2">{calendarDetail.calendar}</td>
            <td className="p-2">
              <div className="float-right whitespace-nowrap">
                <Link to={'/calendarDetails/edit/' + calendarDetail.calendarDetailId} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm">{t('calendarDetail.list.edit')}</Link>
                <span> </span>
                <button type="button" onClick={() => confirmDelete(calendarDetail.calendarDetailId!)} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm">{t('calendarDetail.list.delete')}</button>
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
