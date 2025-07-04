import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate, useParams } from 'react-router';
import { handleServerError, setYupDefaults } from 'app/common/utils';
import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import { MemberVoteDTO } from 'app/member-vote/member-vote-model';
import axios from 'axios';
import InputRow from 'app/common/input-row/input-row';
import useDocumentTitle from 'app/common/use-document-title';
import * as yup from 'yup';


function getSchema() {
  setYupDefaults();
  return yup.object({
    voter: yup.number().integer().emptyToNull().required(),
    location: yup.number().integer().emptyToNull()
  });
}

export default function MemberVoteEdit() {
  const { t } = useTranslation();
  useDocumentTitle(t('memberVote.edit.headline'));

  const navigate = useNavigate();
  const [locationValues, setLocationValues] = useState<Map<number,string>>(new Map());
  const params = useParams();
  const currentId = +params.id!;

  const useFormResult = useForm({
    resolver: yupResolver(getSchema()),
  });

  const prepareForm = async () => {
    try {
      const locationValuesResponse = await axios.get('/api/memberVotes/locationValues');
      setLocationValues(locationValuesResponse.data);
      const data = (await axios.get('/api/memberVotes/' + currentId)).data;
      useFormResult.reset(data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  useEffect(() => {
    prepareForm();
  }, []);

  const updateMemberVote = async (data: MemberVoteDTO) => {
    window.scrollTo(0, 0);
    try {
      await axios.put('/api/memberVotes/' + currentId, data);
      navigate('/memberVotes', {
            state: {
              msgSuccess: t('memberVote.update.success')
            }
          });
    } catch (error: any) {
      handleServerError(error, navigate, useFormResult.setError, t);
    }
  };

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('memberVote.edit.headline')}</h1>
      <div>
        <Link to="/memberVotes" className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-4 rounded px-5 py-2">{t('memberVote.edit.back')}</Link>
      </div>
    </div>
    <form onSubmit={useFormResult.handleSubmit(updateMemberVote)} noValidate>
      <InputRow useFormResult={useFormResult} object="memberVote" field="id" disabled={true} type="number" />
      <InputRow useFormResult={useFormResult} object="memberVote" field="voter" required={true} type="number" />
      <InputRow useFormResult={useFormResult} object="memberVote" field="location" type="select" options={locationValues} />
      <input type="submit" value={t('memberVote.edit.headline')} className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2 mt-6" />
    </form>
  </>);
}
