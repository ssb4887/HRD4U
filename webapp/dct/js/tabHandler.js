
document.addEventListener("DOMContentLoaded", function() {

	window.addEventListener("DOMContentLoaded", function(){
		
		var urlHash = window.location.hash;
		if(urlHash) {
			var activeTab = document.querySelector('.tabs a[href="' + urlHash + '"]');
			
			var clickEvent = new MouseEvent('click', {
				bubbles: true,
				cancelable: true,
				view: window
			});
			
			activeTab.dispatchEvent(clickEvent);
			
		}

	})
});
	
function showTab(button, tabNumber) {
	const btns = document.querySelectorAll('.tablinks');
	btns.forEach(btn => {
		btn.classList.remove('active')
	})
	button.classList.add('active')
	
	const tabCount = document.querySelectorAll('.tabcontent').length;
	for(let i=0;i < tabCount;i++) {
		document.getElementById(`tab${i+1}`).classList.add('hidden');
	}
	document.getElementById(`tab${tabNumber}`).classList.remove('hidden');
}
	





