import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate } from 'react-router';
import { handleServerError, setYupDefaults } from 'app/common/utils';
import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import { UserVoteDTO } from 'app/user-vote/user-vote-model';
import axios from 'axios';
import InputRow from 'app/common/input-row/input-row';
import useDocumentTitle from 'app/common/use-document-title';
import * as yup from 'yup';


function getSchema() {
  setYupDefaults();
  return yup.object({
    userId: yup.number().integer().emptyToNull().required(),
    locationCandidate: yup.number().integer().emptyToNull()
  });
}

export default function UserVoteAdd() {
  const { t } = useTranslation();
  useDocumentTitle(t('userVote.add.headline'));

  const navigate = useNavigate();
  const [locationCandidateValues, setLocationCandidateValues] = useState<Map<number,string>>(new Map());

  const useFormResult = useForm({
    resolver: yupResolver(getSchema()),
  });

  const getMessage = (key: string) => {
    const messages: Record<string, string> = {
      USER_VOTE_LOCATION_CANDIDATE_UNIQUE: t('Exists.userVote.location-candidate')
    };
    return messages[key];
  };

  const prepareRelations = async () => {
    try {
      const locationCandidateValuesResponse = await axios.get('/api/userVotes/locationCandidateValues');
      setLocationCandidateValues(locationCandidateValuesResponse.data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  useEffect(() => {
    prepareRelations();
  }, []);

  const createUserVote = async (data: UserVoteDTO) => {
    window.scrollTo(0, 0);
    try {
      await axios.post('/api/userVotes', data);
      navigate('/userVotes', {
            state: {
              msgSuccess: t('userVote.create.success')
            }
          });
    } catch (error: any) {
      handleServerError(error, navigate, useFormResult.setError, t, getMessage);
    }
  };

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('userVote.add.headline')}</h1>
      <div>
        <Link to="/userVotes" className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-4 rounded px-5 py-2">{t('userVote.add.back')}</Link>
      </div>
    </div>
    <form onSubmit={useFormResult.handleSubmit(createUserVote)} noValidate>
      <InputRow useFormResult={useFormResult} object="userVote" field="userId" required={true} type="number" />
      <InputRow useFormResult={useFormResult} object="userVote" field="locationCandidate" type="select" options={locationCandidateValues} />
      <input type="submit" value={t('userVote.add.headline')} className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2 mt-6" />
    </form>
  </>);
}
