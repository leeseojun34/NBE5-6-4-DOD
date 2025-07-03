import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate } from 'react-router';
import { handleServerError } from 'app/common/utils';
import { UserVoteDTO } from 'app/user-vote/user-vote-model';
import axios from 'axios';
import useDocumentTitle from 'app/common/use-document-title';


export default function UserVoteList() {
  const { t } = useTranslation();
  useDocumentTitle(t('userVote.list.headline'));

  const [userVotes, setUserVotes] = useState<UserVoteDTO[]>([]);
  const navigate = useNavigate();

  const getAllUserVotes = async () => {
    try {
      const response = await axios.get('/api/userVotes');
      setUserVotes(response.data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  const confirmDelete = async (userVoteId: number) => {
    if (!confirm(t('delete.confirm'))) {
      return;
    }
    try {
      await axios.delete('/api/userVotes/' + userVoteId);
      navigate('/userVotes', {
            state: {
              msgInfo: t('userVote.delete.success')
            }
          });
      getAllUserVotes();
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  useEffect(() => {
    getAllUserVotes();
  }, []);

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('userVote.list.headline')}</h1>
      <div>
        <Link to="/userVotes/add" className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2">{t('userVote.list.createNew')}</Link>
      </div>
    </div>
    {!userVotes || userVotes.length === 0 ? (
    <div>{t('userVote.list.empty')}</div>
    ) : (
    <div className="overflow-x-auto">
      <table className="w-full">
        <thead>
          <tr>
            <th scope="col" className="text-left p-2">{t('userVote.userVoteId.label')}</th>
            <th scope="col" className="text-left p-2">{t('userVote.userId.label')}</th>
            <th scope="col" className="text-left p-2">{t('userVote.locationCandidate.label')}</th>
            <th></th>
          </tr>
        </thead>
        <tbody className="border-t-2 border-black">
          {userVotes.map((userVote) => (
          <tr key={userVote.userVoteId} className="odd:bg-gray-100">
            <td className="p-2">{userVote.userVoteId}</td>
            <td className="p-2">{userVote.userId}</td>
            <td className="p-2">{userVote.locationCandidate}</td>
            <td className="p-2">
              <div className="float-right whitespace-nowrap">
                <Link to={'/userVotes/edit/' + userVote.userVoteId} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm">{t('userVote.list.edit')}</Link>
                <span> </span>
                <button type="button" onClick={() => confirmDelete(userVote.userVoteId!)} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm">{t('userVote.list.delete')}</button>
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
