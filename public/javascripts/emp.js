$(document).ready(function() {
    $('#empDataTable').DataTable( {
        "ajax": {
            "url": "/emp/list",
            "dataType": "json"
        },
         "columns": [
                    { "data": "name" },
                    { "data": "email" },
                    { "data": "dob" },
                    { "data": "position" },
                    { "data": "companyName" },
                    { data: "id" ,
                     "render": function ( data) {
                                  return '<i id=" ' + data +' " class="edit-button glyphicon glyphicon-edit cursorPointer" ></i>';
                                }
                            },
                     { data: "id" ,
                        "render": function ( data ) {
                                   return '<i id=" ' + data +' " class="remove-button glyphicon glyphicon-trash cursorPointer"></i>';
                               }
                     }

                ]
    } );

    var tableEmp = $('#empDataTable').DataTable();

    // Delete employee
    $("body").on( 'click', '.remove-button', function () {
       var currentRow = $(this);
       var employeeId = $(this).attr('id').trim();
        $.ajax({
              url: "/emp/delete",
              type: "GET",
              data: {empId: employeeId},
              success:function(){
                       tableEmp.row(currentRow.parents('tr') ).remove().draw();
                 },
              error: function(){
                        alert("Error");
                }
           });
     });

     // Edit employee
     $("body").on( 'click', '.edit-button', function () {
            var employeeId = $(this).attr('id').trim();
             $.ajax({
                   url: "/emp/edit",
                   type: "GET",
                   data: {empId: employeeId},
                   success:function(data){
                   console.log(data);
                             $('#empEditModal').modal('show');
                             $.each(data, function(name, val){
                                $('#empEditForm input[name="'+name+'"]').val(val);
                             });
                      },
                   error: function(){
                             alert("Error");
                     }
                });
          });

} );

$('#myModal').on('shown.bs.modal', function () {
  $('#myInput').focus()
});

$(document).ready(function() {

// Show success alert message
var showSuccessAlert = function (message) {
    $('#le-alert').toggleClass('noneDisplay');
    $('#le-alert').addClass('in');
    $('#alertContent').html(message);
}

// Event on close of alert
 $('.close').click(function () {
      $(this).parent().removeClass('in');
      $('#le-alert').toggleClass('noneDisplay');
    });

// Convert form data in JSON format
$.fn.serializeObject = function() {
           var o = {};
           var a = this.serializeArray();
           $.each(a, function() {
                    if (o[this.name] !== undefined) {
                        if (!o[this.name].push) {
                            o[this.name] = [o[this.name]];
                        }
                        o[this.name].push(this.value || '');
                    } else {
                         o[this.name] = this.value || '';
                    }
               });
            return JSON.stringify(o);
        };

// Handling employee form submission
      $('#empForm').on('submit', function(e){
         var formData = $("#empForm").serializeObject();
          e.preventDefault();
           $.ajax({
                url: "/emp/create",
                type: "POST",
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                data: formData,
                success:function(data){
                   if(data.status == "success") {
                         $('#empModal').modal('hide');
                         showSuccessAlert(data.message);
                   } else {

                   }
                },
                error: function(){
                    console.log("Booo, something wrong :(");
                }

            });
            return false;
      });

});