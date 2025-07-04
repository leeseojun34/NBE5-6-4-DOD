import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate } from 'react-router';
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
    isAllDay: yup.bool(),
    externalEtag: yup.string().emptyToNull().required(),
    calendar: yup.number().integer().emptyToNull()
  });
}

export default function CalendarDetailAdd() {
  const { t } = useTranslation();
  useDocumentTitle(t('calendarDetail.add.headline'));

  const navigate = useNavigate();
  const [calendarValues, setCalendarValues] = useState<Map<number,string>>(new Map());

  const useFormResult = useForm({
    resolver: yupResolver(getSchema()),
  });

  const prepareRelations = async () => {
    try {
      const calendarValuesResponse = await axios.get('/api/calendarDetails/calendarValues');
      setCalendarValues(calendarValuesResponse.data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  useEffect(() => {
    prepareRelations();
  }, []);

  const createCalendarDetail = async (data: CalendarDetailDTO) => {
    window.scrollTo(0, 0);
    try {
      await axios.post('/api/calendarDetails', data);
      navigate('/calendarDetails', {
            state: {
              msgSuccess: t('calendarDetail.create.success')
            }
          });
    } catch (error: any) {
      handleServerError(error, navigate, useFormResult.setError, t);
    }
  };

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('calendarDetail.add.headline')}</h1>
      <div>
        <Link to="/calendarDetails" className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-4 rounded px-5 py-2">{t('calendarDetail.add.back')}</Link>
      </div>
    </div>
    <form onSubmit={useFormResult.handleSubmit(createCalendarDetail)} noValidate>
      <InputRow useFormResult={useFormResult} object="calendarDetail" field="title" required={true} />
      <InputRow useFormResult={useFormResult} object="calendarDetail" field="startDatetime" required={true} />
      <InputRow useFormResult={useFormResult} object="calendarDetail" field="endDatetime" required={true} />
      <InputRow useFormResult={useFormResult} object="calendarDetail" field="syncedAt" required={true} type="datetimepicker" />
      <InputRow useFormResult={useFormResult} object="calendarDetail" field="isAllDay" type="checkbox" />
      <InputRow useFormResult={useFormResult} object="calendarDetail" field="externalEtag" required={true} type="textarea" />
      <InputRow useFormResult={useFormResult} object="calendarDetail" field="calendar" type="select" options={calendarValues} />
      <input type="submit" value={t('calendarDetail.add.headline')} className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2 mt-6" />
    </form>
  </>);
}
