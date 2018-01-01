import com.bdlions.util.ACTION;
import com.bdlions.util.REQUEST_TYPE;
import com.google.gson.GsonBuilder;
import org.bdlions.auction.dto.DTOBid;
import org.bdlions.auction.dto.DTOMessageBody;
import org.bdlions.auction.dto.DTOMessageHeader;
import org.bdlions.auction.entity.EntityBid;
import org.bdlions.auction.entity.EntityMessageBody;
import org.bdlions.auction.entity.EntityMessageHeader;
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
public class MessageHandlerTest extends HTTPRequestHelper{
    
    public MessageHandlerTest() {
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
    public void addProductMessageTest() {
        PacketHeaderImpl mockPacketHeader = new PacketHeaderImpl();
        mockPacketHeader.setAction(ACTION.ADD_PRODUCT_MESSAGE);
        mockPacketHeader.setRequestType(REQUEST_TYPE.REQUEST);
        mockPacketHeader.setSessionId(getSessionId());

        String packetHeader = new GsonBuilder().create().toJson(mockPacketHeader);
        System.out.println(packetHeader);
        
        DTOMessageHeader dtoMessageHeader = new DTOMessageHeader();
        EntityMessageHeader entityMessageHeader = new EntityMessageHeader();
        entityMessageHeader.setSubject("Re: House rent at mohakhali");
        entityMessageHeader.setSenderUserId(3);
        entityMessageHeader.setReceiverUserId(1);
        entityMessageHeader.setProductId(1);
        dtoMessageHeader.setEntityMessageHeader(entityMessageHeader);
        EntityMessageBody entityMessageBody = new EntityMessageBody();
        entityMessageBody.setMessage("Please send details.");
        entityMessageBody.setUserId(3);
        dtoMessageHeader.setEntityMessageBody(entityMessageBody);        
        
        String packetBody = new GsonBuilder().create().toJson(dtoMessageHeader);
        System.out.println(packetBody);

        String result = getResult(packetHeader, packetBody);
        System.out.println("Result : " + result);
    }
    
    //@Test
    public void addMessageBodyTest() {
        PacketHeaderImpl mockPacketHeader = new PacketHeaderImpl();
        mockPacketHeader.setAction(ACTION.ADD_MESSAGE_BODY);
        mockPacketHeader.setRequestType(REQUEST_TYPE.REQUEST);
        mockPacketHeader.setSessionId(getSessionId());

        String packetHeader = new GsonBuilder().create().toJson(mockPacketHeader);
        System.out.println(packetHeader);
        
        EntityMessageBody entityMessageBody = new EntityMessageBody();
        entityMessageBody.setMessageHeaderId(2);
        entityMessageBody.setMessage("Also send your choices.");
        entityMessageBody.setUserId(3);
           
        
        String packetBody = new GsonBuilder().create().toJson(entityMessageBody);
        System.out.println(packetBody);

        String result = getResult(packetHeader, packetBody);
        System.out.println("Result : " + result);
    }
    
    //@Test
    public void getSentMessageListTest() {
        PacketHeaderImpl mockPacketHeader = new PacketHeaderImpl();
        mockPacketHeader.setAction(ACTION.FETCH_MESSAGE_SENT_LIST);
        mockPacketHeader.setRequestType(REQUEST_TYPE.REQUEST);
        mockPacketHeader.setSessionId(getSessionId());

        String packetHeader = new GsonBuilder().create().toJson(mockPacketHeader);
        System.out.println(packetHeader);
        
        DTOMessageHeader dtoMessageHeader = new DTOMessageHeader();
        dtoMessageHeader.setOffset(0);
        dtoMessageHeader.setLimit(10);
           
        
        String packetBody = new GsonBuilder().create().toJson(dtoMessageHeader);
        System.out.println(packetBody);

        String result = getResult(packetHeader, packetBody);
        System.out.println("Result : " + result);
    }
    
    //@Test
    public void getInboxMessageListTest() {
        PacketHeaderImpl mockPacketHeader = new PacketHeaderImpl();
        mockPacketHeader.setAction(ACTION.FETCH_MESSAGE_INBOX_LIST);
        mockPacketHeader.setRequestType(REQUEST_TYPE.REQUEST);
        mockPacketHeader.setSessionId(getSessionId());

        String packetHeader = new GsonBuilder().create().toJson(mockPacketHeader);
        System.out.println(packetHeader);
        
        DTOMessageHeader dtoMessageHeader = new DTOMessageHeader();
        dtoMessageHeader.setOffset(0);
        dtoMessageHeader.setLimit(10);
           
        
        String packetBody = new GsonBuilder().create().toJson(dtoMessageHeader);
        System.out.println(packetBody);

        String result = getResult(packetHeader, packetBody);
        System.out.println("Result : " + result);
    }
    
    //@Test
    public void getMessageBodyListTest() {
        PacketHeaderImpl mockPacketHeader = new PacketHeaderImpl();
        mockPacketHeader.setAction(ACTION.FETCH_MESSAGE_BODY_LIST);
        mockPacketHeader.setRequestType(REQUEST_TYPE.REQUEST);
        mockPacketHeader.setSessionId(getSessionId());

        String packetHeader = new GsonBuilder().create().toJson(mockPacketHeader);
        System.out.println(packetHeader);
        
        DTOMessageBody dtoMessageBody = new DTOMessageBody();
        dtoMessageBody.setOffset(0);
        dtoMessageBody.setLimit(10);
        EntityMessageBody entityMessageBody = new EntityMessageBody();
        entityMessageBody.setMessageHeaderId(2);
        dtoMessageBody.setEntityMessageBody(entityMessageBody);
           
        
        String packetBody = new GsonBuilder().create().toJson(dtoMessageBody);
        System.out.println(packetBody);

        String result = getResult(packetHeader, packetBody);
        System.out.println("Result : " + result);
    }
    
    
}
