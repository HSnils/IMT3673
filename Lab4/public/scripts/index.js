
(function(){

	//Gets elements
	$signinButton = $('#sign_in');

	//click event listener
	$signinButton.click(function(){
		firebase.auth().signInAnonymously();
	});

	//Sends user to feed if logged inn
	firebase.auth().onAuthStateChanged(firebaseUser => {
		if (firebaseUser) {
			document.location.href = "register.html";
		}else {
			console.log('not logged inn');
		}
	});

	//Auth listener
	firebase.auth().onAuthStateChanged(firebaseUser => {
		console.log(firebaseUser);
	});

})();