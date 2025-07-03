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
import LikeLocationList from './like-location/like-location-list';
import LikeLocationAdd from './like-location/like-location-add';
import LikeLocationEdit from './like-location/like-location-edit';
import LikeTimetableList from './like-timetable/like-timetable-list';
import LikeTimetableAdd from './like-timetable/like-timetable-add';
import LikeTimetableEdit from './like-timetable/like-timetable-edit';
import CalendarList from './calendar/calendar-list';
import CalendarAdd from './calendar/calendar-add';
import CalendarEdit from './calendar/calendar-edit';
import CalendarDetailList from './calendar-detail/calendar-detail-list';
import CalendarDetailAdd from './calendar-detail/calendar-detail-add';
import CalendarDetailEdit from './calendar-detail/calendar-detail-edit';
import LocationCandidateList from './location-candidate/location-candidate-list';
import LocationCandidateAdd from './location-candidate/location-candidate-add';
import LocationCandidateEdit from './location-candidate/location-candidate-edit';
import MeetingList from './meeting/meeting-list';
import MeetingAdd from './meeting/meeting-add';
import MeetingEdit from './meeting/meeting-edit';
import EventUserList from './event-user/event-user-list';
import EventUserAdd from './event-user/event-user-add';
import EventUserEdit from './event-user/event-user-edit';
import UserVoteList from './user-vote/user-vote-list';
import UserVoteAdd from './user-vote/user-vote-add';
import UserVoteEdit from './user-vote/user-vote-edit';
import LocationList from './location/location-list';
import LocationAdd from './location/location-add';
import LocationEdit from './location/location-edit';
import MiddleRegionList from './middle-region/middle-region-list';
import MiddleRegionAdd from './middle-region/middle-region-add';
import MiddleRegionEdit from './middle-region/middle-region-edit';
import DepartRegionList from './depart-region/depart-region-list';
import DepartRegionAdd from './depart-region/depart-region-add';
import DepartRegionEdit from './depart-region/depart-region-edit';
import CandidateDateList from './candidate-date/candidate-date-list';
import CandidateDateAdd from './candidate-date/candidate-date-add';
import CandidateDateEdit from './candidate-date/candidate-date-edit';
import WorkspaceList from './workspace/workspace-list';
import WorkspaceAdd from './workspace/workspace-add';
import WorkspaceEdit from './workspace/workspace-edit';
import DetailList from './detail/detail-list';
import DetailAdd from './detail/detail-add';
import DetailEdit from './detail/detail-edit';
import ScheduleUserList from './schedule-user/schedule-user-list';
import ScheduleUserAdd from './schedule-user/schedule-user-add';
import ScheduleUserEdit from './schedule-user/schedule-user-edit';
import EventList from './event/event-list';
import EventAdd from './event/event-add';
import EventEdit from './event/event-edit';
import GroupList from './group/group-list';
import GroupAdd from './group/group-add';
import GroupEdit from './group/group-edit';
import TempScheduleList from './temp-schedule/temp-schedule-list';
import TempScheduleAdd from './temp-schedule/temp-schedule-add';
import TempScheduleEdit from './temp-schedule/temp-schedule-edit';
import GroupUserList from './group-user/group-user-list';
import GroupUserAdd from './group-user/group-user-add';
import GroupUserEdit from './group-user/group-user-edit';
import ScheduleList from './schedule/schedule-list';
import ScheduleAdd from './schedule/schedule-add';
import ScheduleEdit from './schedule/schedule-edit';
import Error from './error/error';


