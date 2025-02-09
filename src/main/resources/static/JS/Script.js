'use strict';

document.querySelectorAll('div[title="pill"]').forEach((element) => element.addEventListener("click", toggleContentVisibility));

const searchForm = document.getElementById('searchForm');

const showButton = document.getElementById('showButton');

const searchFields = document.getElementById('searchFields');

const nameInput = document.getElementById('nameInput');

const checkInput = document.getElementById('checkInput');

const startDate = document.getElementById('startDate');

const endDate = document.getElementById('endDate');

const resetButton = document.getElementById('resetButton');

// Toggle pill content visibility
function toggleContentVisibility() {
	
	const pillContent = document.getElementById(this.id + "c");
	
	pillContent.classList.toggle("d-none");
	
	document.getElementById(this.id).scrollIntoView();
}

// Check if end date > start date before submitting
searchForm.addEventListener("submit", function (event) {
    
	if (endDate.value <= startDate.value) {
		alert("Search Error: the Ending Date must be greater than the Starting Date");
        event.preventDefault();	    
	}
})


// Reset input fields
resetButton.addEventListener("click", function (event) {
	
	event.preventDefault();
	
	nameInput.value = null;
	
	checkInput.checked = false;
	
	startDate.value = '2024-01-01T00:00';
	
	endDate.value = '2026-01-01T00:00';
	
	searchForm.submit();
})

// Toggle search fields visibility
showButton.addEventListener("click", function (event) {
	
	event.preventDefault();

	searchFields.classList.toggle("d-none");
	
	showButton.innerText == "Hide" ? showButton.innerText = "Show" : showButton.innerText = "Hide"; 
})







