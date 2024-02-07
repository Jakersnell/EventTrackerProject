<pre>
 ____            _               ___ _   _
|  _ \ _____   _(_) _____      _|_ _| |_| |
| |_) / _ \ \ / / |/ _ \ \ /\ / /| || __| |
|  _ <  __/\ V /| |  __/\ V  V / | || |_|_|
|_| \_\___| \_/ |_|\___| \_/\_/ |___|\__(_)
Now Encrypted With BCrypt!          (v1.0.1)
</pre>

[View the website live.](http://107.21.192.111:8080)

# About
Welcome to ReviewItAPI, a REST api and Frontend for everyday people to make real reviews for real products, intended to be a user driven way for users to track products and get authentic ratings and reviews. The api includes a variety of functionality to provide operations, and ergonomic RESTful endpoints for any sort of front-end, be it mobile, desktop, or web.

# Frontend
The frontend was created using JavaScript, AJAX, HTML, and CSS. By far my favorite piece of the code was how I designed routing between different views with the route method in routing.js, I also really enjoyed the flexibility of the module system the is newer to JavaScript. 

# How to use
The front end can be used to enact CRUD operations on the product type with the admin login listed below.

# Technology Used
* Java 17
* REST API
* Spring Boot
* Spring Data JPA
* JPA
* MySQL
* SQL
* JUnit Jupiter
* JPA Test
* Spring Test
* Eclipse
* MySqlWorkbench
* BCrypt Encryption
* Javascript
* AJAX
* HTML
* CSS

# Lessons learned
I learned a lot about reducing boilerplate and thinking creatively to do so. I love solving problems, and I really dont like boilerplate, so its a win-win! I also learned a lot about the importance of clean consistent patterns and naming, that is an art, and really keeps a project together.

# Docs
<pre>
     _                                       _        _   _
  __| | ___   ___ _   _ _ __ ___   ___ _ __ | |_ __ _| |_(_) ___  _ __
 / _` |/ _ \ / __| | | | '_ ` _ \ / _ \ '_ \| __/ _` | __| |/ _ \| '_ \
| (_| | (_) | (__| |_| | | | | | |  __/ | | | || (_| | |_| | (_) | | | |
 \__,_|\___/ \___|\__,_|_| |_| |_|\___|_| |_|\__\__,_|\__|_|\___/|_| |_|
</pre>
<pre>
BASE-URL: http://107.21.192.111:8080/ReviewItREST
 
admin auth token: 'admin-auth-token'
non-admin auth token: 'generic-auth-token'

admin details:
 username: admin
 password: password123

non-admin user details:
 username: joeschmoe11
 password: password123
 
** note **
 All passwords in the database are encrypted with BCrypt encryption, 
 so the entries in the database will look like gibberish,
 but the password for every mock user is 'password123'.
</pre>
