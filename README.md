Pusher Java classes for Google App Engine
=========================================

This Java classes can be used to communicate very easily with the Pusher REST API (http://www.pusherapp.com) from any Google App engine Web application.

Get Started
-----------
Use the PushApi as seen in ch.mbae.pusher.PushApi.

 
	PushApi api = new PushImpl();

	api.setGAECredentials(pusherApplicationId, pusherApplicationKey, pusherApplicationSecret);

	then call the following, where all arguments are strings.

	PusherResponse resp = api.pushEvent(  channelName,  eventName,  jsonData,  socketId) ;

	The implementation caches your channels, therefore when you have finished, eg a disconnect happens, then

	api.disposeOfChannel(channelName);

	
That's it.

	
License
-------
Copyright 2010, Stephan Scheuermann. Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
