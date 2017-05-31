$('#ExclusionConfirmationModal').on('show.bs.modal', function(event){
	
	var button = $(event.relatedTarget);
	var idExpense = button.data('id');
	var descriptionExpense = button.data('description');
	 var modal = $(this);
	 var form = modal.find('form');
	 var action = form.data('url-base');
	 
	 if(!action.endsWith('/')) {
		 action += '/';
	 }
	 
	 form.attr('action', action + idExpense);
	 
	 modal.find('.modal-body span').html('Are you sure you want to delete the expense <strong>' + descriptionExpense + '</strong>');
});


$(function() {
		$('[rel="tooltip"]').tooltip();
		$('.js-currency').maskMoney();
		
		$('.js-update-status').on('click', function(event) {
			event.preventDefault();
			
			var buttonReceive = $(event.currentTarget);
			var receiveUrl = buttonReceive.attr('href');
			
			var response = $.ajax({
				url: receiveUrl,
				type: 'PUT'
		});
			
			response.done(function(e) {
				var idExpense = buttonReceive.data('id');
				$('[data-role=' + idExpense + ']' ).html('<span class="label label-success">'+ e +'</span>');
				buttonReceive.hide();
			});
			
			response.fail(function(e) {
				console.log(e);
				alert('Error receiving expense');
			});
			
		});
});



