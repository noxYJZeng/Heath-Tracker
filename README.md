# HealthTracker


## Introduction
Embark on a transformative journey with HealthTracker, your ultimate companion in personal wellness. Bid farewell to unclear health paths and embrace a lifestyle where every decision is informed and intentional. HealthTracker brings a suite of advanced features into the palm of your hand, allowing you to effortlessly track caloires burned, workout plan, and community challenges. With a focus on personalized insights and adaptive recommendations, HealthTracker is designed to evolve with you, ensuring your health goals are met with precision and ease. Start your journey to a healthier tomorrow with HealthTracker today—your partner in fostering a vibrant, healthier life. d(`･∀･)b

## Design & Architecture
HealthTracker’s architecture adheres to the Model-View-ViewModel (MVVM) pattern, enhancing the separation of concerns by dividing the user interface logic from the business logic. This architecture supports a clean and organized codebase that is simpler to understand, test, and maintain.


### Design Patterns
- Decorator

An inherent part of using Android Studio was using decorators. Decorators allowed us to write/read to/from Firebase, control what happens before unit tests, turn functions into unit tests, and run instrumented tests.
![](./docs/images/dec1.png)
![](./docs/images/dec2.png)
![](./docs/images/dec3.png)
- Observer

Another part of creating an app is using the observer pattern. Without it, we wouldn't be able to make buttons work or update the screen when new data exists.
![](./docs/images/obs1.png)
![](./docs/images/obs2.png)
- Factory

We used the factory design pattern to provide view fragments with a view model.
![](./docs/images/fac1.png)
![](./docs/images/fac2.png)
- Singleton

We used the singleton pattern to ensure that only the first user set is retained.
![](./docs/images/sin1.png)
- Momento

Activities in Android Studio use the momento pattern to restore previous states.
![](./docs/images/mom1.png)
![](./docs/images/mom2.png)

### Design Diagrams
We used design diagrams because they make it easier to see how everything in our system fits together. They helped us understand complex parts, communicate better with our team, and make sure we were all on the same page before we started implementing anything.

1. Domain Model
The class diagram displays the relationships and interactions between various classes in the HealthTrack app, providing a clear overview of the app’s structure and component interactions.
![](./docs/images/Domain.png)


2. Sequence Diagram:
The sequence diagram outlines the step-by-step process of logging a workout, showing how the user interacts with different parts of the system to complete this task.
![](./docs/images/Sequence.png)


3. Use Case Diagram:
The use case diagram highlights the various functionalities of the HealthTrack app, detailing how different user roles, such as different users interacting with these features.
![](./docs/images/use_case.png)





## UI

Our app is very intuitive to follow from the user's perspective. When the user opens the app for the first time, they will encounter a page to either
log in or sign up. The user can then choose to sign up if they don't have an account. The user can then change their information by navigating to the Personal Info page.

Within the application, the user will be greeted with a calorie tracker page which is displayed graphically for the ease of the user. The bottom navigation bar is also extremely easy to use because all of the pages are there for the user's convenience to change between their desired page. The user can add workout plans, trakcer their workouts and their details, and even see what community challenges are up for them. Each page is extremely detailed and will boost the user experience due to its simplicity and effectiveness.

### Login Page
![Login Page](./docs/images/login_page.png)

This is the login page where the user logs into his account

### Calories Tracker Page

![Calories Page](./docs/images/calories.png)

This is the calories tracker page where the user can figure out the calories he has burnt today and how much he has left.

### Tracker Page

![Tracker Page](./docs/images/tracker.png)

This is the tracker page, which shows the workouts for tracking.

### Workouts Page

![Workout Page](./docs/images/workouts.png)

This is the workouts page where the users can put all of their workouts. As you can see, there is a aestrik (*) next to the repeated workouts.

### Workout Details Page

![Workout Details Page](./docs/images/workout_2.png)

This is the page which is shown when we click on a user's workout. It provides the details like the author, calories, repetitions, sets and notes.

### New Challenge Page

![New Challenge Page](./docs/images/new_challenge.png)

This is the page where people can make a new challenge

### New Challenge Dialog Box

![New Challenge Dialog Box](./docs/images/new_challenge_2.png)

This is how a new challenge dialog box looks like

### Personal Info Page

![New Challenge Page](./docs/images/last_page.png)

This is the last page where we can enter the age, height, weight and gender of the user





## Functionality Demo
Click [here](https://youtu.be/bNTcTpvoals)! ✧◝(⁰▿⁰)◜✧


## Conclusion
As we conclude this phase of the HealthTracker app, we reflect on a journey marked by significant achievements and valuable lessons. Our team's dedication has been instrumental in bringing to life a tool that not only tracks health metrics but also inspires users to embrace a healthier lifestyle.

Throughout the development process, every team member played a pivotal role—from designing the user interface to implementing key functionalities, conducting unit tests, and integrating the database seamlessly. These contributions underscored our commitment to creating a robust and user-friendly application.

Adopting the agile methodology, we navigated through each sprint with a focus on adaptability and continuous improvement. This approach not only honed our project management skills but also enhanced our ability to collaborate effectively. The challenges we faced, such as integrating complex health data algorithms and ensuring user data privacy, pushed us to innovate and think critically.

The implementation of various design patterns was not just a technical exercise but a strategic endeavor that improved our software architecture's efficiency and maintainability. Regular code reviews became a cornerstone of our process, promoting a culture of excellence and mutual accountability.

Beyond the technical aspects, this project was a transformative learning experience. We deepened our understanding of the health tech landscape and the importance of creating intuitive, engaging user experiences. The iterative process of refining HealthTracker allowed us to appreciate the nuances of user-centered design and the impact of technology on personal health management.

In summary, HealthTracker is more than just an app; it is a testament to our team's resilience, innovation, and collective expertise. As we look forward to the next stages of development and deployment, we are proud of our accomplishments and grateful for the profound insights gained, ready to continue our mission to empower individuals towards achieving optimal health.



## Contributor
Contributors to app’s development: Nox(Xilu) Zeng, Joseph Seon Yoo, Zohair Hussain, Benjamin Tam, Bryant Alan Morales-Chora, Hadi Abdul Syed

Contributors to the website: Nox(Xilu) Zeng, Joseph Seon Yoo, Zohair Hussain, Benjamin Tam, Hadi Abdul Syed
