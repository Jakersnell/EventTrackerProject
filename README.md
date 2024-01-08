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
## Auth
<pre>
**POST api/auth/login**
   returns: AuthToken
  **body parameters**
   username: string
   password: string
</pre>
 
