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
        name = "locations",
        indexes = {
            @Index(name = "idx_search_string", columnList = "search_string", unique = true)
        }
)
@NamedQueries(
    {
        @NamedQuery(
                name = "getLocationById", 
                query =  "from EntityLocation location where location.id = :locationId"
        ),@NamedQuery(
                name = "getLocationBySearchString", 
                query =  "from EntityLocation location where location.searchString = :searchString"
        ),@NamedQuery(
                name = "getAllLocations", 
                query =  "from EntityLocation location"
        )
    }
)
public class EntityLocation {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")    
    private int id;

    @Column(name = "search_string", length = 500)
    private String searchString;
    
    @Column(name = "location_type", length = 200)
    private String locationType;
    
    @Column(name = "postcode", length = 200)
    private String postcode;
    
    @Column(name = "lat", columnDefinition = "double DEFAULT 0")
    private double lat;
    
    @Column(name = "lon", columnDefinition = "double DEFAULT 0")
    private double lon;
    
    @Column(name = "created_on", length = 11, columnDefinition = "int(11) unsigned DEFAULT 0")
    private long createdOn;

    @Column(name = "modified_on", length = 11, columnDefinition = "int(11) unsigned DEFAULT 0")
    private long modifiedOn;

    public EntityLocation() 
    {
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
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
