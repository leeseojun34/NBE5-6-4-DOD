import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate, useParams } from 'react-router';
import { handleServerError, setYupDefaults } from 'app/common/utils';
import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import { LikeLocationDTO } from 'app/like-location/like-location-model';
import axios from 'axios';
import InputRow from 'app/common/input-row/input-row';
import useDocumentTitle from 'app/common/use-document-title';
import * as yup from 'yup';


function getSchema() {
  setYupDefaults();
  return yup.object({
    longitude: yup.number().emptyToNull().required(),
    latitude: yup.number().emptyToNull().required(),
    locationName: yup.string().emptyToNull().max(255).required(),
    user: yup.string().emptyToNull().max(255)
  });
}

export default function LikeLocationEdit() {
  const { t } = useTranslation();
  useDocumentTitle(t('likeLocation.edit.headline'));

  const navigate = useNavigate();
  const [userValues, setUserValues] = useState<Record<string,string>>({});
  const params = useParams();
  const currentLikeLocationId = +params.likeLocationId!;

  const useFormResult = useForm({
    resolver: yupResolver(getSchema()),
  });

  const getMessage = (key: string) => {
    const messages: Record<string, string> = {
      LIKE_LOCATION_USER_UNIQUE: t('Exists.likeLocation.user')
    };
    return messages[key];
  };

  const prepareForm = async () => {
    try {
      const userValuesResponse = await axios.get('/api/likeLocations/userValues');
      setUserValues(userValuesResponse.data);
      const data = (await axios.get('/api/likeLocations/' + currentLikeLocationId)).data;
      useFormResult.reset(data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  useEffect(() => {
    prepareForm();
  }, []);

  const updateLikeLocation = async (data: LikeLocationDTO) => {
    window.scrollTo(0, 0);
    try {
      await axios.put('/api/likeLocations/' + currentLikeLocationId, data);
      navigate('/likeLocations', {
            state: {
              msgSuccess: t('likeLocation.update.success')
            }
          });
    } catch (error: any) {
      handleServerError(error, navigate, useFormResult.setError, t, getMessage);
    }
  };

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('likeLocation.edit.headline')}</h1>
      <div>
        <Link to="/likeLocations" className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-4 rounded px-5 py-2">{t('likeLocation.edit.back')}</Link>
      </div>
    </div>
    <form onSubmit={useFormResult.handleSubmit(updateLikeLocation)} noValidate>
      <InputRow useFormResult={useFormResult} object="likeLocation" field="likeLocationId" disabled={true} type="number" />
      <InputRow useFormResult={useFormResult} object="likeLocation" field="longitude" required={true} />
      <InputRow useFormResult={useFormResult} object="likeLocation" field="latitude" required={true} />
      <InputRow useFormResult={useFormResult} object="likeLocation" field="locationName" required={true} />
      <InputRow useFormResult={useFormResult} object="likeLocation" field="user" type="select" options={userValues} />
      <input type="submit" value={t('likeLocation.edit.headline')} className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2 mt-6" />
    </form>
  </>);
}
