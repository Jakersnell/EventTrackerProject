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
* BCrypt Encryption 

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
 All passwords in the database are encrypted with BCrypt encryption, so the entries in the database will look like gibberish,
 but the password for every mock user is 'password123'.
</pre>

# API Reference Index
### ** disclaimer! this is a proof of concept only and should not be used for a production website! **
the format will be as follows: 
&#60;METHOD> &#60;URL> -> &#60;RETURN-TYPE>
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
      email: string &#60;required>
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



## Products
### GET api/products -> List<Product>
<pre>
Returns all products.
Admin auth removes filter of items by isEnabled.
 
    url parameters:
      auth: string &#60;optional> &#60;additional-admin-privledges>
</pre>

### GET api/products/{productId} -> Product
<pre>
Returns a product of the given id if it exists.
Admin auth removes filter of items by isEnabled.
 
    url parameters:
      auth: string &#60;optional> &#60;additional-admin-privledges>
</pre>

### POST api/products -> Product
<pre>
Only admins may use this endpoint.
Creates and Returns a Product.
 
 
    url parameters:
      auth: string &#60;required> &#60;admin-only>
</pre>

### PUT api/products/{productId} -> Product
<pre>
Only admins may use this endpoint.
Updates and returns a product if it exists.
 
    url parameters:
      auth: string &#60;required> &#60;admin-only>

    body parameters:
      name: string &#60;required>
      description: string &#60;optional>
</pre>


### DELETE api/products/{productId} -> VOID
<pre>
Only admins may use this endpoint.
Sets the enabled flag to false if product exists.
 
    url parameters:
      auth: string &#60;required> &#60;admin-only>
</pre>

### GET api/categories/{productId}/categories -> List<Product>
<pre>
Returns the categories associated with a given product.
 
    url parameters:
      auth: string &#60;optional> &#60;additional-admin-privledges>
</pre>

## Product Reviews
### GET api/products/{productId}/reviews -> List<ProductReview>
<pre>
Returns a list of all the reviews associated with a given product.
Admin auth removes filter of items by isEnabled.

    url parameters:
      auth: string &#60;optional> &#60;additional-admin-privledges>
</pre>

### GET api/products/{productId}/reviews/{reviewId} -> ProductReview
<pre>
Returns a review for a product by their respective ids.
Admin auth removes filter of items by isEnabled.

    url parameters:
      auth: string &#60;optional> &#60;additional-admin-privledges>
</pre>

### POST api/products/{productId}/reviews -> ProductReview
<pre>
Creates a review for the given product by productId.
Any user may use this so long as the auth token is valid.

    url parameters:
      auth: string &#60;required> &#60;all-users>
</pre>

### DELETE api/products/{productId}/reviews/{reviewId} -> VOID
<pre>
Disables a review by its id.
The user by auth token must be an admin or the owning user.

    url parameters:
      auth: string &#60;required> &#60;admin-or-owner>
</pre>

### PUT api/products/{productId}/reviews/{reviewId} -> ProductReview
<pre>
Updates a review by its id.
The user by auth token must be an admin or the owning user.
Admin users can set the enabled and verified fields through this method.

    url parameters:
      auth: string &#60;required> &#60;admin-or-owner>
 
    body parameters:
      content: string &#60;required>
      rating: int between 1&5 &#60;required>
</pre>

## Users
### GET api/users/{userId} -> User
<pre>
Returns a user by its id.
Admin auth removes the isEnabled filter.

    url parameters:
      auth: string &#60;optional> &#60;additional-admin-privledges>
</pre>

### PUT api/users/{userId} -> User
<pre>
Updates a user at the given id.
Auth must be user or admin.
Admin auth provides access to the enabled field.

    url parameters:
      auth: string &#60;optional> &#60;additional-admin-privledges>
 
    body parameters:
      username: string &#60;required>
      email: string &#60;required>
      password: string &#60;required>
      enabled: bool &#60;optional> &#60;admin-access-only>
      verified: bool &#60;optional> &#60;admin-access-only>
</pre>

### PUT api/users/{userId} -> User
<pre>
Updates a user at the given id.
Auth must be user or admin.
Admin auth provides access to the enabled and verified fields.

    url parameters:
      auth: string &#60;optional> &#60;additional-admin-privledges>
 
    body parameters:
      username: string &#60;required>
      email: string &#60;required>
      password: string &#60;required>
      enabled: bool &#60;optional> &#60;admin-access-only>
      verified: bool &#60;optional> &#60;admin-access-only>
</pre>






