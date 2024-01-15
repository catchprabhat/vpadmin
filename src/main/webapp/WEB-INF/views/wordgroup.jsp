<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
</head>
<body>
<div class="row">
	<div class="col-md-8">
		<div class="box box-default">
			<div class="box-header">
				<h3 class="box-title">Word group</h3>
			</div>
			<!-- /.box-header -->
			<div class="box-body">
				<table id="wordgroup_table"
					class="table table-bordered table-striped" style="cursor: pointer">
					<thead>
						<tr>
							<th style="display: none;">Id</th>
							<th>Name</th>
							<th>Active</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${wordGroups}" var="wordGroup">
							<tr>
								<td style="display: none;">${wordGroup.id}</td>
								<td>${wordGroup.name}</td>
								<td><c:out value="${wordGroup.active == true ? 'Yes': 'No'}"/></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<!-- /.box-body -->
		</div>
		<!-- /.box -->
	</div>

	<!-- edit form -->
	<div class="col-md-4">

		<div class="box box-default">
			<div class="box-header">
				<h3 class="box-title">Group information</h3>
			</div>
			<!-- /.box-header -->
			<!-- form start -->
			<form role="form" id="wordGroup_form" action="wordGroup/save" method="post">
			<input type="hidden" id="id_hidden" />
				<div class="box-body">
					<div class="form-group">
							<label for="name">Name :</label> <input type="text"
								class="form-control" id="name" autocomplete="off" placeholder="WordGroup name"
								required="required"  maxlength="50">
					</div>
					<div class="checkbox">
							<label> <input type="checkbox" id="active"
								checked="checked"> Active
							</label>
					</div>

					<div class="modal-footer clearfix">
						<button type=reset class="btn btn-danger pull-left">
							<i class="fa fa-times-circle"></i> Clear
						</button>
						<button type="submit" class="btn btn-primary pull-right"
							data-loading-text="Saving..." id="save_sub_btn">
							<i class="fa fa-save"></i> Save
						</button>
					</div>
				</div>
				<!-- /.box-body -->
			</form>
		</div>
		<!-- /.box -->

	</div>
	<!-- edit form -->


</div>


<!-- page script -->
<script type="text/javascript">
	$(function() {

		var wordgroup_table = createDataTableInstance('wordgroup_table');

		$('#wordgroup_table tbody').on('click', 'tr', function() {

			$("#wordGroup_form :input").attr("disabled", true);
			
			var iPos = wordgroup_table.fnGetPosition(this);
			var aData = wordgroup_table.fnGetData(iPos);

			$("#id_hidden").val((aData && aData[0]) ? aData[0] : null);
			$("#name").val((aData && aData[1]) ? aData[1] : null);

			if (aData && (aData[2] == 'Yes')) {
				$('#active').prop('checked', true);
			} else {
				$('#active').prop('checked', false);
			}
			$("#wordGroup_form :input").attr("disabled", false);
		});

		attach_sub_save_btn_handler();
		
		$('#wordGroup_form').on('reset', function(){
	        setTimeout(function(){
	        	$("#id_hidden").val('');
	        });
	    });

	});

	function attach_sub_save_btn_handler() {

		$("#wordGroup_form").on("submit", function(e) {
			e.preventDefault();
			$("#wordGroup_form :input").attr("disabled", true);
			var active = "0";
			if ($('#active').is(":checked")) {
				active = "1";
			}

			var dataString = {
				"id" : ($("#id_hidden").val().length != 0) ? $("#id_hidden").val() : null,
				"name" : $("#name").val(),
				"active" : active
			};

			$("#save_sub_btn").button('loading');

			$.ajax({
				type : 'post',
				url : $(this).attr("action"),
				data : dataString,
				beforeSend : function() {

				},
				complete : function() {
					$("#wordGroup_form :input").attr("disabled", false);
				},
				success : function(result) {
					console.log("result.response_code=" + result.response_code);
					$('#wordGroup_form')[0].reset();
					//Reset once submission is done successfully.
					$("#save_sub_btn").button('reset');
					showMessage("Word group details updated successfully");
					loadControllerMethod("wordGroup/listView");
				}
			});
		});
	}
</script>

</body>