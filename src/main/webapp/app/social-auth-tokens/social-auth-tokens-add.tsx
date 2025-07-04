import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate } from 'react-router';
import { handleServerError, setYupDefaults } from 'app/common/utils';
import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import { SocialAuthTokensDTO } from 'app/social-auth-tokens/social-auth-tokens-model';
import axios from 'axios';
import InputRow from 'app/common/input-row/input-row';
import useDocumentTitle from 'app/common/use-document-title';
import * as yup from 'yup';


function getSchema() {
  setYupDefaults();
  return yup.object({
    accessToken: yup.string().emptyToNull().max(255).required(),
    refreshToken: yup.string().emptyToNull().max(255).required(),
    tokenType: yup.string().emptyToNull().max(255).required(),
    expiresAt: yup.string().emptyToNull().required(),
    provider: yup.string().emptyToNull().max(255).required(),
    member: yup.string().emptyToNull().max(255)
  });
}

export default function SocialAuthTokensAdd() {
  const { t } = useTranslation();
  useDocumentTitle(t('socialAuthTokens.add.headline'));

  const navigate = useNavigate();
  const [memberValues, setMemberValues] = useState<Record<string,string>>({});

  const useFormResult = useForm({
    resolver: yupResolver(getSchema()),
  });

  const getMessage = (key: string) => {
    const messages: Record<string, string> = {
      SOCIAL_AUTH_TOKENS_ACCESS_TOKEN_UNIQUE: t('exists.socialAuthTokens.accessToken'),
      SOCIAL_AUTH_TOKENS_REFRESH_TOKEN_UNIQUE: t('exists.socialAuthTokens.refreshToken')
    };
    return messages[key];
  };

  const prepareRelations = async () => {
    try {
      const memberValuesResponse = await axios.get('/api/socialAuthTokenss/memberValues');
      setMemberValues(memberValuesResponse.data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  useEffect(() => {
    prepareRelations();
  }, []);

  const createSocialAuthTokens = async (data: SocialAuthTokensDTO) => {
    window.scrollTo(0, 0);
    try {
      await axios.post('/api/socialAuthTokenss', data);
      navigate('/socialAuthTokenss', {
            state: {
              msgSuccess: t('socialAuthTokens.create.success')
            }
          });
    } catch (error: any) {
      handleServerError(error, navigate, useFormResult.setError, t, getMessage);
    }
  };

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('socialAuthTokens.add.headline')}</h1>
      <div>
        <Link to="/socialAuthTokenss" className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-4 rounded px-5 py-2">{t('socialAuthTokens.add.back')}</Link>
      </div>
    </div>
    <form onSubmit={useFormResult.handleSubmit(createSocialAuthTokens)} noValidate>
      <InputRow useFormResult={useFormResult} object="socialAuthTokens" field="accessToken" required={true} />
      <InputRow useFormResult={useFormResult} object="socialAuthTokens" field="refreshToken" required={true} />
      <InputRow useFormResult={useFormResult} object="socialAuthTokens" field="tokenType" required={true} />
      <InputRow useFormResult={useFormResult} object="socialAuthTokens" field="expiresAt" required={true} type="datetimepicker" />
      <InputRow useFormResult={useFormResult} object="socialAuthTokens" field="provider" required={true} />
      <InputRow useFormResult={useFormResult} object="socialAuthTokens" field="member" type="select" options={memberValues} />
      <input type="submit" value={t('socialAuthTokens.add.headline')} className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2 mt-6" />
    </form>
  </>);
}
