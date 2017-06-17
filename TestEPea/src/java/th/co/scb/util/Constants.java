/**
 * 
 */
package th.co.scb.util;

/**
 * @author s51486
 *
 */
public class Constants {
	
	public static final String EGUARANTEE_SYSTEM_NAME = "EGUA";
	public static final String GP_SYSTEM_NAME = "LGGP";//add by vorakorn.j@17032015
	public static final String ALS_SYSTEM_NAME = "ALS";
	public static final String THAI_CURRENCY = "THB";
	public static final String BLANK = "";
	
	public static final String ENCODING = "UTF-8";
	public static final String TEMPLETE_XML_RETURN_EBXML = "xml_return_054.vm";
	public static final String TEMPLETE_XML_RETURN_BATCH = "xml_return_batch.vm";
	public static final String TEMPLETE_XML_MQ_DEPOSIT = "mq_request_deoisit.vm";
	public static final String TEMPLETE_XML_MQ_CANCEL_OR_REFUND = "mq_request_cancelorrefund.vm";
	public static final String TEMPLETE_OPEN_ACCT_CDATA = "openacct_cdata.vm";
	public static final String TEMPLETE_OPEN_ACCT_GP_CDATA = "openacct_gp_cdata.vm";
	
	//UR58120031 Phase 2 (send extend request to ALS, send SMS, send mail)
	public static final String TEMPLETE_XML_MQ_EXTEND = "mq_request_extend.vm";
	
	public static final String TEMPLETE_XML_ACK_TO_EBXML = "xml_ack_to_ebxml.vm";
	public static final String TEMPLETE_XML_RETURN_TO_GP = "xml_return_to_gp.vm";
	public static final String TEMPLETE_XML_EED_RETURN_TO_GP = "xml_eed_return_to_gp.vm"; //UR58120031 add by Tana.L@23022016
	
	//----------------- GEN LG No. --------------------------
	public static final String 	LG_NAME = "LG_NO";
	public static final int		LG_RUNNING_LENGTH = 4;
	
	//------------------ GEN BANK TRANSACTION No. --------------------
	public static final String 	BANK_TRAN_NAME = "BANK_NO";
	public static final int		BANK_TRAN_RUNNING_LENGTH = 7;
	
	//------------------ SMS TODO---------------------------------
	public static final String TEMPLETE_XML_SMS = "xml_send_sms_to_mq.vm";
	//UR58120031 Phase 2 (send extend request to ALS, send SMS, send mail)
	public static final String TEMPLETE_XML_EXTEND_EXPIRY_DATE_SMS = "xml_send_extend_expiry_date_sms_to_mq.vm";
	
	public static final String FIELD_EGUARANTEE = "EGUARANTEE";
	public static final String FIELD_ALERT_TYPE_LG = "LG";
	public static final String FIELD_SUB_ALERT_NOTIFY = "NOTIFY";
	public static final String SENDER_ID_SMS = "SMS";
	
	//----------------- E-mail add by 61096 -------------------------
	public static final String TEMPLETE_XML_CONFIRM_MAIL = "xml_confirm_mail_to_mq.vm";
	public static final String TEMPLETE_XML_CANCEL_MAIL = "xml_cancel_mail_to_mq.vm";
	public static final String TEMPLETE_XML_CLAIM_MAIL = "xml_claim_mail_to_mq.vm";
	//UR58120031 Phase 2 (send extend request to ALS, send SMS, send mail)
	public static final String TEMPLETE_XML_EXTEND_EXPIRY_DATE_MAIL = "xml_extend_expiry_date_mail_to_mq.vm";
	
	public static final String FIELD_SUB_ALERT_CONFIRM = "CONFIRM";
	public static final String FIELD_SUB_ALERT_CANCEL = "CANCEL";
	public static final String FIELD_SUB_ALERT_CLAIM = "CLAIM";
	//UR58120031 Phase 2 (send extend request to ALS, send SMS, send mail)
	public static final String FIELD_SUB_ALERT_EXTEND_EXPIRY_DATE = "EXTEND";
	
	public static final String SENDER_ID_EMAIL = "EMAIL";
	
	//---------------Get account from ALS 
    public static final String TEMPLETE_XML_REQ_ACCTALS_ACCTNO_TO_MQ = "mq_request_acctALS_acctinfo.vm";
    public static final String TEMPLETE_XML_REQ_ACCTALS_TAXID_TO_MQ = "mq_request_acctALS_taxid.vm";

	
	//----------------- Get account from ALS add by 61096 ------------------
	public static final String TEMPLETE_XML_REQ_ACCT_TO_MQ = "mq_request_acct.vm";
	public static final String SENDER_ID_LGIN = "LGIN"; //update by Apichart H.@20150515
        
        public static final String SYSTEM_USER = "SYSTEM";

	// Table Name 
	public class TableName{

