$(document).ready(function() {
   
   $("#submit_btn").on("click",function (e) {
      if(!$("#keyword").val()){
         e.preventDefault();
         alert("검색어를 입력해주세요.");
         $("#keyword").focus();
      }
   })
})

function check() {
	if(form.name.value == ""){
		alert("필수정보를 입력해주세요.");
		form.name.focus();
		return false;
	}
	if(form.password.value == ""){
		alert("필수정보를 입력해주세요.");
		form.password.focus();
		return false;
	}
	if(form.email.value == ""){
		alert("필수정보를 입력해주세요.");
		form.email.focus();
		return false;
	}
	if(form.id.value == ""){
		alert("필수정보를 입력해주세요.");
		form.id.focus();
		return false;
	}
	if(form.passwordcheck.value == ""){
		alert("필수정보를 입력해주세요.");
		form.passwordcheck.focus();
		return false;
	}
	if(form.phone1.value == ""){
		alert("필수정보를 입력해주세요.");
		form.phone1.focus();
		return false;
	}
	if(form.phone2.value == ""){
		alert("필수정보를 입력해주세요.");
		form.phone2.focus();
		return false;
	}
	if(form.phone3.value == ""){
		alert("필수정보를 입력해주세요.");
		form.phone3.focus();
		return false;
	}
	if(form.file.value == ""){
		alert("필수정보를 입력해주세요.");
		form.file.focus();
		return false;
	}
} 