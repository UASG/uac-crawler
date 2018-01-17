
 // create smartbutton
$('nav').before('<div id="smartbutton" class="smart"></div>');
$('#smartbutton').append('<div class="buttonline"></div>');
$('#smartbutton').append('<div class="buttonline"></div>');
$('#smartbutton').append('<div class="buttonline"></div>');

// add click listener
$('#smartbutton').click(function(event) 
{
  $('nav').animate({height:'toggle'},200);
});


  $(document).ready(function()
 {
 $(".smart").click(function()
 {
 
 $(".logoMenu").css("height", "330px");
 });
 
 });
 
