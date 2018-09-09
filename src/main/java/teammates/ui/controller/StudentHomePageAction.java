package teammates.ui.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import teammates.common.datatransfer.CourseDetailsBundle;
import teammates.common.datatransfer.FeedbackSessionDetailsBundle;
import teammates.common.datatransfer.attributes.FeedbackSessionAttributes;
import teammates.common.datatransfer.attributes.StudentAttributes;
import teammates.common.exception.EmailSendingException;
import teammates.common.exception.EntityDoesNotExistException;
import teammates.common.util.*;
import teammates.logic.api.EmailSender;
import teammates.ui.pagedata.StudentHomePageData;

public class StudentHomePageAction extends Action {

    @Override
    public ActionResult execute() {
        gateKeeper.verifyLoggedInUserPrivileges();
        EmailWrapper confirmation = new EmailWrapper();
        confirmation.setRecipient(account.email);
        confirmation.setSubject("User: " +  account.name + " logged in.");
        confirmation.setContent("Hey " + account.name + " you have just been logged in to TEAMMATES. Welcome");

        EmailSender sendConfirm = new EmailSender();
        String recentlyJoinedCourseId = getRequestParamValue(Const.ParamsNames.CHECK_PERSISTENCE_COURSE);

        List<CourseDetailsBundle> courses = new ArrayList<>();
        Map<FeedbackSessionAttributes, Boolean> sessionSubmissionStatusMap = new HashMap<>();

        try {
            sendConfirm.sendEmail(confirmation);
            courses = logic.getCourseDetailsListForStudent(account.googleId);
            sessionSubmissionStatusMap = generateFeedbackSessionSubmissionStatusMap(courses, account.googleId);

            CourseDetailsBundle.sortDetailedCoursesByCourseId(courses);

            statusToAdmin = "studentHome Page Load<br>" + "Total courses: " + courses.size();

            boolean isDataConsistent = isCourseIncluded(recentlyJoinedCourseId, courses);
            if (!isDataConsistent) {
                addPlaceholderCourse(courses, recentlyJoinedCourseId, sessionSubmissionStatusMap);
            }

            for (CourseDetailsBundle course : courses) {
                FeedbackSessionDetailsBundle.sortFeedbackSessionsByCreationTime(course.feedbackSessions);
            }

        } catch (EntityDoesNotExistException e) {
            if (recentlyJoinedCourseId == null) {
                statusToUser.add(new StatusMessage(Const.StatusMessages.STUDENT_FIRST_TIME, StatusMessageColor.WARNING));
                statusToAdmin = Const.ACTION_RESULT_FAILURE + " :" + e.getMessage();
            } else {
                addPlaceholderCourse(courses, recentlyJoinedCourseId, sessionSubmissionStatusMap);
            }
        } catch (EmailSendingException e) {
            System.out.println("Unable to send email.");
        }

        StudentHomePageData data = new StudentHomePageData(account, sessionToken, courses, sessionSubmissionStatusMap);

        return createShowPageResult(Const.ViewURIs.STUDENT_HOME, data);
    }

    private Map<FeedbackSessionAttributes, Boolean> generateFeedbackSessionSubmissionStatusMap(
            List<CourseDetailsBundle> courses, String googleId) {
        Map<FeedbackSessionAttributes, Boolean> returnValue = new HashMap<>();

        for (CourseDetailsBundle c : courses) {
            for (FeedbackSessionDetailsBundle fsb : c.feedbackSessions) {
                FeedbackSessionAttributes f = fsb.feedbackSession;
                returnValue.put(f, getStudentStatusForSession(f, googleId));
            }
        }
        return returnValue;
    }

    private boolean getStudentStatusForSession(FeedbackSessionAttributes fs, String googleId) {
        StudentAttributes student = logic.getStudentForGoogleId(fs.getCourseId(), googleId);
        Assumption.assertNotNull(student);

        String studentEmail = student.email;

        return logic.hasStudentSubmittedFeedback(fs, studentEmail);
    }

    private boolean isCourseIncluded(String recentlyJoinedCourseId, List<CourseDetailsBundle> courses) {
        boolean isCourseIncluded = false;

        if (recentlyJoinedCourseId == null) {
            isCourseIncluded = true;
        } else {
            for (CourseDetailsBundle currentCourse : courses) {
                if (currentCourse.course.getId().equals(recentlyJoinedCourseId)) {
                    isCourseIncluded = true;
                }
            }
        }

        return isCourseIncluded;
    }

    private void showEventualConsistencyMessage(String recentlyJoinedCourseId) {
        String errorMessage = String.format(Const.StatusMessages.EVENTUAL_CONSISTENCY_MESSAGE_STUDENT,
                                            recentlyJoinedCourseId);
        statusToUser.add(new StatusMessage(errorMessage, StatusMessageColor.DANGER));
    }

    private void addPlaceholderCourse(List<CourseDetailsBundle> courses, String courseId,
            Map<FeedbackSessionAttributes, Boolean> sessionSubmissionStatusMap) {
        try {
            CourseDetailsBundle course = logic.getCourseDetails(courseId);
            courses.add(course);

            addPlaceholderFeedbackSessions(course, sessionSubmissionStatusMap);
            FeedbackSessionDetailsBundle.sortFeedbackSessionsByCreationTime(course.feedbackSessions);

        } catch (EntityDoesNotExistException e) {
            showEventualConsistencyMessage(courseId);
            statusToAdmin = Const.ACTION_RESULT_FAILURE + " :" + e.getMessage();
        }
    }

    private void addPlaceholderFeedbackSessions(CourseDetailsBundle course,
                                                Map<FeedbackSessionAttributes, Boolean> sessionSubmissionStatusMap) {
        for (FeedbackSessionDetailsBundle fsb : course.feedbackSessions) {
            sessionSubmissionStatusMap.put(fsb.feedbackSession, true);
        }
    }
}
