package com.smartlott.backend.persistence.domain.source;

import java.net.URLEncoder;


/**
 * User: dbudunov
 * Date: 06.08.13
 * Time: 20:21
 */
public class PerfectMoneyDetails {

    public String accountId;

    public String passPhrase;

    public String payerAccount;

    public String payeeAccount;

    public double amount;
	

    public String memo;
    public String paymentId;
    public String code;
    public String period;
    public String batch;

    
    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getPassPhrase() {
        return passPhrase;
    }

    @SuppressWarnings("deprecation")
	public void setPassPhrase(String passPhrase) {
        this.passPhrase = URLEncoder.encode(passPhrase);
    }

    public String getPayerAccount() {
        return payerAccount;
    }

    public void setPayerAccount(String payerAccount) {
        this.payerAccount = payerAccount;
    }

    public String getPayeeAccount() {
        return payeeAccount;
    }

    public void setPayeeAccount(String payeeAccount) {
        this.payeeAccount = payeeAccount;
    }

    
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

	@Override
	public String toString() {
		return "PerfectMoneyDetails [accountId=" + accountId + ", passPhrase=" + passPhrase + ", payerAccount="
				+ payerAccount + ", payeeAccount=" + payeeAccount + ", amount=" + amount + ", memo=" + memo
				+ ", paymentId=" + paymentId + ", code=" + code + ", period=" + period + ", batch=" + batch + "]";
	}
    
}
