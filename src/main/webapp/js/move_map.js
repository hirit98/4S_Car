$(function() {
	const url = new URL(window.location);
	const urlPath = url.pathname;
	if (urlPath.includes('directions')) {
		const scrollTop = document.querySelector(".company_map").offsetTop;
		const headerTop = document.querySelector(".header_container").offsetHeight;

		window.scrollTo({
			top : scrollTop - headerTop,
			behavior : 'smooth'
		});
	}
});