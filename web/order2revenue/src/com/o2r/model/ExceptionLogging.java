package com.o2r.model;
// Generated Oct 23, 2016 4:48:17 PM by Hibernate Tools 3.4.0.CR1


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ExceptionLogging generated by hbm2java
 */
@Entity
@Table(name="exception_logging"
    ,catalog="o2rschema"
)
public class ExceptionLogging  implements java.io.Serializable {


     private int id;
     private String desc;
     private String status;
     private Integer pcid;

    public ExceptionLogging() {
    }

	
    public ExceptionLogging(int id) {
        this.id = id;
    }
    public ExceptionLogging(int id, String desc, String status, Integer pcid) {
       this.id = id;
       this.desc = desc;
       this.status = status;
       this.pcid = pcid;
    }
   
     @Id 

    
    @Column(name="ID", unique=true, nullable=false)
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    
    @Column(name="DESC", length=1000)
    public String getDesc() {
        return this.desc;
    }
    
    public void setDesc(String desc) {
        this.desc = desc;
    }

    
    @Column(name="STATUS", length=45)
    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }

    
    @Column(name="PCID")
    public Integer getPcid() {
        return this.pcid;
    }
    
    public void setPcid(Integer pcid) {
        this.pcid = pcid;
    }




}