export default function AppRoutes() {
  const router = createBrowserRouter([
    {
      element: <App />,
      children: [
        { path: '', element: <Home /> },
        { path: 'members', element: <MemberList /> },
        { path: 'members/add', element: <MemberAdd /> },
        { path: 'members/edit/:userId', element: <MemberEdit /> },
        { path: 'socialAuthTokenss', element: <SocialAuthTokensList /> },
        { path: 'socialAuthTokenss/add', element: <SocialAuthTokensAdd /> },
        { path: 'socialAuthTokenss/edit/:socialAuthTokensId', element: <SocialAuthTokensEdit /> },
        { path: 'likeLocations', element: <LikeLocationList /> },
        { path: 'likeLocations/add', element: <LikeLocationAdd /> },
        { path: 'likeLocations/edit/:likeLocationId', element: <LikeLocationEdit /> },
        { path: 'likeTimetables', element: <LikeTimetableList /> },
        { path: 'likeTimetables/add', element: <LikeTimetableAdd /> },
        { path: 'likeTimetables/edit/:likeTimetableId', element: <LikeTimetableEdit /> },
        { path: 'calendars', element: <CalendarList /> },
        { path: 'calendars/add', element: <CalendarAdd /> },
        { path: 'calendars/edit/:calendarId', element: <CalendarEdit /> },
        { path: 'calendarDetails', element: <CalendarDetailList /> },
        { path: 'calendarDetails/add', element: <CalendarDetailAdd /> },
        { path: 'calendarDetails/edit/:calendarDetailId', element: <CalendarDetailEdit /> },
        { path: 'locationCandidates', element: <LocationCandidateList /> },
        { path: 'locationCandidates/add', element: <LocationCandidateAdd /> },
        { path: 'locationCandidates/edit/:locationCandidateId', element: <LocationCandidateEdit /> },
        { path: 'meetings', element: <MeetingList /> },
        { path: 'meetings/add', element: <MeetingAdd /> },
        { path: 'meetings/edit/:meetingId', element: <MeetingEdit /> },
        { path: 'eventUsers', element: <EventUserList /> },
        { path: 'eventUsers/add', element: <EventUserAdd /> },
        { path: 'eventUsers/edit/:eventUserId', element: <EventUserEdit /> },
        { path: 'userVotes', element: <UserVoteList /> },
        { path: 'userVotes/add', element: <UserVoteAdd /> },
        { path: 'userVotes/edit/:userVoteId', element: <UserVoteEdit /> },
        { path: 'locations', element: <LocationList /> },
        { path: 'locations/add', element: <LocationAdd /> },
        { path: 'locations/edit/:locationId', element: <LocationEdit /> },
        { path: 'middleRegions', element: <MiddleRegionList /> },
        { path: 'middleRegions/add', element: <MiddleRegionAdd /> },
        { path: 'middleRegions/edit/:middleRegionId', element: <MiddleRegionEdit /> },
        { path: 'departRegions', element: <DepartRegionList /> },
        { path: 'departRegions/add', element: <DepartRegionAdd /> },
        { path: 'departRegions/edit/:departRegionId', element: <DepartRegionEdit /> },
        { path: 'candidateDates', element: <CandidateDateList /> },
        { path: 'candidateDates/add', element: <CandidateDateAdd /> },
        { path: 'candidateDates/edit/:candidateDateId', element: <CandidateDateEdit /> },
        { path: 'workspaces', element: <WorkspaceList /> },
        { path: 'workspaces/add', element: <WorkspaceAdd /> },
        { path: 'workspaces/edit/:workspaceId', element: <WorkspaceEdit /> },
        { path: 'details', element: <DetailList /> },
        { path: 'details/add', element: <DetailAdd /> },
        { path: 'details/edit/:detailId', element: <DetailEdit /> },
        { path: 'scheduleUsers', element: <ScheduleUserList /> },
        { path: 'scheduleUsers/add', element: <ScheduleUserAdd /> },
        { path: 'scheduleUsers/edit/:scheduleUserId', element: <ScheduleUserEdit /> },
        { path: 'events', element: <EventList /> },
        { path: 'events/add', element: <EventAdd /> },
        { path: 'events/edit/:eventId', element: <EventEdit /> },
        { path: 'groups', element: <GroupList /> },
        { path: 'groups/add', element: <GroupAdd /> },
        { path: 'groups/edit/:groupId', element: <GroupEdit /> },
        { path: 'tempSchedules', element: <TempScheduleList /> },
        { path: 'tempSchedules/add', element: <TempScheduleAdd /> },
        { path: 'tempSchedules/edit/:tempScheduleId', element: <TempScheduleEdit /> },
        { path: 'groupUsers', element: <GroupUserList /> },
        { path: 'groupUsers/add', element: <GroupUserAdd /> },
        { path: 'groupUsers/edit/:groupUserId', element: <GroupUserEdit /> },
        { path: 'schedules', element: <ScheduleList /> },
        { path: 'schedules/add', element: <ScheduleAdd /> },
        { path: 'schedules/edit/:scheduleId', element: <ScheduleEdit /> },
        { path: 'error', element: <Error /> },
        { path: '*', element: <Error /> }
      ]
    }
  ]);

  return (
    <RouterProvider router={router} />
  );
}
