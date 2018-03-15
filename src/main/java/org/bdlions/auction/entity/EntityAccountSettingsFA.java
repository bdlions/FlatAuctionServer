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
        name = "account_settings_fa",
        indexes = {
            @Index(name = "idx_user_id", columnList = "user_id", unique = true)
        }
)
@NamedQueries({
    @NamedQuery(
            name = "getAccountSettingsFAByUserId",
            query = "from EntityAccountSettingsFA account where account.userId = :userId"
    )
})
public class EntityAccountSettingsFA {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")    
    private int id;
    
    @Column(name = "user_id")    
    private int userId;
    
    @Column(name = "charge_per_click")
    private double chargePerClick;
    
    @Column(name = "daily_budget")
    private double dailyBudget;
    
    @Column(name = "campain_active")
    private boolean campainActive;
    
    @Column(name = "created_on", length = 11, columnDefinition = "int(11) unsigned DEFAULT 0")
    private long createdOn;

    @Column(name = "modified_on", length = 11, columnDefinition = "int(11) unsigned DEFAULT 0")
    private long modifiedOn;

    public EntityAccountSettingsFA() 
    {
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getChargePerClick() {
        return chargePerClick;
    }

    public void setChargePerClick(double chargePerClick) {
        this.chargePerClick = chargePerClick;
    }

    public double getDailyBudget() {
        return dailyBudget;
    }

    public void setDailyBudget(double dailyBudget) {
        this.dailyBudget = dailyBudget;
    }

    public boolean isCampainActive() {
        return campainActive;
    }

    public void setCampainActive(boolean campainActive) {
        this.campainActive = campainActive;
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
