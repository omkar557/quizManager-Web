$(document).ready(function() {
    $(".form1").hide();

    $('#first').show();
    $('#first #final').click(function() {
        $('.form1').hide();
        $('#second').fadeIn(300);
        return false;
    });
    $('#second #final').click(function() {
        $('.form1').hide();
        $('#third').fadeIn(300);
        return false;
    });
    $('#third #final').click(function() {
        $('.form1').hide();
        $('#fourth').fadeIn(300);
        return false;
    });
    $('#fourth #final').click(function() {
        $('.form1').hide();
        $('#fifth').fadeIn(300);
        return false;
    });
    $('#fifth #done').click(function() {
        $('.form1').hide();
        window.location.href = ''
        return false;
    });


})