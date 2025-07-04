import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate } from 'react-router';
import { handleServerError } from 'app/common/utils';
import { MemberVoteDTO } from 'app/member-vote/member-vote-model';
import axios from 'axios';
import useDocumentTitle from 'app/common/use-document-title';


export default function MemberVoteList() {
  const { t } = useTranslation();
  useDocumentTitle(t('memberVote.list.headline'));

  const [memberVotes, setMemberVotes] = useState<MemberVoteDTO[]>([]);
  const navigate = useNavigate();

  const getAllMemberVotes = async () => {
    try {
      const response = await axios.get('/api/memberVotes');
      setMemberVotes(response.data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  const confirmDelete = async (id: number) => {
    if (!confirm(t('delete.confirm'))) {
      return;
    }
    try {
      await axios.delete('/api/memberVotes/' + id);
      navigate('/memberVotes', {
            state: {
              msgInfo: t('memberVote.delete.success')
            }
          });
      getAllMemberVotes();
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  useEffect(() => {
    getAllMemberVotes();
  }, []);

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('memberVote.list.headline')}</h1>
      <div>
        <Link to="/memberVotes/add" className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2">{t('memberVote.list.createNew')}</Link>
      </div>
    </div>
    {!memberVotes || memberVotes.length === 0 ? (
    <div>{t('memberVote.list.empty')}</div>
    ) : (
    <div className="overflow-x-auto">
      <table className="w-full">
        <thead>
          <tr>
            <th scope="col" className="text-left p-2">{t('memberVote.id.label')}</th>
            <th scope="col" className="text-left p-2">{t('memberVote.voter.label')}</th>
            <th scope="col" className="text-left p-2">{t('memberVote.location.label')}</th>
            <th></th>
          </tr>
        </thead>
        <tbody className="border-t-2 border-black">
          {memberVotes.map((memberVote) => (
          <tr key={memberVote.id} className="odd:bg-gray-100">
            <td className="p-2">{memberVote.id}</td>
            <td className="p-2">{memberVote.voter}</td>
            <td className="p-2">{memberVote.location}</td>
            <td className="p-2">
              <div className="float-right whitespace-nowrap">
                <Link to={'/memberVotes/edit/' + memberVote.id} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm">{t('memberVote.list.edit')}</Link>
                <span> </span>
                <button type="button" onClick={() => confirmDelete(memberVote.id!)} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm">{t('memberVote.list.delete')}</button>
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
