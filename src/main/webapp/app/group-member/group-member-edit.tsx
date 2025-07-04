import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate, useParams } from 'react-router';
import { handleServerError, setYupDefaults } from 'app/common/utils';
import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import { GroupMemberDTO } from 'app/group-member/group-member-model';
import axios from 'axios';
import InputRow from 'app/common/input-row/input-row';
import useDocumentTitle from 'app/common/use-document-title';
import * as yup from 'yup';


function getSchema() {
  setYupDefaults();
  return yup.object({
    role: yup.string().emptyToNull().max(255).required(),
    member: yup.string().emptyToNull().max(255),
    group: yup.number().integer().emptyToNull()
  });
}

export default function GroupMemberEdit() {
  const { t } = useTranslation();
  useDocumentTitle(t('groupMember.edit.headline'));

  const navigate = useNavigate();
  const [memberValues, setMemberValues] = useState<Record<string,string>>({});
  const [groupValues, setGroupValues] = useState<Map<number,string>>(new Map());
  const params = useParams();
  const currentId = +params.id!;

  const useFormResult = useForm({
    resolver: yupResolver(getSchema()),
  });

  const prepareForm = async () => {
    try {
      const memberValuesResponse = await axios.get('/api/groupMembers/memberValues');
      setMemberValues(memberValuesResponse.data);
      const groupValuesResponse = await axios.get('/api/groupMembers/groupValues');
      setGroupValues(groupValuesResponse.data);
      const data = (await axios.get('/api/groupMembers/' + currentId)).data;
      useFormResult.reset(data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  useEffect(() => {
    prepareForm();
  }, []);

  const updateGroupMember = async (data: GroupMemberDTO) => {
    window.scrollTo(0, 0);
    try {
      await axios.put('/api/groupMembers/' + currentId, data);
      navigate('/groupMembers', {
            state: {
              msgSuccess: t('groupMember.update.success')
            }
          });
    } catch (error: any) {
      handleServerError(error, navigate, useFormResult.setError, t);
    }
  };

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('groupMember.edit.headline')}</h1>
      <div>
        <Link to="/groupMembers" className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-4 rounded px-5 py-2">{t('groupMember.edit.back')}</Link>
      </div>
    </div>
    <form onSubmit={useFormResult.handleSubmit(updateGroupMember)} noValidate>
      <InputRow useFormResult={useFormResult} object="groupMember" field="id" disabled={true} type="number" />
      <InputRow useFormResult={useFormResult} object="groupMember" field="role" required={true} />
      <InputRow useFormResult={useFormResult} object="groupMember" field="member" type="select" options={memberValues} />
      <InputRow useFormResult={useFormResult} object="groupMember" field="group" type="select" options={groupValues} />
      <input type="submit" value={t('groupMember.edit.headline')} className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2 mt-6" />
    </form>
  </>);
}
