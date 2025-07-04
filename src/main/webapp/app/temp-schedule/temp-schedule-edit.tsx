import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate, useParams } from 'react-router';
import { handleServerError, setYupDefaults } from 'app/common/utils';
import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import { TempScheduleDTO } from 'app/temp-schedule/temp-schedule-model';
import axios from 'axios';
import InputRow from 'app/common/input-row/input-row';
import useDocumentTitle from 'app/common/use-document-title';
import * as yup from 'yup';


function getSchema() {
  setYupDefaults();
  return yup.object({
    date: yup.string().emptyToNull().required(),
    timeBit: yup.number().integer().emptyToNull().required(),
    eventMember: yup.number().integer().emptyToNull()
  });
}

export default function TempScheduleEdit() {
  const { t } = useTranslation();
  useDocumentTitle(t('tempSchedule.edit.headline'));

  const navigate = useNavigate();
  const [eventMemberValues, setEventMemberValues] = useState<Map<number,string>>(new Map());
  const params = useParams();
  const currentId = +params.id!;

  const useFormResult = useForm({
    resolver: yupResolver(getSchema()),
  });

  const prepareForm = async () => {
    try {
      const eventMemberValuesResponse = await axios.get('/api/tempSchedules/eventMemberValues');
      setEventMemberValues(eventMemberValuesResponse.data);
      const data = (await axios.get('/api/tempSchedules/' + currentId)).data;
      useFormResult.reset(data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  useEffect(() => {
    prepareForm();
  }, []);

  const updateTempSchedule = async (data: TempScheduleDTO) => {
    window.scrollTo(0, 0);
    try {
      await axios.put('/api/tempSchedules/' + currentId, data);
      navigate('/tempSchedules', {
            state: {
              msgSuccess: t('tempSchedule.update.success')
            }
          });
    } catch (error: any) {
      handleServerError(error, navigate, useFormResult.setError, t);
    }
  };

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('tempSchedule.edit.headline')}</h1>
      <div>
        <Link to="/tempSchedules" className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-4 rounded px-5 py-2">{t('tempSchedule.edit.back')}</Link>
      </div>
    </div>
    <form onSubmit={useFormResult.handleSubmit(updateTempSchedule)} noValidate>
      <InputRow useFormResult={useFormResult} object="tempSchedule" field="id" disabled={true} type="number" />
      <InputRow useFormResult={useFormResult} object="tempSchedule" field="date" required={true} type="datetimepicker" />
      <InputRow useFormResult={useFormResult} object="tempSchedule" field="timeBit" required={true} type="number" />
      <InputRow useFormResult={useFormResult} object="tempSchedule" field="eventMember" type="select" options={eventMemberValues} />
      <input type="submit" value={t('tempSchedule.edit.headline')} className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2 mt-6" />
    </form>
  </>);
}
