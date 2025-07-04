import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate } from 'react-router';
import { handleServerError } from 'app/common/utils';
import { MiddleRegionDTO } from 'app/middle-region/middle-region-model';
import axios from 'axios';
import useDocumentTitle from 'app/common/use-document-title';


export default function MiddleRegionList() {
  const { t } = useTranslation();
  useDocumentTitle(t('middleRegion.list.headline'));

  const [middleRegions, setMiddleRegions] = useState<MiddleRegionDTO[]>([]);
  const navigate = useNavigate();

  const getAllMiddleRegions = async () => {
    try {
      const response = await axios.get('/api/middleRegions');
      setMiddleRegions(response.data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  const confirmDelete = async (id: number) => {
    if (!confirm(t('delete.confirm'))) {
      return;
    }
    try {
      await axios.delete('/api/middleRegions/' + id);
      navigate('/middleRegions', {
            state: {
              msgInfo: t('middleRegion.delete.success')
            }
          });
      getAllMiddleRegions();
    } catch (error: any) {
      if (error?.response?.data?.code === 'REFERENCED') {
        const messageParts = error.response.data.message.split(',');
        navigate('/middleRegions', {
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
    getAllMiddleRegions();
  }, []);

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('middleRegion.list.headline')}</h1>
      <div>
        <Link to="/middleRegions/add" className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2">{t('middleRegion.list.createNew')}</Link>
      </div>
    </div>
    {!middleRegions || middleRegions.length === 0 ? (
    <div>{t('middleRegion.list.empty')}</div>
    ) : (
    <div className="overflow-x-auto">
      <table className="w-full">
        <thead>
          <tr>
            <th scope="col" className="text-left p-2">{t('middleRegion.id.label')}</th>
            <th scope="col" className="text-left p-2">{t('middleRegion.latitude.label')}</th>
            <th scope="col" className="text-left p-2">{t('middleRegion.longitude.label')}</th>
            <th></th>
          </tr>
        </thead>
        <tbody className="border-t-2 border-black">
          {middleRegions.map((middleRegion) => (
          <tr key={middleRegion.id} className="odd:bg-gray-100">
            <td className="p-2">{middleRegion.id}</td>
            <td className="p-2">{middleRegion.latitude}</td>
            <td className="p-2">{middleRegion.longitude}</td>
            <td className="p-2">
              <div className="float-right whitespace-nowrap">
                <Link to={'/middleRegions/edit/' + middleRegion.id} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm">{t('middleRegion.list.edit')}</Link>
                <span> </span>
                <button type="button" onClick={() => confirmDelete(middleRegion.id!)} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm">{t('middleRegion.list.delete')}</button>
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
