import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate, useParams } from 'react-router';
import { handleServerError, setYupDefaults } from 'app/common/utils';
import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import { CalendarDTO } from 'app/calendar/calendar-model';
import axios from 'axios';
import InputRow from 'app/common/input-row/input-row';
import useDocumentTitle from 'app/common/use-document-title';
import * as yup from 'yup';


function getSchema() {
  setYupDefaults();
  return yup.object({
    calendarName: yup.string().emptyToNull().max(255).required(),
    synced: yup.string().emptyToNull().max(255).required(),
    syncedAt: yup.string().emptyToNull().required(),
    user: yup.string().emptyToNull().max(255)
  });
}

export default function CalendarEdit() {
  const { t } = useTranslation();
  useDocumentTitle(t('calendar.edit.headline'));

  const navigate = useNavigate();
  const [userValues, setUserValues] = useState<Record<string,string>>({});
  const params = useParams();
  const currentCalendarId = +params.calendarId!;

  const useFormResult = useForm({
    resolver: yupResolver(getSchema()),
  });

  const getMessage = (key: string) => {
    const messages: Record<string, string> = {
      CALENDAR_USER_UNIQUE: t('Exists.calendar.user')
    };
    return messages[key];
  };

  const prepareForm = async () => {
    try {
      const userValuesResponse = await axios.get('/api/calendars/userValues');
      setUserValues(userValuesResponse.data);
      const data = (await axios.get('/api/calendars/' + currentCalendarId)).data;
      useFormResult.reset(data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  useEffect(() => {
    prepareForm();
  }, []);

  const updateCalendar = async (data: CalendarDTO) => {
    window.scrollTo(0, 0);
    try {
      await axios.put('/api/calendars/' + currentCalendarId, data);
      navigate('/calendars', {
            state: {
              msgSuccess: t('calendar.update.success')
            }
          });
    } catch (error: any) {
      handleServerError(error, navigate, useFormResult.setError, t, getMessage);
    }
  };

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('calendar.edit.headline')}</h1>
      <div>
        <Link to="/calendars" className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-4 rounded px-5 py-2">{t('calendar.edit.back')}</Link>
      </div>
    </div>
    <form onSubmit={useFormResult.handleSubmit(updateCalendar)} noValidate>
      <InputRow useFormResult={useFormResult} object="calendar" field="calendarId" disabled={true} type="number" />
      <InputRow useFormResult={useFormResult} object="calendar" field="calendarName" required={true} />
      <InputRow useFormResult={useFormResult} object="calendar" field="synced" required={true} />
      <InputRow useFormResult={useFormResult} object="calendar" field="syncedAt" required={true} type="datetimepicker" />
      <InputRow useFormResult={useFormResult} object="calendar" field="user" type="select" options={userValues} />
      <input type="submit" value={t('calendar.edit.headline')} className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2 mt-6" />
    </form>
  </>);
}
