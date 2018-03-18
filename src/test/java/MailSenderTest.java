import com.bdlions.util.ACTION;
import com.bdlions.util.REQUEST_TYPE;
import com.google.gson.GsonBuilder;
import java.util.UUID;
import org.bdlions.auction.packet.PacketHeaderImpl;
import org.bdlions.library.SendMail;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author nazmul hasan
 */
public class MailSenderTest extends HTTPRequestHelper{
    
    public MailSenderTest() 
    {
        
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    
    @Test
    public void sendMailTest() {
        String code = UUID.randomUUID().toString();
        SendMail sendMail = new SendMail();
        sendMail.sendSignUpMail("nazhasan15@yopmail.com", code);
    }
}
