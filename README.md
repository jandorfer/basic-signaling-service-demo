# basic-signaling-service-demo
A basic pseudo-chatroom to demo the basic-signaling-service project functionality. 
A clojurescript client app will connect to a very basic server (essentially just the
basic-signaling-service functionality). The client allows you to send messages to 
topics, which anyone else connected to that topic will receive. You can subscribe
and leave topics as well. So, you're probably thinking its a chat room, which it is,
except there are no user names, and no authentication (the signaling service was not
intended to be a chat, just to introduce clients, so that is outside the intended 
scope.)

See also [basic-signaling-service](https://github.com/jandorfer/basic-signaling-service)

To run:
````
lein run
````
