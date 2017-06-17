/**
 * 
 */
package th.co.scb.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import th.co.scb.db.ETimeTable;
import th.co.scb.model.ETime;
import th.co.scb.util.ConnectDB;
import th.co.scb.util.Constants;
import th.co.scb.util.DateUtil;

/**
 * @author s51486
 *
 */
public class ETimeService {
	
	private static final Logger logger = LoggerFactory.getLogger(ETimeService.class);
	
	public ETime findETime(String systemName) throws Exception {
		
		ConnectDB connectDB = null;
		ETime eTime = null;
		
		try{
			
			connectDB = new ConnectDB();
			
			ETimeTable eTimeTable = new ETimeTable(connectDB);
			eTime = eTimeTable.findETime(systemName);

		}catch(Exception ex){
			logger.error("Error find by E-Time : " + ex.getMessage());
            throw  new Exception(ex.getMessage());
            
        }finally{
        	if(connectDB != null){
        		connectDB.close();
        	}
        }
		
		return eTime;
		
	}
	
	
	public ETime isTimeOffHostALS() throws Exception{
		
		//for the 24-hour clock.
		boolean isTime  = false;
		
		final Calendar cal = DateUtil.getSystemCalendar();
		ETime eTime = null;

		try{
			
			eTime = findETime(Constants.ALS_SYSTEM_NAME);
			System.out.println("start time : " + eTime.getStartTime());
			System.out.println("end time : " + eTime.getEndTime());
			
			final SimpleDateFormat parser = new SimpleDateFormat("HH:mm:ss");
			Date timeFromPeriod1 = parser.parse(eTime.getStartTime());
			Date timeToPeriod1 = parser.parse("24:00:00");
			
			Date timeFromPeriod2 = parser.parse("00:00:00");
			Date timeToPeriod2 = parser.parse(eTime.getEndTime());
			
			Date timeCur = parser.parse(cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND));
			//timeCur = parser.parse("06:00:01");
			
			System.out.println("timeFromPeriod1 : " + timeFromPeriod1);
			System.out.println("timeToPeriod2 : " + timeToPeriod2);
			System.out.println("current time : " + timeCur);
			
			if((timeCur.after(timeFromPeriod1) && timeCur.before(timeToPeriod1)) || timeCur.compareTo(timeFromPeriod1) == 0 || timeCur.compareTo(timeToPeriod1) == 0){
				isTime = true;
				System.out.println("------ isTimeHandleTransaction Period 1----------");
			}else if(timeCur.compareTo(timeFromPeriod2)==0 || timeCur.compareTo(timeToPeriod2)==0 || (timeCur.after(timeFromPeriod2) && timeCur.before(timeToPeriod2))){
				isTime = true;
				System.out.println("------ isTimeHandleTransaction Period 2 ----------");
			}else{
				isTime = false;
				System.out.println("------ not isTimeHandleTransaction ----------");
			}
			
			eTime.setTimeOffHostALS(isTime);
			
		}catch (Exception e) {
			// --: handle exception
			//e.printStackTrace();
			logger.error("Error is Time OffHost ALS : " + e.getMessage());
            throw  new Exception(e.getMessage());
		}
		
		return eTime;
	}

}
