$(document).ready(function() {
    $('#searchBtn').on('click', function(){
        var city = $('input[name="cities"]:checked').val();
        console.log("도시 : " + city);

        var quality = $('input[name="quality"]:checked').val();
        console.log("품질 : " + quality);

        var tier = $('#tier option:checked').val();
        var dotTier = $('#dotTier option:checked').val();
        console.log("티어 : " + tier + "." + dotTier);
    });

    $('#categoryList > li').on('click', function(){
        var c = $(this).attr("value");
        $('#contentsArea img').remove();
        $('#secondContentsArea img').remove();
        $('.content-searchDetail').hide();

        var uri = "";
        var arr_show;
        // T4_SHOES_LEATHER_SET1.png?quality=5
        switch(c){
            case 'warrior' :
                arr_show = weaponWarriorCate;
            break;
            case 'helmet' :
                 uri = 'HEAD_PLATE_';
                 arr_show = plateCate;
            break;
            case 'armor' :
                uri = 'ARMOR_PLATE_';
                arr_show = plateCate;
            break;
            case 'boots' :
                uri = 'SHOES_PLATE_';
                arr_show = plateCate;
            break;

            case 'hunter' :
                arr_show = weaponHunterCate;
            break;
            case 'hood' :
                uri = 'HEAD_LEATHER_';
                arr_show = leatherCate;
            break;
            case 'jacket' :
                uri = 'ARMOR_LEATHER_';
                arr_show = leatherCate;
            break;
            case 'shoes' :
                uri = 'SHOES_LEATHER_';
                arr_show = leatherCate;
            break;

            case 'mage' :
                arr_show = weaponMageCate;
            break;
            case 'cowl' :
            uri = 'HEAD_CLOTH_';
            arr_show = clothCate;
            break;
            case 'robe' :
            uri = 'ARMOR_CLOTH_';
            arr_show = clothCate;
            break;
            case 'sandals' :
            uri = 'SHOES_CLOTH_';
            arr_show = clothCate;
            break;

            case 'cape' :
            break;
            case 'bag' :
            break;
            case 'potion' :
            break;
            case 'food' :
            break;
        }


        for(var i = 0; i < arr_show.length; i++){
            var url = "/image/T8_" + uri + arr_show[i] + ".png";
            $('#contentsArea').append('<img class="itemImage" src="' + url + '" value="_' + arr_show[i] + '" />');
        }
    });

    $('#contentsArea').on('click', '.itemImage' ,function(){
        var val = $(this).attr("value");
        $('#secondContentsArea img').remove();

        if(val in itemTree) {
            var arr = itemTree[val];
            for(var i = 0; i < arr.length; i++){
//                var url = "T8_" + arr[i] + ".png?quality=5";
//                $('#secondContentsArea').append('<a class="itemImage" href="https://render.albiononline.com/v1/item/' + url + '" />');
                var url = "/image/T8_" + arr[i] + ".png";
                $('#secondContentsArea').append('<img class="itemImage" src="' + url + '"  value="' + arr[i] + '" />');
            }
        } else {
             $('.itemImage').removeClass('selctedItem');
             $(this).addClass('selctedItem');
             $('.content-searchDetail').fadeIn(500);
        }
    });

    $('#secondContentsArea').on('click', '.itemImage' ,function(){
        $('.itemImage').removeClass('selctedItem');
        $(this).addClass('selctedItem');
        $('.content-searchDetail').fadeIn(500);
    });



});