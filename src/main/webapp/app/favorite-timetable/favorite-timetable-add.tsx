import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate } from 'react-router';
import { handleServerError, setYupDefaults } from 'app/common/utils';
import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import { FavoriteTimetableDTO } from 'app/favorite-timetable/favorite-timetable-model';
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
    member: yup.string().emptyToNull().max(255)
  });
}

export default function FavoriteTimetableAdd() {
  const { t } = useTranslation();
  useDocumentTitle(t('favoriteTimetable.add.headline'));

  const navigate = useNavigate();
  const [memberValues, setMemberValues] = useState<Record<string,string>>({});

  const useFormResult = useForm({
    resolver: yupResolver(getSchema()),
  });

  const prepareRelations = async () => {
    try {
      const memberValuesResponse = await axios.get('/api/favoriteTimetables/memberValues');
      setMemberValues(memberValuesResponse.data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  useEffect(() => {
    prepareRelations();
  }, []);

  const createFavoriteTimetable = async (data: FavoriteTimetableDTO) => {
    window.scrollTo(0, 0);
    try {
      await axios.post('/api/favoriteTimetables', data);
      navigate('/favoriteTimetables', {
            state: {
              msgSuccess: t('favoriteTimetable.create.success')
            }
          });
    } catch (error: any) {
      handleServerError(error, navigate, useFormResult.setError, t);
    }
  };

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('favoriteTimetable.add.headline')}</h1>
      <div>
        <Link to="/favoriteTimetables" className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-4 rounded px-5 py-2">{t('favoriteTimetable.add.back')}</Link>
      </div>
    </div>
    <form onSubmit={useFormResult.handleSubmit(createFavoriteTimetable)} noValidate>
      <InputRow useFormResult={useFormResult} object="favoriteTimetable" field="startTime" required={true} type="datetimepicker" />
      <InputRow useFormResult={useFormResult} object="favoriteTimetable" field="endTime" required={true} type="datetimepicker" />
      <InputRow useFormResult={useFormResult} object="favoriteTimetable" field="weekday" required={true} />
      <InputRow useFormResult={useFormResult} object="favoriteTimetable" field="member" type="select" options={memberValues} />
      <input type="submit" value={t('favoriteTimetable.add.headline')} className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2 mt-6" />
    </form>
  </>);
}
