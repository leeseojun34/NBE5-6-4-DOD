import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate } from 'react-router';
import { handleServerError } from 'app/common/utils';
import { MeetingDTO } from 'app/meeting/meeting-model';
import axios from 'axios';
import useDocumentTitle from 'app/common/use-document-title';


export default function MeetingList() {
  const { t } = useTranslation();
  useDocumentTitle(t('meeting.list.headline'));

  const [meetings, setMeetings] = useState<MeetingDTO[]>([]);
  const navigate = useNavigate();

  const getAllMeetings = async () => {
    try {
      const response = await axios.get('/api/meetings');
      setMeetings(response.data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  const confirmDelete = async (meetingId: number) => {
    if (!confirm(t('delete.confirm'))) {
      return;
    }
    try {
      await axios.delete('/api/meetings/' + meetingId);
      navigate('/meetings', {
            state: {
              msgInfo: t('meeting.delete.success')
            }
          });
      getAllMeetings();
    } catch (error: any) {
      if (error?.response?.data?.code === 'REFERENCED') {
        const messageParts = error.response.data.message.split(',');
        navigate('/meetings', {
              state: {
                msgError: t(messageParts[0]!, { id: messageParts[1]! })
              }
            });
        return;
      }
      handleServerError(error, navigate);
    }
  };

  useEffect(() => {
    getAllMeetings();
  }, []);

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('meeting.list.headline')}</h1>
      <div>
        <Link to="/meetings/add" className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2">{t('meeting.list.createNew')}</Link>
      </div>
    </div>
    {!meetings || meetings.length === 0 ? (
    <div>{t('meeting.list.empty')}</div>
    ) : (
    <div className="overflow-x-auto">
      <table className="w-full">
        <thead>
          <tr>
            <th scope="col" className="text-left p-2">{t('meeting.meetingId.label')}</th>
            <th scope="col" className="text-left p-2">{t('meeting.meetingPlatform.label')}</th>
            <th scope="col" className="text-left p-2">{t('meeting.platformUrl.label')}</th>
            <th scope="col" className="text-left p-2">{t('meeting.schedule.label')}</th>
            <th></th>
          </tr>
        </thead>
        <tbody className="border-t-2 border-black">
          {meetings.map((meeting) => (
          <tr key={meeting.meetingId} className="odd:bg-gray-100">
            <td className="p-2">{meeting.meetingId}</td>
            <td className="p-2">{meeting.meetingPlatform}</td>
            <td className="p-2">{meeting.platformUrl}</td>
            <td className="p-2">{meeting.schedule}</td>
            <td className="p-2">
              <div className="float-right whitespace-nowrap">
                <Link to={'/meetings/edit/' + meeting.meetingId} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm">{t('meeting.list.edit')}</Link>
                <span> </span>
                <button type="button" onClick={() => confirmDelete(meeting.meetingId!)} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm">{t('meeting.list.delete')}</button>
              </div>
            </td>
          </tr>
          ))}
        </tbody>
      </table>
    </div>
    )}
  </>);
}
