import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate, useParams } from 'react-router';
import { handleServerError, setYupDefaults } from 'app/common/utils';
import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import { EventMemberDTO } from 'app/event-member/event-member-model';
import axios from 'axios';
import InputRow from 'app/common/input-row/input-row';
import useDocumentTitle from 'app/common/use-document-title';
import * as yup from 'yup';


function getSchema() {
  setYupDefaults();
  return yup.object({
    role: yup.string().emptyToNull().max(255).required(),
    member: yup.string().emptyToNull().max(255),
    event: yup.number().integer().emptyToNull()
  });
}

export default function EventMemberEdit() {
  const { t } = useTranslation();
  useDocumentTitle(t('eventMember.edit.headline'));

  const navigate = useNavigate();
  const [memberValues, setMemberValues] = useState<Record<string,string>>({});
  const [eventValues, setEventValues] = useState<Map<number,string>>(new Map());
  const params = useParams();
  const currentId = +params.id!;

  const useFormResult = useForm({
    resolver: yupResolver(getSchema()),
  });

  const prepareForm = async () => {
    try {
      const memberValuesResponse = await axios.get('/api/eventMembers/memberValues');
      setMemberValues(memberValuesResponse.data);
      const eventValuesResponse = await axios.get('/api/eventMembers/eventValues');
      setEventValues(eventValuesResponse.data);
      const data = (await axios.get('/api/eventMembers/' + currentId)).data;
      useFormResult.reset(data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  useEffect(() => {
    prepareForm();
  }, []);

  const updateEventMember = async (data: EventMemberDTO) => {
    window.scrollTo(0, 0);
    try {
      await axios.put('/api/eventMembers/' + currentId, data);
      navigate('/eventMembers', {
            state: {
              msgSuccess: t('eventMember.update.success')
            }
          });
    } catch (error: any) {
      handleServerError(error, navigate, useFormResult.setError, t);
    }
  };

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('eventMember.edit.headline')}</h1>
      <div>
        <Link to="/eventMembers" className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-4 rounded px-5 py-2">{t('eventMember.edit.back')}</Link>
      </div>
    </div>
    <form onSubmit={useFormResult.handleSubmit(updateEventMember)} noValidate>
      <InputRow useFormResult={useFormResult} object="eventMember" field="id" disabled={true} type="number" />
      <InputRow useFormResult={useFormResult} object="eventMember" field="role" required={true} />
      <InputRow useFormResult={useFormResult} object="eventMember" field="member" type="select" options={memberValues} />
      <InputRow useFormResult={useFormResult} object="eventMember" field="event" type="select" options={eventValues} />
      <input type="submit" value={t('eventMember.edit.headline')} className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2 mt-6" />
    </form>
  </>);
}
