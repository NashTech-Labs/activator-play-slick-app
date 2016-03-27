$(document).ready(function() {
    $('#empDataTable').DataTable( {
        "ajax": {
            "url": "/list",
            "dataType": "json"
        },
         "columns": [
                    { "data": "name" },
                    { "data": "email" },
                    { "data": "dob" },
                    { "data": "position" },
                    { "data": "companyName" }
                ]
    } );
} );


$('#myModal').on('shown.bs.modal', function () {
  $('#myInput').focus()
});



$(document).on("submit", this.id, function(e) {
     e.preventDefault();
       var frm     = e.target.id;
       var frmData = $("#"+frm).serialize();
       console.log(frm);
       console.log(frmData);
        $.ajax({
            type: "POST",
            url: "/create", //process to mail
            data: frmData,
            success: function(msg){
               alert("Success");
            },
            error: function(){
                alert("failure");
            }
        });
    });
