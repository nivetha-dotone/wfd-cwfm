//<?php
///* <!--Application Name = PKS-Warehouse Management System
//   Version = 1.0
//   Release Date = January 01, 2015
//   Copyright =  ©2014 SRISYS Inc
//   Developed by = Srisys Inc, 7908 Cincinnati Dayton Rd, Suite C, West Chester, OH 45069 USA
//   web: www.srisys.com---> */
//?>  
   
   function back_to_counters(){
      var base_url =document.getElementById('base_url').value;
      window.location.href=base_url+'ctrl_answers'; 
   }
   function answerseq_unique(name, id){
      var base_url =document.getElementById('base_url').value;
      var id1 =document.getElementById('id1').value;
     var qstno = document.getElementById('edit_answers_question_id').value.trim();
     var answersequ = document.getElementById('edit_answers_answrsqnce_id').value.trim();
     if(answersequ!='' || answersequ!='null') {
        var url=base_url+'ctrl_answers/getanswerseq/'+qstno+'/'+answersequ+'/'+name+'/'+id+'/'+'edit'+ '/'+id1;
        ajax(url,'add_answer_seq_div_edit_id');  
     } 
   }
   function validations() {
   var val_msg2 = document.getElementById('val_msg2').value.trim();
        var val_msg3 = document.getElementById('val_msg3').value.trim();
        var val_msg4 = document.getElementById('val_msg4').value.trim();
        var val_msg5 = document.getElementById('val_msg5').value.trim();
        var val_msg6 = document.getElementById('val_msg6').value.trim();
        var val_msg7 = document.getElementById('val_msg7').value.trim();
        var val_msg8 = document.getElementById('val_msg8').value.trim();
        var val_msg9 = document.getElementById('val_msg9').value.trim();
        var val_msg11 = document.getElementById('val_msg11').value.trim();
//       var add_answers_companyname_id = document.getElementById('add_answers_companyname_id').value;
//       var add_answers_question_id = document.getElementById('add_answers_question_id').value; 
       var edit_answers_answer_id = document.getElementById('edit_answers_answer_id').value.trim();
       var edit_answers_adescription_id = document.getElementById('edit_answers_adescription_id').value.trim();
       var edit_answers_acmtsmandtry_id = document.getElementById('edit_answers_acmtsmandtry_id').value;
       var edit_answers_answrsqnce_id = document.getElementById('edit_answers_answrsqnce_id').value.trim();
        var flag_value = document.getElementById('add_uniq_answer_id').value.trim();
//       if(add_answers_companyname_id === '' || add_answers_companyname_id === null || add_answers_companyname_id === 'val_msg2') {
//         document.getElementById('errormsg').innerHTML='val_msg3';
//         return false;
//       } else if(add_answers_question_id === '' || add_answers_question_id === null) {
//         document.getElementById('errormsg').innerHTML=val_msg4;
//         return false;
         if(edit_answers_answer_id === '' || edit_answers_answer_id === null) {
          document.getElementById('errormsg').innerHTML=val_msg5;
         return false;
       } else  if (flag_value === 'false') {
            document.getElementById('errormsg').innerHTML =val_msg6;
            return false;
       } else if(edit_answers_adescription_id === '' || edit_answers_adescription_id === null) {
          document.getElementById('errormsg').innerHTML=val_msg7;
         return false;
       } else if(edit_answers_acmtsmandtry_id === '' || edit_answers_acmtsmandtry_id === null) {
          document.getElementById('errormsg').innerHTML=val_msg8;
         return false;
       } else if(edit_answers_answrsqnce_id === '' || edit_answers_answrsqnce_id === null) {
          document.getElementById('errormsg').innerHTML=val_msg9;
         return false;
       } else if(parseFloat(edit_answers_answrsqnce_id)=== 0) {
         document.getElementById('errormsg').innerHTML=val_msg11;
         return false; 
       } else{
                document.getElementById('errormsg').innerHTML = '';
                return findunique();
        }
    }
   function findunique(){
      var val_msg10 = document.getElementById('val_msg10').value.trim();
      var boolean_var=document.getElementById('add_uniq_answer_seq_id_edit').value;
      if(boolean_var==='false') {
           document.getElementById('errormsg').innerHTML = val_msg10;
         return false;
      } else {
        return true;
      }
   }
   function clearmessage(){
      document.getElementById('errormsg').innerHTML='';
      return true;
   }
   function questions(value){
      var base_url =document.getElementById('base_url').value;
      if(value==='' || value===null){
        document.getElementById('edit_answers_question_id').value='';
        document.getElementById('edit_answers_question_id').disabled = true;
      }else{
        document.getElementById('edit_answers_question_id').value='';
        document.getElementById('edit_answers_question_id').disabled = false;
        var cmpny=value.split('<$>');
        var url=base_url+'ctrl_answers/getquestions/'+cmpny[0];
        ajax(url,'edit_answers_qstndiv_id');
      }
   }
   function questiondesc(value){
      if(value==='' || value===null){
        document.getElementById('edit_answers_qdescription_id').value='';
        document.getElementById('edit_answers_qdescription_id').disabled = true;
      }else{
        document.getElementById('edit_answers_qdescription_id').value='';
        document.getElementById('edit_answers_qdescription_id').disabled = false;
        var qustn=value.split('<$>');
        var url=base_url+'ctrl_answers/getquestndesc/'+qustn[0];
        ajax(url,'edit_answers_qstndescdiv_id');
      }
   }
   function questionmandtry(value){
      var base_url =document.getElementById('base_url').value;
      if(value==='' || value===null){
        document.getElementById('edit_answers_questnmandtry_id').value='';
        document.getElementById('edit_answers_questnmandtry_id').disabled = true;
      }else{
        document.getElementById('edit_answers_questnmandtry_id').value='';
        document.getElementById('edit_answers_questnmandtry_id').disabled = false;
        var qustn=value.split('<$>');
        var url=base_url+'ctrl_answers/getquestnmandtry/'+qustn[0];
        ajax(url,'edit_answers_qstnmndtrydiv_id');
      }
   }
   function answer_uniqueness() {
      var base_url =document.getElementById('base_url').value;
      var id =document.getElementById('id1').value;
        var cmpnyid = document.getElementById('edit_answers_companyname_id').value.trim();
        var qstnid = document.getElementById('edit_answers_question_id').value.trim();
         var answer = document.getElementById('edit_answers_answer_id').value.trim().split('#').join('ha_sh_sm-bl').split('^').join('%5E').split('?').join('qu_stn_mrk_sm-bl').split('/').join('fr_wrd_sl_sh_sm-bl').split('<').join('%3C').split('>').join('%3E').split('[').join('%5B').split(']').join('%5D').split('{').join('%7B').split('}').join('%7D').split('`').join('%60').split('.').join('dt_sm-bl').split('(').join('opn_br_ckt_sm-bl').split(')').join('cl_sd_br_ckt_sm-bl').split('$').join('dlr_sm-bl');
        var url = base_url+'ctrl_answers/answer_uniqueness/' + cmpnyid + '/' + qstnid +'/' + answer + '/'+id;
        ajax(url, 'edit_uniq_answer_div_id');
    }
    function isNumberKey(evt) {
      var charCode = (evt.which) ? evt.which : event.keyCode;
      if(charCode > 31 && (charCode < 48 || charCode > 57))
         return false;
      else
         return true;
   }
   function cleardata() {
       document.getElementById('edit_answers_qdescription_id').value='';
       document.getElementById('edit_answers_qdescription_id').disabled = false;
       document.getElementById('edit_answers_questnmandtry_id').value='';
       document.getElementById('edit_answers_questnmandtry_id').disabled = false;
   }
  function edit_page() {
      validations();
      if(validations()) {
         document.getElementById('edit_answers_form').submit();
      }
   }