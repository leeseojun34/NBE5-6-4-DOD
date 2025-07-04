import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate, useParams } from 'react-router';
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

export default function FavoriteTimetableEdit() {
  const { t } = useTranslation();
  useDocumentTitle(t('favoriteTimetable.edit.headline'));

  const navigate = useNavigate();
  const [memberValues, setMemberValues] = useState<Record<string,string>>({});
  const params = useParams();
  const currentId = +params.id!;

  const useFormResult = useForm({
    resolver: yupResolver(getSchema()),
  });

  const prepareForm = async () => {
    try {
      const memberValuesResponse = await axios.get('/api/favoriteTimetables/memberValues');
      setMemberValues(memberValuesResponse.data);
      const data = (await axios.get('/api/favoriteTimetables/' + currentId)).data;
      useFormResult.reset(data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  useEffect(() => {
    prepareForm();
  }, []);

  const updateFavoriteTimetable = async (data: FavoriteTimetableDTO) => {
    window.scrollTo(0, 0);
    try {
      await axios.put('/api/favoriteTimetables/' + currentId, data);
      navigate('/favoriteTimetables', {
            state: {
              msgSuccess: t('favoriteTimetable.update.success')
            }
          });
    } catch (error: any) {
      handleServerError(error, navigate, useFormResult.setError, t);
    }
  };

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('favoriteTimetable.edit.headline')}</h1>
      <div>
        <Link to="/favoriteTimetables" className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-4 rounded px-5 py-2">{t('favoriteTimetable.edit.back')}</Link>
      </div>
    </div>
    <form onSubmit={useFormResult.handleSubmit(updateFavoriteTimetable)} noValidate>
      <InputRow useFormResult={useFormResult} object="favoriteTimetable" field="id" disabled={true} type="number" />
      <InputRow useFormResult={useFormResult} object="favoriteTimetable" field="startTime" required={true} type="datetimepicker" />
      <InputRow useFormResult={useFormResult} object="favoriteTimetable" field="endTime" required={true} type="datetimepicker" />
      <InputRow useFormResult={useFormResult} object="favoriteTimetable" field="weekday" required={true} />
      <InputRow useFormResult={useFormResult} object="favoriteTimetable" field="member" type="select" options={memberValues} />
      <input type="submit" value={t('favoriteTimetable.edit.headline')} className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2 mt-6" />
    </form>
  </>);
}
