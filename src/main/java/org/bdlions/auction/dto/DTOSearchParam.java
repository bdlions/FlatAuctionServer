package org.bdlions.auction.dto;

/**
 *
 * @author Nazmul Hasan
 */
public class DTOSearchParam {
    private int offset;
    private int limit;

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
    
}
