/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.scb.service.eguarantee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import th.co.scb.db.eguarantee.GPReviewLogTable;
import th.co.scb.model.eguarantee.GPGuarantee;
import th.co.scb.util.ConnectDB;

/**
 *
 * @author s60982
 */
public class GPReviewLogService {

    private static final Logger logger = LoggerFactory.getLogger(GPGuaranteeService.class);

    public void insert(GPGuarantee gpGuarantee) throws Exception {
        ConnectDB connectDB = null;
        try {
            connectDB = new ConnectDB();
            GPReviewLogTable gpReviewLogTable = new GPReviewLogTable(connectDB);
            gpReviewLogTable.add(gpGuarantee);
        } catch (Exception ex) {
            logger.error("Error Insert GP_REVIEW_LOG : " + ex.getMessage());
            throw new Exception(ex.getMessage());

        } finally {
            if (connectDB != null) {
                connectDB.close();
            }
        }

    }
}
