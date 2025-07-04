import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate } from 'react-router';
import { handleServerError } from 'app/common/utils';
import { FavoriteLocationDTO } from 'app/favorite-location/favorite-location-model';
import axios from 'axios';
import useDocumentTitle from 'app/common/use-document-title';


export default function FavoriteLocationList() {
  const { t } = useTranslation();
  useDocumentTitle(t('favoriteLocation.list.headline'));

  const [favoriteLocations, setFavoriteLocations] = useState<FavoriteLocationDTO[]>([]);
  const navigate = useNavigate();

  const getAllFavoriteLocations = async () => {
    try {
      const response = await axios.get('/api/favoriteLocations');
      setFavoriteLocations(response.data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  const confirmDelete = async (id: number) => {
    if (!confirm(t('delete.confirm'))) {
      return;
    }
    try {
      await axios.delete('/api/favoriteLocations/' + id);
      navigate('/favoriteLocations', {
            state: {
              msgInfo: t('favoriteLocation.delete.success')
            }
          });
      getAllFavoriteLocations();
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  useEffect(() => {
    getAllFavoriteLocations();
  }, []);

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('favoriteLocation.list.headline')}</h1>
      <div>
        <Link to="/favoriteLocations/add" className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2">{t('favoriteLocation.list.createNew')}</Link>
      </div>
    </div>
    {!favoriteLocations || favoriteLocations.length === 0 ? (
    <div>{t('favoriteLocation.list.empty')}</div>
    ) : (
    <div className="overflow-x-auto">
      <table className="w-full">
        <thead>
          <tr>
            <th scope="col" className="text-left p-2">{t('favoriteLocation.id.label')}</th>
            <th scope="col" className="text-left p-2">{t('favoriteLocation.longitude.label')}</th>
            <th scope="col" className="text-left p-2">{t('favoriteLocation.latitude.label')}</th>
            <th scope="col" className="text-left p-2">{t('favoriteLocation.name.label')}</th>
            <th scope="col" className="text-left p-2">{t('favoriteLocation.member.label')}</th>
            <th></th>
          </tr>
        </thead>
        <tbody className="border-t-2 border-black">
          {favoriteLocations.map((favoriteLocation) => (
          <tr key={favoriteLocation.id} className="odd:bg-gray-100">
            <td className="p-2">{favoriteLocation.id}</td>
            <td className="p-2">{favoriteLocation.longitude}</td>
            <td className="p-2">{favoriteLocation.latitude}</td>
            <td className="p-2">{favoriteLocation.name}</td>
            <td className="p-2">{favoriteLocation.member}</td>
            <td className="p-2">
              <div className="float-right whitespace-nowrap">
                <Link to={'/favoriteLocations/edit/' + favoriteLocation.id} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm">{t('favoriteLocation.list.edit')}</Link>
                <span> </span>
                <button type="button" onClick={() => confirmDelete(favoriteLocation.id!)} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm">{t('favoriteLocation.list.delete')}</button>
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
