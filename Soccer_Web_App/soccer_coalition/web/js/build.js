function initPageContent() {
    if (window.name === "") {
        window.name = "home";
    }
    getPageContent(window.name);
}
function getPageContent(page) {
    // clear previous active item
    var main_nav_items = document.getElementById("main-nav").childNodes;
    for (var i = 0; i < main_nav_items.length; i++) {
            main_nav_items[i].className = "nav-item";
    }
    
    window.name = page;
	console.log(page);
    
    
        
    // a disgusting hack -- ensures the page is changed to account when login is successful
    if (document.getElementById(page + "-nav") === null) {
        page = "account";
    }
    // set new active item
    document.getElementById(page + "-nav").className = "nav-item active";
    // load page specific css stylesheet
    document.getElementById("page-css").href = "/soccer/css/" + page + ".css";
    // load main content of page
    $("#page-content").load("/soccer/jsp/" + page + ".jsp");
}