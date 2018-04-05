// adds message to the chatMessages div
function addUser(user) {
	//gets username
	$loggedInUser = firebase.auth().currentUser.displayName;

	//does not show a friend slot for user
	if(user.username == $loggedInUser){
		$showUser = $([
			'<li class="collection-item" id="'+user.username+'">'+user.username+'<span class="hidden usersMessages"></span></li>'
		].join());
	}else{
		$showUser = $([
		''
		].join());
	}

	$('#friendsCollectionBox').append($showUser);

}

(function(){

	// Runs when user adds to database
	var chechForUpdates = function() {
		
		firebase.database().ref().child('users').orderByChild('users').on('child_added', function(snapshot) {
			$loggedInUser = firebase.auth().currentUser.displayName;
			$user = snapshot.val();
			$('#loadingMessages').hide();

			if($user.username != $loggedInUser){
				
				//gets username
				//
				$showUserStart = $(['<li class="collection-item" id="'+$user.username+'">'+$user.username+'<div class="usersMessages"><span class="hidden usersMessage">'
					].join());
				$('#friendsCollectionBox').append($showUserStart);

				firebase.database().ref().child('messages').on('child_added', function(snapshot) {
					$message = snapshot.val();
					console.log($user.username);
					if($user.username == $message.username){
						$('#friendsCollectionBox').append('- '+$message.message+'<br>');
					}
				});

				$showUserEnd = $(['</span></div></li>'
					].join());

				$('#friendsCollectionBox').append($showUserEnd);
			}
		});
	}

	//Loogs for updats to the database
	chechForUpdates();

}());