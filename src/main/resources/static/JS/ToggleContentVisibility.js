'use strict';

document.querySelectorAll('div[title="pill"]').forEach((element) => element.addEventListener("click", toggleContentVisibility));

function toggleContentVisibility() {
	
	let pillContent = document.getElementById(this.id + "c");
	
	pillContent.classList.toggle("d-none");

}

