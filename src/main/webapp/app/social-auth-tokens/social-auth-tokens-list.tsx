import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate } from 'react-router';
import { handleServerError } from 'app/common/utils';
import { SocialAuthTokensDTO } from 'app/social-auth-tokens/social-auth-tokens-model';
import axios from 'axios';
import useDocumentTitle from 'app/common/use-document-title';


export default function SocialAuthTokensList() {
  const { t } = useTranslation();
  useDocumentTitle(t('socialAuthTokens.list.headline'));

  const [socialAuthTokenses, setSocialAuthTokenses] = useState<SocialAuthTokensDTO[]>([]);
  const navigate = useNavigate();

  const getAllSocialAuthTokenses = async () => {
    try {
      const response = await axios.get('/api/socialAuthTokenss');
      setSocialAuthTokenses(response.data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  const confirmDelete = async (id: number) => {
    if (!confirm(t('delete.confirm'))) {
      return;
    }
    try {
      await axios.delete('/api/socialAuthTokenss/' + id);
      navigate('/socialAuthTokenss', {
            state: {
              msgInfo: t('socialAuthTokens.delete.success')
            }
          });
      getAllSocialAuthTokenses();
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  useEffect(() => {
    getAllSocialAuthTokenses();
  }, []);

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('socialAuthTokens.list.headline')}</h1>
      <div>
        <Link to="/socialAuthTokenss/add" className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2">{t('socialAuthTokens.list.createNew')}</Link>
      </div>
    </div>
    {!socialAuthTokenses || socialAuthTokenses.length === 0 ? (
    <div>{t('socialAuthTokens.list.empty')}</div>
    ) : (
    <div className="overflow-x-auto">
      <table className="w-full">
        <thead>
          <tr>
            <th scope="col" className="text-left p-2">{t('socialAuthTokens.id.label')}</th>
            <th scope="col" className="text-left p-2">{t('socialAuthTokens.accessToken.label')}</th>
            <th scope="col" className="text-left p-2">{t('socialAuthTokens.refreshToken.label')}</th>
            <th scope="col" className="text-left p-2">{t('socialAuthTokens.tokenType.label')}</th>
            <th scope="col" className="text-left p-2">{t('socialAuthTokens.expiresAt.label')}</th>
            <th scope="col" className="text-left p-2">{t('socialAuthTokens.provider.label')}</th>
            <th scope="col" className="text-left p-2">{t('socialAuthTokens.member.label')}</th>
            <th></th>
          </tr>
        </thead>
        <tbody className="border-t-2 border-black">
          {socialAuthTokenses.map((socialAuthTokens) => (
          <tr key={socialAuthTokens.id} className="odd:bg-gray-100">
            <td className="p-2">{socialAuthTokens.id}</td>
            <td className="p-2">{socialAuthTokens.accessToken}</td>
            <td className="p-2">{socialAuthTokens.refreshToken}</td>
            <td className="p-2">{socialAuthTokens.tokenType}</td>
            <td className="p-2">{socialAuthTokens.expiresAt}</td>
            <td className="p-2">{socialAuthTokens.provider}</td>
            <td className="p-2">{socialAuthTokens.member}</td>
            <td className="p-2">
              <div className="float-right whitespace-nowrap">
                <Link to={'/socialAuthTokenss/edit/' + socialAuthTokens.id} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm">{t('socialAuthTokens.list.edit')}</Link>
                <span> </span>
                <button type="button" onClick={() => confirmDelete(socialAuthTokens.id!)} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm">{t('socialAuthTokens.list.delete')}</button>
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
