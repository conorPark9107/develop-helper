$(window).bind("pagehide", function (event) {
	if (event.originalEvent.persisted) {
	    console.log('호출');
		turnLoading();
	}
});

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
