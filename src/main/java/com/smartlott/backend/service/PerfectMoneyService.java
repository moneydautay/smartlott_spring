package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.source.PerfectMoneyDetails;
import com.smartlott.backend.persistence.domain.source.PerfectMoneyHistoryFilter;
import com.smartlott.backend.persistence.repositories.PerfectMoneyRepository;
import com.smartlott.exceptions.PerfectMoneyException;
import com.smartlott.utils.HTTPClient;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: dbudunov
 * Date: 06.08.13
 * Time: 20:44
 */
@Service("perfectMoneyService")
public class PerfectMoneyService implements PerfectMoneyRepository {

    protected static String SPEND_MONEY_URL = "https://perfectmoney.is/acct/confirm.asp";
    protected static String SPEND_MONEY_VERIFY_URL = "https://perfectmoney.is/acct/verify.asp";
    protected static String CODE_CONFIRMATION_URL = "https://perfectmoney.is/acct/protection.asp";
    protected static String VERIFY_ACCOUNT_URL = "https://perfectmoney.is/acct/acc_name.asp";
    protected static String HISTORY_URL = "https://perfectmoney.is/acct/historycsv.asp";
    protected static String BALANCE_URL = "https://perfectmoney.is/acct/balance.asp";
    protected static String RATES_URL = "http://perfectmoney.is/acct/rates.asp";

    @Override
    public String transferMoney(PerfectMoneyDetails perfectMoneyDetails) throws PerfectMoneyException {
        String url = SPEND_MONEY_URL + this.getTransferURL(perfectMoneyDetails);
        List<String> response = HTTPClient.sendRequest(url);
        return processPerfectMoneyResponse(response);
    }
    
    @Override
    public Map<String, String> transferMoneyToMap(PerfectMoneyDetails perfectMoneyDetails) throws PerfectMoneyException {
    	String url = SPEND_MONEY_URL + this.getTransferURL(perfectMoneyDetails);
    	List<String> response = HTTPClient.sendRequest(url);
    	return processPerfectMoneyToMap(processPerfectMoneyResponse(response));
    }

    @Override
    public String transferMoneyWithVerification(PerfectMoneyDetails perfectMoneyDetails) throws PerfectMoneyException {
        String url = SPEND_MONEY_VERIFY_URL + this.getTransferURL(perfectMoneyDetails);
        List<String> response = HTTPClient.sendRequest(url);
        return processPerfectMoneyResponse(response);
    }

    @Override
    public String transferMoneyWithProtectionCode(PerfectMoneyDetails perfectMoneyDetails) throws PerfectMoneyException {
        String url = CODE_CONFIRMATION_URL +
                "?AccountID=" + perfectMoneyDetails.accountId +
                "&PassPhrase=" + perfectMoneyDetails.passPhrase +
                "&batch=" + perfectMoneyDetails.batch +
                "&code=" + perfectMoneyDetails.code;
        List<String> response = HTTPClient.sendRequest(url);
        return processPerfectMoneyResponse(response);
    }

    @SuppressWarnings("deprecation")
	@Override
    public String verifyAccount(String accountId, String password, String account) throws PerfectMoneyException {
        String url = VERIFY_ACCOUNT_URL + "?" + "AccountID={0}&PassPhrase={1}&Account={2}";
        List<String> response = HTTPClient.sendRequest(url, accountId,  URLEncoder.encode(password), account);
      
        return processPerfectMoneyResponse(response);
    }

    @Override
    public String getTransferHistory(PerfectMoneyHistoryFilter perfectMoneyHistoryFilter) throws PerfectMoneyException {
        String url = HISTORY_URL +
                "?AccountID=" + perfectMoneyHistoryFilter.accountId +
                "&PassPhrase=" + perfectMoneyHistoryFilter.password +
                "&startmonth=" + perfectMoneyHistoryFilter.startMonth +
                "&startday=" + perfectMoneyHistoryFilter.startDay +
                "&startyear=" + perfectMoneyHistoryFilter.startYear +
                "&endmonth=" + perfectMoneyHistoryFilter.endMonth +
                "&endday=" + perfectMoneyHistoryFilter.endDay +
                "&endyear=" + perfectMoneyHistoryFilter.endYear +
                "&paymentsmade=" + perfectMoneyHistoryFilter.paymentsMade +
                "&paymentsreceived=" + perfectMoneyHistoryFilter.paymentsReceived +
                "&batchfilter=" + perfectMoneyHistoryFilter.batchFilter +
                "&counterfilter=" + perfectMoneyHistoryFilter.counterFilter +
                "&payment_id=" + perfectMoneyHistoryFilter.paymentId +
                "&desc=1";
        List<String> response = HTTPClient.sendRequest(url);
        return processPerfectMoneyResponse(response);
    }

	@SuppressWarnings("deprecation")
	@Override
    public String getAccountBalance(String accountId, String password) {
        String url = BALANCE_URL + "?" + "AccountID={0}&PassPhrase={1}";
        List<String> response = HTTPClient.sendRequest(url, accountId,   URLEncoder.encode(password));
        
        return processPerfectMoneyResponse(response);
    }

    @Override
    public String getExchangeRates(String currency) {
        return processPerfectMoneyResponse(HTTPClient.sendRequest(RATES_URL + "CUR={0}", currency));
    }

    @SuppressWarnings("deprecation")
	private String getTransferURL(PerfectMoneyDetails details) {
    	String url = "?AccountID=" + details.accountId +
                "&PassPhrase=" + details.passPhrase +
                "&Payer_Account=" + details.payerAccount +
                "&Payee_Account=" + details.payeeAccount +
                "&Amount=" + details.amount +
                "&Memo=" + URLEncoder.encode(details.memo);
                
    	if(details.getPaymentId() != null)
    		url+="&PAYMENT_ID=" + details.paymentId;
    	if(details.getCode() != null)
    		url+="&code=" + details.code;
    	if(details.getPeriod() != null)
    		url+="&Period=" + details.period;
        return url;
    }

    private String processPerfectMoneyResponse(List<String> response) {
        String result = "";
        for (String str : response) {
            if (str.contains("Error")) {
                throw new PerfectMoneyException(str);
            }
            result += str;
        }
        return result;
    }
   
   private Map<String, String> processPerfectMoneyToMap(String response){
	   Map<String, String> results = new HashMap<String,String>();
		Matcher matcher = Pattern.compile("<input name='(.+?)' type='hidden' value='(.+?)'>").matcher(response);
		while(matcher.find()){
			results.put(matcher.group(1).toString(), matcher.group(2).toString());
		}
		return results;
   }
}
