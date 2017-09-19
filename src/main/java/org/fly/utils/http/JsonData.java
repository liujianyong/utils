package org.fly.utils.http;

import java.io.Serializable;

/**
 * @author Administrator
 *
 */
public class JsonData implements Serializable{
    private static final long serialVersionUID = 4165939582415475646L;
    private String cardType;
    
    private String cardNo;
    
    private String searchDate;

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(String searchDate) {
        this.searchDate = searchDate;
    }
    
    
}
