package com.zsy.frame.sample.control.android.a30designmode.mvp.bean;

public class DoterBean {
    private String dotaId;
    private String dotaAge;

    public DoterBean(String dotaId, String dotaAge) {
        super();
        this.dotaId = dotaId;
        this.dotaAge = dotaAge;
    }

    public String getDotaId() {
        return dotaId;
    }

    public void setDotaId(String dotaId) {
        this.dotaId = dotaId;
    }

    public String getDotaAge() {
        return dotaAge;
    }

    public void setDotaAge(String dotaAge) {
        this.dotaAge = dotaAge;
    }
    
    @Override
    public String toString() {
        return "Response -----\r\n dotaId: " + dotaId + "\r\n dotaAge: " + dotaAge;
    }
    
    public String getFirstName() {
		return dotaId;
	}

	public String getLastName() {
		return dotaAge;
	}


}