		public static final String 	LOG_EBXML = "log_ebxml";
		public static final String 	LOG_MQ = "log_mq";
		public static final String 	ACCOUNT_ALS = "account_als";
		public static final String 	DOC_RUNNING = "doc_running";
		public static final String 	DECLARATION = "declaration";
		public static final String 	E_STATUS = "e_status";
		public static final String 	E_MAP_CODE_ERROR = "e_map_code_error";
		public static final String 	TRAN_OFFLINE = "tran_offline";
		public static final String 	E_TIME = "e_time";
		public static final String 	E_GUARANTEE = "e_guarantee";
		public static final String 	E_GUARANTEE_ALS_OFFLINE = "e_guarantee_als_offline";
		public static final String	REGISTRATION_ID = "registration_id";
		
		//add by vorakorn.j@19032015
		public static final String 	PROJECT_TAX = "project_tax";
		public static final String 	GP_GUARANTEE = "gp_guarantee";
		public static final String 	TRAN_GP_OFFLINE = "tran_gp_offline";
		public static final String 	GP_GUARANTEE_ALS_OFFLINE = "gp_guarantee_als_offline";
		public static final String 	BANK_INFO = "bank_info";
		
		//Coding for UR58120031 add-extend-expire-date-interface (Phase 1)
		//added
		public static final String	GP_GUARANTEE_H = "gp_guarantee_h";

		//add by apichart.h@13052015
		public static final String BU_CODE = "bu_code";
		
		//add by Narong.W@16062015
//        public static final String 	CONTROL_ACCOUNT = "control_account";
        public static final String 	GP_APPROVAL_LOG = "gp_approval_log";
        //add by Apichart.H@19102015
        public static final String GP_REVIEW_LOG = "gp_review_log";
        
        ////add by Mayurachat L.@20150618
        public static final String USER_AUTH = "user_auth";
		public static final String CONTROL_ACCOUNT = "control_account";
        public static final String CONTROL_MSG_CODE= "control_msg_code";
        
        //UR58060060 Phase 3.2 CADM Function
        public static final String MAS_APPLICATION_PROFILE= "mas_application_profile";
        public static final String MAS_USER_PROFILE= "mas_user_profile";
        public static final String MAS_FUNCTION= "mas_function";
        public static final String MAS_LEVEL= "mas_level";
        public static final String MAS_LEVEL_FUNCTION= "mas_level_function";
        public static final String MAS_USER_APP= "mas_user_app";
        public static final String MAS_MENU= "mas_menu";
        public static final String LOG_ACCESS= "log_access";
        public static final String LOG_CADM_PROCESS= "log_cadm_process";
        public static final String MAS_CONFIG = "mas_config";
	}
	
	//UR58060060 Phase 3.2 CADM Function
	public class CADM{

		public static final String 	APP_CODE = "EGUA0001";
		public static final String 	STATUS_ACTIVE = "1";
		public static final String 	STATUS_INACTIVE = "0";
		public static final String 	STATUS_ACTIVE_DESC = "Active";
		public static final String 	STATUS_INACTIVE_DESC = "Inactive";
	}
	
	// type of connect e-guarantee
	public class TypeConnect{

		public static final String 	WEB = "web";
		public static final String 	BATCH = "batch";
	}
	
	//e-guarantee xml
	public class EGuarantee{

		public static final String 	PAYMENT_METHOD = "PAYMENT_METHOD";
		public static final String 	DEPOSIT = "G1N";
		public static final String 	DEPOSIT_CANCEL = "G1C";
		public static final String 	REFUND = "G2N";
		public static final String 	DEPOSIT_DESC = "deposit";
		public static final String 	DEPOSIT_CANCEL_DESC = "depositcancellation";
		public static final String 	REFUND_DESC = "refund";
		
		public static final String 	DOC_NAME_DEPOSIT = "RTC.G1N.008.01";
		public static final String 	DOC_NAME_DEPOSIT_CANCEL = "RTC.G1C.007.01";
		public static final String 	DOC_NAME_REFUND = "RTC.G2N.007.01";
		
		public static final String 	DOC_RETURN_NAME_DEPOSIT = "RTC.G1N.054.01";
		public static final String 	DOC_RETURN_NAME_DEPOSIT_CANCEL = "RTC.G1C.054.01";
		public static final String 	DOC_RETURN_NAME_REFUND = "RTC.G2N.054.01";
		
		public static final String 	STATUS_BOOK = "BOOK";//ÊÓàÃç¨(à§Ô¹à¢éÒºÑ­ªÕ¡ÃÁÈØÅ¡Ò¡Ã)
		public static final String 	STATUS_INFO = "INFO";//äÁèÊÓàÃç¨(á¨é§àËµØ¼Å)
		
