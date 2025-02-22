$(document).ready(function() {
   $(".btn-delete").on("click", function(event) {
       event.preventDefault();
       var bool = window.confirm("Do you really want to delete the brand?");
       if (bool) window.location = this.href;
   }); 
});