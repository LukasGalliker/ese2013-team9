# ese2013-team9

Project: **Shoppinglist**

#### Possible App Names

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

	##### Synchronizing the original repository with your fork
		1. Terminal > cd to local project directory on your computer, check with "git status"
		2. Make an alias of the original project "git remote add original https://github.com/ese-unibe-ch/ese2013-team9.git"
		3. Get the files from the original repository to your local repository with "git fetch original"
		4. Merge the original with the local repository "git merge original/master"
		5. Push the changes to your own online repository/fork "git push origin master"

	##### Normal GitHub commiting workflow
		1. make the changes in the code
		2. Terminal > cd to local repository if not there yet (you should probably have the most recent version synced before starting to work on it)
		3. "git status" to see what's the current status of that repository
		4. "git add <file>"" or "git add *" (for all files)" to add the changed files to a commit (seen via "git status")
		5. "git commit -m "<commit message"" to commit the added changes and set the message that will appear on GitHub
		6. "git push origin master" (or whatever branch you want to push to) to upload the changes to GitHub
		7. go to your own online repository and click the green "review/compare" button left to the branch dropdown
		8. create a pull request against the original repository
		9. you are taken to the original repository, if the change doesn't need approval of all team members you can merge your pull request on the presented page

- Eclipse
	- We want a multilanguage app, all strings should always be translated to german and english in their respecitve folders (res/values for english, res/values-de for german)
	- To work with fragments seems a bit more complicated (initially) but is for sure a more mvc and best practice way [Tutorial](http://www.cs.dartmouth.edu/~campbell/cs65/lecture08/lecture08.html)

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
	- the editing/deleting of lists could also be managed in a separarate "manage lists" activity, but it would be more user friendly if we had both possibilites (renaming/deleting of lists seems not possible in Out of Milk -> it is...found out, but it's quite hidden, could be done better!)
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
	- See who added what in front of the item (if possible)
	- No registering of a new account to use the sync feature, basically "nooo, not another login"...
	- READ_CONTACTS Permission? (to get the people the lists can be shared with?)
	+ how do we send the lists? (whatsapp, email, sms??) -> in app sharing (maybe with notification for downloading the app if not installed, additionally sending with whatsapp email etc. would be cool)
	- WRITE_SMS Permission (that we can create the SMS if we decide that lists can be shared via SMS)
	- SEND_SMS Permission (if we decide that lists can be shared via SMS)
	+ can we add our own app to the send menu? How do we verify that the chosen sharing partner also has the app?
10. As a user I want to stop sharing a list with other people
	- remove people from the share list (which should be managable per list)
11. As a user I want that every time I edit an item my friends can see the update
	- INTERNET Permission (to connect to remote API)
	- For this we need a Web API / Database, I would suggest that it works with JSON
	- If possible I would not force users to authenticate, e.g. force them to create another account, shoppings lists are not too sensitive
12. As a user I want that every time my friends edit an item I see the update
	- For this we need a Web API / Database, I would suggest that it works with JSON
	- We need to think about a solution / fallback if no internet connection is available, e.g. storing the changes temporarily in a local database and then sync them when a connection is available the next time.
13. As a user I want to add a location to a shopping list and get notified when I get closer to that location
	+ How does the user add the location? -> The user GOES to the desired shops and adds the location to his locations, then you can add them to cateogries
	- Notification Permission? How is this done?
	- ACCESS_COARSE_LOCATION Permission (use Wifi / mobile network towers for positioning)
	- ACCESS_FINE_LOCATION Permission (use GPS)
	- VIBRATE Permission (for notifingy the user when he's close)
14. As a user I want to see an overview of all the items that still need to be bought from all shopping lists
	+ Any ideas how and where (UI) to do this? Don't like that requirement, doesn't make too much sense =) -> could maybe be done via a widget
15. As a user I want to archive a shopping list
	- so we need an "archived" attribute/field
	+ for what is this actually good for? Maybe we can drop that requirement? Question: Why do you need to archive your shopping lists?
	+ what happens if the list was shared? remove himself from the sharing list, but let others use the list normally?
16. As a user I want to explore the shopping lists in the achieve
	- Archive view/activity
17. As a user I want to delete a shopping list from the archive.
	- short tap on an archived list brings up options (delete, unarchive (to edit again))
18. Notification center
	- new lists shared, lists deleted, new messages, reminders (requires date input tracking)
19. In-App Messaging

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
13. At the end of shopping there should be a conclusion/summary of cost like "you spent xx $" -> send notification to share partners "guy x went shopping, he bought ... summary"


### Questions
+ What should be shown when you start up the app? Overview of all shopping lists or the last opened list? -> overview?
+ How should we share the lists (facebook etc. or only in-app)?
+ How far down do we have to go with versions? don't worry about lower versions (till 4, maybe 3)


### Requirements Document

1. Introduction
	- Purpose
	Shoppy is an application to manage your shopping lists digitally and have it with you whenever you need it.
	- Stakeholders
		- Users: Everybody who used to go shopping
	- Definitions (Glossary, special terms)
	- System overview
		- When you open the app it returns to the last opened list and presents you the items you have to buy.
	- References (other applications, sources, information that complements what we're writing)
		- Out of Milk
2. Overall description
	- Use cases
	- Actor characteristics
3. Specific requirements
	- Functional requirements
	- Non-functional requirements (external, performance, etc.)

### Main Use Case

- Memory aid, I want to spend too much time categorizing and adding items to the list, if not I will stop using the app, so it should be quite straight forward
