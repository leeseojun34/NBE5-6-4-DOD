import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate, useParams } from 'react-router';
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
    location: yup.string().emptyToNull().max(255),
    description: yup.string().emptyToNull(),
    meetingPlatform: yup.string().emptyToNull().max(255),
    platformUrl: yup.string().emptyToNull(),
    specificLocation: yup.string().emptyToNull().max(255),
    event: yup.number().integer().emptyToNull()
  });
}

export default function ScheduleEdit() {
  const { t } = useTranslation();
  useDocumentTitle(t('schedule.edit.headline'));

  const navigate = useNavigate();
  const [eventValues, setEventValues] = useState<Map<number,string>>(new Map());
  const params = useParams();
  const currentId = +params.id!;

  const useFormResult = useForm({
    resolver: yupResolver(getSchema()),
  });

  const prepareForm = async () => {
    try {
      const eventValuesResponse = await axios.get('/api/schedules/eventValues');
      setEventValues(eventValuesResponse.data);
      const data = (await axios.get('/api/schedules/' + currentId)).data;
      useFormResult.reset(data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  useEffect(() => {
    prepareForm();
  }, []);

  const updateSchedule = async (data: ScheduleDTO) => {
    window.scrollTo(0, 0);
    try {
      await axios.put('/api/schedules/' + currentId, data);
      navigate('/schedules', {
            state: {
              msgSuccess: t('schedule.update.success')
            }
          });
    } catch (error: any) {
      handleServerError(error, navigate, useFormResult.setError, t);
    }
  };

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('schedule.edit.headline')}</h1>
      <div>
        <Link to="/schedules" className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-4 rounded px-5 py-2">{t('schedule.edit.back')}</Link>
      </div>
    </div>
    <form onSubmit={useFormResult.handleSubmit(updateSchedule)} noValidate>
      <InputRow useFormResult={useFormResult} object="schedule" field="id" disabled={true} type="number" />
      <InputRow useFormResult={useFormResult} object="schedule" field="startTime" required={true} type="datetimepicker" />
      <InputRow useFormResult={useFormResult} object="schedule" field="endTime" required={true} type="datetimepicker" />
      <InputRow useFormResult={useFormResult} object="schedule" field="status" required={true} />
      <InputRow useFormResult={useFormResult} object="schedule" field="location" />
      <InputRow useFormResult={useFormResult} object="schedule" field="description" type="textarea" />
      <InputRow useFormResult={useFormResult} object="schedule" field="meetingPlatform" />
      <InputRow useFormResult={useFormResult} object="schedule" field="platformUrl" type="textarea" />
      <InputRow useFormResult={useFormResult} object="schedule" field="specificLocation" />
      <InputRow useFormResult={useFormResult} object="schedule" field="event" type="select" options={eventValues} />
      <input type="submit" value={t('schedule.edit.headline')} className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2 mt-6" />
    </form>
  </>);
}
