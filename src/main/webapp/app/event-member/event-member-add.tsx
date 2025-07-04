import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate } from 'react-router';
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

export default function EventMemberAdd() {
  const { t } = useTranslation();
  useDocumentTitle(t('eventMember.add.headline'));

  const navigate = useNavigate();
  const [memberValues, setMemberValues] = useState<Record<string,string>>({});
  const [eventValues, setEventValues] = useState<Map<number,string>>(new Map());

  const useFormResult = useForm({
    resolver: yupResolver(getSchema()),
  });

  const prepareRelations = async () => {
    try {
      const memberValuesResponse = await axios.get('/api/eventMembers/memberValues');
      setMemberValues(memberValuesResponse.data);
      const eventValuesResponse = await axios.get('/api/eventMembers/eventValues');
      setEventValues(eventValuesResponse.data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  useEffect(() => {
    prepareRelations();
  }, []);

  const createEventMember = async (data: EventMemberDTO) => {
    window.scrollTo(0, 0);
    try {
      await axios.post('/api/eventMembers', data);
      navigate('/eventMembers', {
            state: {
              msgSuccess: t('eventMember.create.success')
            }
          });
    } catch (error: any) {
      handleServerError(error, navigate, useFormResult.setError, t);
    }
  };

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('eventMember.add.headline')}</h1>
      <div>
        <Link to="/eventMembers" className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-4 rounded px-5 py-2">{t('eventMember.add.back')}</Link>
      </div>
    </div>
    <form onSubmit={useFormResult.handleSubmit(createEventMember)} noValidate>
      <InputRow useFormResult={useFormResult} object="eventMember" field="role" required={true} />
      <InputRow useFormResult={useFormResult} object="eventMember" field="member" type="select" options={memberValues} />
      <InputRow useFormResult={useFormResult} object="eventMember" field="event" type="select" options={eventValues} />
      <input type="submit" value={t('eventMember.add.headline')} className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2 mt-6" />
    </form>
  </>);
}
