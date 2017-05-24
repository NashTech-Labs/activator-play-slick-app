
var SUCCESS = 'success';
var ERROR = 'error';

var serverErrorMessage = 'Oops, something wrong :(';

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
         bootbox.confirm("Are you sure?", function(result) {
            if(result) {
                    $.ajax({
                     url: "/emp/delete",
                     type: "GET",
                     data: {empId: employeeId},
                     success:function(response){
                               if(response.status == SUCCESS) {
                                  showSuccessAlert(response.msg);
                                  tableEmp.row(currentRow.parents('tr') ).remove().draw();
                              } else {
                                  showErrorAlert(serverErrorMessage);
                              }
                        },
                     error: function(){
                          showErrorAlert(serverErrorMessage);
                       }
                  });
            } else {
               //
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
                             $('#empEditModal').modal('show');
                             $.each(response.data, function(key, value){
                                $('#empEditForm input[name="'+key+'"]').val(value);
                             });
                      },
                   error: function(){
                             showErrorAlert(serverErrorMessage);
                     }
                });
          });


$('#empModal').on('shown.bs.modal', function () {
  $('#empForm').trigger("reset");
});

// Show success alert message
var showSuccessAlert = function (message) {
   	$.toaster({ priority : 'success', title : 'Success', message : message});
}

// Show error alert message
var showErrorAlert = function (message) {
    $.toaster({ priority : 'danger', title : 'Error', message : message});
}

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
                          if(this.name == 'id') {
                             o[this.name] = parseInt(this.value) || 0;
                          } else {
                         o[this.name] = this.value || '';
                         }
                    }
               });
            return JSON.stringify(o);
        };

// Handling form submission for create new employee
      $('#empForm').on('submit', function(e){
         var formData = $("#empForm").serializeObject();
         var empTable = $('#empDataTable').dataTable();
          e.preventDefault();
           $.ajax({
                url: "/emp/create",
                type: "POST",
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                data: formData,
                success:function(response){
                   if(response.status == "success") {
                         $('#empModal').modal('hide');
                         var newEmp = jQuery.parseJSON(formData);
                         newEmp['id'] = response.data['id'];
                         empTable.fnAddData([newEmp]);
                         showSuccessAlert(response.msg);
                   } else {
                        $('#empModal').modal('hide');
                        showErrorAlert(response.msg);
                   }
                },
                error: function(){
                    $('#empModal').modal('hide');
                    showErrorAlert(serverErrorMessage);
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
                               $('#empDataTable').DataTable().ajax.reload();
                               showSuccessAlert(response.msg)
                         } else {
                            $('#empEditModal').modal('hide');
                            showErrorAlert(response.msg);
                         }
                      },
                      error: function(){
                          $('#empEditModal').modal('hide');
                          showErrorAlert(serverErrorMessage);
                      }

                  });
                  return false;
            });

});