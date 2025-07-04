import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate } from 'react-router';
import { handleServerError } from 'app/common/utils';
import { CandidateDateDTO } from 'app/candidate-date/candidate-date-model';
import axios from 'axios';
import useDocumentTitle from 'app/common/use-document-title';


export default function CandidateDateList() {
  const { t } = useTranslation();
  useDocumentTitle(t('candidateDate.list.headline'));

  const [candidateDates, setCandidateDates] = useState<CandidateDateDTO[]>([]);
  const navigate = useNavigate();

  const getAllCandidateDates = async () => {
    try {
      const response = await axios.get('/api/candidateDates');
      setCandidateDates(response.data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  const confirmDelete = async (id: number) => {
    if (!confirm(t('delete.confirm'))) {
      return;
    }
    try {
      await axios.delete('/api/candidateDates/' + id);
      navigate('/candidateDates', {
            state: {
              msgInfo: t('candidateDate.delete.success')
            }
          });
      getAllCandidateDates();
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  useEffect(() => {
    getAllCandidateDates();
  }, []);

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('candidateDate.list.headline')}</h1>
      <div>
        <Link to="/candidateDates/add" className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2">{t('candidateDate.list.createNew')}</Link>
      </div>
    </div>
    {!candidateDates || candidateDates.length === 0 ? (
    <div>{t('candidateDate.list.empty')}</div>
    ) : (
    <div className="overflow-x-auto">
      <table className="w-full">
        <thead>
          <tr>
            <th scope="col" className="text-left p-2">{t('candidateDate.id.label')}</th>
            <th scope="col" className="text-left p-2">{t('candidateDate.date.label')}</th>
            <th scope="col" className="text-left p-2">{t('candidateDate.event.label')}</th>
            <th></th>
          </tr>
        </thead>
        <tbody className="border-t-2 border-black">
          {candidateDates.map((candidateDate) => (
          <tr key={candidateDate.id} className="odd:bg-gray-100">
            <td className="p-2">{candidateDate.id}</td>
            <td className="p-2">{candidateDate.date}</td>
            <td className="p-2">{candidateDate.event}</td>
            <td className="p-2">
              <div className="float-right whitespace-nowrap">
                <Link to={'/candidateDates/edit/' + candidateDate.id} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm">{t('candidateDate.list.edit')}</Link>
                <span> </span>
                <button type="button" onClick={() => confirmDelete(candidateDate.id!)} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm">{t('candidateDate.list.delete')}</button>
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
