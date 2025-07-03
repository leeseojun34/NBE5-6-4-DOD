import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate } from 'react-router';
import { handleServerError } from 'app/common/utils';
import { LikeTimetableDTO } from 'app/like-timetable/like-timetable-model';
import axios from 'axios';
import useDocumentTitle from 'app/common/use-document-title';


export default function LikeTimetableList() {
  const { t } = useTranslation();
  useDocumentTitle(t('likeTimetable.list.headline'));

  const [likeTimetables, setLikeTimetables] = useState<LikeTimetableDTO[]>([]);
  const navigate = useNavigate();

  const getAllLikeTimetables = async () => {
    try {
      const response = await axios.get('/api/likeTimetables');
      setLikeTimetables(response.data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  const confirmDelete = async (likeTimetableId: number) => {
    if (!confirm(t('delete.confirm'))) {
      return;
    }
    try {
      await axios.delete('/api/likeTimetables/' + likeTimetableId);
      navigate('/likeTimetables', {
            state: {
              msgInfo: t('likeTimetable.delete.success')
            }
          });
      getAllLikeTimetables();
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  useEffect(() => {
    getAllLikeTimetables();
  }, []);

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('likeTimetable.list.headline')}</h1>
      <div>
        <Link to="/likeTimetables/add" className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2">{t('likeTimetable.list.createNew')}</Link>
      </div>
    </div>
    {!likeTimetables || likeTimetables.length === 0 ? (
    <div>{t('likeTimetable.list.empty')}</div>
    ) : (
    <div className="overflow-x-auto">
      <table className="w-full">
        <thead>
          <tr>
            <th scope="col" className="text-left p-2">{t('likeTimetable.likeTimetableId.label')}</th>
            <th scope="col" className="text-left p-2">{t('likeTimetable.startTime.label')}</th>
            <th scope="col" className="text-left p-2">{t('likeTimetable.endTime.label')}</th>
            <th scope="col" className="text-left p-2">{t('likeTimetable.weekday.label')}</th>
            <th scope="col" className="text-left p-2">{t('likeTimetable.user.label')}</th>
            <th></th>
          </tr>
        </thead>
        <tbody className="border-t-2 border-black">
          {likeTimetables.map((likeTimetable) => (
          <tr key={likeTimetable.likeTimetableId} className="odd:bg-gray-100">
            <td className="p-2">{likeTimetable.likeTimetableId}</td>
            <td className="p-2">{likeTimetable.startTime}</td>
            <td className="p-2">{likeTimetable.endTime}</td>
            <td className="p-2">{likeTimetable.weekday}</td>
            <td className="p-2">{likeTimetable.user}</td>
            <td className="p-2">
              <div className="float-right whitespace-nowrap">
                <Link to={'/likeTimetables/edit/' + likeTimetable.likeTimetableId} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm">{t('likeTimetable.list.edit')}</Link>
                <span> </span>
                <button type="button" onClick={() => confirmDelete(likeTimetable.likeTimetableId!)} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm">{t('likeTimetable.list.delete')}</button>
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
