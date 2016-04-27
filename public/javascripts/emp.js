
var SUCCESS = 'success';
var ERROR = 'error';

$(document).ready(function() {
    $('#empDataTable').DataTable( {
        "ajax": {
            "url": "/emp/list",
            "dataType": "json"
        },
         "columns": [
                    { "data": "name" },
                    { "data": "email" },
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

    // Delete employee event
    $("body").on( 'click', '.remove-button', function () {
       var currentRow = $(this);
       var employeeId = $(this).attr('id').trim();
        $.ajax({
              url: "/emp/delete",
              type: "GET",
              data: {empId: employeeId},
              success:function(response){
               var data = $.parseJSON(response);
                        if(data.status == SUCCESS) {
                           showSuccessAlert(data.msg);
                           tableEmp.row(currentRow.parents('tr') ).remove().draw();
                       } else {
                           showErrorAlert("Oops, something wrong :(");
                       }
                 },
              error: function(){
                        showErrorAlert("Oops, something wrong :(");
                }
           });
     });
     

     // Edit employee event
     $("body").on( 'click', '.edit-button', function () {
            var employeeId = $(this).attr('id').trim();
             $.ajax({
                   url: "/emp/edit",
                   type: "GET",
                   data: {empId: employeeId},
                   success:function(response){
                             var jsonData = $.parseJSON(response);
                             $('#empEditModal').modal('show');
                             $.each(jsonData.data, function(key, value){
                                $('#empEditForm input[name="'+key+'"]').val(value);
                             });
                      },
                   error: function(){
                             showErrorAlert("Oops, something wrong :(");
                     }
                });
          });


$('#myModal').on('shown.bs.modal', function () {
  $('#myInput').focus()
});

// Show success alert message
var showSuccessAlert = function (message) {
    $('#successAlert').toggleClass('noneDisplay in');
    $('#successAlert #alertContent').html(message);
}

// Show error alert message
var showErrorAlert = function (message) {
    $('#errorAlert').toggleClass('noneDisplay in');
    $('#errorAlert #alertContent').html(message);
}

// Events on close of alert
 $('.close').click(function () {
      $(this).parent().toggleClass('in noneDisplay');
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

// Handling form submission for create new employee
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
                        $('#empModal').modal('hide');
                        showErrorAlert("Oops, something wrong :(");
                   }
                },
                error: function(){
                    $('#empModal').modal('hide');
                    showErrorAlert("Oops, something wrong :(");
                }

            });
            return false;
      });

// Handling form submission for update employee
$('#empEditForm').on('submit', function(e){
               var formData = $("#empEditForm").serializeObject();
                e.preventDefault();
                 $.ajax({
                      url: "/emp/update",
                      type: "POST",
                      contentType: "application/json; charset=utf-8",
                      dataType: "json",
                      data: formData,
                      success:function(response){
                         if(response.status == SUCCESS) {
                               $('#empEditModal').modal('hide');
                               showSuccessAlert(response.msg);
                         } else {
                            $('#empEditModal').modal('hide');
                            showErrorAlert(response.message);
                         }
                      },
                      error: function(){
                          $('#empEditModal').modal('hide');
                          showErrorAlert("Oops, something wrong :(");
                      }

                  });
                  return false;
            });

});