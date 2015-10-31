package com.lipisha.sdk;

import com.lipisha.sdk.response.AirtimeDisbursement;
import com.lipisha.sdk.response.Payout;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Test money/sms/airtime disbursement
 */
public class DisbursementTest extends TestCase {

    private LipishaClient lipishaClient;

    public DisbursementTest(String name) {
        super(name);
        this.lipishaClient = lipishaClient;
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(DisbursementTest.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        lipishaClient = new LipishaClient(TestConfig.API_KEY, TestConfig.API_SIGNATURE, LipishaClient.SANDBOX_BASE_URL);
    }

    public void testSendMoney() {
        Payout payout = lipishaClient.sendMoney("0722123456", 100, TestConfig.PAYOUT_ACCOUNT_NUMBER);
        assertNotNull(payout.getAmount());
        assertNotNull(payout.getCustomerName());
        assertNotNull(payout.getMobileNumber());
        assertNotNull(payout.getReference());
        assertEquals(payout.getStatusResponse().getStatus(), "SUCCESS");
    }

    public void testSendAirtime() {
        AirtimeDisbursement airtimeDisbursement = lipishaClient.sendAirtime("0722123456", 100,
                TestConfig.AIRTIME_ACCOUNT_NUMBER, "SAF");
        assertNotNull(airtimeDisbursement.getMobileNumber());
        assertNotNull(airtimeDisbursement.getReference());
        assertNotNull(airtimeDisbursement.getAmount());
        assertEquals(airtimeDisbursement.getStatusResponse().getStatus(), "SUCCESS");
    }

    public void testSendAirtimeInvalidAmount() {
        AirtimeDisbursement airtimeDisbursement = lipishaClient.sendAirtime("0722123456", 0,
                TestConfig.AIRTIME_ACCOUNT_NUMBER, "SAF");
        assertEquals(airtimeDisbursement.getStatusResponse().getStatus(), "FAIL");
    }
}