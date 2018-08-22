package teammates.ui.controller;

import teammates.common.datatransfer.attributes.InstructorAttributes;
import teammates.common.datatransfer.attributes.StudentAttributes;
import teammates.common.datatransfer.attributes.StudentProfileAttributes;
import teammates.common.exception.EntityDoesNotExistException;
import teammates.common.exception.InvalidParametersException;
import teammates.common.util.*;
import teammates.common.util.Logger;
import teammates.storage.entity.StudentProfile;
import teammates.ui.pagedata.InstructorCourseStudentDetailsPageData;

public class InstructorCourseStudentDetailsLikeAction extends Action {

    public ActionResult execute() throws EntityDoesNotExistException {

        final Logger log = Logger.getLogger();
        String googleId=getRequestParamValue(Const.ParamsNames.USER_ID);
        Assumption.assertPostParamNotNull(Const.ParamsNames.USER_ID, googleId);

/*
        String courseId = getRequestParamValue(Const.ParamsNames.COURSE_ID);
        Assumption.assertPostParamNotNull(Const.ParamsNames.COURSE_ID, courseId);

        String studentEmail = getRequestParamValue(Const.ParamsNames.STUDENT_EMAIL);
        Assumption.assertPostParamNotNull(Const.ParamsNames.STUDENT_EMAIL, studentEmail);

        StudentAttributes student = logic.getStudentForEmail(courseId, studentEmail);
        if (student == null) {
            statusToUser.add(new StatusMessage(Const.StatusMessages.STUDENT_NOT_FOUND_FOR_COURSE_DETAILS,
                                               StatusMessageColor.DANGER));
            isError = true;
            return createRedirectResult(Const.ActionURIs.INSTRUCTOR_HOME_PAGE);
        }
        InstructorAttributes instructor = logic.getInstructorForGoogleId(courseId, account.googleId);
        gateKeeper.verifyAccessible(instructor, logic.getCourse(courseId), student.section,
                                    Const.ParamsNames.INSTRUCTOR_PERMISSION_VIEW_STUDENT_IN_SECTIONS);

        boolean hasSection = logic.hasIndicatedSections(courseId);

        StudentProfileAttributes studentProfile = loadStudentProfile(student, instructor);


        InstructorCourseStudentDetailsPageData data =
                new InstructorCourseStudentDetailsPageData(account, sessionToken, student, studentProfile,
                                                           hasSection);

        statusToAdmin = "instructorCourseStudentDetails Page Load<br>"
                        + "Viewing details for Student <span class=\"bold\">" + studentEmail
                        + "</span> in Course <span class=\"bold\">[" + courseId + "]</span>";

        return createShowPageResult(Const.ViewURIs.INSTRUCTOR_COURSE_STUDENT_DETAILS, data);
*/
        StudentProfileAttributes studentProfile = logic.getStudentProfile(googleId);
        studentProfile.like++;
        try {
            logic.updateStudentProfile(studentProfile);
        }
        catch(InvalidParametersException e)
        { }
        log.warning("like for "+studentProfile.shortName+" is: "+studentProfile.like);
        /*statusToAdmin = "instructorCourseStudentDetails Page Load<br>"
                + "Viewing details for Student <span class=\"bold\">" + studentEmail
                + "</span> in Course <span class=\"bold\">[" + courseId + "]</span>";*/
        return createRedirectResult(Const.ActionURIs.STUDENT_PROFILE_PAGE+"?user="+googleId);

    }

}