		public static final String 	STATUS_SUCCESS = "SC";//·Ó¡ÒÃµÑé§, Å´ ËÃ×Í Â¡àÅÔ¡ÊÓàÃç¨
		public static final String 	STATUS_UNSUCCESS = "NS";//·Ó¡ÒÃµÑé§, Å´ ËÃ×Í Â¡àÅÔ¡äÁèÊÓàÃç¨
		public static final String 	STATUS_CANCEL = "CC";
		public static final String 	STATUS_CLAIM = "CL";
		
		//Coding for UR58120031 add-extend-expire-date-interface (Phase 1)
		//added
		public static final String 	STATUS_EXTEND_EXPIRY_DATE = "EE";

		public static final String ERROR_CODE = "ErrorCode";
		public static final String ISQL_MSG = "ISQLMsg";
		
	}
	
	public class GPGuarantee{//add by vorakorn.j@18032015
		
		public static final String 	ISSUE_TYPE = "ISSUE_TYPE";
		public static final String 	SETUP_ISSUE = "0004";
		public static final String 	SETUP_ISSUE_DESC = "Setup Issue";
		public static final String 	CANCEL_ISSUE = "0005";
		public static final String 	CANCEL_ISSUE_DESC = "Cancel Issue";
		public static final String 	CLAIM_ISSUE = "0006";
		public static final String 	CLAIM_ISSUE_DESC = "Claim Issue";
		//Coding for UR58120031 add-extend-expire-date-interface (Phase 1)
		//added
		public static final String 	EXTEND_EXPIRY_DATE_ISSUE = "0007";
		public static final String 	EXTEND_EXPIRY_DATE_ISSUE_DESC = "Extend Issue";
		
		public static final String SETUP_ISSUE_TAG_ROOT_RQ = "wele0004Request";
		public static final String CANCEL_ISSUE_TAG_ROOT_RQ = "wele0005Request";
		public static final String CLAIM_ISSUE_TAG_ROOT_RQ = "wele0006Request";
		//Coding for UR58120031 add-extend-expire-date-interface (Phase 1)
		//added
		public static final String EXTEND_EXPIRY_DATE_ISSUE_TAG_ROOT_RQ = "wele0007Request";
		
		public static final String SETUP_ISSUE_TAG_ROOT_ACK = "wele0004Ack";
		public static final String CANCEL_ISSUE_TAG_ROOT_ACK = "wele0005Ack";
		public static final String CLAIM_ISSUE_TAG_ROOT_ACK = "wele0006Ack";
		//Coding for UR58120031 add-extend-expire-date-interface (Phase 1)
		//added
		public static final String EXTEND_EXPIRY_DATE_ISSUE_TAG_ROOT_ACK = "wele0007Ack";
		
		public static final String GP_GUARANTEE_TAG_ROOT_ACK = "eGPGuaranteeAck";
		
		public static final String SETUP_ISSUE_TAG_ROOT_RS = "wele0004Response";
		public static final String CANCEL_ISSUE_TAG_ROOT_RS = "wele0005Response";
		public static final String CLAIM_ISSUE_TAG_ROOT_RS = "wele0006Response";
		
		//Coding for UR58120031 add-extend-expire-date-interface (Phase 1)
		//added
		public static final String EXTEND_EXPIRY_DATE_ISSUE_TAG_ROOT_RS = "wele0007Response";
		
		public static final String GP_GUARANTEE_TAG_ROOT_RS = "eGPGuaranteeResponse";
		
		public static final String 	REVIEW_PENDING = "PR";
		public static final String 	REVIEW_APPROVED = "AP";
		public static final String 	REVIEW_REJECTED = "RJ";
		public static final String 	REVIEW_REASON_AUTO_APPROVED = "Auto Approved";
		public static final String 	REVIEW_REASON_AUTO_REJECTED = "Auto Rejected";
		public static final String 	REVIEW_REASON_AUTO_PENDING = "Auto Pending";
		public static final String 	REVIEW_REASON_AUTO_PENDING_MULTI_ACCT = "Auto Pending (MA)";
		public static final String 	REVIEW_REASON_AUTO_PENDING_CONTROL_ACCT = "Auto Pending (CA)";
		//UR59040034 Add eGP Pending Review & Resend Response function
		//Add Pending account not found
		public static final String 	REVIEW_REASON_AUTO_PENDING_NULL_ACCT = "Auto Pending (NA)";
		
		//Coding for UR58120031 add-extend-expire-date-interface (Phase 1)
		//added
		public static final String 	REVIEW_REASON_AUTO_PENDING_EXTEND_EXPIRY_DATE = "Auto Pending (EE)";
		
		public static final String 	APPROVAL_PENDING = "PA";
		public static final String 	APPROVAL_APPROVED = "AP";
		public static final String 	APPROVAL_REJECTED = "RJ";
		public static final String 	APPROVAL_REASON_AUTO_APPROVED = "Auto Approved";
		public static final String 	APPROVAL_REASON_AUTO_REJECTED = "Auto Rejected";
		public static final String 	APPROVAL_REASON_AUTO_PENDING = "Auto Pending";
		public static final String 	APPROVAL_REASON_AUTO_PENDING_MULTI_ACCT = "Auto Pending (Multi Account)";
		
