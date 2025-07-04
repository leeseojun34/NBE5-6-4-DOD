import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate } from 'react-router';
import { handleServerError } from 'app/common/utils';
import { FavoriteTimetableDTO } from 'app/favorite-timetable/favorite-timetable-model';
import axios from 'axios';
import useDocumentTitle from 'app/common/use-document-title';


export default function FavoriteTimetableList() {
  const { t } = useTranslation();
  useDocumentTitle(t('favoriteTimetable.list.headline'));

  const [favoriteTimetables, setFavoriteTimetables] = useState<FavoriteTimetableDTO[]>([]);
  const navigate = useNavigate();

  const getAllFavoriteTimetables = async () => {
    try {
      const response = await axios.get('/api/favoriteTimetables');
      setFavoriteTimetables(response.data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  const confirmDelete = async (id: number) => {
    if (!confirm(t('delete.confirm'))) {
      return;
    }
    try {
      await axios.delete('/api/favoriteTimetables/' + id);
      navigate('/favoriteTimetables', {
            state: {
              msgInfo: t('favoriteTimetable.delete.success')
            }
          });
      getAllFavoriteTimetables();
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  useEffect(() => {
    getAllFavoriteTimetables();
  }, []);

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('favoriteTimetable.list.headline')}</h1>
      <div>
        <Link to="/favoriteTimetables/add" className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2">{t('favoriteTimetable.list.createNew')}</Link>
      </div>
    </div>
    {!favoriteTimetables || favoriteTimetables.length === 0 ? (
    <div>{t('favoriteTimetable.list.empty')}</div>
    ) : (
    <div className="overflow-x-auto">
      <table className="w-full">
        <thead>
          <tr>
            <th scope="col" className="text-left p-2">{t('favoriteTimetable.id.label')}</th>
            <th scope="col" className="text-left p-2">{t('favoriteTimetable.startTime.label')}</th>
            <th scope="col" className="text-left p-2">{t('favoriteTimetable.endTime.label')}</th>
            <th scope="col" className="text-left p-2">{t('favoriteTimetable.weekday.label')}</th>
            <th scope="col" className="text-left p-2">{t('favoriteTimetable.member.label')}</th>
            <th></th>
          </tr>
        </thead>
        <tbody className="border-t-2 border-black">
          {favoriteTimetables.map((favoriteTimetable) => (
          <tr key={favoriteTimetable.id} className="odd:bg-gray-100">
            <td className="p-2">{favoriteTimetable.id}</td>
            <td className="p-2">{favoriteTimetable.startTime}</td>
            <td className="p-2">{favoriteTimetable.endTime}</td>
            <td className="p-2">{favoriteTimetable.weekday}</td>
            <td className="p-2">{favoriteTimetable.member}</td>
            <td className="p-2">
              <div className="float-right whitespace-nowrap">
                <Link to={'/favoriteTimetables/edit/' + favoriteTimetable.id} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm">{t('favoriteTimetable.list.edit')}</Link>
                <span> </span>
                <button type="button" onClick={() => confirmDelete(favoriteTimetable.id!)} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm">{t('favoriteTimetable.list.delete')}</button>
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
