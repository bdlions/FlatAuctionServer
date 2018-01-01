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
        name = "message_bodys",
        indexes = {
            @Index(name = "idx_message_header_id", columnList = "message_header_id", unique = false)
        }
)
@NamedQueries(
    {
        @NamedQuery(
                name = "getMessageBodyList", 
                query =  "from EntityMessageBody body where body.messageHeaderId = :messageHeaderId order by body.createdOn desc"
        )
    }
)
public class EntityMessageBody {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")    
    private int id;

    @Column(name = "message_header_id")
    private int messageHeaderId;
    
    @Column(name = "message", length = 1000)
    private String message;
    
    @Column(name = "user_id")
    private int userId;
    
    @Column(name = "created_on", length = 11, columnDefinition = "int(11) unsigned DEFAULT 0")
    private long createdOn;
    
    @Column(name = "modified_on", length = 11, columnDefinition = "int(11) unsigned DEFAULT 0")
    private long modifiedOn;
    
    public EntityMessageBody() 
    {
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMessageHeaderId() {
        return messageHeaderId;
    }

    public void setMessageHeaderId(int messageHeaderId) {
        this.messageHeaderId = messageHeaderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