		public static final String 	PROCESS_BY_SYSTEM = "SYSTEM";
		
		//Coding for UR58120031 add-extend-expire-date-interface (Phase 1)
		//added
		public static final String 	ACTION_SETUP = "SET";
		public static final String 	ACTION_CANCEL = "CAN";
		public static final String 	ACTION_CLAIM = "CLM";
		public static final String 	ACTION_EXTEND_EXPIRY_DATE = "EED";
		
	}
	
	//message code for return to ebxml(customs)
	public class MessageCode{
	/*	public static final String 	MASSAGE_CODE = "MSG_CODE";
		public static final String 	INSUFFICIENT_FUNDS = "0001";//ï¿½Ô¹ã¹ºÑ­ï¿½ï¿½ï¿½ï¿½ï¿½Í¨ï¿½ï¿½ï¿½
		public static final String 	INVALID_BANK_CODE= "0003";//ï¿½ï¿½ï¿½Ê¸ï¿½Ò¤ï¿½ï¿½ï¿½ï¿½ï¿½Ù¡ï¿½ï¿½Í§
		public static final String 	INVALID_ACCT_NO = "0004";//ï¿½Å¢ï¿½ï¿½ï¿½Ñ­ï¿½ï¿½ï¿½ï¿½ï¿½Ù¡ï¿½ï¿½Í§
		public static final String 	UNABLE_TO_PROCESS = "0005";//ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Ã¶ï¿½ï¿½ï¿½ï¿½Â¡ï¿½ï¿½ï¿½ï¿½
		public static final String 	DUP_TRANSACTION = "0006";//ï¿½ï¿½Â¡ï¿½Ã«ï¿½ï¿½ 
		public static final String 	ACCOUNT_CLOSED = "0002";//ï¿½Ñ­ï¿½Õ»Ô´ï¿½ï¿½ï¿½ï¿½
         */		
		public static final String 	ISO_CODE = "ISO_CODE";
		public static final String 	INSUFFICIENT_FUNDS = "AM04";//à§Ô¹ã¹ºÑ­ªÕäÁè¾Í¨èÒÂ
		public static final String 	INVALID_BANK_CODE= "RC01";//ÃËÑÊ¸¹Ò¤ÒÃäÁè¶Ù¡µéÍ§
		public static final String 	INVALID_ACCT_NO = "AC01";//àÅ¢·ÕèºÑ­ªÕäÁè¶Ù¡µéÍ§
		public static final String 	UNABLE_TO_PROCESS = "AG07";//äÁèÊÒÁÒÃ¶·ÓÃÒÂ¡ÒÃä´é
		public static final String 	DUP_TRANSACTION = "AM05";//ÃÒÂ¡ÒÃ«éÓ 
		public static final String 	ACCOUNT_CLOSED = "AC04";//ºÑ­ªÕ»Ô´áÅéÇ
		public static final String 	INVALID_INFO_CUSTOMER = "AC02";//àÅ¢·ÕèºÑ­ªÕäÁè¶Ù¡µéÍ§
		public static final String 	PLEASE_CONTACT_SCB = "AM99";//Please contact SCB
		
	}
	
	public class StatusLGGP{
	
			public static final String 	STATUS_LG_GP = "STATUS_LG_GP";
			public static final String 	APPROVE = "01";//APPROVE
			public static final String 	DRAFT= "02";//DRAFT
			public static final String 	REJECT = "03";//REJECT
			public static final String 	CLAIM = "05";//CLAIM
			public static final String 	CANCLE = "06";//CANCLE
			//Coding for UR58120031 add-extend-expire-date-interface (Phase 1)
			//added
			public static final String 	EXTEND_EXPIRY_DATE = "07";//EXTEND
			
			public static final String 	DUPLICATION = "Duplication";
			public static final String 	CONTROL_ACCOUNT = "Please contact SCB for more information. (CA)";
			public static final String 	INVALID_INFO_CUSTOMER = "Please contact SCB for more information. (tax id not found)";
			public static final String 	INSUFFICIENT_FUNDS = "Insufficient Funds";
			public static final String 	UNABLE_TO_PROCESS = "Unable to Process";
			public static final String 	PLEASE_CONTACT_SCB = "Please contact SCB for more information.";
			public static final String 	INVALID_EXTEND_DATE = "Please contact SCB for more information. (invalid extend date)";
			
		}
	
	// status ALS Online 
	public class StatusALSOnline{

		public static final String 	YES = "1";
		public static final String 	NO = "0";
	}
	
	public class MessageCodeErrorALSMQ{
		
