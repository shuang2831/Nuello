Stan Huang
Documentation 1/31/16

################################################################################
My coding challenge app: PhoneBook!
################################################################################

Description:

PhoneBook is a simple contact list app that pulls in data from a JSON enpoint
taken from here: https://solstice.applauncher.com/external/contacts.json
and creates a contacts list full of names, mobile numbers, and a profile image for each contact.
Upon clicking on one of the contacts, the user is directed to a new screen displaying
the details pertaining to a contact including things such as various phone numbers,
a contact's birthday, company, and address among other things. 

A more Developer-ish Description:

	*External libraries used: Jackson, SimpleJSON*

Copy of dependencies:

dependencies {
    compile(
            [group: 'com.fasterxml.jackson.core', name: 'jackson-core', version: '2.7.0'],
            [group: 'com.fasterxml.jackson.core', name: 'jackson-annotations', version: '2.7.0'],
            [group: 'com.fasterxml.jackson.core', name: 'jackson-databind', version: '2.7.0']
    )
    compile fileTree(include: ['*.jar'], dir: 'libs')
    testCompile 'junit:junit:4.12'
    compile 'com.android.support:appcompat-v7:23.1.1'
    compile 'com.android.support:design:23.1.1'
    compile 'org.json:json:20141113'
}

List of classes/objects:

