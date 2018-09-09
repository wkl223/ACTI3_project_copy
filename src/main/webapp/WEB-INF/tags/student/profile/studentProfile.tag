<%@ tag trimDirectiveWhitespaces="true" %>
<%@ tag description="Student Profile" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ attribute name="student" type="teammates.ui.template.StudentProfile" required="true" %>
<%@ tag import="teammates.common.util.Const" %>
<c:set var="none"><i class="text-muted"><%= Const.STUDENT_PROFILE_FIELD_NOT_FILLED %></i></c:set>
<c:set var="noneForGender"><span class="text-muted"><%= Const.STUDENT_PROFILE_FIELD_NOT_FILLED %></span></c:set>
<c:set var="other"><%= Const.GenderTypes.OTHER %></c:set>
<c:set var="jsIncludes">
  <script type="text/javascript" src="/js/studentProfile.js"></script>
</c:set>
<div class="row">
  <div class="col-xs-12">
    <div class="row" id="studentProfile">
      <div class="col-md-2 col-xs-3 block-center">
        <img src="${student.pictureUrl}" class="profile-pic pull-right">
      </div>
      <div class="col-md-10 col-sm-9 col-xs-8">
        <table class="table table-striped">
          <thead>
            <tr>
              <th colspan="2"><h4><span class="text-bold">Profile Details </span>(submitted by student)</h4></th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td class="text-bold">Short Name (Gender)</td>
              <td>${empty student.shortName ? none : fn:escapeXml(student.shortName)}
                (<i> ${student.gender == other ? noneForGender : fn:escapeXml(student.gender)} </i>)
              </td>
            </tr>
            <tr>
              <td class="text-bold">Personal Email</td>
              <td>${empty student.email ? none : fn:escapeXml(student.email)}</td>
            </tr>
            <tr>
              <td class="text-bold">Institution</td>
              <td>${empty student.institute ? none : fn:escapeXml(student.institute)}</td>
            </tr>
            <tr>
              <td class="text-bold">Nationality</td>
              <td>${empty student.nationality ? none : fn:escapeXml(student.nationality)}</td>
            </tr>
            <tr>
              <td class="text-bold">Like</td>
              <td>${empty student.like ? none : fn:escapeXml(student.like)}
                <form action="<%=Const.ActionURIs.INSTRUCTOR_COURSE_STUDENT_DETAILS_LIKE%>" method = "get">
                <input type="submit"  name="Like" value="Like">
                <input type="hidden" name="<%= Const.ParamsNames.STUDENT_SHORT_NAME %>" data-actual-value="<c:out value="${student.shortName}"/>" value="<c:out value="${student.shortName}"/>">
                <input type="hidden" name="<%= Const.ParamsNames.STUDENT_EMAIL %>" value="${student.email}">
                <input type="hidden" name="<%= Const.ParamsNames.STUDENT_PROFILE_INSTITUTION %>" data-actual-value="<c:out value="${student.institute}"/>" value="<c:out value="${student.institute}"/>">
                <input type="hidden" name="<%=Const.ParamsNames.STUDENT_NATIONALITY%>" value="${student.nationality}">
                <input type="hidden" name="<%=Const.ParamsNames.STUDENT_GENDER%>" value="${student.gender}">
                <input type="hidden" name="<%= Const.ParamsNames.USER_ID %>" value="${student.googleId}">
                <input type="hidden" name="<%= Const.ParamsNames.SESSION_TOKEN %>" value="${sessionToken}">
              </form>
                </td>
              <a class="text-bold" id="studentProfileNavLink" href="studentProfileEditPage">
              Profile edit
            </a>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</div>