		public static final String 	COMMITMENT_NOT_FOUND = "AM3827";
		public static final String 	ACCOUNT_NOT_FOUND = "MIM011";
		public static final String 	ACCOUNT_INVALID = "AM1047";
		public static final String 	ACCOUNT_CLOSED = "AM2731";
		public static final String 	INSUFFICIENT_FUNDS = "AM2081";
		public static final String 	INSUFFICIENT_FUNDS_2 = "AM3395";
		public static final String 	INSUFFICIENT_FUNDS_3 = "AM7104";
		
	}
	
	public class Customs{
		
		public static final String DOC_RETURN_G1N = "RTC.G1N.008.01";
		public static final String DOC_RETURN_G1C = "RTC.G1C.007.01";
		public static final String DOC_RETURN_G2N = "RTC.G2N.007.01";
		
		public static final int COM_TAX_NO = 1;
		public static final int COM_BRANCH = 2;
		public static final String COM_TAX_NO_TEXT = "CMPTAXNUM";
		public static final String COM_BRANCH_TEXT = "CMPBRN";
		
		public static final int DECLARATION_NO = 1;
		public static final int DECLARATION_SEQ = 2;
		public static final int BANK_GUARANTEE_NO = 3;
		public static final int INFORM = 4;
		
		public static final String DECLARATION_NO_TEXT = "DCLNUM";
		public static final String DECLARATION_SEQ_NO_TEXT = "DCLSEQNUM";
		public static final String BANK_GUARANTEE_NO_TEXT = "BNKGRTNUM";
		public static final String INFORM_TEXT = "INFORM";
		
		public static final String CUSTOMS_CODE_T = "TH0041010353980000000010001T5";//For test
		public static final String CUSTOMS_CODE_P = "TH0041010353980000000010001P5";//For Production
		public static final String	SCB_CODE_T = "TH0031010248070000000000001T4";//For test
		public static final String	SCB_CODE_P = "TH0031010248070000000000001P4";//For Production
	}
	
	public class ConfigFile{
		
		public static final String CONFIG_FILE_LOCATION_PROPERTIES = "configFileLocation.properties";
		public static final String CONFIG_FILE_LOCATION = "configfile.location";
		
		public static final String EBXML_PROPERTIES = "ebxml.properties";
		public static final String EBXML_URL = "ebxml.url";
		public static final String EBXML_GP_URL = "ebxml.gp.url";
		
		public static final String DATABASE_PROPERTIES = "database.properties";
		public static final String DATABASE_JDBC_DRIVER = "jdbcDriver";
		public static final String DATABASE_DB_NAME = "dbName";
		public static final String DATABASE_DB_LOOKUP_NAME = "dbLookupName";
		public static final String DATABASE_DB_URL = "dbUrl";
		public static final String DATABASE_DB_USERNAME = "dbUserName";
		public static final String DATABASE_DB_PASSWORD = "dbPassword";
		
		public static final String MQ_PROPERTIES = "mq.properties";
		public static final String MQ_SERVICE_NAME = "mq.servicename";
		public static final String MQ_USER = "mq.user";
		public static final String MQ_PASSWORD = "mq.password";
		public static final String MQ_HOST = "mq.host";
		public static final String MQ_CHANNEL = "mq.channel";
		public static final String MQ_PORT = "mq.port";
		public static final String MQ_Q_MANAGER = "mq.qmanager";
		public static final String MQ_RQ_NAME = "mq.rqname";
		public static final String MQ_RS_NAME = "mq.rsname";
		public static final String MQ_SESSION_USERID = "mq.sessionuserid";
		public static final String MQ_TIMEOUT= "mq.timeout";
		public static final String MQ_CHANNEL_TABLE= "mq.channelTable";
		public static final String MQ_MODE= "mq.mode";
		public static final String MQ_PROCESS_DATE= "mq.processdate";
		public static final String MQ_EFFECTIVE_DATE = "mq.effectivedate";
		//FERN TODO
		public static final String MQ_SMS_RQ_NAME = "EGUA.NNSS.NOTIFYALERT.RQ";//"mq.sms.rqname";
		
		public static final String CUSTOMS_PROPERTIES = "config_xml_customs.properties";
		
