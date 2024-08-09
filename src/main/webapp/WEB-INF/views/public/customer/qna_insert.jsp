<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>QnA 문의</title>
<script
   src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<link rel="icon" type="image/png" sizes="32x32"
   href="images/favicon-32x32.png">
</head>
<body>
   <jsp:include page="/WEB-INF/include/main_top.jsp" />

   <main class="qna container">
      <form method="post" action="qna_insert_ok"
         enctype="multipart/form-data">
         <div>
            <div class="qna_title">
               <h2 class="qury">QnA 문의</h2>
               <p>4sCar를 이용하시면서 불편한 점이나 건의사항 등 자유롭게 문의해주세요.</p>
            </div>
            <div class="qna_content">
               <hr class="line">
               <div class="basic-info">
                  <div class="tit">
                     <span>문의유형</span>
                  </div>
                  <div class="input-area">
                     <span> <select name="qna_board_type" id="sel" class="sele">
                           <option value="팔기">내차팔기</option>
                           <option value="사기">내차사기</option>
                           <option value="서비스">서비스</option>
                           <option value="칭찬접수">칭찬접수</option>
                           <option value="불편접수">불편접수</option>
                           <option value="오류신고">오류신고</option>
                           <option value="기타">기타</option>
                     </select> <span class="select2"></span>
                     </span>
                  </div>
               </div>
               <div class="basic-info">
                  <div class="tit">
                     <span>제목</span>
                  </div>
                  <div class="input-area">
                     <div class="input-title" style="width: 700px;">
                        <input type="text" title="제목" id="subject" maxlength="30"
                           name="qna_board_title" placeholder="제목을 입력해주세요" required>
                        <div class="t-txt" id="textLength">0/30</div>
                     </div>
                  </div>
               </div>
               <div class="basic-info">
                  <div class="tit">
                     <span>질문내용</span>
                  </div>
                  <div class="input-area">
                     <div class="txt-box">
                        <textarea class="tp01" name="qna_board_content" id="contentsLob"
                           title="의견입력" placeholder="질문 내용을 입력해주세요."  required></textarea>
                        <span class="txt-count">0/999</span>
                     </div>
                  </div>
               </div>
               <div class="basic-info">
                  <div class="tit">
                     <span>문의내용</span>
                  </div>
                  <div class="input-area">
                     <div class="file-box">
                        <div class="fb-top">
                           <div class="input-fi" id="qnaFileBox">
                              <label for="fb01" id="addFile1"> <span>첨부파일</span>
                              </label> <input type="file" id="fb01" multiple="multiple"
                                 name="qna_board_file">
                           </div>
                           <span class="file-txt"> 개인정보가 포함된 이미지(등록증, 면허증 등)는 등록하지
                              않도록 주의하시기 바랍니다. <br> <span class="file-type">(첨부가능
                                 : JPG, JPEG, PNG)</span>
                           </span>
                        </div>
                        <div class="fb-bottom"></div>
                     </div>
                  </div>
                  <hr class="line">
                  <div class="area-button">
                     <button type="reset" class="btn01">
                        <span>취소하기</span>
                     </button>
                     <button type="submit" class="btn01 blue">
                        <span>등록하기</span>
                     </button>
                  </div>
               </div>
            </div>
         </div>
      </form>
   </main>

   <jsp:include page="/WEB-INF/include/main_bottom.jsp" />
</body>
</html>