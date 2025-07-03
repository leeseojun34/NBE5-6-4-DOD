import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate } from 'react-router';
import { handleServerError, setYupDefaults } from 'app/common/utils';
import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import { ScheduleUserDTO } from 'app/schedule-user/schedule-user-model';
import axios from 'axios';
import InputRow from 'app/common/input-row/input-row';
import useDocumentTitle from 'app/common/use-document-title';
import * as yup from 'yup';


function getSchema() {
  setYupDefaults();
  return yup.object({
    role: yup.string().emptyToNull().max(255).required(),
    user: yup.string().emptyToNull().max(255),
    schedule: yup.number().integer().emptyToNull()
  });
}

export default function ScheduleUserAdd() {
  const { t } = useTranslation();
  useDocumentTitle(t('scheduleUser.add.headline'));

  const navigate = useNavigate();
  const [userValues, setUserValues] = useState<Record<string,string>>({});
  const [scheduleValues, setScheduleValues] = useState<Map<number,string>>(new Map());

  const useFormResult = useForm({
    resolver: yupResolver(getSchema()),
  });

  const prepareRelations = async () => {
    try {
      const userValuesResponse = await axios.get('/api/scheduleUsers/userValues');
      setUserValues(userValuesResponse.data);
      const scheduleValuesResponse = await axios.get('/api/scheduleUsers/scheduleValues');
      setScheduleValues(scheduleValuesResponse.data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  useEffect(() => {
    prepareRelations();
  }, []);

  const createScheduleUser = async (data: ScheduleUserDTO) => {
    window.scrollTo(0, 0);
    try {
      await axios.post('/api/scheduleUsers', data);
      navigate('/scheduleUsers', {
            state: {
              msgSuccess: t('scheduleUser.create.success')
            }
          });
    } catch (error: any) {
      handleServerError(error, navigate, useFormResult.setError, t);
    }
  };

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('scheduleUser.add.headline')}</h1>
      <div>
        <Link to="/scheduleUsers" className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-4 rounded px-5 py-2">{t('scheduleUser.add.back')}</Link>
      </div>
    </div>
    <form onSubmit={useFormResult.handleSubmit(createScheduleUser)} noValidate>
      <InputRow useFormResult={useFormResult} object="scheduleUser" field="role" required={true} />
      <InputRow useFormResult={useFormResult} object="scheduleUser" field="user" type="select" options={userValues} />
      <InputRow useFormResult={useFormResult} object="scheduleUser" field="schedule" type="select" options={scheduleValues} />
      <input type="submit" value={t('scheduleUser.add.headline')} className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2 mt-6" />
    </form>
  </>);
}
