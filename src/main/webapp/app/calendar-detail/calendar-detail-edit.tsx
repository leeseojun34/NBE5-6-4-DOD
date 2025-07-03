import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate, useParams } from 'react-router';
import { handleServerError, setYupDefaults } from 'app/common/utils';
import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import { CalendarDetailDTO } from 'app/calendar-detail/calendar-detail-model';
import axios from 'axios';
import InputRow from 'app/common/input-row/input-row';
import useDocumentTitle from 'app/common/use-document-title';
import * as yup from 'yup';


function getSchema() {
  setYupDefaults();
  return yup.object({
    title: yup.string().emptyToNull().max(255).required(),
    startDatetime: yup.string().emptyToNull().max(255).required(),
    endDatetime: yup.string().emptyToNull().max(255).required(),
    syncedAt: yup.string().emptyToNull().required(),
    calendar: yup.number().integer().emptyToNull()
  });
}

export default function CalendarDetailEdit() {
  const { t } = useTranslation();
  useDocumentTitle(t('calendarDetail.edit.headline'));

  const navigate = useNavigate();
  const [calendarValues, setCalendarValues] = useState<Map<number,string>>(new Map());
  const params = useParams();
  const currentCalendarDetailId = +params.calendarDetailId!;

  const useFormResult = useForm({
    resolver: yupResolver(getSchema()),
  });

  const prepareForm = async () => {
    try {
      const calendarValuesResponse = await axios.get('/api/calendarDetails/calendarValues');
      setCalendarValues(calendarValuesResponse.data);
      const data = (await axios.get('/api/calendarDetails/' + currentCalendarDetailId)).data;
      useFormResult.reset(data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  useEffect(() => {
    prepareForm();
  }, []);

  const updateCalendarDetail = async (data: CalendarDetailDTO) => {
    window.scrollTo(0, 0);
    try {
      await axios.put('/api/calendarDetails/' + currentCalendarDetailId, data);
      navigate('/calendarDetails', {
            state: {
              msgSuccess: t('calendarDetail.update.success')
            }
          });
    } catch (error: any) {
      handleServerError(error, navigate, useFormResult.setError, t);
    }
  };

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('calendarDetail.edit.headline')}</h1>
      <div>
        <Link to="/calendarDetails" className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-4 rounded px-5 py-2">{t('calendarDetail.edit.back')}</Link>
      </div>
    </div>
    <form onSubmit={useFormResult.handleSubmit(updateCalendarDetail)} noValidate>
      <InputRow useFormResult={useFormResult} object="calendarDetail" field="calendarDetailId" disabled={true} type="number" />
      <InputRow useFormResult={useFormResult} object="calendarDetail" field="title" required={true} />
      <InputRow useFormResult={useFormResult} object="calendarDetail" field="startDatetime" required={true} />
      <InputRow useFormResult={useFormResult} object="calendarDetail" field="endDatetime" required={true} />
      <InputRow useFormResult={useFormResult} object="calendarDetail" field="syncedAt" required={true} type="datetimepicker" />
      <InputRow useFormResult={useFormResult} object="calendarDetail" field="calendar" type="select" options={calendarValues} />
      <input type="submit" value={t('calendarDetail.edit.headline')} className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2 mt-6" />
    </form>
  </>);
}
