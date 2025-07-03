import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate, useParams } from 'react-router';
import { handleServerError, setYupDefaults } from 'app/common/utils';
import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import { MeetingDTO } from 'app/meeting/meeting-model';
import axios from 'axios';
import InputRow from 'app/common/input-row/input-row';
import useDocumentTitle from 'app/common/use-document-title';
import * as yup from 'yup';


function getSchema() {
  setYupDefaults();
  return yup.object({
    meetingPlatform: yup.string().emptyToNull().max(255).required(),
    platformUrl: yup.string().emptyToNull().max(255).required(),
    schedule: yup.number().integer().emptyToNull()
  });
}

export default function MeetingEdit() {
  const { t } = useTranslation();
  useDocumentTitle(t('meeting.edit.headline'));

  const navigate = useNavigate();
  const [scheduleValues, setScheduleValues] = useState<Map<number,string>>(new Map());
  const params = useParams();
  const currentMeetingId = +params.meetingId!;

  const useFormResult = useForm({
    resolver: yupResolver(getSchema()),
  });

  const getMessage = (key: string) => {
    const messages: Record<string, string> = {
      MEETING_SCHEDULE_UNIQUE: t('Exists.meeting.schedule')
    };
    return messages[key];
  };

  const prepareForm = async () => {
    try {
      const scheduleValuesResponse = await axios.get('/api/meetings/scheduleValues');
      setScheduleValues(scheduleValuesResponse.data);
      const data = (await axios.get('/api/meetings/' + currentMeetingId)).data;
      useFormResult.reset(data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  useEffect(() => {
    prepareForm();
  }, []);

  const updateMeeting = async (data: MeetingDTO) => {
    window.scrollTo(0, 0);
    try {
      await axios.put('/api/meetings/' + currentMeetingId, data);
      navigate('/meetings', {
            state: {
              msgSuccess: t('meeting.update.success')
            }
          });
    } catch (error: any) {
      handleServerError(error, navigate, useFormResult.setError, t, getMessage);
    }
  };

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('meeting.edit.headline')}</h1>
      <div>
        <Link to="/meetings" className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-4 rounded px-5 py-2">{t('meeting.edit.back')}</Link>
      </div>
    </div>
    <form onSubmit={useFormResult.handleSubmit(updateMeeting)} noValidate>
      <InputRow useFormResult={useFormResult} object="meeting" field="meetingId" disabled={true} type="number" />
      <InputRow useFormResult={useFormResult} object="meeting" field="meetingPlatform" required={true} />
      <InputRow useFormResult={useFormResult} object="meeting" field="platformUrl" required={true} />
      <InputRow useFormResult={useFormResult} object="meeting" field="schedule" type="select" options={scheduleValues} />
      <input type="submit" value={t('meeting.edit.headline')} className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2 mt-6" />
    </form>
  </>);
}
