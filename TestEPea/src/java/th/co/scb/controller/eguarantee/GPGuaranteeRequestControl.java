/**
 *
 */
package th.co.scb.controller.eguarantee;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import th.co.scb.model.EStatus;

import th.co.scb.service.EStatusService;

import th.co.scb.db.BankInfoTable;
import th.co.scb.db.ConfigTable;
import th.co.scb.db.eguarantee.AccountALSTable;
import th.co.scb.model.BankInfo;
import th.co.scb.model.ETime;
import th.co.scb.model.LogEBXML;
import th.co.scb.model.RegistrationId;
import th.co.scb.model.eguarantee.GPGuarantee;
import th.co.scb.service.DocRunningService;
import th.co.scb.service.EBXMLConfig;
import th.co.scb.service.ETimeService;
import th.co.scb.service.LogEBMXLService;
import th.co.scb.service.RegistrationIdService;
import th.co.scb.service.eguarantee.GPApprovalLogService;
import th.co.scb.service.eguarantee.GPGuaranteeService;
import th.co.scb.service.eguarantee.GPReviewLogService;
import th.co.scb.util.Constants;
import th.co.scb.util.DateUtil;
import th.co.scb.util.StringUtil;
import th.co.scb.util.TemplateUtil;
import th.co.scb.util.XMLUtil;
import java.util.Locale;

/**
 * @author s51486
 *
 */
