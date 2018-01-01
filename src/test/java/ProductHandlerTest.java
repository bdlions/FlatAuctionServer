import com.bdlions.dto.response.ClientResponse;
import com.bdlions.util.ACTION;
import com.bdlions.util.REQUEST_TYPE;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import java.util.ArrayList;
import java.util.List;
import org.bdlions.auction.dto.DTOProduct;
import org.bdlions.auction.dto.DTOUser;
import org.bdlions.auction.entity.EntityProduct;
import org.bdlions.auction.entity.EntityRole;
import org.bdlions.auction.entity.EntityUser;
import org.bdlions.auction.packet.PacketHeaderImpl;
import org.bdlions.auction.util.TimeUtils;
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
public class ProductHandlerTest extends HTTPRequestHelper{
    
    public ProductHandlerTest() {
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
    public void addProductInfoTest() {
        PacketHeaderImpl mockPacketHeader = new PacketHeaderImpl();
        mockPacketHeader.setAction(ACTION.ADD_PRODUCT_INFO);
        mockPacketHeader.setRequestType(REQUEST_TYPE.REQUEST);
        mockPacketHeader.setSessionId(getSessionId());

        String packetHeader = new GsonBuilder().create().toJson(mockPacketHeader);
        System.out.println(packetHeader);
        
        EntityProduct entityProduct = new EntityProduct();
        entityProduct.setReferenceId("ref1");
        entityProduct.setUserId(1);
        entityProduct.setTitle("House rent at banani");
        entityProduct.setDescription("3 BED 4 BATH DRAWING DINNING");
        entityProduct.setBasePrice(200);
        String packetBody = new GsonBuilder().create().toJson(entityProduct);
        System.out.println(packetBody);

        String result = getResult(packetHeader, packetBody);
        System.out.println("Result : " + result);
    }
    
    //@Test
    public void getProductInfoTest() {
        PacketHeaderImpl mockPacketHeader = new PacketHeaderImpl();
        mockPacketHeader.setAction(ACTION.FETCH_PRODUCT_INFO);
        mockPacketHeader.setRequestType(REQUEST_TYPE.REQUEST);
        mockPacketHeader.setSessionId(getSessionId());

        String packetHeader = new GsonBuilder().create().toJson(mockPacketHeader);
        System.out.println(packetHeader);
        
        EntityProduct entityProduct = new EntityProduct();
        entityProduct.setId(1);
        String packetBody = new GsonBuilder().create().toJson(entityProduct);
        System.out.println(packetBody);

        String result = getResult(packetHeader, packetBody);
        System.out.println("Result : " + result);
    }
    
    //@Test
    public void updateProductInfoTest() {
        PacketHeaderImpl mockPacketHeader = new PacketHeaderImpl();
        mockPacketHeader.setAction(ACTION.UPDATE_PRODUCT_INFO);
        mockPacketHeader.setRequestType(REQUEST_TYPE.UPDATE);
        mockPacketHeader.setSessionId(getSessionId());

        String packetHeader = new GsonBuilder().create().toJson(mockPacketHeader);
        System.out.println(packetHeader);
        
        EntityProduct entityProduct = new EntityProduct();
        entityProduct.setId(1);
        entityProduct.setReferenceId("ref11");
        entityProduct.setUserId(1);
        entityProduct.setTitle("House rent at banani1");
        entityProduct.setDescription("3 BED 4 BATH DRAWING DINNING1");
        entityProduct.setBasePrice(201);
        String packetBody = new GsonBuilder().create().toJson(entityProduct);
        System.out.println(packetBody);

        String result = getResult(packetHeader, packetBody);
        System.out.println("Result : " + result);
    }
    
    //@Test
    public void getUserProductListTest() {
        PacketHeaderImpl mockPacketHeader = new PacketHeaderImpl();
        mockPacketHeader.setAction(ACTION.FETCH_USER_PRODUCT_LIST);
        mockPacketHeader.setRequestType(REQUEST_TYPE.REQUEST);
        mockPacketHeader.setSessionId(getSessionId());

        String packetHeader = new GsonBuilder().create().toJson(mockPacketHeader);
        System.out.println(packetHeader);
        
        DTOProduct dtoProduct = new DTOProduct();
        dtoProduct.setOffset(0);
        dtoProduct.setLimit(10);
        String packetBody = new GsonBuilder().create().toJson(dtoProduct);
        System.out.println(packetBody);

        String result = getResult(packetHeader, packetBody);
        System.out.println("Result : " + result);        
    }
    
    //@Test
    public void getClosingProductListTest() {
        PacketHeaderImpl mockPacketHeader = new PacketHeaderImpl();
        mockPacketHeader.setAction(ACTION.FETCH_CLOSING_PRODUCT_LIST);
        mockPacketHeader.setRequestType(REQUEST_TYPE.REQUEST);
        mockPacketHeader.setSessionId(getSessionId());

        String packetHeader = new GsonBuilder().create().toJson(mockPacketHeader);
        System.out.println(packetHeader);
        
        DTOProduct dtoProduct = new DTOProduct();
        dtoProduct.setOffset(0);
        dtoProduct.setLimit(10);
        String packetBody = new GsonBuilder().create().toJson(dtoProduct);
        System.out.println(packetBody);

        String result = getResult(packetHeader, packetBody);
        System.out.println("Result : " + result);        
    }
}
