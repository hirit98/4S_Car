document.querySelector('.next-btn').addEventListener('click', function() {
    var currentPage = new URL(window.location.href).searchParams.get('notice_page');
    var nextPage = parseInt(currentPage) + 1;
    var newUrl = window.location.href.replace('notice_page=' + currentPage, 'notice_page=' + nextPage);
    window.location.href = newUrl;
});
// Previous 버튼 클릭 시
document.querySelector('.prev-btn').addEventListener('click', function() {
    var currentPage = parseInt(new URLSearchParams(window.location.search).get('notice_page'));
    var prevPage = currentPage - 1;
    if (prevPage >= 1) {
        window.location.href = 'notice_list?notice_page=' + prevPage;
    }
});