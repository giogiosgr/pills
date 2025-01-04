'use strict';

const form = document.getElementById('searchForm');

const startDate = document.getElementById('startDate');

const endDate = document.getElementById('endDate');

// check if end date > start date before submitting
form.addEventListener("submit", function (event) {
    
	if (endDate.value <= startDate.value) {
		alert("Search Error: the Ending Date must be greater than the Starting Date");
        event.preventDefault();	    
	}
})