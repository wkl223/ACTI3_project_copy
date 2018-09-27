package teammates.storage.entity;

import java.security.SecureRandom;

//Beginning of added imports
package com.example.getstarted.util;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Acl.Role;
import com.google.cloud.storage.Acl.User;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
//End of added imports

import com.google.appengine.api.datastore.Text;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

/**
 * An association class that represents the association Account
 * --> [is an instructor for] --> Course.
 */
@Entity
@Index
public class Instructor extends BaseEntity {

    /**
     * The primary key. Format: email%courseId e.g., adam@gmail.com%cs1101
     */
    @Id
    private String id;

    /**
     * The Google id of the instructor, used as the foreign key to locate the Account object.
     */
    private String googleId;

    /** The foreign key to locate the Course object. */
    private String courseId;

    /** new attribute. Default value: Old Entity--null  New Entity--false*/
    private Boolean isArchived;

    /** The instructor's name used for this course. */
    private String name;

    /** The instructor's email used for this course. */
    private String email;

    /** The instructor's registration key used for joining. */
    private String registrationKey;

    @Unindex
    private String role;

    @Unindex
    private Boolean isDisplayedToStudents;

    @Unindex
    private String displayedName;

    private Text instructorPrivilegesAsText;
    
    //Beginning of lines added
    
    final String bucketName = "acti3-216912.appspot.com";
    
    /* Name of the PDF file to be uploaded*/
    private String PDFFileName;
    
    public void setPDFFileame(String name) {
        this.PDFFileName = name;
    }
    
    private static Storage storage = null;
    
    static {
        storage = StorageOptions.getDefaultInstance().getService();
      }

    /* File upload method */
    public String uploadFile(String filename, final String bucketName) throws IOException {
        BlobInfo blobInfo =
            storage.create(BlobInfo.newBuilder(bucketName, filename).setAcl(new ArrayList<>(Arrays.asList(Acl.of(User.ofAllUsers(), Role.READER)))).build(),filePart.getInputStream());
        // return the public download link
        return blobInfo.getMediaLink();
      }
    
    //End of lines added
    
    @SuppressWarnings("unused")
    private Instructor() {
        // required by Objectify
    }

    public Instructor(String instructorGoogleId, String courseId, Boolean isArchived, String instructorName,
                      String instructorEmail, String role, boolean isDisplayedToStudents, String displayedName,
                      String instructorPrivilegesAsText) {
        this.setGoogleId(instructorGoogleId);
        this.setCourseId(courseId);
        this.setIsArchived(isArchived);
        this.setName(instructorName);
        this.setEmail(instructorEmail);
        this.setRole(role);
        this.setIsDisplayedToStudents(isDisplayedToStudents);
        this.setDisplayedName(displayedName);
        this.setInstructorPrivilegeAsText(instructorPrivilegesAsText);
        // setId should be called after setting email and courseId
        this.setUniqueId(this.getEmail() + '%' + this.getCourseId());
        this.setRegistrationKey(generateRegistrationKey());
    }

    /**
     * Constructor used for testing purpose only.
     */
    public Instructor(String instructorGoogleId, String courseId, Boolean isArchived, String instructorName,
                      String instructorEmail, String key, String role, boolean isDisplayedToStudents,
                      String displayedName, String instructorPrivilegesAsText) {
        this.setGoogleId(instructorGoogleId);
        this.setCourseId(courseId);
        this.setIsArchived(isArchived);
        this.setName(instructorName);
        this.setEmail(instructorEmail);
        this.setRole(role);
        this.setIsDisplayedToStudents(isDisplayedToStudents);
        this.setDisplayedName(displayedName);
        this.setInstructorPrivilegeAsText(instructorPrivilegesAsText);
        // setId should be called after setting email and courseId
        this.setUniqueId(this.getEmail() + '%' + this.getCourseId());
        this.setRegistrationKey(key);
    }

    /**
     * Returns the unique ID of the entity (format: email%courseId).
     */
    public String getUniqueId() {
        return id;
    }

    /**
     * Sets the unique ID for the instructor entity.
     *
     * @param uniqueId
     *          The unique ID of the entity (format: email%courseId).
     */
    public void setUniqueId(String uniqueId) {
        this.id = uniqueId;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String instructorGoogleId) {
        this.googleId = instructorGoogleId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public Boolean getIsArchived() {
        return isArchived;
    }

    public void setIsArchived(Boolean isArchived) {
        this.isArchived = isArchived;
    }

    public String getName() {
        return name;
    }

    public void setName(String instructorName) {
        this.name = instructorName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String instructorEmail) {
        this.email = instructorEmail;
    }

    public String getRegistrationKey() {
        return registrationKey;
    }

    public void setRegistrationKey(String key) {
        this.registrationKey = key;
    }

    public void setGeneratedKeyIfNull() {
        if (this.registrationKey == null) {
            setRegistrationKey(generateRegistrationKey());
        }
    }

    /**
     * Generate unique registration key for the instructor.
     * The key contains random elements to avoid being guessed.
     */
    private String generateRegistrationKey() {
        String uniqueId = getUniqueId();
        SecureRandom prng = new SecureRandom();

        return uniqueId + prng.nextInt();
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isDisplayedToStudents() {
        if (this.isDisplayedToStudents == null) {
            return true;
        }
        return isDisplayedToStudents;
    }

    public void setIsDisplayedToStudents(boolean shouldDisplayToStudents) {
        this.isDisplayedToStudents = shouldDisplayToStudents;
    }

    public String getDisplayedName() {
        return displayedName;
    }

    public void setDisplayedName(String displayedName) {
        this.displayedName = displayedName;
    }

    public String getInstructorPrivilegesAsText() {
        if (instructorPrivilegesAsText == null) {
            return null;
        }
        return instructorPrivilegesAsText.getValue();
    }

    public void setInstructorPrivilegeAsText(String instructorPrivilegesAsText) {
        this.instructorPrivilegesAsText = new Text(instructorPrivilegesAsText);
    }
    
}
