import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate, useParams } from 'react-router';
import { handleServerError, setYupDefaults } from 'app/common/utils';
import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import { DepartRegionDTO } from 'app/depart-region/depart-region-model';
import axios from 'axios';
import InputRow from 'app/common/input-row/input-row';
import useDocumentTitle from 'app/common/use-document-title';
import * as yup from 'yup';


function getSchema() {
  setYupDefaults();
  return yup.object({
    dapartLocationName: yup.string().emptyToNull().max(255).required(),
    latitude: yup.number().emptyToNull().required(),
    longitude: yup.number().emptyToNull().required(),
    meeting: yup.number().integer().emptyToNull(),
    middleRegion: yup.number().integer().emptyToNull()
  });
}

export default function DepartRegionEdit() {
  const { t } = useTranslation();
  useDocumentTitle(t('departRegion.edit.headline'));

  const navigate = useNavigate();
  const [meetingValues, setMeetingValues] = useState<Map<number,string>>(new Map());
  const [middleRegionValues, setMiddleRegionValues] = useState<Map<number,string>>(new Map());
  const params = useParams();
  const currentDepartRegionId = +params.departRegionId!;

  const useFormResult = useForm({
    resolver: yupResolver(getSchema()),
  });

  const prepareForm = async () => {
    try {
      const meetingValuesResponse = await axios.get('/api/departRegions/meetingValues');
      setMeetingValues(meetingValuesResponse.data);
      const middleRegionValuesResponse = await axios.get('/api/departRegions/middleRegionValues');
      setMiddleRegionValues(middleRegionValuesResponse.data);
      const data = (await axios.get('/api/departRegions/' + currentDepartRegionId)).data;
      useFormResult.reset(data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  useEffect(() => {
    prepareForm();
  }, []);

  const updateDepartRegion = async (data: DepartRegionDTO) => {
    window.scrollTo(0, 0);
    try {
      await axios.put('/api/departRegions/' + currentDepartRegionId, data);
      navigate('/departRegions', {
            state: {
              msgSuccess: t('departRegion.update.success')
            }
          });
    } catch (error: any) {
      handleServerError(error, navigate, useFormResult.setError, t);
    }
  };

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('departRegion.edit.headline')}</h1>
      <div>
        <Link to="/departRegions" className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-4 rounded px-5 py-2">{t('departRegion.edit.back')}</Link>
      </div>
    </div>
    <form onSubmit={useFormResult.handleSubmit(updateDepartRegion)} noValidate>
      <InputRow useFormResult={useFormResult} object="departRegion" field="departRegionId" disabled={true} type="number" />
      <InputRow useFormResult={useFormResult} object="departRegion" field="dapartLocationName" required={true} />
      <InputRow useFormResult={useFormResult} object="departRegion" field="latitude" required={true} />
      <InputRow useFormResult={useFormResult} object="departRegion" field="longitude" required={true} />
      <InputRow useFormResult={useFormResult} object="departRegion" field="meeting" type="select" options={meetingValues} />
      <InputRow useFormResult={useFormResult} object="departRegion" field="middleRegion" type="select" options={middleRegionValues} />
      <input type="submit" value={t('departRegion.edit.headline')} className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2 mt-6" />
    </form>
  </>);
}
