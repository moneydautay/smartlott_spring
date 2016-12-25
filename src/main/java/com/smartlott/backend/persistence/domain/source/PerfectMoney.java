package com.smartlott.backend.persistence.domain.source;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;

import javax.persistence.Embeddable;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;


@Embeddable
@SuppressWarnings("deprecation")
public class PerfectMoney{
	
	@Transient
	private String PAYMENT_ID = null;
	
	@Transient
	private String PAYEE_ACCOUNT;
	
	@Transient
	private Double PAYMENT_AMOUNT = 0.0;
	
	@Transient
	private String PAYMENT_UNITS = "USD";
	
	@Transient
	private String PAYMENT_BATCH_NUM;
	
	@Transient
	private String PAYER_ACCOUNT;
	
	@Transient
	private String TIMESTAMPGMT;
	
	@Transient
	private long ORDER_NUM;
	
	@Transient
	@NotNull
	private int CUST_NUM;
	
	@Transient
	private String V2_HASH;

	@Transient
	private String passPhrase;
	
	public PerfectMoney() {}
	
	
	public String getPAYMENT_ID() {
		return PAYMENT_ID;
	}


	public void setPAYMENT_ID(String pAYMENT_ID) {
		PAYMENT_ID = pAYMENT_ID;
	}


	public String getPAYEE_ACCOUNT() {
		return PAYEE_ACCOUNT;
	}


	public void setPAYEE_ACCOUNT(String pAYEE_ACCOUNT) {
		PAYEE_ACCOUNT = pAYEE_ACCOUNT;
	}


	public Double getPAYMENT_AMOUNT() {
		return PAYMENT_AMOUNT;
	}


	public void setPAYMENT_AMOUNT(Double pAYMENT_AMOUNT) {
		PAYMENT_AMOUNT = pAYMENT_AMOUNT;
	}


	public String getPAYMENT_UNITS() {
		return PAYMENT_UNITS;
	}


	public void setPAYMENT_UNITS(String pAYMENT_UNITS) {
		PAYMENT_UNITS = pAYMENT_UNITS;
	}


	public String getPAYMENT_BATCH_NUM() {
		return PAYMENT_BATCH_NUM;
	}


	public void setPAYMENT_BATCH_NUM(String pAYMENT_BATCH_NUM) {
		PAYMENT_BATCH_NUM = pAYMENT_BATCH_NUM;
	}


	public String getPAYER_ACCOUNT() {
		return PAYER_ACCOUNT;
	}


	public void setPAYER_ACCOUNT(String pAYER_ACCOUNT) {
		PAYER_ACCOUNT = pAYER_ACCOUNT;
	}


	public String getTIMESTAMPGMT() {
		return TIMESTAMPGMT;
	}


	public void setTIMESTAMPGMT(String tIMESTAMPGMT) {
		TIMESTAMPGMT = tIMESTAMPGMT;
	}


	public long getORDER_NUM() {
		return ORDER_NUM;
	}


	public void setORDER_NUM(long oRDER_NUM) {
		ORDER_NUM = oRDER_NUM;
	}


	public int getCUST_NUM() {
		return CUST_NUM;
	}


	public void setCUST_NUM(int cUST_NUM) {
		CUST_NUM = cUST_NUM;
	}


	public String getV2_HASH() {
		return V2_HASH;
	}


	public void setV2_HASH(String v2_HASH) {
		V2_HASH = v2_HASH;
	}


	public String getPassPhrase() {
		return passPhrase;
	}

	public void setPassPhrase(String passPhrase) {
		this.passPhrase = passPhrase;
	}


	
	/*PAYMENT_ID = AB-123
	PAYEE_ACCOUNT = U123456
	PAYMENT_AMOUNT = 300.00
	PAYMENT_UNITS = USD
	PAYMENT_BATCH_NUM = 789012
	PAYER_ACCOUNT = U456789
	TIMESTAMPGMT = 876543210
	*/
	
	@Override
	public String toString() {
		return "PerfectMoney [PAYMENT_ID=" + PAYMENT_ID + ", PAYEE_ACCOUNT=" + PAYEE_ACCOUNT + ", PAYMENT_AMOUNT="
				+ PAYMENT_AMOUNT + ", PAYMENT_UNITS=" + PAYMENT_UNITS + ", PAYMENT_BATCH_NUM=" + PAYMENT_BATCH_NUM
				+ ", PAYER_ACCOUNT=" + PAYER_ACCOUNT + ", TIMESTAMPGMT=" + TIMESTAMPGMT + ", ORDER_NUM=" + ORDER_NUM
				+ ", CUST_NUM=" + CUST_NUM + ", V2_HASH=" + V2_HASH + ", passPhrase=" + passPhrase + "]";
	}


	public String dov2Hash(){
		PasswordEncoder encoder = new Md5PasswordEncoder();
		String pp = encoder.encodePassword(passPhrase,null);
		String v2Hash = PAYMENT_ID+":"+PAYEE_ACCOUNT+":"+PAYMENT_UNITS+":"+PAYMENT_BATCH_NUM+":"+PAYER_ACCOUNT+":"+pp+":"+TIMESTAMPGMT;
		return encoder.encodePassword(v2Hash,null);
	}
	
	public boolean isValid(){
		return V2_HASH == dov2Hash();
	}
}
