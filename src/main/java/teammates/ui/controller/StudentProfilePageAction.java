package teammates.ui.controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import teammates.common.datatransfer.attributes.StudentAttributes;
import teammates.common.datatransfer.attributes.StudentProfileAttributes;
import teammates.common.exception.EntityDoesNotExistException;
import teammates.common.util.*;
import teammates.ui.pagedata.StudentProfilePageData;

/**
 * Action: showing the profile page for a student in a course.
 */
public class StudentProfilePageAction extends Action {

    private static final Logger log = Logger.getLogger();
    private static String user;
    @Override
    public ActionResult execute() throws EntityDoesNotExistException {


        String studentEmail = getRequestParamValue("user");
        Assumption.assertPostParamNotNull(Const.ParamsNames.STUDENT_EMAIL, studentEmail);

        StudentProfileAttributes student = logic.getStudentProfile(studentEmail);
        //log.warning("got student:"+studentEmail+" with: "+student.googleId+" as google ID");
        if (student == null) {
            statusToUser.add(new StatusMessage(Const.StatusMessages.STUDENT_NOT_FOUND_FOR_COURSE_DETAILS,
                    StatusMessageColor.DANGER));
            isError = true;
            return createRedirectResult(Const.ActionURIs.INSTRUCTOR_HOME_PAGE);
        }

        /*StudentProfileAttributes studentProfile = loadStudentProfile(student);


        StudentProfilePageData data =
                new StudentProfilePageData(account, sessionToken, student, studentProfile);

        statusToAdmin = "studentProfile Page Load<br>"
                + "Viewing details for Student <span class=\"bold\">" + studentEmail
               + "</span> in Course <span class=\"bold\">[" + courseId + "]</span>";
        */
        StudentProfilePageData data =
                new StudentProfilePageData(account,sessionToken,student);
        return createShowPageResult(Const.ViewURIs.STUDENT_PROFILE_PAGE, data);

    }

    private StudentProfileAttributes loadStudentProfile(StudentAttributes student) {
        StudentProfileAttributes studentProfile = null;
        boolean isStudentWithProfile = !student.googleId.isEmpty();
        if (isStudentWithProfile) {
            studentProfile = logic.getStudentProfile(student.googleId);
            Assumption.assertNotNull(studentProfile);
            return studentProfile;
        }
        // this means that the user is returning to the page and is not the first time
        boolean hasExistingStatus = !statusToUser.isEmpty()
                || session.getAttribute(Const.ParamsNames.STATUS_MESSAGES_LIST) != null;
        if (!hasExistingStatus) {
            statusToUser.add(new StatusMessage(Const.StatusMessages.STUDENT_NOT_JOINED_YET_FOR_RECORDS,
                    StatusMessageColor.WARNING));
        }
        if (!hasExistingStatus) {
            statusToUser.add(new StatusMessage(Const.StatusMessages.STUDENT_PROFILE_UNACCESSIBLE_TO_INSTRUCTOR,
                    StatusMessageColor.WARNING));
        }
        return null;
    }
}
