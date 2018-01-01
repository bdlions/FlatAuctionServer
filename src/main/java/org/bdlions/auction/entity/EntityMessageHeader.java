package org.bdlions.auction.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author nazmul hasan
 */
@Entity
@Table(
        name = "message_headers",
        indexes = {
            @Index(name = "idx_subject", columnList = "subject", unique = false),
            @Index(name = "idx_sender_user_id", columnList = "sender_user_id", unique = false)
        }
)
@NamedQueries(
    {
        @NamedQuery(
                name = "getMessageHeaderById", 
                query =  "from EntityMessageHeader header where header.id = :id"
        ),
        @NamedQuery(
                name = "getProductMessageHeaderByUserId", 
                query =  "from EntityMessageHeader header where header.senderUserId = :senderUserId and header.productId = :productId"
        ),
        @NamedQuery(
                name = "getSentMessageList", 
                query =  "from EntityMessageHeader header where header.senderUserId = :senderUserId"
        ),
        @NamedQuery(
                name = "getInboxMessageList", 
                query =  "from EntityMessageHeader header where header.senderUserId = :senderUserId or header.receiverUserId = :receiverUserId"
        )
    }
)
public class EntityMessageHeader {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")    
    private int id;

    @Column(name = "product_id")
    private int productId;
    
    @Column(name = "subject", length = 1000)
    private String subject;
    
    @Column(name = "sender_user_id")
    private int senderUserId;
    
    @Column(name = "receiver_user_id")
    private int receiverUserId;
    
    @Column(name = "created_on", length = 11, columnDefinition = "int(11) unsigned DEFAULT 0")
    private long createdOn;
    
    @Column(name = "modified_on", length = 11, columnDefinition = "int(11) unsigned DEFAULT 0")
    private long modifiedOn;
    
    public EntityMessageHeader() 
    {
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getSenderUserId() {
        return senderUserId;
    }

    public void setSenderUserId(int senderUserId) {
        this.senderUserId = senderUserId;
    }

    public int getReceiverUserId() {
        return receiverUserId;
    }

    public void setReceiverUserId(int receiverUserId) {
        this.receiverUserId = receiverUserId;
    }

    public long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(long createdOn) {
        this.createdOn = createdOn;
    }

    public long getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(long modifiedOn) {
        this.modifiedOn = modifiedOn;
    }
    
}
