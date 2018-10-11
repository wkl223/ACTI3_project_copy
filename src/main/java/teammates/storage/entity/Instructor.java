package teammates.storage.entity;

import java.security.SecureRandom;

//Beginning of added imports

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.Channels;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.tools.cloudstorage.GcsFileOptions;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsInputChannel;
import com.google.appengine.tools.cloudstorage.GcsOutputChannel;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.RetryParams;

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

    private static final int BUFFER_SIZE = 2 * 1024 * 1024;

    /**
     * The primary key. Format: email%courseId e.g., adam@gmail.com%cs1101
     */
    @Id
    private String id;

    /**
     * The Google id of the instructor, used as the foreign key to locate the Account object.
     */
    private String googleId;

    /**
     * The foreign key to locate the Course object.
     */
    private String courseId;

    /**
     * new attribute. Default value: Old Entity--null  New Entity--false
     */
    private Boolean isArchived;

    /**
     * The instructor's name used for this course.
     */
    private String name;

    /**
     * The instructor's email used for this course.
     */
    private String email;

    /**
     * The instructor's registration key used for joining.
     */
    private String registrationKey;

    @Unindex
    private String role;

    @Unindex
    private Boolean isDisplayedToStudents;

    @Unindex
    private String displayedName;

    private Text instructorPrivilegesAsText;
    
    //Beginning of lines added
    /* Code referred to Google Cloud appengine documentation:
     * https://cloud.google.com/appengine/docs/standard/java/googlecloudstorageclient/read-write-to-cloud-storage
     * */
    
    public final GcsService gcsService = GcsServiceFactory.createGcsService(new RetryParams.Builder()
            .initialRetryDelayMillis(10)
            .retryMaxAttempts(10)
            .totalRetryPeriodMillis(15000)
            .build());
    
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        GcsFileOptions instance = GcsFileOptions.getDefaultInstance();
        GcsFilename fileName = getFileName(req);
        GcsOutputChannel outputChannel;
        outputChannel = gcsService.createOrReplace(fileName, instance);
        copy(req.getInputStream(), Channels.newOutputStream(outputChannel));
      }
    
    private void copy(InputStream input, OutputStream output) throws IOException {
        try {
          byte[] buffer = new byte[BUFFER_SIZE];
          int bytesRead = input.read(buffer);
          while (bytesRead != -1) {
            output.write(buffer, 0, bytesRead);
            bytesRead = input.read(buffer);
          }
        } finally {
          input.close();
          output.close();
        }    
    }        
    
    private GcsFilename getFileName(HttpServletRequest req) {
        GcsFilename fileName = new GcsFilename(this.bucketName, this.PDFFileName);
        return fileName;
    }
    
    // Revise here for test purposes
    final String bucketName = "acti3-216912.appspot.com";
    
    /* Name of the PDF file to be uploaded*/
    private String PDFFileName;
    
    public void setPDFFileame(String name) {
        this.PDFFileName = name;
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
     * @param uniqueId The unique ID of the entity (format: email%courseId).
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
