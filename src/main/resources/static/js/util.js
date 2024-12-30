$(document).ready(function() {
    $('.container-contents-button').on('click', function(){
        turnLoading();
    });

    $('.switchBtn').on('click', function(){
        localStorage.removeItem("mode");

        if(document.body.dataset.theme === 'light-mode'){
            document.body.dataset.theme = 'dark-mode';
            localStorage.setItem("mode", "dark-mode");

        } else{
            document.body.dataset.theme = 'light-mode';
            localStorage.setItem("mode", "light-mode");
        }
    });

});

$(window).bind("pagehide", function (event) {
	if (event.originalEvent.persisted) {
	    var load = $('#loading').css('display');
	    if(load == 'block'){
	        turnLoading();
	    }
	}
});

// 시간 구하기.
const getPerTime = (date) => {
	const start = new Date(date);
	const now = new Date();
	const end = new Date(now.getTime() + (now.getTimezoneOffset() * 60 * 1000));
	const seconds = Math.floor((end.getTime() - start.getTime()) / 1000);
	if (seconds < 60) return '방금 전';

	const minutes = seconds / 60;
	if (minutes < 60) return `${Math.floor(minutes)}분 전`;

	const hours = minutes / 60;
	if (hours < 24) return `${Math.floor(hours)}시간 전`;

	const days = hours / 24;
	if (days <= 90) return `${Math.floor(days)}일 전`;

	if(days > 90) return '오래 전 데이터.';

	return `${start.toLocaleDateString()}`;
};

// alert 메세지
function showAlert(msg){
    $.confirm({
        theme: 'supervan',
        title: '',
        content: msg,
        buttons: {
            '네': function (e) {}
        }
    });

}

function turnLoading(){
    var load = $('#loading').css('display');
    if(load == 'none'){
        $('#blackArea').show();
        $('#loading').css('display', 'block');
    }else{
        $('#blackArea').hide();
        $('#loading').css('display', 'none');
    }
}
