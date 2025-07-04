import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate } from 'react-router';
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
    name: yup.string().emptyToNull().max(255).required(),
    synced: yup.bool(),
    syncedAt: yup.string().emptyToNull().required(),
    member: yup.string().emptyToNull().max(255)
  });
}

export default function CalendarAdd() {
  const { t } = useTranslation();
  useDocumentTitle(t('calendar.add.headline'));

  const navigate = useNavigate();
  const [memberValues, setMemberValues] = useState<Record<string,string>>({});

  const useFormResult = useForm({
    resolver: yupResolver(getSchema()),
  });

  const getMessage = (key: string) => {
    const messages: Record<string, string> = {
      CALENDAR_MEMBER_UNIQUE: t('Exists.calendar.member')
    };
    return messages[key];
  };

  const prepareRelations = async () => {
    try {
      const memberValuesResponse = await axios.get('/api/calendars/memberValues');
      setMemberValues(memberValuesResponse.data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  useEffect(() => {
    prepareRelations();
  }, []);

  const createCalendar = async (data: CalendarDTO) => {
    window.scrollTo(0, 0);
    try {
      await axios.post('/api/calendars', data);
      navigate('/calendars', {
            state: {
              msgSuccess: t('calendar.create.success')
            }
          });
    } catch (error: any) {
      handleServerError(error, navigate, useFormResult.setError, t, getMessage);
    }
  };

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('calendar.add.headline')}</h1>
      <div>
        <Link to="/calendars" className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-4 rounded px-5 py-2">{t('calendar.add.back')}</Link>
      </div>
    </div>
    <form onSubmit={useFormResult.handleSubmit(createCalendar)} noValidate>
      <InputRow useFormResult={useFormResult} object="calendar" field="name" required={true} />
      <InputRow useFormResult={useFormResult} object="calendar" field="synced" type="checkbox" />
      <InputRow useFormResult={useFormResult} object="calendar" field="syncedAt" required={true} type="datetimepicker" />
      <InputRow useFormResult={useFormResult} object="calendar" field="member" type="select" options={memberValues} />
      <input type="submit" value={t('calendar.add.headline')} className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2 mt-6" />
    </form>
  </>);
}
