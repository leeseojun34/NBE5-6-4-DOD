import React from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate } from 'react-router';
import { handleServerError, setYupDefaults } from 'app/common/utils';
import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import { MemberDTO } from 'app/member/member-model';
import axios from 'axios';
import InputRow from 'app/common/input-row/input-row';
import useDocumentTitle from 'app/common/use-document-title';
import * as yup from 'yup';


function getSchema() {
  setYupDefaults();
  return yup.object({
    id: yup.string().emptyToNull().max(255),
    password: yup.string().emptyToNull().max(255).required(),
    provider: yup.string().emptyToNull().max(255).required(),
    role: yup.string().emptyToNull().max(255).required(),
    email: yup.string().emptyToNull().max(255).required(),
    name: yup.string().emptyToNull().max(255).required(),
    profileImageNumber: yup.number().integer().emptyToNull().required(),
    tel: yup.string().emptyToNull().max(255).required()
  });
}

export default function MemberAdd() {
  const { t } = useTranslation();
  useDocumentTitle(t('member.add.headline'));

  const navigate = useNavigate();

  const useFormResult = useForm({
    resolver: yupResolver(getSchema()),
  });

  const getMessage = (key: string) => {
    const messages: Record<string, string> = {
      MEMBER_ID_VALID: t('exists.member.id'),
      MEMBER_EMAIL_UNIQUE: t('exists.member.email'),
      MEMBER_TEL_UNIQUE: t('exists.member.tel')
    };
    return messages[key];
  };

  const createMember = async (data: MemberDTO) => {
    window.scrollTo(0, 0);
    try {
      await axios.post('/api/members', data);
      navigate('/members', {
            state: {
              msgSuccess: t('member.create.success')
            }
          });
    } catch (error: any) {
      handleServerError(error, navigate, useFormResult.setError, t, getMessage);
    }
  };

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('member.add.headline')}</h1>
      <div>
        <Link to="/members" className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-4 rounded px-5 py-2">{t('member.add.back')}</Link>
      </div>
    </div>
    <form onSubmit={useFormResult.handleSubmit(createMember)} noValidate>
      <InputRow useFormResult={useFormResult} object="member" field="id" required={true} />
      <InputRow useFormResult={useFormResult} object="member" field="password" required={true} />
      <InputRow useFormResult={useFormResult} object="member" field="provider" required={true} />
      <InputRow useFormResult={useFormResult} object="member" field="role" required={true} />
      <InputRow useFormResult={useFormResult} object="member" field="email" required={true} />
      <InputRow useFormResult={useFormResult} object="member" field="name" required={true} />
      <InputRow useFormResult={useFormResult} object="member" field="profileImageNumber" required={true} type="number" />
      <InputRow useFormResult={useFormResult} object="member" field="tel" required={true} />
      <input type="submit" value={t('member.add.headline')} className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2 mt-6" />
    </form>
  </>);
}
