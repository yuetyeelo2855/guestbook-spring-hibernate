### A bare minimum Spring, Hibernate, Cloud SQL webapp for App Engine ###

See my blog post here:
http://blog.essaytagger.com/2011/11/spring-hibernate-on-google-app-engines.html

As mentioned in the writeup, the Manager/DAO layer owes a big debt to David Chandler's Objectify-based GenericDAO.

UPDATE: I ran into Session conflicts when trying to save objects that contained not-yet-initialized member objects (aka proxies). See my solution to this problem here:
http://blog.essaytagger.com/2011/11/avoiding-session-conflicts-on-save-due.html

I'm not sure it's a bulletproof solution yet so I haven't updated the repository with the new version.