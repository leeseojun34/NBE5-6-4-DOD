import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate } from 'react-router';
import { handleServerError, setYupDefaults } from 'app/common/utils';
import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import { ScheduleMemberDTO } from 'app/schedule-member/schedule-member-model';
import axios from 'axios';
import InputRow from 'app/common/input-row/input-row';
import useDocumentTitle from 'app/common/use-document-title';
import * as yup from 'yup';


function getSchema() {
  setYupDefaults();
  return yup.object({
    role: yup.string().emptyToNull().max(255).required(),
    departLocationName: yup.string().emptyToNull().max(255),
    latitude: yup.number().emptyToNull(),
    longitude: yup.number().emptyToNull(),
    member: yup.string().emptyToNull().max(255),
    schedule: yup.number().integer().emptyToNull(),
    middleRegion: yup.number().integer().emptyToNull()
  });
}

export default function ScheduleMemberAdd() {
  const { t } = useTranslation();
  useDocumentTitle(t('scheduleMember.add.headline'));

  const navigate = useNavigate();
  const [memberValues, setMemberValues] = useState<Record<string,string>>({});
  const [scheduleValues, setScheduleValues] = useState<Map<number,string>>(new Map());
  const [middleRegionValues, setMiddleRegionValues] = useState<Map<number,string>>(new Map());

  const useFormResult = useForm({
    resolver: yupResolver(getSchema()),
  });

  const prepareRelations = async () => {
    try {
      const memberValuesResponse = await axios.get('/api/scheduleMembers/memberValues');
      setMemberValues(memberValuesResponse.data);
      const scheduleValuesResponse = await axios.get('/api/scheduleMembers/scheduleValues');
      setScheduleValues(scheduleValuesResponse.data);
      const middleRegionValuesResponse = await axios.get('/api/scheduleMembers/middleRegionValues');
      setMiddleRegionValues(middleRegionValuesResponse.data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  useEffect(() => {
    prepareRelations();
  }, []);

  const createScheduleMember = async (data: ScheduleMemberDTO) => {
    window.scrollTo(0, 0);
    try {
      await axios.post('/api/scheduleMembers', data);
      navigate('/scheduleMembers', {
            state: {
              msgSuccess: t('scheduleMember.create.success')
            }
          });
    } catch (error: any) {
      handleServerError(error, navigate, useFormResult.setError, t);
    }
  };

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('scheduleMember.add.headline')}</h1>
      <div>
        <Link to="/scheduleMembers" className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-4 rounded px-5 py-2">{t('scheduleMember.add.back')}</Link>
      </div>
    </div>
    <form onSubmit={useFormResult.handleSubmit(createScheduleMember)} noValidate>
      <InputRow useFormResult={useFormResult} object="scheduleMember" field="role" required={true} />
      <InputRow useFormResult={useFormResult} object="scheduleMember" field="departLocationName" />
      <InputRow useFormResult={useFormResult} object="scheduleMember" field="latitude" />
      <InputRow useFormResult={useFormResult} object="scheduleMember" field="longitude" />
      <InputRow useFormResult={useFormResult} object="scheduleMember" field="member" type="select" options={memberValues} />
      <InputRow useFormResult={useFormResult} object="scheduleMember" field="schedule" type="select" options={scheduleValues} />
      <InputRow useFormResult={useFormResult} object="scheduleMember" field="middleRegion" type="select" options={middleRegionValues} />
      <input type="submit" value={t('scheduleMember.add.headline')} className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2 mt-6" />
    </form>
  </>);
}
