import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate } from 'react-router';
import { handleServerError } from 'app/common/utils';
import { LocationDTO } from 'app/location/location-model';
import axios from 'axios';
import useDocumentTitle from 'app/common/use-document-title';


export default function LocationList() {
  const { t } = useTranslation();
  useDocumentTitle(t('location.list.headline'));

  const [locations, setLocations] = useState<LocationDTO[]>([]);
  const navigate = useNavigate();

  const getAllLocations = async () => {
    try {
      const response = await axios.get('/api/locations');
      setLocations(response.data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  const confirmDelete = async (id: number) => {
    if (!confirm(t('delete.confirm'))) {
      return;
    }
    try {
      await axios.delete('/api/locations/' + id);
      navigate('/locations', {
            state: {
              msgInfo: t('location.delete.success')
            }
          });
      getAllLocations();
    } catch (error: any) {
      if (error?.response?.data?.code === 'REFERENCED') {
        const messageParts = error.response.data.message.split(',');
        navigate('/locations', {
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
    getAllLocations();
  }, []);

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('location.list.headline')}</h1>
      <div>
        <Link to="/locations/add" className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2">{t('location.list.createNew')}</Link>
      </div>
    </div>
    {!locations || locations.length === 0 ? (
    <div>{t('location.list.empty')}</div>
    ) : (
    <div className="overflow-x-auto">
      <table className="w-full">
        <thead>
          <tr>
            <th scope="col" className="text-left p-2">{t('location.id.label')}</th>
            <th scope="col" className="text-left p-2">{t('location.latitude.label')}</th>
            <th scope="col" className="text-left p-2">{t('location.longitude.label')}</th>
            <th scope="col" className="text-left p-2">{t('location.name.label')}</th>
            <th scope="col" className="text-left p-2">{t('location.suggestedMemberId.label')}</th>
            <th scope="col" className="text-left p-2">{t('location.voteCount.label')}</th>
            <th scope="col" className="text-left p-2">{t('location.status.label')}</th>
            <th scope="col" className="text-left p-2">{t('location.middleRegion.label')}</th>
            <th></th>
          </tr>
        </thead>
        <tbody className="border-t-2 border-black">
          {locations.map((location) => (
          <tr key={location.id} className="odd:bg-gray-100">
            <td className="p-2">{location.id}</td>
            <td className="p-2">{location.latitude}</td>
            <td className="p-2">{location.longitude}</td>
            <td className="p-2">{location.name}</td>
            <td className="p-2">{location.suggestedMemberId}</td>
            <td className="p-2">{location.voteCount}</td>
            <td className="p-2">{location.status}</td>
            <td className="p-2">{location.middleRegion}</td>
            <td className="p-2">
              <div className="float-right whitespace-nowrap">
                <Link to={'/locations/edit/' + location.id} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm">{t('location.list.edit')}</Link>
                <span> </span>
                <button type="button" onClick={() => confirmDelete(location.id!)} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm">{t('location.list.delete')}</button>
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
