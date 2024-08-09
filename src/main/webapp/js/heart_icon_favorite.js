$(function(){
	
    var $likeBtn =$('.icon.heart');

        $likeBtn.click(function(){
		location.reload(true);
        $likeBtn.toggleClass('active');

        if($likeBtn.hasClass('active')){   
		       
           $(this).find('img').attr({
              'src': 'https://cdn-icons-png.flaticon.com/512/803/803087.png',
               alt:'찜하기 완료'
                });
             
         }else{
           $(this).find('img').attr({

              'src': 'https://cdn-icons-png.flaticon.com/512/812/812327.png',
              alt:"찜하기"
           })
			
         }
     })




	
})


