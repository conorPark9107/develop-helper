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

    $('#categoryList li').click(function(){
        var c = $(this).attr("value");
        $('#contentsArea img').remove();

        var category;
        var uri;
        var arr_show;
        // T4_SHOES_LEATHER_SET1.png?quality=5
        switch(c){
            case 'warrior' :
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

        ;
        for(var i = 0; i < arr_show.length; i++){
            var url = "/image/T8_" + uri + arr_show[i] + ".png";
            $('#contentsArea').append('<img class="itemImage" src="' + url + '"  alt=""/>');
        }
    });

    const plateCate = ["SET1", "SET2", "SET3", "ROYAL", "UNDEAD", "HELL", "KEEPER", "AVALON", "FEY"];
    const leatherCate = ["SET1", "SET2", "SET3", "ROYAL", "MORGANA", "HELL", "UNDEAD", "AVALON", "FEY"];
    const clothCate = ["SET1", "SET2", "SET3", "ROYAL", "KEEPER", "HELL", "MORGANA", "AVALON", "FEY"];
});


// <img class="itemImage" src="https://render.albiononline.com/v1/item/T4_SHOES_LEATHER_SET1@4.png?quality=5"  alt=""/>