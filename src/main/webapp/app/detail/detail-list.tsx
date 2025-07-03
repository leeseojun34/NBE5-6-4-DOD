import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate } from 'react-router';
import { handleServerError } from 'app/common/utils';
import { DetailDTO } from 'app/detail/detail-model';
import axios from 'axios';
import useDocumentTitle from 'app/common/use-document-title';


export default function DetailList() {
  const { t } = useTranslation();
  useDocumentTitle(t('detail.list.headline'));

  const [details, setDetails] = useState<DetailDTO[]>([]);
  const navigate = useNavigate();

  const getAllDetails = async () => {
    try {
      const response = await axios.get('/api/details');
      setDetails(response.data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  const confirmDelete = async (detailId: number) => {
    if (!confirm(t('delete.confirm'))) {
      return;
    }
    try {
      await axios.delete('/api/details/' + detailId);
      navigate('/details', {
            state: {
              msgInfo: t('detail.delete.success')
            }
          });
      getAllDetails();
    } catch (error: any) {
      if (error?.response?.data?.code === 'REFERENCED') {
        const messageParts = error.response.data.message.split(',');
        navigate('/details', {
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
    getAllDetails();
  }, []);

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('detail.list.headline')}</h1>
      <div>
        <Link to="/details/add" className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2">{t('detail.list.createNew')}</Link>
      </div>
    </div>
    {!details || details.length === 0 ? (
    <div>{t('detail.list.empty')}</div>
    ) : (
    <div className="overflow-x-auto">
      <table className="w-full">
        <thead>
          <tr>
            <th scope="col" className="text-left p-2">{t('detail.detailId.label')}</th>
            <th scope="col" className="text-left p-2">{t('detail.location.label')}</th>
            <th scope="col" className="text-left p-2">{t('detail.schedule.label')}</th>
            <th></th>
          </tr>
        </thead>
        <tbody className="border-t-2 border-black">
          {details.map((detail) => (
          <tr key={detail.detailId} className="odd:bg-gray-100">
            <td className="p-2">{detail.detailId}</td>
            <td className="p-2">{detail.location}</td>
            <td className="p-2">{detail.schedule}</td>
            <td className="p-2">
              <div className="float-right whitespace-nowrap">
                <Link to={'/details/edit/' + detail.detailId} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm">{t('detail.list.edit')}</Link>
                <span> </span>
                <button type="button" onClick={() => confirmDelete(detail.detailId!)} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm">{t('detail.list.delete')}</button>
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
