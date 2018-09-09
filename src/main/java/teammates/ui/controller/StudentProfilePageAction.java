package teammates.ui.controller;


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

        statusToAdmin = "studentProfile Page Load<br>"
                + "Viewing details for Student <span class=\"bold\">" + studentEmail;

        StudentProfilePageData data =
                new StudentProfilePageData(account,sessionToken,student);
        return createShowPageResult(Const.ViewURIs.STUDENT_PROFILE_PAGE, data);

    }
}
