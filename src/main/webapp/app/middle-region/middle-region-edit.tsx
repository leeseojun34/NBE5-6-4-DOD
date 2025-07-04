import React, { useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate, useParams } from 'react-router';
import { handleServerError, setYupDefaults } from 'app/common/utils';
import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import { MiddleRegionDTO } from 'app/middle-region/middle-region-model';
import axios from 'axios';
import InputRow from 'app/common/input-row/input-row';
import useDocumentTitle from 'app/common/use-document-title';
import * as yup from 'yup';


function getSchema() {
  setYupDefaults();
  return yup.object({
    latitude: yup.number().emptyToNull().required(),
    longitude: yup.number().emptyToNull().required()
  });
}

export default function MiddleRegionEdit() {
  const { t } = useTranslation();
  useDocumentTitle(t('middleRegion.edit.headline'));

  const navigate = useNavigate();
  const params = useParams();
  const currentId = +params.id!;

  const useFormResult = useForm({
    resolver: yupResolver(getSchema()),
  });

  const prepareForm = async () => {
    try {
      const data = (await axios.get('/api/middleRegions/' + currentId)).data;
      useFormResult.reset(data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  useEffect(() => {
    prepareForm();
  }, []);

  const updateMiddleRegion = async (data: MiddleRegionDTO) => {
    window.scrollTo(0, 0);
    try {
      await axios.put('/api/middleRegions/' + currentId, data);
      navigate('/middleRegions', {
            state: {
              msgSuccess: t('middleRegion.update.success')
            }
          });
    } catch (error: any) {
      handleServerError(error, navigate, useFormResult.setError, t);
    }
  };

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('middleRegion.edit.headline')}</h1>
      <div>
        <Link to="/middleRegions" className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-4 rounded px-5 py-2">{t('middleRegion.edit.back')}</Link>
      </div>
    </div>
    <form onSubmit={useFormResult.handleSubmit(updateMiddleRegion)} noValidate>
      <InputRow useFormResult={useFormResult} object="middleRegion" field="id" disabled={true} type="number" />
      <InputRow useFormResult={useFormResult} object="middleRegion" field="latitude" required={true} />
      <InputRow useFormResult={useFormResult} object="middleRegion" field="longitude" required={true} />
      <input type="submit" value={t('middleRegion.edit.headline')} className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2 mt-6" />
    </form>
  </>);
}
