import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate } from 'react-router';
import { handleServerError, setYupDefaults } from 'app/common/utils';
import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import { ScheduleDTO } from 'app/schedule/schedule-model';
import axios from 'axios';
import InputRow from 'app/common/input-row/input-row';
import useDocumentTitle from 'app/common/use-document-title';
import * as yup from 'yup';


function getSchema() {
  setYupDefaults();
  return yup.object({
    startTime: yup.string().emptyToNull().required(),
    endTime: yup.string().emptyToNull().required(),
    status: yup.string().emptyToNull().max(255).required(),
    event: yup.number().integer().emptyToNull()
  });
}

export default function ScheduleAdd() {
  const { t } = useTranslation();
  useDocumentTitle(t('schedule.add.headline'));

  const navigate = useNavigate();
  const [eventValues, setEventValues] = useState<Map<number,string>>(new Map());

  const useFormResult = useForm({
    resolver: yupResolver(getSchema()),
  });

  const prepareRelations = async () => {
    try {
      const eventValuesResponse = await axios.get('/api/schedules/eventValues');
      setEventValues(eventValuesResponse.data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  useEffect(() => {
    prepareRelations();
  }, []);

  const createSchedule = async (data: ScheduleDTO) => {
    window.scrollTo(0, 0);
    try {
      await axios.post('/api/schedules', data);
      navigate('/schedules', {
            state: {
              msgSuccess: t('schedule.create.success')
            }
          });
    } catch (error: any) {
      handleServerError(error, navigate, useFormResult.setError, t);
    }
  };

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('schedule.add.headline')}</h1>
      <div>
        <Link to="/schedules" className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-4 rounded px-5 py-2">{t('schedule.add.back')}</Link>
      </div>
    </div>
    <form onSubmit={useFormResult.handleSubmit(createSchedule)} noValidate>
      <InputRow useFormResult={useFormResult} object="schedule" field="startTime" required={true} type="datetimepicker" />
      <InputRow useFormResult={useFormResult} object="schedule" field="endTime" required={true} type="datetimepicker" />
      <InputRow useFormResult={useFormResult} object="schedule" field="status" required={true} />
      <InputRow useFormResult={useFormResult} object="schedule" field="event" type="select" options={eventValues} />
      <input type="submit" value={t('schedule.add.headline')} className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2 mt-6" />
    </form>
  </>);
}
