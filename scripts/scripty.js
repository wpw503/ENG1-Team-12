$(window).on('load', function(){

    $('#docbtn').on('click', function(){
        openInfo(event, 'docs')
    })

    $('#picbtn').on('click', function(){
        openInfo(event, 'pics')
    })

    $('#linkbtn').on('click', function(){
        openInfo(event, 'links')
    })

})

function openInfo(evt, tabname) {

    $('.section').hide();

    // Get all elements with class="tablinks" and remove the class "active"

    $('.navlinks').removeClass('active');
  
    // Show the current tab, and add an "active" class to the button that opened the tab

    var tempname = '#' + tabname;

    $(tempname).show();
    
    evt.currentTarget.className += " active";
}