# Messenger Simulator

A console App designed to send and recieve messages between users using the following technologies: Java, SQL, MYSQL Database.

The application starts with a login menu. The first user who will be signed in is admin with super admin privileges(username:admin, password:admin).After login a menu is displayed. Admin user has the following privileges: CRUD all messages, CRUD his own messages and CRUD users assigning a role to each created one.

There are four other roles. Each role has different responsibilities to the application. All users can send message to other users.
User A can view, edit and delete all the messages. User B can view and edit all messages. User C can view all messages. Finally, a simple user can CRUD only his own messages.

All messages are saved in a txt file by date. Every new message is appended to the same file. 

The application ends when user press in menus option 'exit'.
