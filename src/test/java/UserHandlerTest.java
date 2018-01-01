import com.bdlions.dto.response.ClientResponse;
import com.bdlions.util.ACTION;
import com.bdlions.util.REQUEST_TYPE;
import com.google.gson.Gson;
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
public class UserHandlerTest extends HTTPRequestHelper{
    
    public UserHandlerTest() {
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
    public void getUserInfoTest() {
        PacketHeaderImpl mockPacketHeader = new PacketHeaderImpl();
        mockPacketHeader.setAction(ACTION.FETCH_USER_INFO);
        mockPacketHeader.setRequestType(REQUEST_TYPE.REQUEST);
        mockPacketHeader.setSessionId(getSessionId());

        String packetHeader = new GsonBuilder().create().toJson(mockPacketHeader);
        System.out.println(packetHeader);
        String packetBody = "{}";
        System.out.println(packetBody);

        String result = getResult(packetHeader, packetBody);
        System.out.println("Result : " + result);
    }
    
    //@Test
    public void getMemberRolesTest() {
        PacketHeaderImpl mockPacketHeader = new PacketHeaderImpl();
        mockPacketHeader.setAction(ACTION.FETCH_MEMBER_ROLES);
        mockPacketHeader.setRequestType(REQUEST_TYPE.REQUEST);
        mockPacketHeader.setSessionId(getSessionId());

        String packetHeader = new GsonBuilder().create().toJson(mockPacketHeader);
        System.out.println(packetHeader);
        String packetBody = "{}";
        System.out.println(packetBody);

        String result = getResult(packetHeader, packetBody);
        System.out.println("Result : " + result);
    }
    
    //@Test
    public void updateUserInfoTest() {
        DTOUser dTOUser = new DTOUser();
        EntityUser entityUser = new EntityUser();
        entityUser.setId(8);
        entityUser.setEmail("emon@gmail.com");
        entityUser.setPassword("pass");
        entityUser.setCell("01777123456");
        dTOUser.setEntityUser(entityUser);
        EntityRole entityRole = new EntityRole();
        entityRole.setId(3);
        List<EntityRole> entityRoles = new ArrayList<>();
        entityRoles.add(entityRole);
        dTOUser.setRoles(entityRoles);
        
        PacketHeaderImpl mockPacketHeader1 = new PacketHeaderImpl();
        mockPacketHeader1.setAction(ACTION.UPDATE_USER_INFO);
        mockPacketHeader1.setRequestType(REQUEST_TYPE.UPDATE);
        mockPacketHeader1.setSessionId(getSessionId());

        String packetHeader1 = new GsonBuilder().create().toJson(mockPacketHeader1);
        System.out.println(packetHeader1);
        
        String packetBody1 = new GsonBuilder().create().toJson(dTOUser);
        System.out.println(packetBody1);

        String result1 = getResult(packetHeader1, packetBody1);
        System.out.println("Result1 : " + result1);
        
    }
    
    //@Test
    public void updateUserProfilePictureTest() {
        EntityUser entityUser = new EntityUser();
        entityUser.setImg("Desert.jpg");
        
        PacketHeaderImpl mockPacketHeader1 = new PacketHeaderImpl();
        mockPacketHeader1.setAction(ACTION.UPDATE_USER_PROFILE_PICTURE);
        mockPacketHeader1.setRequestType(REQUEST_TYPE.UPDATE);
        mockPacketHeader1.setSessionId(getSessionId());

        String packetHeader1 = new GsonBuilder().create().toJson(mockPacketHeader1);
        System.out.println(packetHeader1);
        
        String packetBody1 = new GsonBuilder().create().toJson(entityUser);
        System.out.println(packetBody1);

        String result1 = getResult(packetHeader1, packetBody1);
        System.out.println("Result1 : " + result1);
        
    }
}
