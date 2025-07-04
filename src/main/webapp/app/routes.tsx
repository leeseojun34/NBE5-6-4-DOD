import React from 'react';
import { createBrowserRouter, RouterProvider } from 'react-router';
import App from "./app";
import Home from './home/home';
import MemberList from './member/member-list';
import MemberAdd from './member/member-add';
import MemberEdit from './member/member-edit';
import SocialAuthTokensList from './social-auth-tokens/social-auth-tokens-list';
import SocialAuthTokensAdd from './social-auth-tokens/social-auth-tokens-add';
import SocialAuthTokensEdit from './social-auth-tokens/social-auth-tokens-edit';
import FavoriteLocationList from './favorite-location/favorite-location-list';
import FavoriteLocationAdd from './favorite-location/favorite-location-add';
import FavoriteLocationEdit from './favorite-location/favorite-location-edit';
import FavoriteTimetableList from './favorite-timetable/favorite-timetable-list';
import FavoriteTimetableAdd from './favorite-timetable/favorite-timetable-add';
import FavoriteTimetableEdit from './favorite-timetable/favorite-timetable-edit';
import CalendarList from './calendar/calendar-list';
import CalendarAdd from './calendar/calendar-add';
import CalendarEdit from './calendar/calendar-edit';
import CalendarDetailList from './calendar-detail/calendar-detail-list';
import CalendarDetailAdd from './calendar-detail/calendar-detail-add';
import CalendarDetailEdit from './calendar-detail/calendar-detail-edit';
import EventMemberList from './event-member/event-member-list';
import EventMemberAdd from './event-member/event-member-add';
import EventMemberEdit from './event-member/event-member-edit';
import MemberVoteList from './member-vote/member-vote-list';
import MemberVoteAdd from './member-vote/member-vote-add';
import MemberVoteEdit from './member-vote/member-vote-edit';
import LocationList from './location/location-list';
import LocationAdd from './location/location-add';
import LocationEdit from './location/location-edit';
import MiddleRegionList from './middle-region/middle-region-list';
import MiddleRegionAdd from './middle-region/middle-region-add';
import MiddleRegionEdit from './middle-region/middle-region-edit';
import WorkspaceList from './workspace/workspace-list';
import WorkspaceAdd from './workspace/workspace-add';
import WorkspaceEdit from './workspace/workspace-edit';
import ScheduleMemberList from './schedule-member/schedule-member-list';
import ScheduleMemberAdd from './schedule-member/schedule-member-add';
import ScheduleMemberEdit from './schedule-member/schedule-member-edit';
import EventList from './event/event-list';
import EventAdd from './event/event-add';
import EventEdit from './event/event-edit';
import GroupList from './group/group-list';
import GroupAdd from './group/group-add';
import GroupEdit from './group/group-edit';
import TempScheduleList from './temp-schedule/temp-schedule-list';
import TempScheduleAdd from './temp-schedule/temp-schedule-add';
import TempScheduleEdit from './temp-schedule/temp-schedule-edit';
import GroupMemberList from './group-member/group-member-list';
import GroupMemberAdd from './group-member/group-member-add';
import GroupMemberEdit from './group-member/group-member-edit';
import ScheduleList from './schedule/schedule-list';
import ScheduleAdd from './schedule/schedule-add';
import ScheduleEdit from './schedule/schedule-edit';
import CandidateDateList from './candidate-date/candidate-date-list';
import CandidateDateAdd from './candidate-date/candidate-date-add';
import CandidateDateEdit from './candidate-date/candidate-date-edit';
import Error from './error/error';


