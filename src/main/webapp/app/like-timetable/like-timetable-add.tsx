import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate } from 'react-router';
import { handleServerError, setYupDefaults } from 'app/common/utils';
import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import { LikeTimetableDTO } from 'app/like-timetable/like-timetable-model';
import axios from 'axios';
import InputRow from 'app/common/input-row/input-row';
import useDocumentTitle from 'app/common/use-document-title';
import * as yup from 'yup';


function getSchema() {
  setYupDefaults();
  return yup.object({
    startTime: yup.string().emptyToNull().required(),
    endTime: yup.string().emptyToNull().required(),
    weekday: yup.string().emptyToNull().max(255).required(),
    user: yup.string().emptyToNull().max(255)
  });
}

export default function LikeTimetableAdd() {
  const { t } = useTranslation();
  useDocumentTitle(t('likeTimetable.add.headline'));

  const navigate = useNavigate();
  const [userValues, setUserValues] = useState<Record<string,string>>({});

  const useFormResult = useForm({
    resolver: yupResolver(getSchema()),
  });

  const prepareRelations = async () => {
    try {
      const userValuesResponse = await axios.get('/api/likeTimetables/userValues');
      setUserValues(userValuesResponse.data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  useEffect(() => {
    prepareRelations();
  }, []);

  const createLikeTimetable = async (data: LikeTimetableDTO) => {
    window.scrollTo(0, 0);
    try {
      await axios.post('/api/likeTimetables', data);
      navigate('/likeTimetables', {
            state: {
              msgSuccess: t('likeTimetable.create.success')
            }
          });
    } catch (error: any) {
      handleServerError(error, navigate, useFormResult.setError, t);
    }
  };

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('likeTimetable.add.headline')}</h1>
      <div>
        <Link to="/likeTimetables" className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-4 rounded px-5 py-2">{t('likeTimetable.add.back')}</Link>
      </div>
    </div>
    <form onSubmit={useFormResult.handleSubmit(createLikeTimetable)} noValidate>
      <InputRow useFormResult={useFormResult} object="likeTimetable" field="startTime" required={true} type="datetimepicker" />
      <InputRow useFormResult={useFormResult} object="likeTimetable" field="endTime" required={true} type="datetimepicker" />
      <InputRow useFormResult={useFormResult} object="likeTimetable" field="weekday" required={true} />
      <InputRow useFormResult={useFormResult} object="likeTimetable" field="user" type="select" options={userValues} />
      <input type="submit" value={t('likeTimetable.add.headline')} className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2 mt-6" />
    </form>
  </>);
}
