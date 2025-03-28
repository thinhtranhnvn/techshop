$(document).ready(function() {
   $(".btn-delete-brand").on("click", function(event) {
       event.preventDefault();
       var bool = window.confirm("Do you really want to delete the brand?");
       if (bool) window.location = this.href;
   });
   
   $(".btn-more-images").on("click", function(event) {
       var $input = $('<input type="text" class="form-control mt-2" name="image-url" />');
       $input.insertBefore(this);
   });
   
   $("#promotion").richText();
   $("#description").richText();
   $("#specification").richText();
});
