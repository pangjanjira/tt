        <!-- MENU SECTION -->
       <div id="left" >
           <ul id="menu" class="collapse">                
               <li class="panel" id="index"><a href="index.jsp" ><i class="icon-home"></i> Home</a></li>
               <!-- //UR58120031 Edit Request Review Icon by Tana L. @15022016 -->
               <li class="panel" id="createRequest">
                   <a href="#" data-parent="#menu" data-toggle="collapse" class="accordion-toggle" data-target="#control-nav">
                       <i class=" icon-tasks"></i> Create Request
                       <span class="pull-right">
                           <i class="icon-angle-right"></i>
                       </span>
                   </a>
                   <ul class="collapse" id="control-nav">
                       <li><a href="request_new_issue_lg.jsp" id="requestNewIssue"><i class="icon-plus-sign-alt"></i> Issue LG Request </a></li>
                       <li><a href="request_extend_lg.jsp" id="requestExtend"><i class="icon-plus-sign-alt"></i> Extend LG Request </a></li>
                       <li><a href="requestExtendIncrease.jsp" id="requestExIncrease"><i class="icon-plus-sign-alt"></i> Extend LG & Increase amt </a></li>
                       <li><a href="increaseAmtDuringYear.jsp" id="increaseAmt"><i class="icon-plus-sign-alt"></i> Increase amt during year </a></li>
                       <li><a href="decreaseAmtDuringYear.jsp" id="decreaseAmt"><i class="icon-plus-sign-alt"></i> Decrease amt during year </a></li>
                       <li><a href="claim.jsp" id="claim"><i class="icon-plus-sign-alt"></i> Claim LG </a></li>
                       <li><a href="cancel.jsp" id="claim"><i class="icon-plus-sign-alt"></i> Cancel LG </a></li>
                   </ul>
                   
               </li>
               <li class="panel" id="requestInquiry"><a href="requestInquiry.jsp" ><i class="icon-search"></i> Request Inquiry</a></li>
               <li class="panel" id="requestApproval"><a href="requestApproval.jsp" ><i class="icon-edit-sign"></i> Request Approval</a></li>
               <li class="panel" id="accountAls"><a href="account_als.jsp" ><i class="icon-list"></i> Account ALS</a></li>
               
               <li><a href="javascript:void(0)" onclick = "logout()"><i class="icon-signin" id = "logout"></i> Logout </a></li>
           </ul>
        </div>
        <!--END MENU SECTION -->

