package com.semidev.techshop.model.entity;

import com.semidev.techshop.exception.ExceptionInvalidPurchaseId;
import com.semidev.techshop.exception.ExceptionInvalidPurchaseStatus;
import com.semidev.techshop.exception.ExceptionInvalidVisitorId;
import com.semidev.techshop.exception.ExceptionNullPurchasePlacedDate;

import java.time.LocalDateTime;


public class Purchase {

    private Integer id;
    
        public int getId() {
            return this.id;
        }
        
        public void setId(int id) throws ExceptionInvalidPurchaseId {
            if (id < 1)
                throw new ExceptionInvalidPurchaseId();
            else
                this.id = id;
        }
        
    private Integer visitorId;
    
        public int getVisitorId() {
            return this.visitorId;
        }
        
        public void setVisitorId(int visitorId) throws ExceptionInvalidVisitorId {
            if (visitorId < 1)
                throw new ExceptionInvalidVisitorId();
            else
                this.visitorId = visitorId;
        }
    
    private LocalDateTime placedDate;
    
        public LocalDateTime getPlacedDate() {
            return this.placedDate;
        }
        
        public void setPlacedDate(LocalDateTime placedDate) throws ExceptionNullPurchasePlacedDate {
            if (placedDate == null)
                throw new ExceptionNullPurchasePlacedDate();
            else
                this.placedDate = placedDate;
        }
    
    private Integer status;
    
        public int getStatus() {
            return this.status;
        }
        
        public void setStatus(int status) throws ExceptionInvalidPurchaseStatus {
            if (status < 0 || 2 < status)
                throw new ExceptionInvalidPurchaseStatus();
            else
                this.status = status;
        }
    
    public static Purchase createInstance(
        int           id,
        int           visitorId,
        LocalDateTime placedDate,
        int           status
    ) throws ExceptionInvalidPurchaseId
           , ExceptionNullPurchasePlacedDate
           , ExceptionInvalidPurchaseStatus
           , ExceptionInvalidVisitorId
    {
        var instance = new Purchase();
        instance.setId(id);
        instance.setVisitorId(visitorId);
        instance.setPlacedDate(placedDate);
        instance.setStatus(status);
        return instance;
    }
    
}