1. MainActivity (Activity + fragment container, is run at the start)
2. ContactFragment (Fragment, creates and implements the contact list)
3. InfoFragment (Fragment, creates and implements the contact info screen)
4. CustomAdapter (Custom adapter, creates and sets list items for the contact list)
5. ContactDetails (Class, stores info taken from the first JSON endpoint (through RetrieveContacts)
6. MoreContractDetails (Class, stores info taken from the "detailsURL" of a contact (through RetrieveContactsMoreDetails)
7. RetrieveContacts (Asynchrounous Task, pulls details from the JSON endpoint and places it into a ContactDetails class)
8. RetrieveContactsMoreDetails (Asynchrounous Task, pulls details from a JSON endpoint and places it into a MoreContactDetails class)
9. LoadImage (Asynchrounous Task, loads an image, given an url)

List of layout views:

1. activity_main (fragment container, context is MainActivity)
2. fragment_contact_list (fragment list view, depicts the contact list)
3. fragment_contact_list_item (fragment list item view, depicts the list item to be used in the contact list)
4. fragment_contact_info (fragment view, depicts the view of the contact info that is shown after a user clicks/touches a contact.

PhoneBook is mainly composed of one main activity, and two fragments. Its main activity is
appropriately named MainActivity.java and is responsible for setting up and updating 
the app's fragment container, as well as lauching a couple asynchrounous tasks which pull
data from the JSON endpoint. 

PhoneBook's first fragment is called ContactFragment and is responsible for creating/updating
the actual contacts list (fragment_contact_list.xml) through the use of a 
custom adapter (CustomAdapter). ContactFragment 
also decides what happens once an item in the contact list is clicked, which in this case
is to call a function that creates and implements a new infoFragment fragment.

PhoneBook's second fragment is called InfoFragment and is the fragment responsible for creating
the contact details page (fragment_contact_info.xml) and loading it with different details
from the JSON endpoint. 

A Description of How the App Works/Process

1. Once the app is opened, it starts MainActivity.java (calling onCreate)

	1a. An Asynchronous Task RetrieveContacts is executed: pulls out the details in the JSON endpoint and 
	puts them into a list of custom objects called ContactDetails, where each instance of ContactDetails
	represents a differnt contact in the JSON endpoint

	1b. Another Asynchrounous Task RetrieveContactsMoreDetails is exexuted for each contact: 
	Does the same thing as RetrieveContacts but pulls out different information, specifically
	data located in the "detailsURL" value in each contact. Also, instead of pulling out a list
	directly, the task is run once for each contact, pulling out info from each "detailsURL".
	The resulting instances of custom object MoreContactDetails are then placed into a list.

2. MainActivity sets up itself as a framce container and establishes activity_main.xml as its initial view.

3. MainActivity then creates an instance of the ContactFragment fragment and sets it as the main view within
its fragment container. 

4. ContactFragment, upon creation, takes the information from the JSON endpoint retrieved by MainActivity and
uses it, as well as a list item layout fragment_contact_list_item, in order to create a list view.

	4a. Those parameters are put into a custom list adapter called CustomAdapter.java which sets each list item's
	name, mobile number, profile image, and favorite star (if favorite contact, add a star), based on the info
	taken from the JSON endpoint. The list view is then updated with those values and returned.

	Using an inflated listview located in layout fragment_contact_list.xml, ContactFragment takes the result of the
	list adapter and creates a view representing a list of contacts.

5. Once a contact in the contacts list is clicked or tapped, ContactFragment recieves that information in its OnListItemClick
listener, it will call a function in MainActivity openContactInfo(position of contact clicked), which goes on to create a new fragment and 
replace the current one.

6. MainActivity then goes on to replace the ContactFragment in its fragment container with a new instance of a new fragment called
InfoFragment. It also puts the previous fragment in the backstack so when the user touches the system back button on his or her device, the 
app will return to this ContactFragment fragment.

7. InfoFragment, once created, will take in the information from the JSON endpoint (taken from MainActivity) and update its view to contain
the relevant info. Anything that needs to be converted or changed is done so here, and the fragment_contact_info view populates the screen.

There it is! That's how the app works in simple terms. For more info, check out the comments in the code.

But wait... There's more to talk about.

################################################################################
Features
################################################################################

What is the app able to do?

	1. Display contacts in a scrollable list

		a. Displays Name

		b. Displays Mobile Number

			Why Mobile?: Most people register their mobile numbers as their main number. Having it displayed
			in the list itself is more convient for users who only want to find a number to call/text
		
		c. Displays Profile Image (small thumbnail, gotta see those smiles!)
	
		d. Displays favorite star (A star appears if a contact is registered as favorite)

			Why put a star here?: The idea is that usually users will set contacts that they often call or text,
			so the use of the favorite value should be to make it easier for users to find their favorites. Marking
			them with a star will make it easier find them as users can ignore any contact without a star, given that 
			they are looking for one of their favorite contacts.

	2. Displays contact info in a view

		a. Displays Name

		b. Displays Company
		
			Why ellipses?: While longer company names can always just take up multiple lines, having the single line
			and ellipses keeps the look of the contacts page cleaner while also showing that there is still more
			relevant information. Though it's probably not a good idea for an actual contacts app to hide info, for the purposes
			of this coding challege, I think it should be ok. Ideally, I would be able to implement a text resizer function in the future that
			would fix this.

		c. Displays Birthday

		d. Displays Address

		e. Displays E-Mail

What should the app do in the future? (Features to think about implementing later)

	1. Add, Edit, and Delete Contacts

		a. Add contacts using the actionbutton in the bottom left corner

		b. Edit and delete contacts useing the menu settings

		c. Possibly allow editing the JSON endpoint?

	2. Have a call button

		a. Allow users to call/text a number directly from the contacts list (by accessing the system native phone app)

################################################################################
Problems and Future Improvements
################################################################################

Unfortunatly, there are some bugs that show up in this app, . Here's a list of the ones I've seen so far.

1. Slow Loading Image:

	Images pulled from urls such as the profile images in the contacts list tend to load a bit slow. 
	A possiblity might be that the Asynchrounous task LoadImage isn't doing an efficient
	job of pulling out the image. 

	Something that is definitely cause slow loading images is the list view recycling views, as designed in the 
	custom adapter CustomAdapter. Every time the list is scrolled, the list items are reused and the images are changed.
	Problem is, sometimes the image has trouble keeping up and so it will often need a few seconds in order to set itself
	correctly (where the image will flash through different image files).

	Possible Fixes:
	
		A. Use a library such as Volley or Aquery: After doing some research online, it seems like these libraries could really
		help with pulling images from urls. I actually did attempt to use Volley for a bit before running into some bugs and
		giving up for now. I'd love to try again when I get the chance.

		B. Don't recycle list views: We could always just not reuse views, but this often causes some performance issues later on
		when multiple images are loaded, so I would rather find a better solution. 
		
		C. Cache images: cache the images as they are initially loaded into the app, and then use those images to set as the 
		ImageView. That way there shouldn't be an issue of the app constantly loading multiple images, though there might be
		more roadblocks to get around by going down this route.

2. TextViews cannot resize text:

	An issue with the TextViews is that if it needs to display a long string of text, such as some of the example company names, 
	it will either use multiple lines or a single line cutting off the text is singleLine is set to true (which it is in this App).

	Possible Fix: 

		A. Implement a text resizer in order to change the text size based on the length of a company name. Might still look strange 
		if the text is really long.

		B. Change up the design of the Contact Info view. Nothing is set in stone when it comes to UI, perhaps it can look better?

3. Alphabetize the Contact List: 

	Ordering the contacts in alphabetical order would make navigation much easier.

	Possible implementation:

		A. It should be easy enough to organize our list of contacts before throwing it into CustomAdapter.java

4. A better favorites implementation:

	While I like the way my favorite stars are implemented, in the case of many favorite, it doesn't really help that much as navigating based on favorites
	can get tedious loooking back and forth between stars and names. 

	Possible implementation:

		A. Make a favorites tab for contacts: This would require a new view displaying just the favorites based on some button or tab being pressed.
		It would definitely make having many favorites much easier.

		B. Add a favorites section: Instead of a whole new view, just add a section of favorites before the actual list of contacts. 
		Might be hard to make this one look good though.

5. Make a nicer contact info view design:

	Yes, we can make it look nicer! I think this one just requires some more time and brainstorming. I think the design has a lot more potential than
	what is shown in the app already. The way that a user recieves info can be very particular, so it'd be great to be able to get some more user testing
	in and whatnot. (Actually, the color is green because of user input. Green's a nice color on the eyes.)

################################################################################
Thanks + End
################################################################################

As you can see, there's room for improvement in my app. Still, I actually had a lot of fun doing this coding challenge and I feel like I learned a lot!

I don't know if Alex will see this so I'll put it into my email to him too, but I wanted to thank him for the interview/conversation we had.
It's not often that I get to have a nice conversation during an interview, so it was a lovely surprise!
I also really enjoyed being able to discuss narrative structure in program and product design, as it is something that I love applying to 
the things I do. Oddly enough, I don't get much of a chance to do that usually.

Anyways, thank you for the interview and for letting me know so much about Solstice Mobile. It sounds like an amazing place to work and
I already have such a great first impression. I hope I can only make an impression on the company that is half as nice.

I hope that this app is sufficient to fulfill the expectations of Solstice Mobile, and I look forward to hearing from you!