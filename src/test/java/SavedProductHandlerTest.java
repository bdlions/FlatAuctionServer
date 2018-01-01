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
import org.bdlions.auction.entity.EntitySavedProduct;
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
public class SavedProductHandlerTest extends HTTPRequestHelper{
    
    public SavedProductHandlerTest() {
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
    public void addSavedProductTest() {
        PacketHeaderImpl mockPacketHeader = new PacketHeaderImpl();
        mockPacketHeader.setAction(ACTION.ADD_SAVED_PRODUCT);
        mockPacketHeader.setRequestType(REQUEST_TYPE.REQUEST);
        mockPacketHeader.setSessionId(getSessionId());

        String packetHeader = new GsonBuilder().create().toJson(mockPacketHeader);
        System.out.println(packetHeader);
        
        EntitySavedProduct entityUserProduct = new EntitySavedProduct();
        entityUserProduct.setProductId(2);
        
        String packetBody = new GsonBuilder().create().toJson(entityUserProduct);
        System.out.println(packetBody);

        String result = getResult(packetHeader, packetBody);
        System.out.println("Result : " + result);
    }
    
    
    
    //@Test
    public void getSavedProductTest() {
        PacketHeaderImpl mockPacketHeader = new PacketHeaderImpl();
        mockPacketHeader.setAction(ACTION.FETCH_SAVED_PRODUCT_LIST);
        mockPacketHeader.setRequestType(REQUEST_TYPE.REQUEST);
        mockPacketHeader.setSessionId(getSessionId());

        String packetHeader = new GsonBuilder().create().toJson(mockPacketHeader);
        System.out.println(packetHeader);
        
        DTOProduct dTOProduct = new DTOProduct();
        dTOProduct.setOffset(0);
        dTOProduct.setLimit(10);
        
        String packetBody = new GsonBuilder().create().toJson(dTOProduct);
        System.out.println(packetBody);

        String result = getResult(packetHeader, packetBody);
        System.out.println("Result : " + result);
    }
}
