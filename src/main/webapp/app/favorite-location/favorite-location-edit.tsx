import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate, useParams } from 'react-router';
import { handleServerError, setYupDefaults } from 'app/common/utils';
import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import { FavoriteLocationDTO } from 'app/favorite-location/favorite-location-model';
import axios from 'axios';
import InputRow from 'app/common/input-row/input-row';
import useDocumentTitle from 'app/common/use-document-title';
import * as yup from 'yup';


function getSchema() {
  setYupDefaults();
  return yup.object({
    longitude: yup.number().emptyToNull().required(),
    latitude: yup.number().emptyToNull().required(),
    name: yup.string().emptyToNull().max(255).required(),
    member: yup.string().emptyToNull().max(255)
  });
}

export default function FavoriteLocationEdit() {
  const { t } = useTranslation();
  useDocumentTitle(t('favoriteLocation.edit.headline'));

  const navigate = useNavigate();
  const [memberValues, setMemberValues] = useState<Record<string,string>>({});
  const params = useParams();
  const currentId = +params.id!;

  const useFormResult = useForm({
    resolver: yupResolver(getSchema()),
  });

  const getMessage = (key: string) => {
    const messages: Record<string, string> = {
      FAVORITE_LOCATION_MEMBER_UNIQUE: t('Exists.favoriteLocation.member')
    };
    return messages[key];
  };

  const prepareForm = async () => {
    try {
      const memberValuesResponse = await axios.get('/api/favoriteLocations/memberValues');
      setMemberValues(memberValuesResponse.data);
      const data = (await axios.get('/api/favoriteLocations/' + currentId)).data;
      useFormResult.reset(data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  useEffect(() => {
    prepareForm();
  }, []);

  const updateFavoriteLocation = async (data: FavoriteLocationDTO) => {
    window.scrollTo(0, 0);
    try {
      await axios.put('/api/favoriteLocations/' + currentId, data);
      navigate('/favoriteLocations', {
            state: {
              msgSuccess: t('favoriteLocation.update.success')
            }
          });
    } catch (error: any) {
      handleServerError(error, navigate, useFormResult.setError, t, getMessage);
    }
  };

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('favoriteLocation.edit.headline')}</h1>
      <div>
        <Link to="/favoriteLocations" className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-4 rounded px-5 py-2">{t('favoriteLocation.edit.back')}</Link>
      </div>
    </div>
    <form onSubmit={useFormResult.handleSubmit(updateFavoriteLocation)} noValidate>
      <InputRow useFormResult={useFormResult} object="favoriteLocation" field="id" disabled={true} type="number" />
      <InputRow useFormResult={useFormResult} object="favoriteLocation" field="longitude" required={true} />
      <InputRow useFormResult={useFormResult} object="favoriteLocation" field="latitude" required={true} />
      <InputRow useFormResult={useFormResult} object="favoriteLocation" field="name" required={true} />
      <InputRow useFormResult={useFormResult} object="favoriteLocation" field="member" type="select" options={memberValues} />
      <input type="submit" value={t('favoriteLocation.edit.headline')} className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2 mt-6" />
    </form>
  </>);
}
