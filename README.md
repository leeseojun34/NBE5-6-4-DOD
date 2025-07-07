testtest lkh

--- 

[깃허브 룰]
- 이슈 활용
- 이슈, PR 올릴 때 라벨 사용하기
- CRUD까지는 PR merge 5명 모두 체크
- CRUD 끝나면 PR merge 시 2명 이상 체크

---


# Spring

This app was created with Bootify.io - tips on working with the code [can be found here](https://bootify.io/next-steps/).

## Development

When starting the application `docker compose up` is called and the app will connect to the contained services.
[Docker](https://www.docker.com/get-started/) must be available on the current system.

During development it is recommended to use the profile `local`. In IntelliJ `-Dspring.profiles.active=local` can be
added in the VM options of the Run Configuration after enabling this property in "Modify options". Create your own
`application-local.yml` file to override settings for development.

Lombok must be supported by your IDE. For IntelliJ install the Lombok plugin and enable annotation processing -
[learn more](https://bootify.io/next-steps/spring-boot-with-lombok.html).

In addition to the Spring Boot application, the DevServer must also be started - for this
[Node.js](https://nodejs.org/) version 22 is required. On first usage and after updates the dependencies have to be installed:

```
npm install
```

The DevServer can be started as follows:

```
npm run devserver
```

Using a proxy the whole application is now accessible under `localhost:3000`. All changes to the templates and JS/CSS
files are immediately visible in the browser. Frontend unit tests can be executed with `npm run test`.

## Build

The application can be built using the following command:

```
gradlew clean build
```

Node.js is automatically downloaded using the `gradle-node-plugin` and the final JS/CSS files are integrated into the jar.

Start your application with the following command - here with the profile `production`:
 
```
java -Dspring.profiles.active=production -jar ./build/libs/spring-0.0.1-SNAPSHOT.jar
```

If required, a Docker image can be created with the Spring Boot plugin. Add `SPRING_PROFILES_ACTIVE=production` as
environment variable when running the container.

```
gradlew bootBuildImage --imageName=com.grepp/spring
```

## Further readings

* [Gradle member manual](https://docs.gradle.org/)  
* [Spring Boot reference](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)  
* [Spring Data JPA reference](https://docs.spring.io/spring-data/jpa/reference/jpa.html)
* [Learn React](https://react.dev/learn)
* [Webpack concepts](https://webpack.js.org/concepts/)  
* [npm docs](https://docs.npmjs.com/)  
* [Tailwind CSS](https://tailwindcss.com/)  