public class GPGuaranteeRequestControl extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(GPGuaranteeRequestControl.class);

    //private GPGuarantee prepareGPGuaranteeForApproval(HttpServletRequest request){
    //}
    //Coding for UR58120031 add-extend-expire-date-interface (Phase 1)
    //changed from
    /*
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String resCode = "";
        String resMsg = "";
        String responseMsg = "";
        String requestor = (request.getParameter("requestor") != null ? request.getParameter("requestor") : "");		//R58060012 : edit by bussara.b @20150615

        LogEBXML logEBXML = null;
        GPGuarantee gpGuarantee = null;
        RegistrationId registrationId = null;

        ETimeService eTimeService = null;
        LogEBMXLService logEBXMLService = null;
        GPGuaranteeService gpGuaranteeService = null;
        RegistrationIdService registrationIdService = null;
        DocRunningService docRunningService = null;
        GPApprovalLogService gpApprovalLogService = null;
        GPReviewLogService gpReviewLogService = null;

        System.out.println("=== START GPGuaranteeRequestControl.processRequest ===== ");
        try {

            eTimeService = new ETimeService();
            logEBXMLService = new LogEBMXLService();
            gpGuaranteeService = new GPGuaranteeService();
            registrationIdService = new RegistrationIdService();
            docRunningService = new DocRunningService();
            gpApprovalLogService = new GPApprovalLogService();
            gpReviewLogService = new GPReviewLogService();

            ETime eTime = eTimeService.isTimeOffHostALS();
            registrationId = registrationIdService.findRegistrationId();

            System.out.print("[Parameter]");
            System.out.print("requestor : " + request.getParameter("requestor"));
            System.out.print("iGpGuaranteeId : " + request.getParameter("iGpGuaranteeId"));
            System.out.print("iApprovalStatus : " + request.getParameter("iApprovalStatus"));
            System.out.print("iApprovalReason : " + request.getParameter("iApprovalReason"));
            System.out.print("iApprovalBy : " + request.getParameter("iApprovalBy"));

            //R58060012 : edit by bussara.b @20150611
            if (requestor.toLowerCase().equals("approval")) {
                int id = Integer.parseInt(request.getParameter("iGpGuaranteeId"));
                System.out.print("----- [Request from Approval for iGpGuaranteeId(" + String.valueOf(id) + ") ] -----");
                gpGuarantee = gpGuaranteeService.getGPGuaranteeById(id);
                gpGuarantee.setAccountNo(request.getParameter("iAccount"));
                //set approval status
				gpGuarantee = setApprovalStatus(gpGuarantee
						, request.getParameter("iApprovalStatus")	//ApproveStatus
						, new Date()								//ApproveDtm
						, request.getParameter("iApprovalBy")		//ApproveBy
						, request.getParameter("iApprovalReason"));	//ApproveReason
                gpGuaranteeService.updateApproveStatus(gpGuarantee);
                //get logEBXML
                logEBXML = new LogEBXML();
                logEBXML = logEBXMLService.getXmlInputByTxtRef(gpGuarantee.getTxRef());
                System.out.print("LOGEBXML ID: " + logEBXML.getId());
                System.out.print("XML Input: " + logEBXML.getXmlInput());
                System.out.println("gpGuarantee : " + gpGuarantee);
            } else if (requestor.toLowerCase().equals("review")) {
                int id = Integer.parseInt(request.getParameter("iGpGuaranteeId"));
                System.out.print("----- [Request from Review for iGpGuaranteeId(" + String.valueOf(id) + ") ] -----");
                gpGuarantee = gpGuaranteeService.getGPGuaranteeById(id);
                gpGuarantee.setAccountNo(request.getParameter("iAccount"));
				gpGuarantee = setReviewStatus(gpGuarantee
						, request.getParameter("iReviewStatus")		//ReviewStatus
						, new Date()								//ReviewDtm
						, request.getParameter("iReviewBy")			//ReviewBy
						, request.getParameter("iReviewReason"));	//ReviewReason
				//update review status
                gpGuaranteeService.updateReviewStatus(gpGuarantee);
            } else {
                System.out.print("----- [Request from Customer] -----");
                //---------- get request & keep log  ---------
                logEBXML = logEBXMLService.readInputXMLFile(request, eTime.isTimeOffHostALS(), true, Constants.GP_SYSTEM_NAME);
                if (logEBXML == null) {
                    System.out.println("@process Request : Data not found?");
                    throw new Exception("Data not found");
                }

                //--response to ebxml
                //set xml response to ebxml
                logEBXML = responseAckToEbxml(request, response, logEBXML);
                System.out.println("continue.......");

                //get object from xml  
                gpGuarantee = gpGuaranteeService.ebxmlToObject(logEBXML.getXmlInput(), eTime);
                System.out.println("gpGuarantee : " + gpGuarantee);

                //Coding for UR58120031 add-extend-expire-date-interface (Phase 1)
                //changed from
                //if (!Constants.GPGuarantee.SETUP_ISSUE.equals(gpGuarantee.getIssueType())) {
                if (!Constants.GPGuarantee.SETUP_ISSUE.equals(gpGuarantee.getIssueType())
                		&& !Constants.GPGuarantee.EXTEND_EXPIRY_DATE_ISSUE.equals(gpGuarantee.getIssueType())) {
                    //set approval flag for cancel / claim (auto approved)
                    System.out.println("----- set approval flag for cancel / claim (auto approved) -----");
					gpGuarantee = setReviewStatus(gpGuarantee
									, Constants.GPGuarantee.REVIEW_APPROVED					//ReviewStatus
									, new Date()											//ReviewDtm
									, Constants.GPGuarantee.PROCESS_BY_SYSTEM				//ReviewBy
									, Constants.GPGuarantee.REVIEW_REASON_AUTO_APPROVED);	//ReviewReason
					gpGuarantee = setApprovalStatus(gpGuarantee
									, Constants.GPGuarantee.APPROVAL_APPROVED				//ApproveStatus
									, new Date()											//ApproveDtm
									, Constants.GPGuarantee.PROCESS_BY_SYSTEM				//ApproveBy
									, Constants.GPGuarantee.APPROVAL_REASON_AUTO_APPROVED);	//ApproveReason
                }
            }

            //-------- Process ------
            //R58060060 : add by Apichart.H @20151019
            if (requestor.toLowerCase().equals("approval") || requestor.toLowerCase().equals("")) {
            	//approval or request from customer
                gpGuarantee = gpGuaranteeService.processGPguaranteeWebService(logEBXML, eTime, request, gpGuarantee);
            }

            //R58060012 : edit by bussara.b @20150611
            if (requestor.toLowerCase().equals("approval")) {
            	//approval
                //reset status gpGuarantee for sending response
                if (Constants.GPGuarantee.APPROVAL_REJECTED.equals(gpGuarantee.getApproveStatus())) {
                    gpGuarantee.setStatusLG(Constants.StatusLGGP.REJECT);
                    gpGuarantee.setMsgCode(Constants.MessageCode.PLEASE_CONTACT_SCB);
                    EStatusService eStatusService = new EStatusService();
                    EStatus eStatus = null;
                    try {
                        eStatus = eStatusService.findEstatus(new EStatus(Constants.MessageCode.ISO_CODE, Constants.MessageCode.PLEASE_CONTACT_SCB));
                    } catch (Exception e) {
                        eStatus = new EStatus(gpGuarantee.getMsgCode(), "");
                        eStatus.setDescEn(Constants.StatusLGGP.PLEASE_CONTACT_SCB);
                    }
                    gpGuarantee.setXmlOutput(eStatus.getDescEn());
                }
            } else if (requestor.toLowerCase().equals("review")) {
            	//review
            	//insert review log
                System.out.print("----- [Request from Review - insert review log] -----");
                gpReviewLogService.insert(gpGuarantee);
            } else {
            	//request from customer
                //insert into gp_guarantee
                System.out.print("----- [Request from Customer - insert gp_guarantee] -----");
                gpGuaranteeService.insertGPGuarantee(gpGuarantee);
            }

            //R58060012 : edit by bussara.b @20150615
            //R58060012 : edit by siwat.n @20150625
            boolean isStraigthThrough = false;
            if (!Constants.GPGuarantee.SETUP_ISSUE.equals(gpGuarantee.getIssueType())) {
                System.out.println("Cancel / Claim request - straigth through sending response");
                isStraigthThrough = true;
            } else {
                System.out.print("Setup request for");
                System.out.print(" id[" + String.valueOf(gpGuarantee.getId()) + "]");
                System.out.print(" tx_ref[" + gpGuarantee.getTxRef() + "]");
                System.out.print(" seqNo[" + String.valueOf(gpGuarantee.getSeqNo()) + "]");
                System.out.print(" approve_status[" + gpGuarantee.getApproveStatus() + "]");
                System.out.println(" status_lg[" + gpGuarantee.getStatusLG() + "]");

                //R58060060 : add by Apichart.H @20151019  
                if (requestor.toLowerCase().equals("review")) {
                    isStraigthThrough = false;
                } else {
                    if (Constants.GPGuarantee.APPROVAL_PENDING.equals(gpGuarantee.getApproveStatus())) {
                        //pending approve - not straight through
                        System.out.println("pending approve - not straight through");
                        isStraigthThrough = false;
                    } else {
                        if (eTime.isTimeOffHostALS()) {
                            if (Constants.GPGuarantee.APPROVAL_REJECTED.equals(gpGuarantee.getApproveStatus())) {
                                System.out.println("offline reject - straight through");
                                isStraigthThrough = true;
                            } else {
                                System.out.println("offline approve - not straight through");
                                isStraigthThrough = false;
                            }
                        } else {
                            if (Constants.GPGuarantee.APPROVAL_REJECTED.equals(gpGuarantee.getApproveStatus())) {
                                //reject - straight through
                                System.out.println("online reject - straight through");
                                isStraigthThrough = true;
                            } else {
                                //approve
                                if (Constants.StatusLGGP.APPROVE.equals(gpGuarantee.getStatusLG())) {
                                    System.out.println("online approve and als approved - straight through");
                                    isStraigthThrough = true;
                                } else {

                                    System.out.println("online approve and als rejected - not straight through");
                                    isStraigthThrough = false;
                                }
                            }
                        }
                    }
                }
            }

            //R58060012 : edit by bussara.b @20150615
            if (isStraigthThrough) {
                responseMsg = gpGuaranteeService.objectToEbxmlReturnGP(gpGuarantee, request, registrationId);
                System.out.println("responseMsg : " + responseMsg);

                boolean isDuplicateResponse = false;
                if (eTime.isTimeOffHostALS()) {
                    System.out.print("----- [check DUP offline] -----");
                    if (Constants.MessageCode.DUP_TRANSACTION.equals(gpGuarantee.getMsgCode())) {
                        if (gpGuaranteeService.isDupProjectTax5Keys(gpGuarantee)) {
                            System.out.print("----- [DUP - 5 keys] -----");
                            isDuplicateResponse = true;
                        }
                    }
                } else {
                    System.out.print("----- [check DUP online] -----");
                    if (gpGuaranteeService.isDupGPGuaranteeCompletedTxn(gpGuarantee)) {
                        System.out.print("----- [DUP - complete txn] -----");
                        isDuplicateResponse = true;
                    }
                }

                if (isDuplicateResponse) {
                    System.out.print("----- [Request is dup then do not send response to WSG] -----");
                } else {
                    System.out.print("----- [Request is not dup then send response to WSG] -----");
                    responseMsg = gpGuaranteeService.objectToEbxmlReturnGP(gpGuarantee, request, registrationId);
                    System.out.println("responseMsg : " + responseMsg);

                    // keep log return result to EBXML
                    logEBXML.setXmlOutput(responseMsg);
                    keepLogOutputEBXML(logEBXML);
                    System.out.println("Call URL EBXML.......................");
                    // call URL EBXML
                    //callURLEBXML(responseMsg, gpGuarantee, registrationId, logEBXML);
                    callURLEBXMLWithRetry(responseMsg, gpGuarantee, registrationId, logEBXML); //update by Malinee T. UR58100048 @20160104

                }
            }

        } catch (Exception e) {
            // ---: handle exception
            System.out.print("----- [Exception occur !!!] -----");
            resCode = "99";
            resMsg = "Exception -->" + e.getMessage();
            System.out.println("Exception -->" + e.getMessage());

            //----------- response --------------
            try {
                try {

                    System.out.print("----- [Response exception to WSG] -----");

                    registrationIdService = new RegistrationIdService();
                    registrationId = registrationIdService.findRegistrationId();

                    if (gpGuarantee.getIssueType().equals(Constants.GPGuarantee.SETUP_ISSUE)) {
                        gpGuarantee.setStatusLG(Constants.StatusLGGP.REJECT);
                    } else if (gpGuarantee.getIssueType().equals(Constants.GPGuarantee.CANCEL_ISSUE)) {
                        gpGuarantee.setStatusLG(Constants.StatusLGGP.CANCLE);
                    } else if (gpGuarantee.getIssueType().equals(Constants.GPGuarantee.CLAIM_ISSUE)) {
                        gpGuarantee.setStatusLG(Constants.StatusLGGP.CLAIM);
                    }
                    gpGuarantee.setMsgCode(Constants.MessageCode.UNABLE_TO_PROCESS);
                    gpGuarantee.setXmlOutput(Constants.StatusLGGP.UNABLE_TO_PROCESS);
                    gpGuarantee.setTransactionStatus(Constants.EGuarantee.STATUS_UNSUCCESS);
                    gpGuarantee.setReviewStatus(Constants.GPGuarantee.REVIEW_APPROVED);
                    gpGuarantee.setReviewDtm(new Date());
                    gpGuarantee.setReviewBy(Constants.GPGuarantee.PROCESS_BY_SYSTEM);
                    gpGuarantee.setReviewReason(Constants.GPGuarantee.REVIEW_REASON_AUTO_APPROVED);
                    gpGuarantee.setApproveStatus(Constants.GPGuarantee.APPROVAL_REJECTED);
                    gpGuarantee.setApproveDtm(new Date());
                    gpGuarantee.setApproveBy(Constants.GPGuarantee.PROCESS_BY_SYSTEM);
                    gpGuarantee.setApproveReason(Constants.GPGuarantee.APPROVAL_REASON_AUTO_REJECTED);

                    BankInfoTable bankInfoTable = new BankInfoTable();
                    BankInfo bankInfo = bankInfoTable.findBankInfoNoConn(new BankInfo(1));

                    gpGuarantee.setAppvAmt(gpGuarantee.getGuaranteeAmt());
                    gpGuarantee.setAppvDate(DateUtil.getDateFormatYYYYMMDD(gpGuarantee.getProcessDate()));

                    if (gpGuarantee.getIssueType().equals(Constants.GPGuarantee.SETUP_ISSUE)) {
                        gpGuarantee.setBankCode(bankInfo.getBankCode());
                        gpGuarantee.setBranchCode(bankInfo.getBranchCode());
                        gpGuarantee.setBranchName(bankInfo.getBranchName());
                        gpGuarantee.setBankAddr(bankInfo.getBankAddr());

                        //set blank
                        gpGuarantee.setAuthSigName(Constants.BLANK);
                        gpGuarantee.setPosition(Constants.BLANK);
                        gpGuarantee.setWitnessName1(Constants.BLANK);
                        gpGuarantee.setWitnessName2(Constants.BLANK);
                    }

                    if (requestor.toLowerCase().equals("approval")) {
                        System.out.print("----- [Request from Approval - update gp_guarantee and insert approval log] -----");
                        gpGuaranteeService.updateApprovalProcess(gpGuarantee);
                        gpApprovalLogService.insert(gpGuarantee);
                    } else if (requestor.toLowerCase().equals("review")) {
                        //R58060060 : add by Apichart.H @20151019  
                        System.out.print("----- [Request from Review - insert review log] -----");
                        gpReviewLogService.insert(gpGuarantee);
                    } else {
                        System.out.print("----- [Request from Customer - insert gp_guarantee] -----");
                        gpGuaranteeService.insertGPGuarantee(gpGuarantee);
                    }
                    if (requestor.toLowerCase().equals("review")) {
                        responseMsg = gpGuaranteeService.objectToEbxmlReturnGP(gpGuarantee, request, registrationId);
                        System.out.println("responseMsg : " + responseMsg);

                        //keep log return result to EBXML
                        logEBXML.setXmlOutput(responseMsg);
                        keepLogOutputEBXML(logEBXML);
                        System.out.println("Call URL EBXML.......................");
                        //call URL EBXML
                        //callURLEBXML(responseMsg, gpGuarantee, registrationId, logEBXML);
                        callURLEBXMLWithRetry(responseMsg, gpGuarantee, registrationId, logEBXML); //update by Malinee T. UR58100048 @20160104
                    }
                } catch (Exception ee) {
                    System.out.println("Exception while response exception  -->" + ee.getMessage());
                }

                responseToEbxml(response, genXMLACKResponse(request, logEBXML, resCode, resMsg));

            } catch (Exception ex) {
                // --: handle exception
                //ex.printStackTrace();
                System.out.println("Exception -->" + ex.getMessage());
            }

        }

    }
	*/
    //changed to
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String resCode = "";
        String resMsg = "";
        String responseMsg = "";
        String requestor = (request.getParameter("requestor") != null ? request.getParameter("requestor") : "");
        LogEBXML logEBXML = null;
        GPGuarantee gpGuarantee = null;
        ETimeService eTimeService = null;
        GPGuaranteeService gpGuaranteeService = null;
        GPApprovalLogService gpApprovalLogService = null;
        GPReviewLogService gpReviewLogService = null;
        gpGuarantee = new GPGuarantee();
        gpGuarantee.setId(0);
        boolean isResponseSent = false;

        System.out.println("=== START GPGuaranteeRequestControl.processRequest ===== ");
        try {
            eTimeService = new ETimeService();
            gpGuaranteeService = new GPGuaranteeService();
            gpApprovalLogService = new GPApprovalLogService();
            gpReviewLogService = new GPReviewLogService();
            ETime eTime = eTimeService.isTimeOffHostALS();
            logEBXML = initialLogEBXML(requestor, request, eTime);
            
            //-------- Send acknowledge response to WSG -> eGP (in case of request from customer) ------
            if (requestor.toLowerCase().equals("")) {
            	System.out.print("----- [Request from customer, Send acknowledge response to WSG -> eGP ] -----");
            	logEBXML = responseAckToEbxml(request, response, logEBXML);
            }
            
            //-------- Process ------
            System.out.print("----- [Begin Processing] -----");
            gpGuarantee = gpGuaranteeService.processGPguaranteeWebService(logEBXML, eTime, request);
	        if(Constants.GPGuarantee.EXTEND_EXPIRY_DATE_ISSUE.equals(gpGuarantee.getIssueType()) 
	        		&& gpGuarantee.getAccountNo() == null) {
	        	throw new Exception("LG No. not existed.");
	        }
            System.out.print("----- [End Processing] -----");
            //System.out.print("gpGuarantee: " + gpGuarantee);
            
            //-------- Determine response process ------
            boolean isStraigthThrough = isStraigthThroughProcess(gpGuarantee, requestor, eTime);
            if (isStraigthThrough) {
            	//UR59040034 Add eGP Pending Review & Resend Response function
            	//boolean isDuplicateResponse = isDuplicateResponse(gpGuarantee, eTime);
                //if (isDuplicateResponse) {
                    //System.out.print("----- [Request is dup then do not send response to WSG] -----");
                //} else {
                    //System.out.print("----- [Request is not dup then send response to WSG] -----");
                    isResponseSent =true;
                    responseMsg = gpGuaranteeService.objectToEbxmlReturnGP(gpGuarantee, request);
                    System.out.println("responseMsg : " + responseMsg);
                    //keep log return result to EBXML
                    logEBXML.setXmlOutput(responseMsg);
                    keepLogOutputEBXML(logEBXML);
                    System.out.print("----- [Call URL EBXML] -----");
                    callURLEBXMLWithRetry(responseMsg, gpGuarantee, logEBXML);
                //}
            }else{
            	System.out.print("----- [Request is not straigth through then do not send response to WSG  ] -----");
            }
        } catch (Exception e) {
            // ---: handle exception
            System.out.print("----- [Exception occur !!!] -----");
            resCode = "99";
            resMsg = "Exception -->" + e.getMessage();
            System.out.println("Exception -->" + e.getMessage());
            //----------- response --------------
            try {
                try {
                    System.out.print("----- [Response exception to WSG] -----");
                    gpGuarantee = setStatusError(gpGuarantee);
                    
                    if (requestor.toLowerCase().equals("approval")) {
                        System.out.print("----- [Request from Approval - update gp_guarantee and insert approval log] -----");
                        gpGuaranteeService.updateApprovalProcess(gpGuarantee);
                        gpApprovalLogService.insert(gpGuarantee);
                    } else if (requestor.toLowerCase().equals("review")) {
                        System.out.print("----- [Request from Review - insert review log] -----");
                        gpReviewLogService.insert(gpGuarantee);
                    } else {
                        System.out.print("----- [Request from Customer - insert gp_guarantee] -----");
                        if(gpGuarantee.getId() == 0){
                        	gpGuaranteeService.insertGPGuarantee(gpGuarantee);
                        }
                    }
                    if (requestor.toLowerCase().equals("approval") || requestor.toLowerCase().equals("")) {
                    	if(!isResponseSent){
		                    responseMsg = gpGuaranteeService.objectToEbxmlReturnGP(gpGuarantee, request);
		                    System.out.println("responseMsg : " + responseMsg);
		
		                    //keep log return result to EBXML
		                    logEBXML.setXmlOutput(responseMsg);
		                    keepLogOutputEBXML(logEBXML);
		                    System.out.println("Call URL EBXML.......................");
		                    callURLEBXMLWithRetry(responseMsg, gpGuarantee, logEBXML); //update by Malinee T. UR58100048 @20160104
                    	}
                    }
                } catch (Exception ee) {
                    System.out.println("Exception while response exception  -->" + ee.getMessage());
                }
                responseToEbxml(response, genXMLACKResponse(request, logEBXML, resCode, resMsg));
            } catch (Exception ex) {
                System.out.println("Exception -->" + ex.getMessage());
            }

        }

    }
	private GPGuarantee setStatusError(GPGuarantee gpGuarantee) throws Exception {
		//RegistrationIdService registrationIdService = new RegistrationIdService();
		//RegistrationId registrationId = registrationIdService.findRegistrationId();
        String statusLG = "";
        if (gpGuarantee.getIssueType().equals(Constants.GPGuarantee.SETUP_ISSUE)) {
        	statusLG = Constants.StatusLGGP.REJECT;
        } else if (gpGuarantee.getIssueType().equals(Constants.GPGuarantee.CANCEL_ISSUE)) {
        	statusLG = Constants.StatusLGGP.CANCLE;
        } else if (gpGuarantee.getIssueType().equals(Constants.GPGuarantee.CLAIM_ISSUE)) {
        	statusLG = Constants.StatusLGGP.CLAIM;
        }
        gpGuarantee = setTransactionStatus(gpGuarantee
        		, Constants.EGuarantee.STATUS_UNSUCCESS
        		, statusLG
        		, Constants.MessageCode.UNABLE_TO_PROCESS
        		, Constants.StatusLGGP.UNABLE_TO_PROCESS);
        gpGuarantee = setReviewStatus(gpGuarantee
        		, Constants.GPGuarantee.REVIEW_REJECTED
        		, new Date()
        		, Constants.GPGuarantee.PROCESS_BY_SYSTEM
        		, Constants.GPGuarantee.REVIEW_REASON_AUTO_APPROVED);
        gpGuarantee = setApprovalStatus(gpGuarantee
        		, Constants.GPGuarantee.APPROVAL_REJECTED
        		, new Date()
        		, Constants.GPGuarantee.PROCESS_BY_SYSTEM
        		, Constants.GPGuarantee.APPROVAL_REASON_AUTO_REJECTED);
        
        BankInfoTable bankInfoTable = new BankInfoTable();
        BankInfo bankInfo = bankInfoTable.findBankInfoNoConn(new BankInfo(1));

        gpGuarantee.setAppvAmt(gpGuarantee.getGuaranteeAmt());
        gpGuarantee.setAppvDate(DateUtil.getDateFormatYYYYMMDD(gpGuarantee.getProcessDate()));

        if (gpGuarantee.getIssueType().equals(Constants.GPGuarantee.SETUP_ISSUE)) {
            gpGuarantee.setBankCode(bankInfo.getBankCode());
            gpGuarantee.setBranchCode(bankInfo.getBranchCode());
            gpGuarantee.setBranchName(bankInfo.getBranchName());
            gpGuarantee.setBankAddr(bankInfo.getBankAddr());

            //set blank
            gpGuarantee.setAuthSigName(Constants.BLANK);
            gpGuarantee.setPosition(Constants.BLANK);
            gpGuarantee.setWitnessName1(Constants.BLANK);
            gpGuarantee.setWitnessName2(Constants.BLANK);
        }
        return gpGuarantee;
	}
