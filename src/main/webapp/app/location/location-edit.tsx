import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate, useParams } from 'react-router';
import { handleServerError, setYupDefaults } from 'app/common/utils';
import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import { LocationDTO } from 'app/location/location-model';
import axios from 'axios';
import InputRow from 'app/common/input-row/input-row';
import useDocumentTitle from 'app/common/use-document-title';
import * as yup from 'yup';


function getSchema() {
  setYupDefaults();
  return yup.object({
    latitude: yup.number().emptyToNull().required(),
    longitude: yup.number().emptyToNull().required(),
    locationName: yup.string().emptyToNull().max(255).required(),
    middleRegion: yup.number().integer().emptyToNull()
  });
}

export default function LocationEdit() {
  const { t } = useTranslation();
  useDocumentTitle(t('location.edit.headline'));

  const navigate = useNavigate();
  const [middleRegionValues, setMiddleRegionValues] = useState<Map<number,string>>(new Map());
  const params = useParams();
  const currentLocationId = +params.locationId!;

  const useFormResult = useForm({
    resolver: yupResolver(getSchema()),
  });

  const getMessage = (key: string) => {
    const messages: Record<string, string> = {
      LOCATION_MIDDLE_REGION_UNIQUE: t('Exists.location.middle-region')
    };
    return messages[key];
  };

  const prepareForm = async () => {
    try {
      const middleRegionValuesResponse = await axios.get('/api/locations/middleRegionValues');
      setMiddleRegionValues(middleRegionValuesResponse.data);
      const data = (await axios.get('/api/locations/' + currentLocationId)).data;
      useFormResult.reset(data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  useEffect(() => {
    prepareForm();
  }, []);

  const updateLocation = async (data: LocationDTO) => {
    window.scrollTo(0, 0);
    try {
      await axios.put('/api/locations/' + currentLocationId, data);
      navigate('/locations', {
            state: {
              msgSuccess: t('location.update.success')
            }
          });
    } catch (error: any) {
      handleServerError(error, navigate, useFormResult.setError, t, getMessage);
    }
  };

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('location.edit.headline')}</h1>
      <div>
        <Link to="/locations" className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-4 rounded px-5 py-2">{t('location.edit.back')}</Link>
      </div>
    </div>
    <form onSubmit={useFormResult.handleSubmit(updateLocation)} noValidate>
      <InputRow useFormResult={useFormResult} object="location" field="locationId" disabled={true} type="number" />
      <InputRow useFormResult={useFormResult} object="location" field="latitude" required={true} />
      <InputRow useFormResult={useFormResult} object="location" field="longitude" required={true} />
      <InputRow useFormResult={useFormResult} object="location" field="locationName" required={true} />
      <InputRow useFormResult={useFormResult} object="location" field="middleRegion" type="select" options={middleRegionValues} />
      <input type="submit" value={t('location.edit.headline')} className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2 mt-6" />
    </form>
  </>);
}
