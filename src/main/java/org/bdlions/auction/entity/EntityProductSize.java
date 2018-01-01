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
        name = "product_sizes",
        indexes = {
            @Index(name = "idx_title", columnList = "title", unique = true)
        }
)
@NamedQueries(
    {
        @NamedQuery(
                name = "getAllSizes", 
                query =  "from EntityProductSize size"
        )
    }
)
public class EntityProductSize {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    
    private int id;

    @Column(name = "title", length = 200)
    private String title;

    public EntityProductSize() 
    {
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }    
}