//	private LogEBXML initialLogEBXML(String requestor, HttpServletRequest request, ETime eTime) throws Exception {
//		LogEBXML logEBXML = new LogEBXML(); 
//		GPGuaranteeService gpGuaranteeService = new GPGuaranteeService();
//		LogEBMXLService logEBXMLService = new LogEBMXLService();
//        if (requestor.toLowerCase().equals("approval")) {
//            int id = Integer.parseInt(request.getParameter("iGpGuaranteeId"));
//            GPGuarantee gpGuarantee = gpGuaranteeService.getGPGuaranteeById(id);
//            logEBXML = logEBXMLService.getXmlInputByTxtRef(gpGuarantee.getTxRef());
//            if (logEBXML == null) {
//                System.out.println("@process Request : Data not found?");
//                throw new Exception("Data not found");
//            }
//        } else if (requestor.toLowerCase().equals("")) {
//        	logEBXML = logEBXMLService.readInputXMLFile(request, eTime.isTimeOffHostALS(), true, Constants.GP_SYSTEM_NAME);
//        	if (logEBXML == null) {
//                System.out.println("@process Request : Data not found?");
//                throw new Exception("Data not found");
//            }
//        }
//        return logEBXML;
//	}
	//UR59040034 Add eGP Pending Review & Resend Response function
	//get logEBXML with the correct sequence
	//tx_ref can be duplicated, so we must sent the sequence of the tx_ref together to get the correct log_ebxml record
	private LogEBXML initialLogEBXML(String requestor, HttpServletRequest request, ETime eTime) throws Exception {
		LogEBXML logEBXML = new LogEBXML(); 
		GPGuaranteeService gpGuaranteeService = new GPGuaranteeService();
		LogEBMXLService logEBXMLService = new LogEBMXLService();
		//UR59040034 Add eGP Pending Review & Resend Response function
        //if (requestor.toLowerCase().equals("approval")) {
		if (requestor.toLowerCase().equals("approval") || requestor.toLowerCase().equals("resend")) { 	
            int id = Integer.parseInt(request.getParameter("iGpGuaranteeId"));
            GPGuarantee gpGuarantee = gpGuaranteeService.getGPGuaranteeWithSeqById(id);
            logEBXML = logEBXMLService.getXmlInputByTxtRefAndSeq(gpGuarantee.getTxRef(), gpGuarantee.getSeq());
            if (logEBXML == null) {
                System.out.println("@process Request : Data not found?");
                throw new Exception("Data not found");
            }
        } else if (requestor.toLowerCase().equals("")) {
        	logEBXML = logEBXMLService.readInputXMLFile(request, eTime.isTimeOffHostALS(), true, Constants.GP_SYSTEM_NAME);
        	if (logEBXML == null) {
                System.out.println("@process Request : Data not found?");
                throw new Exception("Data not found");
            }
        }
        return logEBXML;
	}
	
	private boolean isDuplicateResponse(GPGuarantee gpGuarantee, ETime eTime) throws Exception {
		boolean isDuplicateResponse = false;
		GPGuaranteeService gpGuaranteeService = new GPGuaranteeService();
        if (eTime.isTimeOffHostALS()) {
            System.out.print("----- [check DUP offline] -----");
            if (Constants.MessageCode.DUP_TRANSACTION.equals(gpGuarantee.getMsgCode())) {
                if (gpGuaranteeService.isDupProjectTax5Keys(gpGuarantee)) {
                    System.out.print("----- [DUP - 5 keys] -----");
                    isDuplicateResponse = true;
                }
            }
        } else {
            System.out.print("----- [check DUP online] -----");
            if (gpGuaranteeService.isDupGPGuaranteeCompletedTxn(gpGuarantee)) {
                System.out.print("----- [DUP - complete txn] -----");
                isDuplicateResponse = true;
            }
        }
        return isDuplicateResponse;
	}
	private boolean isStraigthThroughProcess(GPGuarantee gpGuarantee, String requestor, ETime eTime){
        boolean isStraigthThrough = false;
        if (!Constants.GPGuarantee.SETUP_ISSUE.equals(gpGuarantee.getIssueType())
        		&& !Constants.GPGuarantee.EXTEND_EXPIRY_DATE_ISSUE.equals(gpGuarantee.getIssueType())) {
            System.out.println("Cancel / Claim request - straigth through sending response");
            isStraigthThrough = true;
        } 
        //UR59040034 Add eGP Pending Review & Resend Response function
        else if (requestor.toLowerCase().equals("resend")) {
        	isStraigthThrough = true;
        } else {
            System.out.print(gpGuarantee.getIssueType() + " request for");
            System.out.print(" id[" + String.valueOf(gpGuarantee.getId()) + "]");
            System.out.print(" tx_ref[" + gpGuarantee.getTxRef() + "]");
            System.out.print(" seqNo[" + String.valueOf(gpGuarantee.getSeqNo()) + "]");
            System.out.print(" approve_status[" + gpGuarantee.getApproveStatus() + "]");
            System.out.println(" status_lg[" + gpGuarantee.getStatusLG() + "]");
            if (requestor.toLowerCase().equals("review")) {
                isStraigthThrough = false;
            } else {
                if (Constants.GPGuarantee.APPROVAL_PENDING.equals(gpGuarantee.getApproveStatus())) {
                    //pending approve - not straight through
                    System.out.println("pending approve - not straight through");
                    isStraigthThrough = false;
                } else {
                    if (eTime.isTimeOffHostALS()) {
                        if (Constants.GPGuarantee.APPROVAL_REJECTED.equals(gpGuarantee.getApproveStatus())) {
                            System.out.println("offline reject - straight through");
                            isStraigthThrough = true;
                        } else {
                            System.out.println("offline approve - not straight through");
                            isStraigthThrough = false;
                        }
                    } else {
                        if (Constants.GPGuarantee.APPROVAL_REJECTED.equals(gpGuarantee.getApproveStatus())) {
                            //reject - straight through
                            System.out.println("online reject - straight through");
                            isStraigthThrough = true;
                        } else {
                            //approve
                            if (Constants.StatusLGGP.APPROVE.equals(gpGuarantee.getStatusLG())
                            		|| Constants.StatusLGGP.EXTEND_EXPIRY_DATE.equals(gpGuarantee.getStatusLG())) {
                            	if(Constants.StatusLGGP.EXTEND_EXPIRY_DATE.equals(gpGuarantee.getStatusLG())){
                            		if(gpGuarantee.getTransactionStatus().equals(Constants.EGuarantee.STATUS_SUCCESS)){
                            			System.out.println("EXTEND online approve and als approved - straight through");
                            			isStraigthThrough = true;
                            		}else{
                            			System.out.println("EXTEND online approve and als rejected (or error) - not straight through");
                            			isStraigthThrough = false;
                            		}
                            	}else{
                            		System.out.println("SETUP online approve and als approved - straight through");
                            		isStraigthThrough = true;
                            	}
                            } else {
                                System.out.println("online approve and als rejected - not straight through");
                                isStraigthThrough = false;
                            }
                        }
                    }
                }
            }
        }
        return isStraigthThrough;
	}

    private LogEBXML responseAckToEbxml(HttpServletRequest request, HttpServletResponse response, LogEBXML logEBXML) throws Exception {
        //update log_ebxml
        logEBXML.setXmlResponseToEbxml(genXMLACKResponse(request, logEBXML));
        keepLogXmlResponseToEBXml(logEBXML);
        responseToEbxml(response, logEBXML.getXmlResponseToEbxml());
        return logEBXML;
    }

    private void keepLogXmlResponseToEBXml(LogEBXML logEBXML) throws Exception {
        try {
            LogEBMXLService logEBXMLService = new LogEBMXLService();
            logEBXMLService.updateXmlResponseToEBXml(logEBXML);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private void responseToEbxml(HttpServletResponse response, String xmlResponse) throws Exception {
        response.setContentType("text/xml");
        OutputStreamWriter out = new OutputStreamWriter(response.getOutputStream(), "UTF-8");
        out.write(xmlResponse);
        logger.debug("xmlResponse : -->" + xmlResponse);
        out.close();
    }

    private String genXMLACKResponse(HttpServletRequest request, LogEBXML logEBXML) {
        return genXMLACKResponse(request, logEBXML, "0", "Successful");
    }

    private String genXMLACKResponse(HttpServletRequest request, LogEBXML logEBXML, String resCode, String resMsg) {
        String xmlRes = "";
        String tempFile = Constants.TEMPLETE_XML_ACK_TO_EBXML;
        TemplateUtil template = new TemplateUtil(tempFile, request.getSession().getServletContext());
        if (logEBXML == null) {
            logEBXML = new LogEBXML();
        }
        template.setContext("rootTag", StringUtil.nullToBlank(getRootTag(logEBXML)));
        template.setContext("tranxId", StringUtil.nullToBlank(logEBXML.getCustomsRefNo()));
        template.setContext("bankRef", StringUtil.nullToBlank(logEBXML.getTransDate()));
        template.setContext("respCode", StringUtil.nullToBlank(resCode));
        template.setContext("respMsg", StringUtil.nullToBlank(resMsg));
        template.setContext("balance", Constants.BLANK);
        template.setContext("msg", Constants.BLANK);
        xmlRes = template.getXML();
        return xmlRes;
    }

    private String getRootTag(LogEBXML logEBXML) {
        String rootTag = "";
        if (Constants.GPGuarantee.SETUP_ISSUE.equals(logEBXML.getIssueType())) {
            rootTag = Constants.GPGuarantee.SETUP_ISSUE_TAG_ROOT_ACK;
        } else if (Constants.GPGuarantee.CANCEL_ISSUE.equals(logEBXML.getIssueType())) {
            rootTag = Constants.GPGuarantee.CANCEL_ISSUE_TAG_ROOT_ACK;
        } else if (Constants.GPGuarantee.CLAIM_ISSUE.equals(logEBXML.getIssueType())) {
            rootTag = Constants.GPGuarantee.CLAIM_ISSUE_TAG_ROOT_ACK;
        //Coding for UR58120031 add-extend-expire-date-interface (Phase 1)
        //changed from
        //} else {
        //	rootTag = Constants.GPGuarantee.GP_GUARANTEE_TAG_ROOT_ACK;
        //}
        //changed to
        } else if (Constants.GPGuarantee.EXTEND_EXPIRY_DATE_ISSUE.equals(logEBXML.getIssueType())) {
        	rootTag = Constants.GPGuarantee.EXTEND_EXPIRY_DATE_ISSUE_TAG_ROOT_ACK;
        } else {
            rootTag = Constants.GPGuarantee.GP_GUARANTEE_TAG_ROOT_ACK;
        }
        return rootTag;
    }

    private void keepLogOutputEBXML(LogEBXML logEBXML) throws Exception {
        try {
            LogEBMXLService logEBXMLService = new LogEBMXLService();
            logEBXMLService.updateLogEBXMLXmlOutput(logEBXML);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private void keepLogResponseFromEBXML(LogEBXML logEBXML) throws Exception {
        try {
            LogEBMXLService logEBXMLService = new LogEBMXLService();
            logEBXMLService.updateXmlResponseFromEBXml(logEBXML);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private boolean callURLEBXML(String responseMsg, GPGuarantee gpGuarantee, LogEBXML logEBXML) throws Exception {
    	boolean result = false;
    	EBXMLConfig ebxmlConfig = new EBXMLConfig();

        String toservlet = ebxmlConfig.getUrlGP();

        String xmlFileReturnName = genXMLFileReturnName(gpGuarantee);
        System.out.println("file Name : " + xmlFileReturnName);

        //-- String to file---
        File f = new File(xmlFileReturnName);
        Writer exportFile = null;

        HttpClient httpclient = new DefaultHttpClient();
        try {

            HttpPost httppost = new HttpPost(toservlet);

            exportFile = new OutputStreamWriter(new FileOutputStream(f), "UTF-8");
            responseMsg = responseMsg.replaceAll("\\r\\n|\\r|\\n", "");
            exportFile.write(responseMsg.trim());
            exportFile.flush();

            FileBody fileBody = new FileBody(f);

            MultipartEntity reqEntity = new MultipartEntity();
            reqEntity.addPart("file", fileBody);
            reqEntity.addPart("filename", new StringBody(xmlFileReturnName, Charset.forName("UTF-8")));
            httppost.setEntity(reqEntity);

            System.out.println("executing request " + httppost.getRequestLine());
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity resEntity = response.getEntity();

            System.out.println("----------------------------------------");
            System.out.println(response.getStatusLine());
            if (resEntity != null) {

                String responseContent = EntityUtils.toString(response.getEntity());
                System.out.println("Response content : " + responseContent);

                //update log
                logEBXML.setXmlResponseFromEbxml(responseContent);
                keepLogResponseFromEBXML(logEBXML);
                
                //add by Malinee T. UR58100048 @20160104
                //determine acknowledge status
                String TAG_RESPCODE = "respCode";
                String TAG_RESPMSG = "respMsg";
                String TAG_TRANXID = "tranxId";
                Date egpAckDtm = Calendar.getInstance(Locale.US).getTime();
                String egpAckCode = XMLUtil.getTagValue(TAG_RESPCODE, responseContent);
                gpGuarantee.setEgpAckDtm(egpAckDtm);
                gpGuarantee.setEgpAckCode(egpAckCode);
                gpGuarantee.setEgpAckMsg(XMLUtil.getTagValue(TAG_RESPMSG, responseContent));
                gpGuarantee.setEgpAckTranxId(XMLUtil.getTagValue(TAG_TRANXID, responseContent));
                //gpGuarantee.set
                if("0".equals(egpAckCode)){
                    gpGuarantee.setEgpAckStatus("SC");
                    result = true;
                }else{
                    gpGuarantee.setEgpAckStatus("NS");
                    result = false;
                }
                updateGpGuaranteeEgpAcknowledge(gpGuarantee);
            }
            EntityUtils.consume(resEntity);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            throw new Exception("Error : Call Back EBXML : " + e.getMessage());
        } finally {
            if (exportFile != null) {
                try {
                    exportFile.close();
                } catch (Exception e) {
                	//nothing
                }
            }
            try {
                httpclient.getConnectionManager().shutdown();
            } catch (Exception ignore) {
            	//nothing
            }
        }
        return result;
    }

    private String genXMLFileReturnName(GPGuarantee gpGuarantee) {
        StringBuilder str = new StringBuilder();
        str.append(gpGuarantee.getProjNo());
        str.append("_");
        str.append(gpGuarantee.getTxRef());
        str.append(".xml");
        System.out.println("genXMLFileReturnName : " + str.toString());
        return str.toString();
    }
    
    //add by Malinee T. UR58100048 @20160104
    private void updateGpGuaranteeEgpAcknowledge(GPGuarantee gpGuarantee) throws Exception {
    	try {
            GPGuaranteeService service = new GPGuaranteeService();
            service.updateGpGuaranteeEgpAcknowledge(gpGuarantee);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    	
    }
    //add by Malinee T. UR58100048 @20160104
    public void delay(int sec){
    	 try{
    	        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    	        System.out.println("=========== delay for " + String.valueOf(sec) + " sec ============");
    	        Calendar dc = Calendar.getInstance(Locale.US);
    	        System.out.println("----- BEGIN DELAY: " + sdf.format(dc.getTime()) + " -----");
    	        java.util.concurrent.TimeUnit.SECONDS.sleep(sec);
    	        dc = Calendar.getInstance(Locale.US);
    	        System.out.println("----- END DELAY: " + sdf.format(dc.getTime()) + " -----");
    	    }catch(Exception e){
    	        e.printStackTrace();
    	 }
    }
    
    //add by Malinee T. UR58100048 @20160104
    private void callURLEBXMLWithRetry(String responseMsg, GPGuarantee gpGuarantee, LogEBXML logEBXML) throws Exception {
    	boolean egpResult = false;
        ConfigTable configTable = new ConfigTable();
        int delayTime = configTable.getConfigIntNoConn("DELAY_TIME");
        int retryTime = configTable.getConfigIntNoConn("RETRY_TIME");
        System.out.println("callURLEBXML : 0");
        egpResult = callURLEBXML(responseMsg, gpGuarantee, logEBXML);
        if(!egpResult){
            if(retryTime > 0){
            	//UR58120031 Phase 2
                //for(int i = 1; i <= 3; i++){
            	for(int i = 1; i <= retryTime; i++){
                    delay(delayTime);
                    System.out.println("callURLEBXML : " + String.valueOf(i));
                    boolean retryResult = callURLEBXML(responseMsg, gpGuarantee, logEBXML);
                    if(retryResult){
                        break;
                    }
                }
            }
        }
    }
    
	//Coding for UR58120031 add-extend-expire-date-interface (Phase 1)
	//added
	private GPGuarantee setTransactionStatus(GPGuarantee gpGuarantee, String status, String statusLg, String msgCode, String xmlOutput){
		gpGuarantee.setTransactionStatus(status);
		gpGuarantee.setStatusLG(statusLg);
		gpGuarantee.setMsgCode(msgCode);
		gpGuarantee.setXmlOutput(xmlOutput);
		return gpGuarantee;
	}
	
	//Coding for UR58120031 add-extend-expire-date-interface (Phase 1)
	//added
	private GPGuarantee setReviewStatus(GPGuarantee gpGuarantee, String status, Date dtm, String user, String reason){
		gpGuarantee.setReviewStatus(status);
		gpGuarantee.setReviewDtm(dtm);
		gpGuarantee.setReviewBy(user);
		gpGuarantee.setReviewReason(reason);
		return gpGuarantee;
	}
	
	//Coding for UR58120031 add-extend-expire-date-interface (Phase 1)
	//added
	private GPGuarantee setApprovalStatus(GPGuarantee gpGuarantee, String status, Date dtm, String user, String reason){
		gpGuarantee.setApproveStatus(status);
		gpGuarantee.setApproveDtm(dtm);
		gpGuarantee.setApproveBy(user);
		gpGuarantee.setApproveReason(reason);
		return gpGuarantee;
	}
    

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // --- Auto-generated method stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // -- Auto-generated method stub
        processRequest(request, response);
    }

}
