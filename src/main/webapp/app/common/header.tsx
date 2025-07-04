import React, { useEffect, useRef } from 'react';
import { Link } from 'react-router';
import { useTranslation } from 'react-i18next';


export default function Header() {
  const { t } = useTranslation();
  const headerRef = useRef<HTMLElement|null>(null);

  const handleClick = (event: Event) => {
    // close any open dropdown
    const $clickedDropdown = (event.target as HTMLElement).closest('.js-dropdown');
    const $dropdowns = headerRef.current!.querySelectorAll('.js-dropdown');
    $dropdowns.forEach(($dropdown:Element) => {
      if ($clickedDropdown !== $dropdown && $dropdown.getAttribute('data-dropdown-keepopen') !== 'true') {
        $dropdown.ariaExpanded = 'false';
        $dropdown.nextElementSibling!.classList.add('hidden');
      }
    });
    // toggle selected if applicable
    if ($clickedDropdown) {
      $clickedDropdown.ariaExpanded = '' + ($clickedDropdown.ariaExpanded !== 'true');
      $clickedDropdown.nextElementSibling!.classList.toggle('hidden');
    }
  };

  useEffect(() => {
    document.body.addEventListener('click', handleClick);
    return () => document.body.removeEventListener('click', handleClick);
  }, []);

  return (
    <header ref={headerRef} className="bg-gray-50">
      <div className="container mx-auto px-4 md:px-6">
        <nav className="flex flex-wrap items-center justify-between py-2">
          <Link to="/" className="flex py-1.5 mr-4">
            <img src="/images/logo.png" alt={t('app.title')} width="30" height="30" className="inline-block" />
            <span className="text-xl pl-3">{t('app.title')}</span>
          </Link>
          <button type="button" className="js-dropdown md:hidden border rounded cursor-pointer" data-dropdown-keepopen="true"
              aria-label={t('navigation.toggle')} aria-controls="navbarToggle" aria-expanded="false">
            <div className="space-y-1.5 my-2.5 mx-4">
              <div className="w-6 h-0.5 bg-gray-500"></div>
              <div className="w-6 h-0.5 bg-gray-500"></div>
              <div className="w-6 h-0.5 bg-gray-500"></div>
            </div>
          </button>
          <div className="hidden md:block flex grow md:grow-0 justify-end basis-full md:basis-auto pt-3 md:pt-1 pb-1" id="navbarToggle">
            <ul className="flex">
              <li>
                <Link to="/" className="block text-gray-500 p-2">{t('navigation.home')}</Link>
              </li>
              <li className="relative">
                <button type="button" className="js-dropdown block text-gray-500 p-2 cursor-pointer" id="navbarEntitiesLink"
                    aria-expanded="false">
                  <span>{t('navigation.entities')}</span>
                  <span className="text-[9px] align-[3px] pl-0.5">&#9660;</span>
                </button>
                <ul className="hidden block absolute right-0 bg-white border border-gray-300 rounded min-w-[10rem] py-2" aria-labelledby="navbarEntitiesLink">
                  <li><Link to="/members" className="inline-block w-full hover:bg-gray-200 px-4 py-1">{t('member.list.headline')}</Link></li>
                  <li><Link to="/socialAuthTokenss" className="inline-block w-full hover:bg-gray-200 px-4 py-1">{t('socialAuthTokens.list.headline')}</Link></li>
                  <li><Link to="/favoriteLocations" className="inline-block w-full hover:bg-gray-200 px-4 py-1">{t('favoriteLocation.list.headline')}</Link></li>
                  <li><Link to="/favoriteTimetables" className="inline-block w-full hover:bg-gray-200 px-4 py-1">{t('favoriteTimetable.list.headline')}</Link></li>
                  <li><Link to="/calendars" className="inline-block w-full hover:bg-gray-200 px-4 py-1">{t('calendar.list.headline')}</Link></li>
                  <li><Link to="/calendarDetails" className="inline-block w-full hover:bg-gray-200 px-4 py-1">{t('calendarDetail.list.headline')}</Link></li>
                  <li><Link to="/eventMembers" className="inline-block w-full hover:bg-gray-200 px-4 py-1">{t('eventMember.list.headline')}</Link></li>
                  <li><Link to="/memberVotes" className="inline-block w-full hover:bg-gray-200 px-4 py-1">{t('memberVote.list.headline')}</Link></li>
                  <li><Link to="/locations" className="inline-block w-full hover:bg-gray-200 px-4 py-1">{t('location.list.headline')}</Link></li>
                  <li><Link to="/middleRegions" className="inline-block w-full hover:bg-gray-200 px-4 py-1">{t('middleRegion.list.headline')}</Link></li>
                  <li><Link to="/workspaces" className="inline-block w-full hover:bg-gray-200 px-4 py-1">{t('workspace.list.headline')}</Link></li>
                  <li><Link to="/scheduleMembers" className="inline-block w-full hover:bg-gray-200 px-4 py-1">{t('scheduleMember.list.headline')}</Link></li>
                  <li><Link to="/events" className="inline-block w-full hover:bg-gray-200 px-4 py-1">{t('event.list.headline')}</Link></li>
                  <li><Link to="/groups" className="inline-block w-full hover:bg-gray-200 px-4 py-1">{t('group.list.headline')}</Link></li>
                  <li><Link to="/tempSchedules" className="inline-block w-full hover:bg-gray-200 px-4 py-1">{t('tempSchedule.list.headline')}</Link></li>
                  <li><Link to="/groupMembers" className="inline-block w-full hover:bg-gray-200 px-4 py-1">{t('groupMember.list.headline')}</Link></li>
                  <li><Link to="/schedules" className="inline-block w-full hover:bg-gray-200 px-4 py-1">{t('schedule.list.headline')}</Link></li>
                  <li><Link to="/candidateDates" className="inline-block w-full hover:bg-gray-200 px-4 py-1">{t('candidateDate.list.headline')}</Link></li>
                </ul>
              </li>
            </ul>
          </div>
        </nav>
      </div>
    </header>
  );
}
