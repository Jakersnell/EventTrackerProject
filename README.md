<pre>
 ____            _               ___ _      _    ____ ___ _
|  _ \ _____   _(_) _____      _|_ _| |_   / \  |  _ \_ _| |
| |_) / _ \ \ / / |/ _ \ \ /\ / /| || __| / _ \ | |_) | || |
|  _ <  __/\ V /| |  __/\ V  V / | || |_ / ___ \|  __/| ||_|
|_| \_\___| \_/ |_|\___| \_/\_/ |___|\__/_/   \_\_|  |___(_)
</pre>

# About
Welcome to ReviewItAPI, a REST api for everyday people to make real reviews for real products, intended to be a user driven way for users to track products and get authentic ratings and reviews. The api includes a variety of functionality to provide operations, and ergonomic RESTful endpoints for any sort of front-end, be it mobile, desktop, or web.

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

# Lessons learned
I learned a lot about reducing boilerplate and thinking creatively to do so. I love solving problems, and I really dont like boilerplate, so its a win-win! I also learned a lot about the importance of clean consistent patterns and naming, that is an art, and really keeps a project together.

<pre>
     _                                       _        _   _
  __| | ___   ___ _   _ _ __ ___   ___ _ __ | |_ __ _| |_(_) ___  _ __
 / _` |/ _ \ / __| | | | '_ ` _ \ / _ \ '_ \| __/ _` | __| |/ _ \| '_ \
| (_| | (_) | (__| |_| | | | | | |  __/ | | | || (_| | |_| | (_) | | | |
 \__,_|\___/ \___|\__,_|_| |_| |_|\___|_| |_|\__\__,_|\__|_|\___/|_| |_|
</pre>

# API Reference Index
the format will be as follows: 
&#60;METHOD> &#60;URL> -> &#60;RETURN-TYPE
## Auth
### Object: AuthToken 
<pre>
An object that contains an auth token key and its expiration date and time.

    data:
      token: string
      expiresOn: ISO date-time
</pre>
### POST api/auth/login -> AuthToken
<pre>
Login end point that expects an object containing username and password data
for a user that already exists, upon successful authentication. 
 Returns an auth token that will be valid for 24 hours.
 
    body parameters:
      username: string &#60;required>
      password: string &#60;required>
</pre>

###  POST api/auth/signup -> AuthToken
<pre>
Signup endpoint to create a new user. Username and password must not be null.
Username and password must be valid. Username must be unique.

    body parameters:
      username: string &#60;required>
      password: string &#60;required>
</pre>

## Categories
### GET api/categories -> List<AuthToken>
<pre>
Returns a list of all categories on the server.
Admin authentication allows for viewing of hidden categories.
 
    url parameters:
      auth: string <optional> 
</pre>

### GET api/categories/{categoryId} -> Category
<pre>
Returns a category of categoryId if it exists.
Admin authentication allows for viewing of hidden categories.

    url parameters:
      auth: string &#60;optional>
</pre>

### POST api/categories/{categoryId} -> Category
<pre>
Creates and returns a new Category object. Assuming valid data.
This is an Admin only endpoint, and the auth token provided must be
valid and have admin privledges.

    url parameters:
      auth: string &#60;required> &#60;admin-only>

    body parameters:
      name: string &#60;required> // the name for the new category 
</pre>

### DELETE api/categories/{categoryId} -> VOID
<pre>
Only admins may use this endpoint.
Sets the enabled flag on a category to false, rendering it unable to be accessed
by non admin tokens.

    url parameters:
      auth: string &#60;required> &#60;admin-only>
</pre>

### PUT api/categories/{categoryId} -> Category
<pre>
Only admins may use this endpoint.
Updates fields and statuses on a Category
with id equal to &#60;categoryId>.
 
    url parameters:
      auth: string &#60;required> &#60;admin-only>

    body parameters:
      name: string &#60;required>
      enabled: boolean &#60;required>
</pre>

##
