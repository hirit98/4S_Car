timer();
var current=0;
var $interval;

function timer(){
  var $interval=setInterval(function(){slide()},3000);                        
}

function slide(){
  $(".slide").animate({marginLeft:"-=285px"},5000,function(){
     $(".slide").append( $(".slide").children("li").eq(0));
    $(this).css({"marginLeft":"0px"});
   
  });
  current++;
  if(current==5)current=0;
}

$(function(){

    $("#scroll_top").click(function(){
        $("html").animate({ scrollTop: 0 });
    })
        
});

$(function(){

	if($("div").hasClass("login_page")){
		$("div.inner").remove();
	}
	if($("main").hasClass("main_sign_up")){
		$("div.inner").remove();
	}
		
	
	var sc;
	
    $(window).scroll(function(){ 

            sc = $("html").scrollTop(); 
            
      		if(sc > 300){
            	$(".service").animate({marginTop :"-90px", opacity: 1});
            }
            	
            if(sc > 0){
            	$(".inner").fadeIn(300);
            	$(".scroll_top").fadeIn(300);
            }else{
            	$(".inner").fadeOut(300);
            	$(".scroll_top").fadeOut(300);
            }
       })
});
   
$(document).ready(function() {

    $.ajax({
        type: "POST",
        url: "check_login_status",
        success: function(response) {
            if (response.loggedIn == 1) {
            
                // 유저 로그인 상태일 경우
                $('.login_main').hide(); // 로그인 버튼 숨기기
                $('#admin_btn').hide(); // 관리자 로그인 버튼 숨기기
                $('.logout').show();
                $('#user_name').html('<a href="mypage">' + response.username + '</a> 님');
                $('#user_name').show();
                
                // 사용자 번호도 함께 받아오기
                var userno = response.userno;
            } else if(response.loggedIn == 0){
            
            	// 관리자 로그인 상태일 경우
                $('.login_main').hide();    // 로그인 버튼 숨기기
                $('#admin_btn').hide();     // 관리자 로그인 버튼 숨기기
                $('#admin_page_btn').show(); // 관리자 페이지 버튼 보이기
                $('.logout').show();
                $('#user_name').html('<a href="admin_main">' + response.adminname + '</a> 님');
                $('#user_name').show();
                
                // 사용자 번호도 함께 받아오기
                var adminno = response.adminno;
                
                $('#my_page').removeAttr("onclick");
            	$('#my_page').click(function() {
            		alert("관리자로 로그인했습니다!!");
            	})
            	$('#car_sell_btn').removeAttr("onclick");
            	$('#car_sell_btn').click(function() {
            		alert("관리자로 로그인했습니다!!");
	            })
	            $('.lnb-item #login_first').removeAttr("href");
	            $('.lnb-item #login_first').mouseover(function() {
            		$('.lnb-item #login_first').css("cursor", "pointer");
            	})
            	$('.lnb-item #login_first').click(function() {
            		alert("관리자로 로그인했습니다!!");
            	})
            } else {
            	
            	$('#admin_page_btn').hide();
                $('.logout').hide();
                $('#user_name').hide();
                $('.login_main').show();
                $('#admin_btn').show();
            	
            	$('#my_page').removeAttr("onclick");
            	$('#my_page').click(function() {
            		alert("로그인 후 이용하실 수 있습니다.");
            		location.href='user_login';
            	})
            	$('#car_sell_btn').removeAttr("onclick");
            	$('#car_sell_btn').click(function() {
            		alert("로그인 후 이용하실 수 있습니다.");
            		location.href='user_login';
	            })
	            $('.lnb-item #login_first').removeAttr("href");
	            $('.lnb-item #login_first').mouseover(function() {
            		$('.lnb-item #login_first').css("cursor", "pointer");
            	})
            	$('.lnb-item #login_first').click(function() {
            		alert("로그인 후 이용하실 수 있습니다.");
            		location.href='user_login';
            	})
            	
            	
            	return false;
            }
        }
    });
    
    
})

var a = setInterval("test()",3000); 


function test(){

    $(".main_visual ul:first").stop().animate({ marginLeft:"-100%"},function(){ //동작, 이미지 슬라이드 과정을 볼 수 있습니다.

        $(".main_visual ul:first li:first").appendTo(".main_visual ul:first"); //조작
        $(".main_visual ul:first").css({ marginLeft: 0 }); // 조작

    });

};


// 로그아웃 버튼 클릭 시
$(document).on('click', '#logout_btn', function(e) {
    e.preventDefault(); // 기본 동작 중단

    $.ajax({
        type: "GET",
        url: "logout", // 로그아웃 요청을 보낼 URL
        success: function(response) {
        	alert('로그아웃 했습니다.');
        	location.href='main';
        }
	});
});