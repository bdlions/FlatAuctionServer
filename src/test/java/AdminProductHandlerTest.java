import com.bdlions.util.ACTION;
import com.bdlions.util.REQUEST_TYPE;
import com.google.gson.GsonBuilder;
import org.bdlions.auction.dto.DTOProduct;
import org.bdlions.auction.packet.PacketHeaderImpl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author nazmul hasan
 */
public class AdminProductHandlerTest extends HTTPRequestHelper{
    
    public AdminProductHandlerTest() {
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
    public void getProductListTest() {
        PacketHeaderImpl mockPacketHeader = new PacketHeaderImpl();
        mockPacketHeader.setAction(ACTION.FETCH_PRODUCT_LIST);
        mockPacketHeader.setRequestType(REQUEST_TYPE.REQUEST);
        mockPacketHeader.setSessionId(getSessionId());

        String packetHeader = new GsonBuilder().create().toJson(mockPacketHeader);
        System.out.println(packetHeader);
        
        DTOProduct dtoProduct = new DTOProduct();
        dtoProduct.setOffset(0);
        dtoProduct.setLimit(10);
        
        String packetBody = "{}";
        System.out.println(packetBody);

        String result = getResult(packetHeader, packetBody);
        System.out.println("Result : " + result);
    }
}