		//-------------------- Deposit ----------------------
		public static final String CUSTOMS_DEPOSIT_MESSAGE_ROOT = "deposit.messageRoot";
		public static final String CUSTOMS_DEPOSIT_CUSTOMS_NAME = "deposit.customsName";
		public static final String CUSTOMS_DEPOSIT_CUSTOMS_REF_NO = "deposit.customsRefNo";
		public static final String CUSTOMS_DEPOSIT_CUSTOMS_TRANS_DATETIME = "deposit.customsTransDateTime";
		public static final String CUSTOMS_DEPOSIT_NUMBER_OF_TRANS = "deposit.noOfTrans";
		public static final String CUSTOMS_DEPOSIT_PAYMENT_METHOD = "deposit.paymentMethod";
		public static final String CUSTOMS_DEPOSIT_TRANS_TYPE = "deposit.transtype";
		public static final String CUSTOMS_DEPOSIT_REQUEST_DATE = "deposit.requestDate";
		public static final String CUSTOMS_DEPOSIT_CUSTOMS_BANK_ACCT = "deposit.customsBankAcct";
		public static final String CUSTOMS_DEPOSIT_CUSTOMS_BANK_CODE = "deposit.customsBankCode";
		public static final String CUSTOMS_DEPOSIT_CUSTOMS_BRANCH_CODE = "deposit.customsBranchCode";
		public static final String CUSTOMS_DEPOSIT_DEPOSIT_AMT = "deposit.depositAmt";
		public static final String CUSTOMS_DEPOSIT_DEBTOR_BANK_CODE = "deposit.debtorBankCode";
		public static final String CUSTOMS_DEPOSIT_DEBTOR_BRANCH_CODE = "deposit.debtorBranchCode";
		public static final String CUSTOMS_DEPOSIT_DEBTOR_COMP_NAME = "deposit.debtorCompName";
		public static final String CUSTOMS_DEPOSIT_DEBTOR_COMP_TAX_NO = "deposit.debtorCompTaxNo";
		public static final String CUSTOMS_DEPOSIT_DEBTOR_COMP_BRANCH = "deposit.debtorCompBranch";
		public static final String CUSTOMS_DEPOSIT_DEBTOR_BANK_ACCT_NO = "deposit.debtorBankAcctNo";
		public static final String CUSTOMS_DEPOSIT_DECLARATION_NO = "deposit.declarationNo";
		public static final String CUSTOMS_DEPOSIT_RELATE_DATE = "deposit.relateDate";
		public static final String CUSTOMS_DEPOSIT_DECLARATION_SEQ_NO = "deposit.declarationSeqNo";
		
		//----------------- Deposit Cancel -------------------
		public static final String CUSTOMS_DEPOSITCANCEL_MESSAGE_ROOT = "depositcancel.messageRoot";
		public static final String CUSTOMS_DEPOSITCANCEL_CUSTOMS_REF_NO = "depositcancel.customsRefNo";
		public static final String CUSTOMS_DEPOSITCANCEL_CUSTOMS_TRANS_DATETIME = "depositcancel.customsTransDateTime";
		public static final String CUSTOMS_DEPOSITCANCEL_NUMBER_OF_TRANS = "depositcancel.noOfTrans";
		public static final String CUSTOMS_DEPOSITCANCEL_ORIGINAL_CUSTOMS_REF_NO = "depositcancel.originalCustomsRefNo";
		public static final String CUSTOMS_DEPOSITCANCEL_DEPOSIT_AMOUNT = "depositcancel.depositAmount";
		public static final String CUSTOMS_DEPOSITCANCEL_DECLARATION_NO = "depositcancel.declarationNo";
		public static final String CUSTOMS_DEPOSITCANCEL_RELATE_DATE = "depositcancel.relateDate";
		public static final String CUSTOMS_DEPOSITCANCEL_DECLARATION_SEQ_NO = "depositcancel.declarationSeqNo";
		public static final String CUSTOMS_DEPOSITCANCEL_BANK_GUARANTEE_NO = "depositcancel.bankGuaranteeNo";
		public static final String CUSTOMS_DEPOSITCANCEL_DEBTOR_COMP_NAME = "depositcancel.debtorCompName";
		public static final String CUSTOMS_DEPOSITCANCEL_DEBTOR_COMP_TAX_NO = "depositcancel.debtorCompTaxNo";
		public static final String CUSTOMS_DEPOSITCANCEL_DEBTOR_COMP_BRANCH = "depositcancel.debtorCompBranch";
		public static final String CUSTOMS_DEPOSITCANCEL_DEBTOR_BANK_ACCT_NO = "depositcancel.debtorBankAcctNo";
		public static final String CUSTOMS_DEPOSITCANCEL_DEBTOR_BANK_CODE = "depositcancel.debtorBankCode";
		public static final String CUSTOMS_DEPOSITCANCEL_DEBTOR_BRANCH_CODE = "depositcancel.debtorBranchCode";
		
