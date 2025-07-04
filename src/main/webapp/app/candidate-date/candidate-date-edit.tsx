import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate, useParams } from 'react-router';
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
    startTime: yup.string().emptyToNull(),
    endTime: yup.string().emptyToNull().required(),
    event: yup.number().integer().emptyToNull()
  });
}

export default function CandidateDateEdit() {
  const { t } = useTranslation();
  useDocumentTitle(t('candidateDate.edit.headline'));

  const navigate = useNavigate();
  const [eventValues, setEventValues] = useState<Map<number,string>>(new Map());
  const params = useParams();
  const currentId = +params.id!;

  const useFormResult = useForm({
    resolver: yupResolver(getSchema()),
  });

  const prepareForm = async () => {
    try {
      const eventValuesResponse = await axios.get('/api/candidateDates/eventValues');
      setEventValues(eventValuesResponse.data);
      const data = (await axios.get('/api/candidateDates/' + currentId)).data;
      useFormResult.reset(data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  useEffect(() => {
    prepareForm();
  }, []);

  const updateCandidateDate = async (data: CandidateDateDTO) => {
    window.scrollTo(0, 0);
    try {
      await axios.put('/api/candidateDates/' + currentId, data);
      navigate('/candidateDates', {
            state: {
              msgSuccess: t('candidateDate.update.success')
            }
          });
    } catch (error: any) {
      handleServerError(error, navigate, useFormResult.setError, t);
    }
  };

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('candidateDate.edit.headline')}</h1>
      <div>
        <Link to="/candidateDates" className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-4 rounded px-5 py-2">{t('candidateDate.edit.back')}</Link>
      </div>
    </div>
    <form onSubmit={useFormResult.handleSubmit(updateCandidateDate)} noValidate>
      <InputRow useFormResult={useFormResult} object="candidateDate" field="id" disabled={true} type="number" />
      <InputRow useFormResult={useFormResult} object="candidateDate" field="startTime" type="datetimepicker" />
      <InputRow useFormResult={useFormResult} object="candidateDate" field="endTime" required={true} type="datetimepicker" />
      <InputRow useFormResult={useFormResult} object="candidateDate" field="event" type="select" options={eventValues} />
      <input type="submit" value={t('candidateDate.edit.headline')} className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2 mt-6" />
    </form>
  </>);
}
