import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate, useParams } from 'react-router';
import { handleServerError, setYupDefaults } from 'app/common/utils';
import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import { WorkspaceDTO } from 'app/workspace/workspace-model';
import axios from 'axios';
import InputRow from 'app/common/input-row/input-row';
import useDocumentTitle from 'app/common/use-document-title';
import * as yup from 'yup';


function getSchema() {
  setYupDefaults();
  return yup.object({
    url: yup.string().emptyToNull().required(),
    schedule: yup.number().integer().emptyToNull()
  });
}

export default function WorkspaceEdit() {
  const { t } = useTranslation();
  useDocumentTitle(t('workspace.edit.headline'));

  const navigate = useNavigate();
  const [scheduleValues, setScheduleValues] = useState<Map<number,string>>(new Map());
  const params = useParams();
  const currentId = +params.id!;

  const useFormResult = useForm({
    resolver: yupResolver(getSchema()),
  });

  const prepareForm = async () => {
    try {
      const scheduleValuesResponse = await axios.get('/api/workspaces/scheduleValues');
      setScheduleValues(scheduleValuesResponse.data);
      const data = (await axios.get('/api/workspaces/' + currentId)).data;
      useFormResult.reset(data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  useEffect(() => {
    prepareForm();
  }, []);

  const updateWorkspace = async (data: WorkspaceDTO) => {
    window.scrollTo(0, 0);
    try {
      await axios.put('/api/workspaces/' + currentId, data);
      navigate('/workspaces', {
            state: {
              msgSuccess: t('workspace.update.success')
            }
          });
    } catch (error: any) {
      handleServerError(error, navigate, useFormResult.setError, t);
    }
  };

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('workspace.edit.headline')}</h1>
      <div>
        <Link to="/workspaces" className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-4 rounded px-5 py-2">{t('workspace.edit.back')}</Link>
      </div>
    </div>
    <form onSubmit={useFormResult.handleSubmit(updateWorkspace)} noValidate>
      <InputRow useFormResult={useFormResult} object="workspace" field="id" disabled={true} type="number" />
      <InputRow useFormResult={useFormResult} object="workspace" field="url" required={true} type="textarea" />
      <InputRow useFormResult={useFormResult} object="workspace" field="schedule" type="select" options={scheduleValues} />
      <input type="submit" value={t('workspace.edit.headline')} className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2 mt-6" />
    </form>
  </>);
}
