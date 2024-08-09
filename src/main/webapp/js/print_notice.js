$(document).ready(function() {
    $.ajax({
        type: "GET",
        url: "print_sell_list",
        dataType: "json",
        success: function(sellResponse) {
            if (sellResponse.length === 0) {
                // 판매 목록이 없는 경우 처리
            } else {
                printSellList(sellResponse);
            }
            setTimeout(fetchRecommendSellList, 500);
        },
        error: function(xhr, status, error) {
            console.error("Error:", error);
        }
    });
})  

function fetchNoticeList() {
    $.ajax({
        type: "GET",
        url: "print_notice",
        dataType: "json",
        success: function(response) {
            if (response.length === 0) {
                // 판매 목록이 없는 경우 처리
            } else {
                printNotices(response);
            }
        },
        error: function(xhr, status, error) {
            console.error("Error:", error);
        }
    });
}

function fetchSellList() {
    $.ajax({
        type: "GET",
        url: "print_sell_list",
        dataType: "json",
        success: function(sellResponse) {
            if (sellResponse.length === 0) {
                // 판매 목록이 없는 경우 처리
            } else {
                printSellList(sellResponse);
            }
        },
        error: function(xhr, status, error) {
            console.error("Error:", error);
        }
    });
}

function fetchRecommendSellList(){	
		$.ajax({
			type: "GET",
			url: "print_recommend_sell_list",
			dataType: "json",
			success: function(RecommendSellResponse){
			
				if(RecommendSellResponse.length === 0){	
					//
				} else {		
					printRecommendSellList(RecommendSellResponse);	
				}
				setTimeout(fetchNoticeList, 500);
			},
			error: function(xhr, status, error){
				console.error("Error:", error);
			}		
		});			
}

function printNotices(notices) {
    var noticesHtml = "";
    $.each(notices, function(index, notice) {
    	if(index === 4){
    		noticesHtml += "<div><span>";
            noticesHtml += "<a href='main_notice_content?no=" + notice.no + "'>" + notice.title + "</a>";
            noticesHtml += "</span>" + "<span>" + notice.date.substring(0, 10) + "</span></div>";
    	} else {
            noticesHtml += "<div><span>";
            noticesHtml += "<a href='main_notice_content?no=" + notice.no + "'>" + notice.title + "</a>";
            noticesHtml += "</span>" + "<span>" + notice.date.substring(0, 10) + "</span></div>" +"<hr>";
        }
    });
    $(".notice_mainpage_box2 .notice_display").html(noticesHtml);
    $('.notice_mainpage_box2 a').hover(function() {
        $(this).css("text-decoration", "underline");
    },function() {
        $(this).css('text-decoration', 'none');
    })
}

function printSellList(boards) {
	var boardsHtml = "";
	boardsHtml += "<ul>";
	$.each(boards, function(index, board) {
		boardsHtml += "<li>" + "<div class='fam-img'>" + "<a href='car_list_content?no=";
		boardsHtml += board.no + "'>"
		boardsHtml += "<img src='upload_images/"+ board.file + "'>" + "</a></div>";
		boardsHtml += "<div class='fam-product1'><p class='p'>" + board.std + "</p>";
		boardsHtml += "<p>가격 <span>" + board.price + "</span> 원</p>";
		boardsHtml += "</div></li>";
	});
	boardsHtml += "</ul>";
	$(".fam-product").html(boardsHtml);
}

function printRecommendSellList(boards){

	var boardsHtml = "";
    boardsHtml += "<ul>";
    $.each(boards, function(index, board) {
        boardsHtml += "<li>" + "<div class='recom-img'><a href='car_list_content?no=";
        boardsHtml += board.no + "'><img src='upload_images/"+ board.file + "'></a></div>";
        boardsHtml += "<div class='recom-product1'><p class='p'>" + board.std + "</p>";
        boardsHtml += "<p>가격 <span>" + board.price + "</span> 원</p>";
        boardsHtml += "</div></li>";
    });
    boardsHtml += "</ul>";
    $(".recom-product").html(boardsHtml);

}