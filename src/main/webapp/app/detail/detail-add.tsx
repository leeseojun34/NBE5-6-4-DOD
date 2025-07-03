import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate } from 'react-router';
import { handleServerError, setYupDefaults } from 'app/common/utils';
import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import { DetailDTO } from 'app/detail/detail-model';
import axios from 'axios';
import InputRow from 'app/common/input-row/input-row';
import useDocumentTitle from 'app/common/use-document-title';
import * as yup from 'yup';


function getSchema() {
  setYupDefaults();
  return yup.object({
    location: yup.string().emptyToNull().max(255).required(),
    schedule: yup.number().integer().emptyToNull()
  });
}

export default function DetailAdd() {
  const { t } = useTranslation();
  useDocumentTitle(t('detail.add.headline'));

  const navigate = useNavigate();
  const [scheduleValues, setScheduleValues] = useState<Map<number,string>>(new Map());

  const useFormResult = useForm({
    resolver: yupResolver(getSchema()),
  });

  const getMessage = (key: string) => {
    const messages: Record<string, string> = {
      DETAIL_SCHEDULE_UNIQUE: t('Exists.detail.schedule')
    };
    return messages[key];
  };

  const prepareRelations = async () => {
    try {
      const scheduleValuesResponse = await axios.get('/api/details/scheduleValues');
      setScheduleValues(scheduleValuesResponse.data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  useEffect(() => {
    prepareRelations();
  }, []);

  const createDetail = async (data: DetailDTO) => {
    window.scrollTo(0, 0);
    try {
      await axios.post('/api/details', data);
      navigate('/details', {
            state: {
              msgSuccess: t('detail.create.success')
            }
          });
    } catch (error: any) {
      handleServerError(error, navigate, useFormResult.setError, t, getMessage);
    }
  };

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('detail.add.headline')}</h1>
      <div>
        <Link to="/details" className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-4 rounded px-5 py-2">{t('detail.add.back')}</Link>
      </div>
    </div>
    <form onSubmit={useFormResult.handleSubmit(createDetail)} noValidate>
      <InputRow useFormResult={useFormResult} object="detail" field="location" required={true} />
      <InputRow useFormResult={useFormResult} object="detail" field="schedule" type="select" options={scheduleValues} />
      <input type="submit" value={t('detail.add.headline')} className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2 mt-6" />
    </form>
  </>);
}