		//------------------------- Refund --------------------
		public static final String CUSTOMS_REFUND_MESSAGE_ROOT = "refund.messageRoot";
		public static final String CUSTOMS_REFUND_CUSTOMS_REF_NO = "refund.customsRefNo";
		public static final String CUSTOMS_REFUND_CUSTOMS_TRANS_DATETIME = "refund.customsTransDateTime";
		public static final String CUSTOMS_REFUND_NUMBER_OF_TRANS = "refund.noOfTrans";
		public static final String CUSTOMS_REFUND_ORIGINAL_CUSTOMS_REF_NO = "refund.originalCustomsRefNo";
		public static final String CUSTOMS_REFUND_DEPOSIT_AMOUNT = "refund.depositAmount";
		public static final String CUSTOMS_REFUND_DECLARATION_NO = "refund.declarationNo";
		public static final String CUSTOMS_REFUND_BANK_GUARANTEE_NO = "refund.bankGuaranteeNo";
		public static final String CUSTOMS_REFUND_INFORM_PORT = "refund.informPort";
		public static final String CUSTOMS_REFUND_INFORM_NO = "refund.informNo";
		public static final String CUSTOMS_REFUND_INFORM_DATE = "refund.informDate";
		public static final String CUSTOMS_REFUND_DEBTOR_COMP_NAME = "refund.debtorCompName";
		public static final String CUSTOMS_REFUND_DEBTOR_COMP_TAX_NO = "refund.debtorCompTaxNo";
		public static final String CUSTOMS_REFUND_DEBTOR_COMP_BRANCH = "refund.debtorCompBranch";
		public static final String CUSTOMS_REFUND_DEBTOR_BANK_ACCT_NO = "refund.debtorBankAcctNo";
		public static final String CUSTOMS_REFUND_DEBTOR_BANK_CODE = "refund.debtorBankCode";
		public static final String CUSTOMS_REFUND_DEBTOR_BRANCH_CODE = "refund.debtorBranchCode";
		
        // add by Mayurachat L.@19062015
        public static final String LDAP_PROPERTIES = "ldap.properties";
        
        // add by Apichart.H @130102015
        public static final String REPORT_PROPERTIES = "report.properties";
        public static final String REPORT_FOLDER_PATH = "report.path";

	}
	
	public class ConfigGPFile{//add by vorakorn.j@18032015

		public static final String CUSTOMS_GP_PROPERTIES = "config_xml_customs_gp.properties";
		
		public static final String CUSTOMS_GP_SETPUP_ISSUE_MESSAGE_ROOT = "setup.issue.messageRoot";
		public static final String CUSTOMS_GP_CANCEL_ISSUE_MESSAGE_ROOT = "cancel.issue.messageRoot";
		public static final String CUSTOMS_GP_CLAIM_ISSUE_MESSAGE_ROOT = "claim.issue.messageRoot";
		//Coding for UR58120031 add-extend-expire-date-interface (Phase 1)
		//added
		public static final String CUSTOMS_GP_EXTEND_EXPIRY_DATE_ISSUE_MESSAGE_ROOT = "extend_expiry_date.issue.messageRoot";
		
		public static final String CUSTOMS_GP_TRANS_REF = "transRef";
		public static final String CUSTOMS_GP_TRANS_DATE = "transDate";
		public static final String CUSTOMS_GP_PROJECT_NO = "projectNo";
		public static final String CUSTOMS_GP_DEPT_CODE = "deptCode";
		public static final String CUSTOMS_GP_VENDOR_TAX_ID = "vendorTaxID";
		public static final String CUSTOMS_GP_VENDOR_NAME = "vendorName";
		public static final String CUSTOMS_GP_COMP_ID = "compID";
		public static final String CUSTOMS_GP_USER_ID = "userId";
		public static final String CUSTOMS_GP_SEQ_NO = "seqNo";
		public static final String CUSTOMS_GP_CONSIDER_DESC = "considerDesc";
		public static final String CUSTOMS_GP_CONSIDER_MONEY = "considerMoney";
		public static final String CUSTOMS_GP_GUARANTEE_AMT = "guaranteeAmt";
		public static final String CUSTOMS_GP_CONTRACT_NO = "contractNo";
		public static final String CUSTOMS_GP_CONTRACT_DATE = "contractDate";
		public static final String CUSTOMS_GP_GUARANTEE_PRICE = "guaranteePrice";
		public static final String CUSTOMS_GP_GUARANTEE_PERCENT = "guaranteePercent";
		public static final String CUSTOMS_GP_ADV_GUARANTEE_PRICE = "advanceGuaranteePrice";
		public static final String CUSTOMS_GP_ADV_PAYMENT = "advancePayment";
		public static final String CUSTOMS_GP_WORKS_GUARANTEE_PRICE = "worksGuaranteePrice";
		public static final String CUSTOMS_GP_WORKS_GUARANTEE_PERCENT = "worksGuaranteePercent";
		public static final String CUSTOMS_GP_COLLECTION_PHASE = "collectionPhase";
		public static final String CUSTOMS_GP_END_DATE = "endDate";
		public static final String CUSTOMS_GP_START_DATE = "startDate";
		public static final String CUSTOMS_GP_BOND_TYPE = "bondType";
		public static final String CUSTOMS_GP_PROJECT_NAME = "projName";
		public static final String CUSTOMS_GP_PROJECT_AMT = "projAmt";
		public static final String CUSTOMS_GP_PROJECT_OWN_NAME = "projOwnName";
		public static final String CUSTOMS_GP_COST_CENTER = "costCenter";
		public static final String CUSTOMS_GP_COST_CENTER_NAME = "costCenterName";
		public static final String CUSTOMS_GP_DOCUMENT_NO = "documentNo";
		public static final String CUSTOMS_GP_DOCUMENT_DATE = "documentDate";
		public static final String CUSTOMS_GP_EXPIRE_DATE = "expireDate";
		
