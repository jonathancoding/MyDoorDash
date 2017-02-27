# MyDoorDash
This is my own food delivery app using DD api. :)
The codebase will be updated as this is the only init version. 

Youtube Demo: https://www.youtube.com/watch?v=mnzgdmEKw6I

Libaries that I used:
* OKHttp for network calls
* Gson for convertion between string and json
* Picasso for image caching 

Code Structure:
* Activities
* Fragments
* Models
* Adapters

Patterns:
* Viewholder Pattern for optimizing scrolling

Benefits:
* Supports data/view state retention during rotation
* Supports save to my favorite functionality 
* Layout UI is reusale for restaurant list nearby page and my favorite list page
* One activity, two Fragments

Caveats:
* UI is not that beautiful due to time constraints, will refactor later
* Network API wrapper is not available yet due to time constraints
* RxJava/Android is not available yet due to time constraints


Screenshots are here: https://github.com/JonathanGitHub/MyDoorDash/issues/1
