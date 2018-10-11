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
        String googleId = getRequestParamValue(Const.ParamsNames.USER_ID);
        Assumption.assertPostParamNotNull(Const.ParamsNames.USER_ID, googleId);
        StudentProfileAttributes studentProfile = logic.getStudentProfile(googleId);
        studentProfile.like++;
        try {
            logic.updateStudentProfile(studentProfile);
        } catch (InvalidParametersException e) {
        }
        log.warning("like for " + studentProfile.shortName + " is: " + studentProfile.like);
        /*statusToAdmin = "instructorCourseStudentDetails Page Load<br>"
                + "Viewing details for Student <span class=\"bold\">" + studentEmail
                + "</span> in Course <span class=\"bold\">[" + courseId + "]</span>";*/
        return createRedirectResult(Const.ActionURIs.STUDENT_PROFILE_PAGE + "?user=" + googleId);

    }

}

