import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate } from 'react-router';
import { handleServerError, setYupDefaults } from 'app/common/utils';
import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import { CandidateDateDTO } from 'app/candidate-date/candidate-date-model';
import axios from 'axios';
import InputRow from 'app/common/input-row/input-row';
import useDocumentTitle from 'app/common/use-document-title';
import * as yup from 'yup';


function getSchema() {
  setYupDefaults();
  return yup.object({
    date: yup.string().emptyToNull().required(),
    event: yup.number().integer().emptyToNull()
  });
}

export default function CandidateDateAdd() {
  const { t } = useTranslation();
  useDocumentTitle(t('candidateDate.add.headline'));

  const navigate = useNavigate();
  const [eventValues, setEventValues] = useState<Map<number,string>>(new Map());

  const useFormResult = useForm({
    resolver: yupResolver(getSchema()),
  });

  const getMessage = (key: string) => {
    const messages: Record<string, string> = {
      CANDIDATE_DATE_DATE_UNIQUE: t('exists.candidateDate.date')
    };
    return messages[key];
  };

  const prepareRelations = async () => {
    try {
      const eventValuesResponse = await axios.get('/api/candidateDates/eventValues');
      setEventValues(eventValuesResponse.data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  useEffect(() => {
    prepareRelations();
  }, []);

  const createCandidateDate = async (data: CandidateDateDTO) => {
    window.scrollTo(0, 0);
    try {
      await axios.post('/api/candidateDates', data);
      navigate('/candidateDates', {
            state: {
              msgSuccess: t('candidateDate.create.success')
            }
          });
    } catch (error: any) {
      handleServerError(error, navigate, useFormResult.setError, t, getMessage);
    }
  };

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('candidateDate.add.headline')}</h1>
      <div>
        <Link to="/candidateDates" className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-4 rounded px-5 py-2">{t('candidateDate.add.back')}</Link>
      </div>
    </div>
    <form onSubmit={useFormResult.handleSubmit(createCandidateDate)} noValidate>
      <InputRow useFormResult={useFormResult} object="candidateDate" field="date" required={true} type="datetimepicker" />
      <InputRow useFormResult={useFormResult} object="candidateDate" field="event" type="select" options={eventValues} />
      <input type="submit" value={t('candidateDate.add.headline')} className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2 mt-6" />
    </form>
  </>);
}
