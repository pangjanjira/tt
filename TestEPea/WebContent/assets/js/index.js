//UR58060060 Phase 3.2 CADM Function
var viewFuncCode = INDEX_VIEW;
var modifyFuncCode = "";

$(document).ready(function () {
    //set menu
	displayMenu(viewFuncCode);
    //check user authorization
    checkViewAuthorize(viewFuncCode);
});

