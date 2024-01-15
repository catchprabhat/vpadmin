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
				<h3 class="box-title">Words</h3>
			</div>
			<!-- /.box-header -->
			<div class="box-body">
				<table id="words_table"
					class="table table-bordered table-striped" style="cursor: pointer">
					<thead>
						<tr>
							<th style="display: none;">Id</th>
							<th>Word group</th>
							<th>Name</th>
							<th>Hint</th>
							<th>Type</th>
							<th>Meaning</th>
							<th>Sentence</th>
							<th>Pronounication</th>
							<th>Active</th>
							<th style="display: none;">GroupId</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${words}" var="word">
							<tr>
								<td style="display: none;">${word.id}</td>
								<td>${word.groupName}</td>
								<td>${word.name}</td>
								<td>${word.hint}</td>
								<td>${word.type}</td>
								<td>${word.meaning}</td>
								<td>${word.sentence}</td>
								<td>${word.pronunication}</td>
								<td><c:out value="${word.active == true ? 'Yes': 'No'}"/></td>
								<td style="display: none;">${word.groupId}</td>
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
	<div class="col-md-4" id="form_div">

		<div class="box box-default">
			<div class="box-header">
				<h3 class="box-title">Word information</h3>
			</div>
			<!-- /.box-header -->
			<!-- form start -->
			<form role="form" id="word_form" action="word/save" method="post">
			<input type="hidden" id="id_hidden" />
				<div class="box-body">
					<div class="form-group">
						<label>Group</label> 
						<br>
						<select class="form-control" name="wordGroup" id="groups_select" required>
							<option value="-1" selected="selected" disabled="disabled">-- Select group --</option>
							<c:forEach items="${wordGroups}" var="wordGroup">
								<option value="${wordGroup.id}">${wordGroup.name}</option>
							</c:forEach>
						</select>
					</div>
					<div class="form-group">
							<label for="name">Name</label> <input type="text"
								class="form-control" id="name" autocomplete="off" placeholder="Word"
								required="required"  maxlength="45">
					</div>
					<div class="form-group">
							<label for="hint">Hint</label> <input type="text"
								class="form-control" id="hint" autocomplete="off" placeholder="Hint"
								required="required" maxlength="100">
					</div>
					<div class="form-group">
						<label>Type</label> 
						<br>
						<select class="form-control" name="type" id="type" required>
								<option value="Verb" selected="selected">Verb</option>
								<option value="Noun">Noun</option>
								<option value="Adjective">Adjective</option>
								<option value="Verb,Noun" selected="selected">Verb,Noun</option>
								<option value="Noun,Adjective">Noun,Adjective</option>
								<option value="Verb,Adjective">Verb,Adjective</option>
						</select>
					</div>
					<div class="form-group">
							<label for="meaning">Meaning</label> <input type="text"
								class="form-control" id="meaning" autocomplete="off" placeholder="Meaning"
								required="required" maxlength="300">
					</div>
					<div class="form-group">
							<label for="sentence">Sentence</label> <input type="text"
								class="form-control" id="sentence" autocomplete="off" placeholder="Sentence"
								required="required" maxlength="400">
					</div>
					<div class="form-group">
							<label for="sentence">Pronounication</label> <input type="text"
								class="form-control" id="pronounce" autocomplete="off" placeholder="pronounce"
								required="required" maxlength="400">
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
							data-loading-text="Saving..." id="save_word_btn">
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

		var words_table = createDataTableInstance('words_table');
		attach_word_save_btn_handler();

		$('#words_table tbody').on('click', 'tr', function() {

			var iPos = words_table.fnGetPosition(this);
			var aData = words_table.fnGetData(iPos);

			$("#id_hidden").val((aData && aData[0]) ? aData[0] : null);
			$("#groupName").val((aData && aData[1]) ? aData[1] : null);
			$("#name").val((aData && aData[2]) ? aData[2] : null);
			$("#hint").val((aData && aData[3]) ? aData[3] : null);
			$("#type").val((aData && aData[4]) ? aData[4] : null);
			$("#meaning").val((aData && aData[5]) ? aData[5] : null);
			$("#sentence").val((aData && aData[6]) ? aData[6] : null);
			$("#pronounce").val((aData && aData[7]) ? aData[7] : null);
			if (aData && (aData[8] == 'Yes')) {
				$('#active').prop('checked', true);
			} else {
				$('#active').prop('checked', false);
			}
			$("#groups_select").val((aData && aData[9]) ? aData[9] : null);
		});

		$('#word_form').on('reset', function(){
	        setTimeout(function(){
	        	$("#id_hidden").val('');
	        });
	    });

	});

	function attach_word_save_btn_handler() {

		$("#word_form").on("submit", function(e) {
			
			e.preventDefault();
			$("#word_form :input").attr("disabled", true);

			var active = "0";
			if ($('#active').is(":checked")) {
				active = "1";
			}

			var dataString = {
				"id" : ($("#id_hidden").val().length != 0) ? $("#id_hidden").val() : null,
				"name" : $("#name").val(),
				"hint" : $("#hint").val(),
				"type" : $("#type").val(),
				"meaning" : $("#meaning").val(),
				"sentence" : $("#sentence").val(),
				"pronunication" : $("#pronounce").val(),
				"active" : active,
				"groupId" : $("#groups_select").val()
			};

			console.log('dataString=' + JSON.stringify(dataString));

			$("#save_word_btn").button('loading');

			$.ajax({
				type : 'post',
				url : $(this).attr("action"),
				data : dataString,
				beforeSend : function() {

				},
				complete : function() {
					$("#word_form :input").attr("disabled", false);
				},
				success : function(result) {
					console.log("result.response_code=" + result.response_code);
					$('#word_form')[0].reset();
					//Reset once submission is done successfully.
					$("#save_word_btn").button('reset');
					showMessage("Word details updated successfully");
					loadControllerMethod("word/listView");
				}
			});
		});
	}
</script>

</body>