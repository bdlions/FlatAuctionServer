import com.bdlions.dto.response.ClientResponse;
import com.bdlions.util.ACTION;
import com.bdlions.util.REQUEST_TYPE;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import java.util.ArrayList;
import java.util.List;
import org.bdlions.auction.dto.DTOBid;
import org.bdlions.auction.dto.DTOProduct;
import org.bdlions.auction.dto.DTOUser;
import org.bdlions.auction.entity.EntityBid;
import org.bdlions.auction.entity.EntityProduct;
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
public class BidHandlerTest extends HTTPRequestHelper{
    
    public BidHandlerTest() {
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
    public void addBidInfoTest() {
        PacketHeaderImpl mockPacketHeader = new PacketHeaderImpl();
        mockPacketHeader.setAction(ACTION.ADD_PRODUCT_BID);
        mockPacketHeader.setRequestType(REQUEST_TYPE.REQUEST);
        mockPacketHeader.setSessionId(getSessionId());

        String packetHeader = new GsonBuilder().create().toJson(mockPacketHeader);
        System.out.println(packetHeader);
        
        EntityBid entityBid = new EntityBid();
        entityBid.setProductId(1);
        entityBid.setProductTitle("House rent at banani12");
        entityBid.setUserId(2);
        entityBid.setFullName("Alamgir Kabir");
        entityBid.setPrice(30);
        
        String packetBody = new GsonBuilder().create().toJson(entityBid);
        System.out.println(packetBody);

        String result = getResult(packetHeader, packetBody);
        System.out.println("Result : " + result);
    }
    
    
    
    //@Test
    public void getProductBidListTest() {
        PacketHeaderImpl mockPacketHeader = new PacketHeaderImpl();
        mockPacketHeader.setAction(ACTION.FETCH_PRODUCT_BID_LIST);
        mockPacketHeader.setRequestType(REQUEST_TYPE.REQUEST);
        mockPacketHeader.setSessionId(getSessionId());

        String packetHeader = new GsonBuilder().create().toJson(mockPacketHeader);
        System.out.println(packetHeader);
        
        DTOBid dtoBid = new DTOBid();
        dtoBid.setOffset(0);
        dtoBid.setLimit(10);
        EntityBid entityBid = new EntityBid();
        entityBid.setProductId(1);
        dtoBid.setEntityBid(entityBid);
        String packetBody = new GsonBuilder().create().toJson(dtoBid);
        System.out.println(packetBody);

        String result = getResult(packetHeader, packetBody);
        System.out.println("Result : " + result);
    }
}
