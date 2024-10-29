$(window).bind("pagehide", function (event) {
	if (event.originalEvent.persisted) {
	    var load = $('#loading').css('display');
	    if(load == 'block'){
	        turnLoading();
	    }
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
