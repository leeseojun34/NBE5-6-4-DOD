import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate, useParams } from 'react-router';
import { handleServerError, setYupDefaults } from 'app/common/utils';
import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import { EventDTO } from 'app/event/event-model';
import axios from 'axios';
import InputRow from 'app/common/input-row/input-row';
import useDocumentTitle from 'app/common/use-document-title';
import * as yup from 'yup';


function getSchema() {
  setYupDefaults();
  return yup.object({
    title: yup.string().emptyToNull().max(255).required(),
    description: yup.string().emptyToNull(),
    meetingType: yup.string().emptyToNull().max(255).required(),
    maxMember: yup.number().integer().emptyToNull().required(),
    group: yup.number().integer().emptyToNull()
  });
}

export default function EventEdit() {
  const { t } = useTranslation();
  useDocumentTitle(t('event.edit.headline'));

  const navigate = useNavigate();
  const [groupValues, setGroupValues] = useState<Map<number,string>>(new Map());
  const params = useParams();
  const currentId = +params.id!;

  const useFormResult = useForm({
    resolver: yupResolver(getSchema()),
  });

  const prepareForm = async () => {
    try {
      const groupValuesResponse = await axios.get('/api/events/groupValues');
      setGroupValues(groupValuesResponse.data);
      const data = (await axios.get('/api/events/' + currentId)).data;
      useFormResult.reset(data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  useEffect(() => {
    prepareForm();
  }, []);

  const updateEvent = async (data: EventDTO) => {
    window.scrollTo(0, 0);
    try {
      await axios.put('/api/events/' + currentId, data);
      navigate('/events', {
            state: {
              msgSuccess: t('event.update.success')
            }
          });
    } catch (error: any) {
      handleServerError(error, navigate, useFormResult.setError, t);
    }
  };

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('event.edit.headline')}</h1>
      <div>
        <Link to="/events" className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-4 rounded px-5 py-2">{t('event.edit.back')}</Link>
      </div>
    </div>
    <form onSubmit={useFormResult.handleSubmit(updateEvent)} noValidate>
      <InputRow useFormResult={useFormResult} object="event" field="id" disabled={true} type="number" />
      <InputRow useFormResult={useFormResult} object="event" field="title" required={true} />
      <InputRow useFormResult={useFormResult} object="event" field="description" type="textarea" />
      <InputRow useFormResult={useFormResult} object="event" field="meetingType" required={true} />
      <InputRow useFormResult={useFormResult} object="event" field="maxMember" required={true} type="number" />
      <InputRow useFormResult={useFormResult} object="event" field="group" type="select" options={groupValues} />
      <input type="submit" value={t('event.edit.headline')} className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2 mt-6" />
    </form>
  </>);
}