		public static final String CUSTOMS_GP_LG_NO = "lgNo";
		public static final String CUSTOMS_GP_AMT_REQ = "amtReq";
		
		//Coding for UR58120031 add-extend-expire-date-interface (Phase 1)
		//added
		public static final String CUSTOMS_GP_EXTEND_DATE = "extendDate";
		
	}
        
        public class AccountPurpose{

		public static final String 	ACCOUNT_CUSTOMS = "07";
		public static final String 	ACCOUNT_EGP = "09";
	}
    
        
//----------------- Excel Report --------------------------------
    //----------------- add by Apichart H.@20151020 -----------------
    public static final Object[] EXCEL_HEADER_FIELDS = {
    	"TxRef", "Vendor Name", "Account No.",
        "Guarantee Amt", "Msg Code",
        "Expire Date", "Extend Date", "Request Dtm", //UR58120031 Extend Date add by Tana L. @10022016
        "Proj No.", "Proj Name",
        "Start Date", "End Date",
        "Bond Type", "ALS Msg",
        "Request Type",  //UR58120031 Request Type add by Tana L. @18022016
        "LG No.", "Vendor Tax ID",
        "Review Status", "Review Reason", "Review By", "Review Dtm",
        "Approve Status", "Approve Reason", "Approve By", "Approve Dtm"
        , "OC Code", "Branch"
        , "eGP Status", "eGP Message" //add by Malinee T. UR58100048 @20151224

    };

    public static final String[] EXCEL_HEADER_TYPE = {
        "String", "String", "String", 
        "BigDecimal;#0.00", "String",
        "String", "String", "String", //UR58120031 Extend Date add by Tana L. @10022016
        "String", "String",
        "String", "String",
        "String", "String",
        "String", "String",
        "String", //UR58120031 Request Type add by Tana L. @18022016
        "String", "String", "String", "String",
        "String", "String", "String", "String"
        , "String", "String"
        , "String", "String" //add by Malinee T. UR58100048 @20151224
    };
    
    public static final Object[] EXCEL_REVIEW_HEADER_FIELDS = {
    	"TxRef","Request Type", "Vendor Name", "Account No.", //UR58120031 Request Type add by Tana L. @18022016
        "Guarantee Amt", "Msg Code",
        "Expire Date", "Extend Date", "Request Dtm", //UR58120031 Extend Date add by Tana L. @10022016
        "Proj No.", "Proj Name",
        "Start Date", "End Date",
        "Bond Type", "ALS Msg",
        "LG No.", "Vendor Tax ID",
        "Review Status", "Review Reason", "Review By", "Review Dtm",
        "Approve Status", "Approve Reason", "Approve By", "Approve Dtm"
        , "OC Code", "Branch"
        , "eGP Status", "eGP Message" //add by Malinee T. UR58100048 @20151224

    };

    public static final String[] EXCEL_REVIEW_HEADER_TYPE = {
        "String", "String", "String", "String", //UR58120031 Request Type add by Tana L. @18022016
        "BigDecimal;#0.00", "String",
        "String", "String", "String", //UR58120031 Extend Date add by Tana L. @10022016
        "String", "String",
        "String", "String",
        "String", "String",
        "String", "String",
        "String", "String", "String", "String",
        "String", "String", "String", "String"
        , "String", "String"
        , "String", "String" //add by Malinee T. UR58100048 @20151224
    };
    
    public static final Object[] EXCEL_MONITOR_HEADER_FIELDS = {
        "TxRef", "Request Type", "Vendor Name", "Account No.", //UR58120031 Request Type add by Tana L. @18022016
        "Guarantee Amt", "Msg Code",
        "Expire Date", "Extend Date", "Request Dtm", //UR58120031 Extend Date add by Tana L. @10022016
        "Proj No.", "Proj Name",
        "Start Date", "End Date",
        "Bond Type", "ALS Msg",
        "LG No.", "Vendor Tax ID",
        "Review Status", "Review Reason", "Review By", "Review Dtm"
        , "OC Code", "Branch"
        , "eGP Status", "eGP Message" //add by Malinee T. UR58100048 @20151224
    };

    public static final String[] EXCEL_MONITOR_HEADER_TYPE = {
    	"String", "String", "String", "String", //UR58120031 Request Type add by Tana L. @18022016
        "BigDecimal;#0.00", "String",
        "String", "String", "String", //UR58120031 Extend Date add by Tana L. @10022016
        "String", "String",
        "String", "String",
        "String", "String",
        "String", "String",
        "String", "String", "String", "String"
        , "String", "String"
        , "String", "String" //add by Malinee T. UR58100048 @20151224
    };

}