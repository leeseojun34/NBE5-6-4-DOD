import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate } from 'react-router';
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
    startTime: yup.string().emptyToNull().required(),
    endTime: yup.string().emptyToNull().required(),
    eventMember: yup.number().integer().emptyToNull()
  });
}

export default function TempScheduleAdd() {
  const { t } = useTranslation();
  useDocumentTitle(t('tempSchedule.add.headline'));

  const navigate = useNavigate();
  const [eventMemberValues, setEventMemberValues] = useState<Map<number,string>>(new Map());

  const useFormResult = useForm({
    resolver: yupResolver(getSchema()),
  });

  const prepareRelations = async () => {
    try {
      const eventMemberValuesResponse = await axios.get('/api/tempSchedules/eventMemberValues');
      setEventMemberValues(eventMemberValuesResponse.data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  useEffect(() => {
    prepareRelations();
  }, []);

  const createTempSchedule = async (data: TempScheduleDTO) => {
    window.scrollTo(0, 0);
    try {
      await axios.post('/api/tempSchedules', data);
      navigate('/tempSchedules', {
            state: {
              msgSuccess: t('tempSchedule.create.success')
            }
          });
    } catch (error: any) {
      handleServerError(error, navigate, useFormResult.setError, t);
    }
  };

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('tempSchedule.add.headline')}</h1>
      <div>
        <Link to="/tempSchedules" className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-4 rounded px-5 py-2">{t('tempSchedule.add.back')}</Link>
      </div>
    </div>
    <form onSubmit={useFormResult.handleSubmit(createTempSchedule)} noValidate>
      <InputRow useFormResult={useFormResult} object="tempSchedule" field="startTime" required={true} type="datetimepicker" />
      <InputRow useFormResult={useFormResult} object="tempSchedule" field="endTime" required={true} type="datetimepicker" />
      <InputRow useFormResult={useFormResult} object="tempSchedule" field="eventMember" type="select" options={eventMemberValues} />
      <input type="submit" value={t('tempSchedule.add.headline')} className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2 mt-6" />
    </form>
  </>);
}
