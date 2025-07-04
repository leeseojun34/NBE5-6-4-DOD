import React from 'react';
import { Link } from 'react-router';
import { Trans, useTranslation } from 'react-i18next';
import useDocumentTitle from 'app/common/use-document-title';
import './home.css';


export default function Home() {
  const { t } = useTranslation();
  useDocumentTitle(t('home.index.headline'));

  return (<>
    <h1 className="grow text-3xl md:text-4xl font-medium mb-8">{t('home.index.headline')}</h1>
    <p className="mb-4"><Trans i18nKey="home.index.text" components={{ a: <a />, strong: <strong /> }} /></p>
    <p className="mb-12">
      <span>{t('home.index.swagger.text')}</span>
      <span> </span>
      <a href={process.env.API_PATH + '/swagger-ui.html'} target="_blank" className="underline">{t('home.index.swagger.link')}</a>.
    </p>
    <div className="md:w-2/5 mb-12">
      <h4 className="text-2xl font-medium mb-4">{t('home.index.exploreEntities')}</h4>
      <div className="flex flex-col border border-gray-300 rounded">
        <Link to="/members" className="w-full border-gray-300 hover:bg-gray-100 border-b rounded-t px-4 py-2">{t('member.list.headline')}</Link>
        <Link to="/socialAuthTokenss" className="w-full border-gray-300 hover:bg-gray-100 border-b px-4 py-2">{t('socialAuthTokens.list.headline')}</Link>
        <Link to="/favoriteLocations" className="w-full border-gray-300 hover:bg-gray-100 border-b px-4 py-2">{t('favoriteLocation.list.headline')}</Link>
        <Link to="/favoriteTimetables" className="w-full border-gray-300 hover:bg-gray-100 border-b px-4 py-2">{t('favoriteTimetable.list.headline')}</Link>
        <Link to="/calendars" className="w-full border-gray-300 hover:bg-gray-100 border-b px-4 py-2">{t('calendar.list.headline')}</Link>
        <Link to="/calendarDetails" className="w-full border-gray-300 hover:bg-gray-100 border-b px-4 py-2">{t('calendarDetail.list.headline')}</Link>
        <Link to="/eventMembers" className="w-full border-gray-300 hover:bg-gray-100 border-b px-4 py-2">{t('eventMember.list.headline')}</Link>
        <Link to="/memberVotes" className="w-full border-gray-300 hover:bg-gray-100 border-b px-4 py-2">{t('memberVote.list.headline')}</Link>
        <Link to="/locations" className="w-full border-gray-300 hover:bg-gray-100 border-b px-4 py-2">{t('location.list.headline')}</Link>
        <Link to="/middleRegions" className="w-full border-gray-300 hover:bg-gray-100 border-b px-4 py-2">{t('middleRegion.list.headline')}</Link>
        <Link to="/workspaces" className="w-full border-gray-300 hover:bg-gray-100 border-b px-4 py-2">{t('workspace.list.headline')}</Link>
        <Link to="/scheduleMembers" className="w-full border-gray-300 hover:bg-gray-100 border-b px-4 py-2">{t('scheduleMember.list.headline')}</Link>
        <Link to="/events" className="w-full border-gray-300 hover:bg-gray-100 border-b px-4 py-2">{t('event.list.headline')}</Link>
        <Link to="/groups" className="w-full border-gray-300 hover:bg-gray-100 border-b px-4 py-2">{t('group.list.headline')}</Link>
        <Link to="/tempSchedules" className="w-full border-gray-300 hover:bg-gray-100 border-b px-4 py-2">{t('tempSchedule.list.headline')}</Link>
        <Link to="/groupMembers" className="w-full border-gray-300 hover:bg-gray-100 border-b px-4 py-2">{t('groupMember.list.headline')}</Link>
        <Link to="/schedules" className="w-full border-gray-300 hover:bg-gray-100 border-b px-4 py-2">{t('schedule.list.headline')}</Link>
        <Link to="/candidateDates" className="w-full border-gray-300 hover:bg-gray-100 rounded-b px-4 py-2">{t('candidateDate.list.headline')}</Link>
      </div>
    </div>
  </>);
}
