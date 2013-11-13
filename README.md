# Shoppy

Project: **Shoppinglist**

Version: **v1.0**


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

**These are the user stories from the original project description**

1. As a user I want to create different shopping lists
	
2. As a user I want to rename and delete shopping lists

3. As a user I want to add items to shopping lists

4. As a user I want to add a quantity/number of items to an item

5. As a user I want to delete an item

6. As a user I want to mark an item as bought

7. As a user I want to see all the items of a list

8. As a user I want to see all items that are still not bought

9. As a user I want to share a list with a friend or more

10. As a user I want to stop sharing a list with other people

11. As a user I want that every time I edit an item my friends can see the update

12. As a user I want that every time my friends edit an item I see the update

13. As a user I want to add a location to a shopping list and get notified when I get closer to that location

14. As a user I want to see an overview of all the items that still need to be bought from all shopping lists

15. As a user I want to archive a shopping list

16. As a user I want to explore the shopping lists in the achieve

17. As a user I want to delete a shopping list from the archive.

18. Notification center

19. In-App Messaging