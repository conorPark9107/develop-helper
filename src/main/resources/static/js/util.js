$(document).ready(function() {
    $('.container-contents-button').on('click', function(){
        turnLoading();
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
	console.log(start);
    console.log(end);
    console.log('-----------------')
	const seconds = Math.floor((end.getTime() - start.getTime()) / 1000);
	if (seconds < 60) return '방금 전';

	const minutes = seconds / 60;
	if (minutes < 60) return `${Math.floor(minutes)}분 전`;

	const hours = minutes / 60;
	if (hours < 24) return `${Math.floor(hours)}시간 전`;

	const days = hours / 24;
	if (days <= 90) return `${Math.floor(days)}일 전`;

	if(days > 90) return '직접 입력 바랍니다.';

	return `${start.toLocaleDateString()}`;
};

// alert 메세지
function showAlert(msg){
    $.confirm({
        theme: 'supervan',
        title: '',
        content: msg,
        buttons: {
            '네': function () {}
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
