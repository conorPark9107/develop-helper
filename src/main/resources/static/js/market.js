$(document).ready(function() {
    $('#searchBtn').on('click', function(){
    });



    $('#categoryList > li').on('click', function(){
        var c = $(this).attr("value");
        $('#contentsArea img').remove();
        $('#secondContentsArea img').remove();
        $('.content-searchDetail').hide(500);
        $('.itemArea table tbody tr').remove();
        $('.itemArea').hide();

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
            $('#contentsArea').append('<img class="itemImage" src="' + url + '" value="' + uri +arr_show[i] + '" />');
        }
    });

    $('#contentsArea').on('click', '.itemImage' ,function(){
        var val = $(this).attr("value");
        $('#secondContentsArea img').remove();
        $('.content-searchDetail').hide();
        $('.itemArea table tbody tr').remove();
        $('.itemArea').hide();

        if("_" + val in itemTree) {
            var arr = itemTree["_" + val];
            for(var i = 0; i < arr.length; i++){
//                var url = "T8_" + arr[i] + ".png?quality=5";
//                $('#secondContentsArea').append('<a class="itemImage" href="https://render.albiononline.com/v1/item/' + url + '" />');
                var url = "/image/T8_" + arr[i] + ".png";
                $('#secondContentsArea').append('<img class="itemImage" src="' + url + '"  value="' + arr[i] + '" />');
            }
        } else {
            $('.itemArea table tbody tr').remove();
            $('.itemArea').hide();
            $('.itemImage').removeClass('selctedItem');
            $(this).addClass('selctedItem');

            showMarketData();
        }
    });

    $('#secondContentsArea').on('click', '.itemImage' ,function(){
        $('.itemArea table tbody tr').remove();
        $('.itemArea').hide();
        $('.itemImage').removeClass('selctedItem');
        $(this).addClass('selctedItem');

        showMarketData();
    });

    function showMarketData(){
         //            var city = $('input[name="cities"]:checked').val();
         //            if(city == undefined || city == null){
         //                alert('도시를 선택해주세요.');
         //                return;
         //            }
         //            console.log("도시 : " + city);

         var server = $('input[name="server"]:checked').val();
         var quality = $('input[name="quality"]:checked').val();
         var tier = $('#tier option:checked').val();
         var dotTier = $('#dotTier option:checked').val();
         var itemName = $('.selctedItem').attr('value');

         $.ajax({
             type : 'get',
             url : '/market/getPrice',
             async : true,
             dataType : 'json',
             data : {
                 server : server,
                 quality : quality,
                 tier : tier,
                 dotTier : dotTier,
                 itemName : itemName
             },
             beforeSend : function(){
                turnLoading();
             },
             success : function(result) {
                 console.log(result.length);
                 for(var i = 0; i < result.length; i++){
                    var city = result[i].city;
                    var sellMin = result[i].sell_price_min;
                    var sellMax = result[i].sell_price_max;
                    var buyMin = result[i].buy_price_min;
                    var buyMax = result[i].buy_price_max;

                    $('.itemArea table caption img').attr("src", "https://render.albiononline.com/v1/item/" + tier + "_" + itemName + dotTier + "?quality=" + quality);
                    $('.itemArea table tbody').append("<tr class='"+city+"'><tr>");
                    $('.itemArea table tbody .' + city).append("<td>" + sellMin.toLocaleString() +  "</td>");
                    $('.itemArea table tbody .' + city).append("<td>" + sellMax.toLocaleString() +  "</td>");
                    $('.itemArea table tbody .' + city).append("<td>" + buyMin.toLocaleString() +  "</td>");
                    $('.itemArea table tbody .' + city).append("<td>" + buyMax.toLocaleString() +  "</td>");
                    $('.itemArea table tbody').append("<tr class='space'><tr>");
                 }
                 $('.itemArea').fadeIn(500);
                 turnLoading();

                 $('.content-searchDetail').fadeIn(500);
             },
             error : function(request, status, error) {
                 console.log(error);
             }
         });


    }



});