package teammates.ui.pagedata;

import teammates.common.datatransfer.attributes.AccountAttributes;
import teammates.common.datatransfer.attributes.StudentAttributes;
import teammates.common.datatransfer.attributes.StudentProfileAttributes;
import teammates.common.util.Const;
import teammates.common.util.Url;
import teammates.ui.template.ElementTag;
import teammates.ui.template.StudentInfoTable;
import teammates.ui.template.StudentProfile;

public class InstructorCourseStudentDetailsPageData extends PageData {

    private StudentProfile studentProfile;
    private StudentInfoTable studentInfoTable;

    public InstructorCourseStudentDetailsPageData(AccountAttributes account, String sessionToken, StudentAttributes student,
            StudentProfileAttributes studentProfile, boolean hasSection) {
        super(account, sessionToken);
        if (studentProfile != null) {
            String pictureUrl = getPictureUrl(studentProfile.pictureKey);
            this.studentProfile = new StudentProfile(student.name, studentProfile, pictureUrl);
        }
        this.studentInfoTable = new StudentInfoTable(student, getInstructorCourseDetailsLink(student.course), hasSection);
        /*
        ElementTag likeButton = createButton("Like", "btn btn-default btn-xs"
                , "","/page/instructorCourseStudentDetailsPage?", "Like",
                false);//String content, String buttonClass, String id, String href, String title, boolean isDisabled
        //likeButton.setAttribute("data-course-id", course.getId());
        likeButton.setAttribute("style", "color: red");*/

    }

    public StudentProfile getStudentProfile() {
        return studentProfile;
    }

    public StudentInfoTable getStudentInfoTable() {
        return studentInfoTable;
    }
/*
    private ElementTag createButton(String content, String buttonClass, String id, String href, String title,
                                    boolean isDisabled) {
        ElementTag button = new ElementTag(content);

        button.setAttribute("class", buttonClass);

        if (id != null && !id.isEmpty()) {
            button.setAttribute("id", id);
        }

        if (href != null && !href.isEmpty()) {
            button.setAttribute("href", href);
        }

        if (title != null && !title.isEmpty()) {
            button.setAttribute("title", title);
            button.setAttribute("data-toggle", "tooltip");
            button.setAttribute("data-placement", "top");
        }

        if (isDisabled) {
            button.setAttribute("disabled", null);
        }
        return button;
    }*/
}
