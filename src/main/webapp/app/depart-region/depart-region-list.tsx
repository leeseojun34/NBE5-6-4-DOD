import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate } from 'react-router';
import { handleServerError } from 'app/common/utils';
import { DepartRegionDTO } from 'app/depart-region/depart-region-model';
import axios from 'axios';
import useDocumentTitle from 'app/common/use-document-title';


export default function DepartRegionList() {
  const { t } = useTranslation();
  useDocumentTitle(t('departRegion.list.headline'));

  const [departRegions, setDepartRegions] = useState<DepartRegionDTO[]>([]);
  const navigate = useNavigate();

  const getAllDepartRegions = async () => {
    try {
      const response = await axios.get('/api/departRegions');
      setDepartRegions(response.data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  const confirmDelete = async (departRegionId: number) => {
    if (!confirm(t('delete.confirm'))) {
      return;
    }
    try {
      await axios.delete('/api/departRegions/' + departRegionId);
      navigate('/departRegions', {
            state: {
              msgInfo: t('departRegion.delete.success')
            }
          });
      getAllDepartRegions();
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  useEffect(() => {
    getAllDepartRegions();
  }, []);

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('departRegion.list.headline')}</h1>
      <div>
        <Link to="/departRegions/add" className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2">{t('departRegion.list.createNew')}</Link>
      </div>
    </div>
    {!departRegions || departRegions.length === 0 ? (
    <div>{t('departRegion.list.empty')}</div>
    ) : (
    <div className="overflow-x-auto">
      <table className="w-full">
        <thead>
          <tr>
            <th scope="col" className="text-left p-2">{t('departRegion.departRegionId.label')}</th>
            <th scope="col" className="text-left p-2">{t('departRegion.dapartLocationName.label')}</th>
            <th scope="col" className="text-left p-2">{t('departRegion.latitude.label')}</th>
            <th scope="col" className="text-left p-2">{t('departRegion.longitude.label')}</th>
            <th scope="col" className="text-left p-2">{t('departRegion.meeting.label')}</th>
            <th scope="col" className="text-left p-2">{t('departRegion.middleRegion.label')}</th>
            <th></th>
          </tr>
        </thead>
        <tbody className="border-t-2 border-black">
          {departRegions.map((departRegion) => (
          <tr key={departRegion.departRegionId} className="odd:bg-gray-100">
            <td className="p-2">{departRegion.departRegionId}</td>
            <td className="p-2">{departRegion.dapartLocationName}</td>
            <td className="p-2">{departRegion.latitude}</td>
            <td className="p-2">{departRegion.longitude}</td>
            <td className="p-2">{departRegion.meeting}</td>
            <td className="p-2">{departRegion.middleRegion}</td>
            <td className="p-2">
              <div className="float-right whitespace-nowrap">
                <Link to={'/departRegions/edit/' + departRegion.departRegionId} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm">{t('departRegion.list.edit')}</Link>
                <span> </span>
                <button type="button" onClick={() => confirmDelete(departRegion.departRegionId!)} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm">{t('departRegion.list.delete')}</button>
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
