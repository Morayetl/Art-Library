Picture gallery application for sharing and downloading pictures

Application was build with Spring-boot, Next.JS, AntD, MongonDb and Docker

Site is responsive and works with mobile

Used 

Java 11.0.19
Node 16.15.1
Maven 3.6.0

# Add captcha keys to application-dev.properties

Its in ./art-library/src/main/resources/application-dev.properties

# First turn on docker and then start backend

cd ./art-library
mvn -Pdev clean install
docker-compose up
./mvnw spring-boot:run -Pdev

# Install needed packages and run frontend
cd ./art-library-frontend
npm install
npm run dev

# Open website
http://localhost:3000/


# Login to upload images
http://localhost:3000/login

username: test
password: test

# Upload image
http://localhost:3000/user/media/add

# Edit/Delete images
http://localhost:3000/user/media

![Screenshot.](/screenshots/image1.png "screenshot.")
![Screenshot.](/screenshots/image2.png "screenshot.")
![Screenshot.](/screenshots/image3.png "screenshot.")
![Screenshot.](/screenshots/image4.png "screenshot.")
![Screenshot.](/screenshots/image5.png "screenshot.")
![Screenshot.](/screenshots/image6.png "screenshot.")
![Screenshot.](/screenshots/image7.png "screenshot.")