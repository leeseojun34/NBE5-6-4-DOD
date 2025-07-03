import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate } from 'react-router';
import { handleServerError } from 'app/common/utils';
import { LocationCandidateDTO } from 'app/location-candidate/location-candidate-model';
import axios from 'axios';
import useDocumentTitle from 'app/common/use-document-title';


export default function LocationCandidateList() {
  const { t } = useTranslation();
  useDocumentTitle(t('locationCandidate.list.headline'));

  const [locationCandidates, setLocationCandidates] = useState<LocationCandidateDTO[]>([]);
  const navigate = useNavigate();

  const getAllLocationCandidates = async () => {
    try {
      const response = await axios.get('/api/locationCandidates');
      setLocationCandidates(response.data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  const confirmDelete = async (locationCandidateId: number) => {
    if (!confirm(t('delete.confirm'))) {
      return;
    }
    try {
      await axios.delete('/api/locationCandidates/' + locationCandidateId);
      navigate('/locationCandidates', {
            state: {
              msgInfo: t('locationCandidate.delete.success')
            }
          });
      getAllLocationCandidates();
    } catch (error: any) {
      if (error?.response?.data?.code === 'REFERENCED') {
        const messageParts = error.response.data.message.split(',');
        navigate('/locationCandidates', {
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
    getAllLocationCandidates();
  }, []);

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('locationCandidate.list.headline')}</h1>
      <div>
        <Link to="/locationCandidates/add" className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2">{t('locationCandidate.list.createNew')}</Link>
      </div>
    </div>
    {!locationCandidates || locationCandidates.length === 0 ? (
    <div>{t('locationCandidate.list.empty')}</div>
    ) : (
    <div className="overflow-x-auto">
      <table className="w-full">
        <thead>
          <tr>
            <th scope="col" className="text-left p-2">{t('locationCandidate.locationCandidateId.label')}</th>
            <th scope="col" className="text-left p-2">{t('locationCandidate.suggestUserId.label')}</th>
            <th scope="col" className="text-left p-2">{t('locationCandidate.locationName.label')}</th>
            <th scope="col" className="text-left p-2">{t('locationCandidate.latitude.label')}</th>
            <th scope="col" className="text-left p-2">{t('locationCandidate.longitude.label')}</th>
            <th scope="col" className="text-left p-2">{t('locationCandidate.voteCount.label')}</th>
            <th scope="col" className="text-left p-2">{t('locationCandidate.status.label')}</th>
            <th scope="col" className="text-left p-2">{t('locationCandidate.detail.label')}</th>
            <th></th>
          </tr>
        </thead>
        <tbody className="border-t-2 border-black">
          {locationCandidates.map((locationCandidate) => (
          <tr key={locationCandidate.locationCandidateId} className="odd:bg-gray-100">
            <td className="p-2">{locationCandidate.locationCandidateId}</td>
            <td className="p-2">{locationCandidate.suggestUserId}</td>
            <td className="p-2">{locationCandidate.locationName}</td>
            <td className="p-2">{locationCandidate.latitude}</td>
            <td className="p-2">{locationCandidate.longitude}</td>
            <td className="p-2">{locationCandidate.voteCount}</td>
            <td className="p-2">{locationCandidate.status}</td>
            <td className="p-2">{locationCandidate.detail}</td>
            <td className="p-2">
              <div className="float-right whitespace-nowrap">
                <Link to={'/locationCandidates/edit/' + locationCandidate.locationCandidateId} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm">{t('locationCandidate.list.edit')}</Link>
                <span> </span>
                <button type="button" onClick={() => confirmDelete(locationCandidate.locationCandidateId!)} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm">{t('locationCandidate.list.delete')}</button>
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
