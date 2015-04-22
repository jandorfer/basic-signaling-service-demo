# basic-signaling-service-demo
A basic pseudo-chatroom to demo the basic-signaling-service project functionality. 
A clojurescript client app will connect to a very basic server (essentially just the
basic-signaling-service functionality). The client allows you to send messages to 
topics, which anyone else connected to that topic will receive. You can subscribe
and leave topics as well. So, you're probably thinking its a chat room, which it is,
except there are no user names, and no authentication (the signaling service was not
intended to be a chat, just to introduce clients, so that is outside the intended 
scope.)

You will need the [basic-signaling-service](https://github.com/jandorfer/basic-signaling-service) 
itself available to run this demo project. As of this writing, it isn't in clojars, 
so you'll want to check out that project as well, then `lein install` it locally
before trying to run this demo project.

To run:
````
lein run
````
then view http://localhost:8080/
