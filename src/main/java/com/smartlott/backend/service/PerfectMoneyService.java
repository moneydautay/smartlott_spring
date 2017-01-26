package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.source.PerfectMoneyDetails;
import com.smartlott.backend.persistence.domain.source.PerfectMoneyHistoryFilter;
import com.smartlott.backend.persistence.repositories.PerfectMoneyRepository;
import com.smartlott.exceptions.PerfectMoneyException;
import com.smartlott.utils.HTTPClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.time.Clock;
import java.time.LocalDate;
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

    @Value(value = "${perfectmoney.id}")
    private String accountId;

    @Value(value = "${perfectmoney.passPhrase}")
    private String passpharse;

    @Value(value = "${perfectmoney.account}")
    private String accountNumber;

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
        System.out.println(url);
        List<String> response = HTTPClient.sendRequest(url);
        return processPerfectMoneyResponse(response);
    }

    @SuppressWarnings("deprecation")
	@Override
    public String verifyAccount(String accountId, String password, String account) throws PerfectMoneyException {
        String url = VERIFY_ACCOUNT_URL + "?" + "AccountID={0}&PassPhrase={1}&Account={2}";
        List<String> response = HTTPClient.sendRequest(url, accountId, password, account);
      
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
                "&endyear=" + perfectMoneyHistoryFilter.endYear;
                if(perfectMoneyHistoryFilter.paymentsMade != 0)
                    url += "&paymentsmade=" + perfectMoneyHistoryFilter.paymentsMade;
                if(perfectMoneyHistoryFilter.paymentsMade != 0)
                    url += "&paymentsreceived=" + perfectMoneyHistoryFilter.paymentsMade;
                if(perfectMoneyHistoryFilter.batchFilter != 0)
                    url += "&batchfilter=" + perfectMoneyHistoryFilter.batchFilter;
                if(perfectMoneyHistoryFilter.counterFilter != null)
                    url += "&counterfilter=" + perfectMoneyHistoryFilter.counterFilter;
                if(perfectMoneyHistoryFilter.paymentId != null)
                    url += "&payment_id=" + perfectMoneyHistoryFilter.paymentId;
                url += "&desc=1";
        List<String> response = HTTPClient.sendRequest(url);
        return processPerfectMoneyResponse(response);
    }

    public boolean checkExistBatch(LocalDate starDate, String batch) throws  PerfectMoneyException{

        PerfectMoneyHistoryFilter filter = new PerfectMoneyHistoryFilter();
        filter.setAccountId(Long.valueOf(accountId));
        filter.setPassword(passpharse);
        filter.setBatchFilter(Integer.valueOf(batch));
        filter.setStartMonth(starDate.getMonthValue());
        filter.setStartDay(starDate.getDayOfMonth());
        filter.setStartYear(starDate.getYear());
        filter.setEndMonth(LocalDate.now(Clock.systemDefaultZone()).getMonthValue());
        filter.setEndDay(LocalDate.now(Clock.systemDefaultZone()).getDayOfMonth());
        filter.setEndYear(LocalDate.now(Clock.systemDefaultZone()).getYear());
        filter.setPaymentsReceived(1);
        filter.setCounterFilter(accountNumber);

        String result = getTransferHistory(filter);
        return result.contains(batch);
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
                "&Memo=" +details.memo;
                
    	if(details.getPaymentId() != null)
    		url+="&PAYMENT_ID=" + details.paymentId;
    	if(details.getCode() != null)
    		url+="&code=" + details.code;
    	if(details.getPeriod() != null)
    		url+="&period=" + details.period;
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