export default function AppRoutes() {
  const router = createBrowserRouter([
    {
      element: <App />,
      children: [
        { path: '', element: <Home /> },
        { path: 'members', element: <MemberList /> },
        { path: 'members/add', element: <MemberAdd /> },
        { path: 'members/edit/:id', element: <MemberEdit /> },
        { path: 'socialAuthTokenss', element: <SocialAuthTokensList /> },
        { path: 'socialAuthTokenss/add', element: <SocialAuthTokensAdd /> },
        { path: 'socialAuthTokenss/edit/:id', element: <SocialAuthTokensEdit /> },
        { path: 'favoriteLocations', element: <FavoriteLocationList /> },
        { path: 'favoriteLocations/add', element: <FavoriteLocationAdd /> },
        { path: 'favoriteLocations/edit/:id', element: <FavoriteLocationEdit /> },
        { path: 'favoriteTimetables', element: <FavoriteTimetableList /> },
        { path: 'favoriteTimetables/add', element: <FavoriteTimetableAdd /> },
        { path: 'favoriteTimetables/edit/:id', element: <FavoriteTimetableEdit /> },
        { path: 'calendars', element: <CalendarList /> },
        { path: 'calendars/add', element: <CalendarAdd /> },
        { path: 'calendars/edit/:id', element: <CalendarEdit /> },
        { path: 'calendarDetails', element: <CalendarDetailList /> },
        { path: 'calendarDetails/add', element: <CalendarDetailAdd /> },
        { path: 'calendarDetails/edit/:id', element: <CalendarDetailEdit /> },
        { path: 'eventMembers', element: <EventMemberList /> },
        { path: 'eventMembers/add', element: <EventMemberAdd /> },
        { path: 'eventMembers/edit/:id', element: <EventMemberEdit /> },
        { path: 'memberVotes', element: <MemberVoteList /> },
        { path: 'memberVotes/add', element: <MemberVoteAdd /> },
        { path: 'memberVotes/edit/:id', element: <MemberVoteEdit /> },
        { path: 'locations', element: <LocationList /> },
        { path: 'locations/add', element: <LocationAdd /> },
        { path: 'locations/edit/:id', element: <LocationEdit /> },
        { path: 'middleRegions', element: <MiddleRegionList /> },
        { path: 'middleRegions/add', element: <MiddleRegionAdd /> },
        { path: 'middleRegions/edit/:id', element: <MiddleRegionEdit /> },
        { path: 'workspaces', element: <WorkspaceList /> },
        { path: 'workspaces/add', element: <WorkspaceAdd /> },
        { path: 'workspaces/edit/:id', element: <WorkspaceEdit /> },
        { path: 'scheduleMembers', element: <ScheduleMemberList /> },
        { path: 'scheduleMembers/add', element: <ScheduleMemberAdd /> },
        { path: 'scheduleMembers/edit/:id', element: <ScheduleMemberEdit /> },
        { path: 'events', element: <EventList /> },
        { path: 'events/add', element: <EventAdd /> },
        { path: 'events/edit/:id', element: <EventEdit /> },
        { path: 'groups', element: <GroupList /> },
        { path: 'groups/add', element: <GroupAdd /> },
        { path: 'groups/edit/:id', element: <GroupEdit /> },
        { path: 'tempSchedules', element: <TempScheduleList /> },
        { path: 'tempSchedules/add', element: <TempScheduleAdd /> },
        { path: 'tempSchedules/edit/:id', element: <TempScheduleEdit /> },
        { path: 'groupMembers', element: <GroupMemberList /> },
        { path: 'groupMembers/add', element: <GroupMemberAdd /> },
        { path: 'groupMembers/edit/:id', element: <GroupMemberEdit /> },
        { path: 'schedules', element: <ScheduleList /> },
        { path: 'schedules/add', element: <ScheduleAdd /> },
        { path: 'schedules/edit/:id', element: <ScheduleEdit /> },
        { path: 'candidateDates', element: <CandidateDateList /> },
        { path: 'candidateDates/add', element: <CandidateDateAdd /> },
        { path: 'candidateDates/edit/:id', element: <CandidateDateEdit /> },
        { path: 'error', element: <Error /> },
        { path: '*', element: <Error /> }
      ]
    }
  ]);

  return (
    <RouterProvider router={router} />
  );
}
