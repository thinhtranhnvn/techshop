$(".btn-delete-cart").on("click", function(event) {
    event.preventDefault();
    var bool = window.confirm("Do you really want to delete the item?");
    if (bool) window.location = this.href;
});