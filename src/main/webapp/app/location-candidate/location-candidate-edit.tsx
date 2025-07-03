import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate, useParams } from 'react-router';
import { handleServerError, setYupDefaults } from 'app/common/utils';
import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import { LocationCandidateDTO } from 'app/location-candidate/location-candidate-model';
import axios from 'axios';
import InputRow from 'app/common/input-row/input-row';
import useDocumentTitle from 'app/common/use-document-title';
import * as yup from 'yup';


function getSchema() {
  setYupDefaults();
  return yup.object({
    suggestUserId: yup.string().emptyToNull().max(255).required(),
    locationName: yup.string().emptyToNull().max(255).required(),
    latitude: yup.number().emptyToNull().required(),
    longitude: yup.number().emptyToNull().required(),
    voteCount: yup.number().integer().emptyToNull().required(),
    status: yup.string().emptyToNull().max(255).required(),
    detail: yup.number().integer().emptyToNull()
  });
}

export default function LocationCandidateEdit() {
  const { t } = useTranslation();
  useDocumentTitle(t('locationCandidate.edit.headline'));

  const navigate = useNavigate();
  const [detailValues, setDetailValues] = useState<Map<number,string>>(new Map());
  const params = useParams();
  const currentLocationCandidateId = +params.locationCandidateId!;

  const useFormResult = useForm({
    resolver: yupResolver(getSchema()),
  });

  const getMessage = (key: string) => {
    const messages: Record<string, string> = {
      LOCATION_CANDIDATE_LOCATION_NAME_UNIQUE: t('exists.locationCandidate.locationName')
    };
    return messages[key];
  };

  const prepareForm = async () => {
    try {
      const detailValuesResponse = await axios.get('/api/locationCandidates/detailValues');
      setDetailValues(detailValuesResponse.data);
      const data = (await axios.get('/api/locationCandidates/' + currentLocationCandidateId)).data;
      useFormResult.reset(data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  useEffect(() => {
    prepareForm();
  }, []);

  const updateLocationCandidate = async (data: LocationCandidateDTO) => {
    window.scrollTo(0, 0);
    try {
      await axios.put('/api/locationCandidates/' + currentLocationCandidateId, data);
      navigate('/locationCandidates', {
            state: {
              msgSuccess: t('locationCandidate.update.success')
            }
          });
    } catch (error: any) {
      handleServerError(error, navigate, useFormResult.setError, t, getMessage);
    }
  };

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('locationCandidate.edit.headline')}</h1>
      <div>
        <Link to="/locationCandidates" className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-4 rounded px-5 py-2">{t('locationCandidate.edit.back')}</Link>
      </div>
    </div>
    <form onSubmit={useFormResult.handleSubmit(updateLocationCandidate)} noValidate>
      <InputRow useFormResult={useFormResult} object="locationCandidate" field="locationCandidateId" disabled={true} type="number" />
      <InputRow useFormResult={useFormResult} object="locationCandidate" field="suggestUserId" required={true} />
      <InputRow useFormResult={useFormResult} object="locationCandidate" field="locationName" required={true} />
      <InputRow useFormResult={useFormResult} object="locationCandidate" field="latitude" required={true} />
      <InputRow useFormResult={useFormResult} object="locationCandidate" field="longitude" required={true} />
      <InputRow useFormResult={useFormResult} object="locationCandidate" field="voteCount" required={true} type="number" />
      <InputRow useFormResult={useFormResult} object="locationCandidate" field="status" required={true} />
      <InputRow useFormResult={useFormResult} object="locationCandidate" field="detail" type="select" options={detailValues} />
      <input type="submit" value={t('locationCandidate.edit.headline')} className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2 mt-6" />
    </form>
  </>);
}
