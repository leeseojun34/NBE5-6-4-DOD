import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate } from 'react-router';
import { handleServerError, setYupDefaults } from 'app/common/utils';
import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import { GroupUserDTO } from 'app/group-user/group-user-model';
import axios from 'axios';
import InputRow from 'app/common/input-row/input-row';
import useDocumentTitle from 'app/common/use-document-title';
import * as yup from 'yup';


function getSchema() {
  setYupDefaults();
  return yup.object({
    groupRole: yup.string().emptyToNull().max(255).required(),
    user: yup.string().emptyToNull().max(255),
    group: yup.number().integer().emptyToNull()
  });
}

export default function GroupUserAdd() {
  const { t } = useTranslation();
  useDocumentTitle(t('groupUser.add.headline'));

  const navigate = useNavigate();
  const [userValues, setUserValues] = useState<Record<string,string>>({});
  const [groupValues, setGroupValues] = useState<Map<number,string>>(new Map());

  const useFormResult = useForm({
    resolver: yupResolver(getSchema()),
  });

  const prepareRelations = async () => {
    try {
      const userValuesResponse = await axios.get('/api/groupUsers/userValues');
      setUserValues(userValuesResponse.data);
      const groupValuesResponse = await axios.get('/api/groupUsers/groupValues');
      setGroupValues(groupValuesResponse.data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  useEffect(() => {
    prepareRelations();
  }, []);

  const createGroupUser = async (data: GroupUserDTO) => {
    window.scrollTo(0, 0);
    try {
      await axios.post('/api/groupUsers', data);
      navigate('/groupUsers', {
            state: {
              msgSuccess: t('groupUser.create.success')
            }
          });
    } catch (error: any) {
      handleServerError(error, navigate, useFormResult.setError, t);
    }
  };

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('groupUser.add.headline')}</h1>
      <div>
        <Link to="/groupUsers" className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-4 rounded px-5 py-2">{t('groupUser.add.back')}</Link>
      </div>
    </div>
    <form onSubmit={useFormResult.handleSubmit(createGroupUser)} noValidate>
      <InputRow useFormResult={useFormResult} object="groupUser" field="groupRole" required={true} />
      <InputRow useFormResult={useFormResult} object="groupUser" field="user" type="select" options={userValues} />
      <InputRow useFormResult={useFormResult} object="groupUser" field="group" type="select" options={groupValues} />
      <input type="submit" value={t('groupUser.add.headline')} className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2 mt-6" />
    </form>
  </>);
}
