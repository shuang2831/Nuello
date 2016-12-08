# Nuello Android Documentation 

Nuello is a brand new social media app that will allow users to check in on their close friends’ 
emotional state, availability, and current interests. 
In this busy and ever changing generation of millennials, Nuello will provide the ability to keep track 
of any changes in a person’s situation, as well as allow users to set their own availability, 
emotional state, and current interests. With that information avaibale, new conversations will be easier to start, 
and invitations will be easier to plan. As a result, Users will find new ways to say hello! Hence the name, Nuello!

## Table of Contents

Overview    
Requirements/Prerequisites  
Features   
Architecture    
UI/UX Design    
Things to know  
Future Enhancements/Features    

## Overview

Current social media focuses on spreading information, personal or not, to a large number of connected users. Whether it be a tweet, or status update, users are able to shout out anything they want to their peers. However, this increased connection and extroversion often trivializes a person’s emotional status due to the common use of hyperbole. With an app like Nuello, users will be able to pay attention to these factors, and can complement current social media by adding a genuine exchange of emotions to a close circle of friends.

Nuello is written compeltely in Java for the Android Framework. It makes use of a few different libraries as well as
a good amount of its own code in order to set the logic for the app. Its backend is contained in a MySQL database that
is accessed and queued through php calls which are included in this repo. These backend files are all contained on a 
server.

As a social media app it uses a variety of network calls in order to send and retrieve data from the database. These
network calls include ways to login, register an account, retrieve friend data, update profile info, and more.

## Requirements/Prerequisites

Hardware:
* A mobile device with Android on it
* A machine that can run a server

Software:
* An IDE that can program Java/Android (e.g. Android Studio)
* The ability to run a server (For now, WAMPServer64)
* Android SDK (min: 6.0)

Libraries/Packages:
* Volley (min: 1.0.0)
* Jackson (min: 2.7.0)
* Android Support (24.2.1)

## Features
* __Login/Register__: Upon first starting up the App, users should be able to log in with a username and password in order to gain access to their account. If they do not have an account, there should be the ability to register for on directly on the login screen. The app should also remember if a user is already logged in and skip this step when able.
* __Check Friends__: The very first screen displayed to the user should be a list of all their friends, their moods, availabilities, and a profile picture. This list should be searchable and be ordered by the last updated entry.
* __Change Profile Info__: There should be a profile screen that can be easily accessed (right now it’s with a swipe) that allows users to edit their mood, status, and “Current Interests” at ease.
* __Add/Confirm Friends__: Users should be able to easily access a view that lets them send friend requests via usernames, as well as confirm pending friend requests.
* __Reactive UI Color__: Depending on the avaibility of the user, the colors of various UI elements such as the toolbar will change to that avaibility's color to match.
* __Search__: Users should be able to search for specific friends in the main list.

## Architecture

The architecture of Nuello makes use of a couple activities and a series of different fragments. 

### Activities

1. LoginActivity: This activity handles the act of displaying a login screen as well as logging a user into the app with their username and password 
upon the app being opened. Once a user logs in, the app will store the user's id in sharedPreferences so that the next time the app is opened, 
this activity starts MainActivity automatically.

2. RegisterActivity: This activity can be started from LoginActivity by clicking on the register button/text. This activity allows new users to enter in
a proposed username, password, email, and name. The activity will make a Volley request and return success if the user has registed, or an error message if
the user cannot be registered (e.g. username taken).

2. MainActivity: 

