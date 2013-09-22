# ese2013-team9

Project: **Shoppinglist**

**Possible App Names**

Add your username behind the app name of your choice in parenthesis as your vote for the app name, e.g. Shoppy (exside)

- Shoppy
- Shoppy App
- Shapp


## Taskpool

Let's have a To-Do list with individual tasks that can be assignes to team members
- (all) Discuss requirements
- ~~(luk) First setup of the project organization~~


## Organization

**What do we need?**

- Coding conventions (indenting, coding style etc.)
- A common GitHub commit/merge strategy
- Requirements, probably gathered from a use case / UML diagram


## Workflow

- GitHub
	1. Each team member forks the original repository to his own account to work on it
	2. Clone the forked repository to your local machine
	3. Work on the project, add and commit the changes to your local repository (should we use branching?)
	4. Push the changes online to the master branch
	5. Make a pull request against the original repositry master branch
	6. Pull request should be merged only, if all team members have commented with +1 to give their vote, if this is the case the responsible for the pull request can merge the change into the projects master branch
	7. Do the merging of pull request in the original repository via the web frontend, so nobody would have to fork the "real/original" repository and thus maybe make unintentional changes
	8. For faster testing, it would be very handy if everybody could compile an actual .apk package that can be tested on a real device!
- Eclipse
	- We want a multilanguage app, all strings should always be translated to german and english in their respecitve folders (res/values for english, res/values-de for german)


## Knowledge / Links

- How to use GitHub, an [introduction](http://rogerdudler.github.io/git-guide/index.de.html)
- [GitHub Markdown Cheatsheet](https://github.com/adam-p/markdown-here/wiki/Markdown-Cheatsheet)


## Requirements

**These are the user stories from the original project descriptions with annotations in sublists**

1. As a user I want to create different shopping lists
	- EditText View, very good example is Out of Milk app, but the field sould be at the bottom for better usability
2. As a user I want to rename and delete shopping lists
	- different lists could be added on the left action bar dropdown (as in Out of Milk)
	- below the lists there are actions for lists (in Out of Milk there seems to be no way to edit or delete a list...)
	- the editing/deleting of lists could also be managed in a separarate "manage lists" activity, but it would be more user friendly if we had both possibilites (renaming/deleting of lists seems not possible in Out of Milk, or I didn't find out how till now)
3. As a user I want to add items to shopping lists
	- select a list from the action bar dropdown to add items to it
4. As a user I want to add a quantity/number of items to an item
	- short tap on item brings up edit/delete options
5. As a user I want to delete an item
	- short tap on item brings up edit/delete options
6. As a user I want to mark an item as bought
	- long tap on an item marks it as bought
7. As a user I want to see all the items of a list
	- just go into the list, all items and categories are listed there
	- items should be dragable/droppable into categories
8. As a user I want to see all items that are still not bought
	- move bought items to the end of the list
9. As a user I want to share a list with a friend or more
	- READ_CONTACTS Permission? (to get the people the lists can be shared with?)
	- how do we send the lists? (whatsapp, email, sms??)
	- WRITE_SMS Permission (that we can create the SMS if we decide that lists can be shared via SMS)
	- SEND_SMS Permission (if we decide that lists can be shared via SMS)
	- can we add our own app to the send menu? How do we verify that the chosen sharing partner also has the app?
10. As a user I want to stop sharing a list with other people
	- remove people from the share list (which should be managable per list)
11. As a user I want that every time I edit an item my friends can see the update
	- INTERNET Permission (to connect to remote API)
	- For this we need a Web API / Database, I would suggest that it works with JSON
	- If possible I would not force users to authenticate, e.g. force them to create another account, shoppings lists are not too sensitive
12. As a user I want that every time my friends edit an item I see the update
	- For this we need a Web API / Database, I would suggest that it works with JSON
13. As a user I want to add a location to a shopping list and get notified when I get closer to that location
	- How does the user add the location?
	- Notification Permission? How is this done?
	- ACCESS_COARSE_LOCATION Permission (use Wifi / mobile network towers for positioning)
	- ACCESS_FINE_LOCATION Permission (use GPS)
	- VIBRATE Permission (for notifingy the user when he's close)
14. As a user I want to see an overview of all the items that still need to be bought from all shopping lists
	- Any ideas how and where (UI) to do this? Don't like that requirement, doesn't make too much sense =)
15. As a user I want to archive a shopping list
	- so we need an "archived" attribute/field
	- what happens if the list was shared? remove himself from the sharing list, but let others use the list normally?
16. As a user I want to explore the shopping lists in the achieve
	- Archive view/activity
17. As a user I want to delete a shopping list from the archive.
	- short tap on an archived list brings up options (delete, unarchive (to edit again))

**These are newly evaluated or proposed features/requirements**

1. Very nice features would be adding products via "history", "barcode", "voice input" (as in out of milk)
	- CAMERA Permission (to make pictures from barcodes and process them)
	- RECORD_AUDIO Permission (to record audio for google voice api)
2. Autosuggest for list items (from remote database, this needs to be configurabele via system settings to disable, only to when wifi is available etc.)
3. Better Barcode database than out of milk, how do we get the data
4. Auto building Product Database with newly added barcode items (how can we do this and how can we asure that spam items don't get into the database, veryfing by 10 users?)
5. Ability to store recipies and build shoppings lists from them automatically (ev. a pro feature)
6. Ability to have an inventory list (as out of Milk), which would be linked to the other lists (recipies) etc., which get deducted when you cooked a recipy
7. Is a To-Do List (as in out of milk) a good feature?
8. Password protect lists! (have seen this in many comments), doesn't have to be a very sophisticated security system, just prevent kids from reading present lists
9. Display product details (image, description, nutrients, calories, origin, ecological-rating?, warnings, labels [bio, fairtraide etc.] usw.) in a tab of the edit window (fetched from remote database if available) (ev. a pro feature)
10. Nice animations for the list scrolling
11. Customizable UI (Colors, Fonts, Themes) (ev. a pro feature?)
12. Quickly add items to a list via widget (ev. a pro feature?)
