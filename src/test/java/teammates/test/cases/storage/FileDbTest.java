package teammates.test.cases.storage;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
//import teammates.common.datatransfer.DataBundle;
import teammates.common.exception.EntityAlreadyExistsException;
//import teammates.storage.api.InstructorsDb;
import teammates.test.cases.BaseComponentTestCase;

import java.util.Set;

public class FileDbTest extends BaseComponentTestCase {
    /*
    private static final FileDb fileDb = new FileDb();
    //private DataBundle dataBundle = getTypicalDataBundle();
*/
    @BeforeClass
    public void classSetup() throws Exception {
        //addFilesToDb();
    }
/*
    private void addFilesToDb() throws Exception {
        //use Key as reference?
        //... some assignments here for assigning keys
/*
        try {

            fileDb.createEntity();

        } catch (EntityAlreadyExistsException e) {

            fileDb.updateFile();


        }
    }
   */
    /*
    @Test
    1.Get key of the file, then use the key in local (generated while upload) to compare
        a. 1 file
        b. 3 files
        c. 10 files
    2. Upload the file with the same file name (rewrite the old one), and compare the newest key with the local one.
        a. 1 file
        b. 3 files
    3. Retrieve file with corresponding key.
        a. 1 file
        b. 3 files
     */


}
