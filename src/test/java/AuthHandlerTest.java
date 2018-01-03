import com.bdlions.util.ACTION;
import com.bdlions.util.REQUEST_TYPE;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.List;
import org.bdlions.auction.dto.DTOUser;
import org.bdlions.auction.entity.EntityRole;
import org.bdlions.auction.entity.EntityUser;
import org.bdlions.auction.packet.PacketHeaderImpl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author nazmul hasan
 */
public class AuthHandlerTest extends HTTPRequestHelper{
    
    public AuthHandlerTest() {
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
    
    //@Test
    public void signInTest() {
        PacketHeaderImpl mockPacketHeader = new PacketHeaderImpl();
        mockPacketHeader.setAction(ACTION.SIGN_IN);
        mockPacketHeader.setRequestType(REQUEST_TYPE.AUTH);

        String packetHeader = new GsonBuilder().create().toJson(mockPacketHeader);
        System.out.println(packetHeader);


        String packetBody = "{\"userName\":\"admin@gmail.com\", \"password\":\"pass\"}";
        System.out.println(packetBody);

        String result = getResult(packetHeader, packetBody);
        System.out.println("Result : " + result);
    }
    
    //@Test
    public void signOutTest() 
    {   
        PacketHeaderImpl mockPacketHeader = new PacketHeaderImpl();
        mockPacketHeader.setAction(ACTION.SIGN_OUT);
        mockPacketHeader.setRequestType(REQUEST_TYPE.AUTH);
        mockPacketHeader.setSessionId(getSessionId());

        String packetHeader = new GsonBuilder().create().toJson(mockPacketHeader);
        System.out.println(packetHeader);

        String packetBody = "{}";
        System.out.println(packetBody);

        String result = getResult(packetHeader, packetBody);
        System.out.println("Result : " + result);
    }
    
    //@Test
    public void signUpTest() {
        PacketHeaderImpl mockPacketHeader = new PacketHeaderImpl();
        mockPacketHeader.setAction(ACTION.SIGN_UP);
        mockPacketHeader.setRequestType(REQUEST_TYPE.AUTH);

        String packetHeader = new GsonBuilder().create().toJson(mockPacketHeader);
        System.out.println(packetHeader);
        
        EntityUser entityUser = new EntityUser();
        entityUser.setEmail("emon@gmail.com");
        entityUser.setPassword("pass");
        entityUser.setCell("01766123456");
        EntityRole entityRole = new EntityRole();
        entityRole.setId(2);
        List<EntityRole> entityRoles = new ArrayList<>();
        entityRoles.add(entityRole);
        DTOUser dTOUser = new DTOUser();
        dTOUser.setEntityUser(entityUser);
        dTOUser.setRoles(entityRoles);
        

        String packetBody = new GsonBuilder().create().toJson(dTOUser);
        System.out.println(packetBody);

        String result = getResult(packetHeader, packetBody);
        System.out.println("Result : " + result);
    }
}
