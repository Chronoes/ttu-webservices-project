# SOAP API Documentation
**Version 1.0.0**

The MediaService API handles relations between Genre and Media.
Each Media can be associated with multiple Genres.

With this API, new genres and media can be added or edited.
The API also provides searching for both objects with various filters.

# Operations
All operations must be supplied with an appropriate API_TOKEN within the body.
Valid requests will return valid responses as defined in [WSDL](../project.wsdl).
Requests with missing or invalid data will return a fault with relevant error message.

Operations that provide the ability to search for Genre or Media are prefixed with _Get_.
Any modification such as adding or editing a Genre or Media are prefixed with _Add_ or _Edit_.
In addition, a client-generated random token (`clientId`) must be provided to operations that modify data to ensure no duplicate changes go through.

Elements marked with an asterisk **\*** are required.

----
## Genre

### GetGenreByIdOrName
This Genre operation returns a genre when given an ID or name of genre.
At least one of these elements is required.

#### Input
* `API_TOKEN` **\*** - [_TokenType_](#tokentype) element, provided by API owner
* `genre` **\*** - [_GenreShortType_](#genreshorttype) element

#### Output
* `genre` **\*** - [_GenreType_](#genretype) element

###### Example request
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ns="http://www.ttu.ee/idu0075/143076/MediaService/1.0">
   <soapenv:Header/>
   <soapenv:Body>
      <ns:getGenreByIdOrNameRequest>
         <ns:API_TOKEN>TESTTOKEN</ns:API_TOKEN>
         <ns:genre>
            <ns:name>drama</ns:name>
         </ns:genre>
      </ns:getGenreByIdOrNameRequest>
   </soapenv:Body>
</soapenv:Envelope>
```

###### Example response
```xml
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
   <S:Body>
      <getGenreByIdOrNameResponse xmlns="http://www.ttu.ee/idu0075/143076/MediaService/1.0">
         <genre>
            <genreId>3</genreId>
            <name>Drama</name>
            <createdAt>2016-12-16T15:04:29.965+02:00</createdAt>
            <description>Things happen here and get repeated over and over for extra effect</description>
         </genre>
      </getGenreByIdOrNameResponse>
   </S:Body>
</S:Envelope>
```

----
### AddNewGenre
This Genre operation creates a new genre when given appopriate parameters.
createdAt is not used on request.

#### Input
* `API_TOKEN` **\*** - [_TokenType_](#tokentype) element, provided by API owner
* `clientId` **\*** - [_TokenType_](#tokentype) element, provided by client
* `name` **\*** - _string_ element, name of Genre
* `description` - _string_ element, description of Genre

#### Output
* `clientId` **\*** - [_TokenType_](#tokentype) element, provided by client
* `genre` **\*** - [_GenreType_](#genretype) element

###### Example request
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ns="http://www.ttu.ee/idu0075/143076/MediaService/1.0">
   <soapenv:Header/>
   <soapenv:Body>
      <ns:addNewGenreRequest>
         <ns:API_TOKEN>TESTTOKEN</ns:API_TOKEN>
         <ns:clientId>newGenre1</ns:clientId>
         <ns:name>New genre</ns:name>
         <ns:description>It's a new genre</ns:description>
      </ns:addNewGenreRequest>
   </soapenv:Body>
</soapenv:Envelope>
```

###### Example response
```xml
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
   <S:Body>
      <addNewGenreResponse xmlns="http://www.ttu.ee/idu0075/143076/MediaService/1.0">
         <clientId>newGenre1</clientId>
         <genre>
            <genreId>7</genreId>
            <name>New genre</name>
            <createdAt>2016-12-17T16:12:36.495+02:00</createdAt>
            <description>It's a new genre</description>
         </genre>
      </addNewGenreResponse>
   </S:Body>
</S:Envelope>
```

----
### EditGenre
This Genre operation edits an existing genre when given appopriate parameters.
createdAt is not used on request.

#### Input
* `API_TOKEN` **\*** - [_TokenType_](#tokentype) element, provided by API owner
* `clientId` **\*** - [_TokenType_](#tokentype) element, provided by client
* `genre` **\*** - [_GenreType_](#genretype) element


#### Output
* `clientId` **\*** - [_TokenType_](#tokentype) element, provided by client
* `genre` **\*** - [_GenreType_](#genretype) element

###### Example request
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ns="http://www.ttu.ee/idu0075/143076/MediaService/1.0">
   <soapenv:Header/>
   <soapenv:Body>
      <ns:editGenreRequest>
         <ns:API_TOKEN>TESTTOKEN</ns:API_TOKEN>
         <ns:clientId>editGenre1</ns:clientId>
         <ns:genre>
            <ns:genreId>3</ns:genreId>
            <ns:name>Drama</ns:name>
            <ns:description>A new description for Drama</ns:description>
         </ns:genre>
      </ns:editGenreRequest>
   </soapenv:Body>
</soapenv:Envelope>
```

###### Example response
```xml
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
   <S:Body>
      <editGenreResponse xmlns="http://www.ttu.ee/idu0075/143076/MediaService/1.0">
         <clientId>editGenre1</clientId>
         <genre>
            <genreId>3</genreId>
            <name>Drama</name>
            <createdAt>2016-12-17T15:04:29.965+02:00</createdAt>
            <description>A new description for Drama</description>
         </genre>
      </editGenreResponse>
   </S:Body>
</S:Envelope>
```

----
### GetAllGenres
This operation provides the ability to search for all Genres with optional filters, such as querying by creation date.

#### Input
* `API_TOKEN` **\*** - [_TokenType_](#tokentype) element, provided by API owner
* `filters` - _sequence_ element
  * `createdBefore` (0..1) - _dateTime_ element, filter Genres created before this datetime.
  * `createdAfter` (0..1) - _dateTime_ element, filter Genres created after this datetime.


#### Output
* `genres` **\*** - _sequence_ element
  * `genre` (0..) **\*** - [_GenreType_](#genretype) element

###### Example request
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ns="http://www.ttu.ee/idu0075/143076/MediaService/1.0">
   <soapenv:Header/>
   <soapenv:Body>
      <ns:getAllGenresRequest>
         <ns:API_TOKEN>TESTTOKEN</ns:API_TOKEN>
         <ns:filters>
            <ns:createdAfter>2016-12-16T15:08:15.853+02:00</ns:createdAfter>
         </ns:filters>
      </ns:getAllGenresRequest>
   </soapenv:Body>
</soapenv:Envelope>
```

###### Example response
```xml
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
   <S:Body>
      <getAllGenresResponse xmlns="http://www.ttu.ee/idu0075/143076/MediaService/1.0">
         <genres>
            <genre>
               <genreId>1</genreId>
               <name>Action</name>
               <createdAt>2016-12-16T16:49:11.838+02:00</createdAt>
               <description>Things happen here explosively</description>
            </genre>
            <genre>
               <genreId>2</genreId>
               <name>Adventure</name>
               <createdAt>2016-12-16T19:04:29.965+02:00</createdAt>
               <description>Things happen here progressively</description>
            </genre>
         </genres>
      </getAllGenresResponse>
   </S:Body>
</S:Envelope>
```

----
## Media

### GetMediaById
This Media operation returns a media when given an unique ID.

#### Input
* `API_TOKEN` **\*** - [_TokenType_](#tokentype) element, provided by API owner
* `mediaId` **\*** - _integer_ element, unique ID of media

#### Output
* `media` **\*** - [_MediaType_](#mediatype) element

###### Example request
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ns="http://www.ttu.ee/idu0075/143076/MediaService/1.0">
   <soapenv:Header/>
   <soapenv:Body>
      <ns:getMediaByIdRequest>
         <ns:API_TOKEN>TESTTOKEN</ns:API_TOKEN>
         <ns:mediaId>5</ns:mediaId>
      </ns:getMediaByIdRequest>
   </soapenv:Body>
</soapenv:Envelope>
```

###### Example response
```xml
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
   <S:Body>
      <getMediaByIdResponse xmlns="http://www.ttu.ee/idu0075/143076/MediaService/1.0">
         <media type="music">
            <mediaId>5</mediaId>
            <name>Claude Debussy - Clair de Lune</name>
            <genres>
               <genre>
                  <id>5</id>
               </genre>
            </genres>
            <description>A most beautiful melodical impressionist piece https://www.youtube.com/watch?v=CvFH_6DNRCY</description>
         </media>
      </getMediaByIdResponse>
   </S:Body>
</S:Envelope>
```

----
### AddNewMedia
This Media operation creates a new media when given appopriate parameters.

#### Input
* `API_TOKEN` **\*** - [_TokenType_](#tokentype) element, provided by API owner
* `clientId` **\*** - [_TokenType_](#tokentype) element, provided by client
* `type` **\*** - [_TypeOfMediaType_](#typeofmediatype) element, type of Media
* `name` **\*** - _string_ element, name of Media
* `description` - _string_ element, description of Media
* `genres` **\*** - _sequence_ element
  * `genre` (1..5) **\*** - [_GenreShortType_](#genreshorttype) element, genre of media

#### Output
* `clientId` **\*** - [_TokenType_](#tokentype) element, provided by client
* `media` **\*** - [_MediaType_](#mediatype) element

###### Example request
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ns="http://www.ttu.ee/idu0075/143076/MediaService/1.0">
   <soapenv:Header/>
   <soapenv:Body>
      <ns:addNewMediaRequest>
         <ns:API_TOKEN>TESTTOKEN</ns:API_TOKEN>
         <ns:clientId>newMedia1</ns:clientId>
         <ns:type>music</ns:type>
         <ns:name>New media</ns:name>
         <ns:genres>
            <!--1 to 5 repetitions:-->
            <ns:genre>
               <ns:name>action</ns:name>
            </ns:genre>
            <ns:genre>
               <ns:id>5</ns:id>
            </ns:genre>
         </ns:genres>
      </ns:addNewMediaRequest>
   </soapenv:Body>
</soapenv:Envelope>
```

###### Example response
```xml
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
   <S:Body>
      <addNewMediaResponse xmlns="http://www.ttu.ee/idu0075/143076/MediaService/1.0">
         <clientId>newMedia1</clientId>
         <media type="music">
            <mediaId>6</mediaId>
            <name>New media</name>
            <genres>
               <genre>
                  <id>1</id>
               </genre>
               <genre>
                  <id>5</id>
               </genre>
            </genres>
         </media>
      </addNewMediaResponse>
   </S:Body>
</S:Envelope>
```

----
### AddMediaRating
This Media operation adds a rating to media.

#### Input
* `API_TOKEN` **\*** - [_TokenType_](#tokentype) element, provided by API owner
* `clientId` **\*** - [_TokenType_](#tokentype) element, provided by client
* `mediaId` **\*** - [_TypeOfMediaType_](#typeofmediatype) element, ID of Media
* `rating` **\*** - [_RatingType_](#ratingtype) element, rating to give to specified Media

#### Output
* `clientId` **\*** - [_TokenType_](#tokentype) element, provided by client
* `media` **\*** - [_MediaType_](#mediatype) element, `rating` added to `aggregateRating`

###### Example request
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ns="http://www.ttu.ee/idu0075/143076/MediaService/1.0">
   <soapenv:Header/>
   <soapenv:Body>
      <ns:addMediaRatingRequest>
         <ns:API_TOKEN>TESTTOKEN</ns:API_TOKEN>
         <ns:clientId>mediarating1</ns:clientId>
         <ns:mediaId>2</ns:mediaId>
         <ns:rating>60.0</ns:rating>
      </ns:addMediaRatingRequest>
   </soapenv:Body>
</soapenv:Envelope>
```

###### Example response
```xml
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
   <S:Body>
      <addMediaRatingResponse xmlns="http://www.ttu.ee/idu0075/143076/MediaService/1.0">
         <clientId>mediarating1</clientId>
         <media type="movie">
            <mediaId>2</mediaId>
            <name>Fast &amp; Furious 1337</name>
            <genres>
               <genre>
                  <id>2</id>
               </genre>
            </genres>
            <aggregateRating>60.0</aggregateRating>
            <description>It's pretty fast (and furious)</description>
         </media>
      </addMediaRatingResponse>
   </S:Body>
</S:Envelope>
```

----
### EditMedia
This Media operation edits an existing media when given appopriate parameters.

#### Input
* `API_TOKEN` **\*** - [_TokenType_](#tokentype) element, provided by API owner
* `clientId` **\*** - [_TokenType_](#tokentype) element, provided by client
* `media` **\*** - [_MediaType_](#mediatype) element

#### Output
* `clientId` **\*** - [_TokenType_](#tokentype) element, provided by client
* `media` **\*** - [_MediaType_](#mediatype) element

###### Example request
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ns="http://www.ttu.ee/idu0075/143076/MediaService/1.0">
   <soapenv:Header/>
   <soapenv:Body>
      <ns:editMediaRequest>
         <ns:API_TOKEN>TESTTOKEN</ns:API_TOKEN>
         <ns:clientId>editMedia1</ns:clientId>
         <ns:media type="tv-series">
            <ns:mediaId>4</ns:mediaId>
            <ns:name>Indiana Jones and a TV series</ns:name>
            <ns:genres>
               <!--1 to 5 repetitions:-->
               <ns:genre>
                  <ns:id>1</ns:id>
               </ns:genre>
               <ns:genre>
                  <ns:id>2</ns:id>
               </ns:genre>   
            </ns:genres>
            <ns:description>Yet another installment of the adventurer</ns:description>
         </ns:media>
      </ns:editMediaRequest>
   </soapenv:Body>
</soapenv:Envelope>
```

###### Example response
```xml
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
   <S:Body>
      <editMediaResponse xmlns="http://www.ttu.ee/idu0075/143076/MediaService/1.0">
         <clientId>editMedia1</clientId>
         <media type="tv-series">
            <mediaId>4</mediaId>
            <name>Indiana Jones and a TV series</name>
            <genres>
               <genre>
                  <id>1</id>
               </genre>
               <genre>
                  <id>2</id>
               </genre>
            </genres>
            <description>Yet another installment of the adventurer</description>
         </media>
      </editMediaResponse>
   </S:Body>
</S:Envelope>
```

----
### GetMediaByGenre
This operation provides the ability to search for all Media associated by a Genre.

#### Input
* `API_TOKEN` **\*** - [_TokenType_](#tokentype) element, provided by API owner
* `genre` **\*** - [_GenreShortType_](#genreshorttype) element

#### Output
* `media` **\*** - _sequence_ element
  * `media` (0..) **\*** - [_MediaType_](#mediatype) element

###### Example request
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ns="http://www.ttu.ee/idu0075/143076/MediaService/1.0">
   <soapenv:Header/>
   <soapenv:Body>
      <ns:getMediaByGenreRequest>
         <ns:API_TOKEN>TESTTOKEN</ns:API_TOKEN>
         <ns:genre>
            <ns:name>action</ns:name>
         </ns:genre>
      </ns:getMediaByGenreRequest>
   </soapenv:Body>
</soapenv:Envelope>
```

###### Example response
```xml
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
   <S:Body>
      <getMediaByGenreResponse xmlns="http://www.ttu.ee/idu0075/143076/MediaService/1.0">
         <media>
            <media type="other">
               <mediaId>3</mediaId>
               <name>Some OTHER type media</name>
               <genres>
                  <genre>
                     <id>1</id>
                  </genre>
                  <genre>
                     <id>2</id>
                  </genre>
                  <genre>
                     <id>3</id>
                  </genre>
                  <genre>
                     <id>4</id>
                  </genre>
                  <genre>
                     <id>5</id>
                  </genre>
               </genres>
               <description>Contains all 5 initial genres</description>
            </media>
            <media type="tv-series">
               <mediaId>4</mediaId>
               <name>Indiana Jones and a TV series</name>
               <genres>
                  <genre>
                     <id>1</id>
                  </genre>
                  <genre>
                     <id>2</id>
                  </genre>
               </genres>
               <description>Yet another installment of the adventurer</description>
            </media>
            <media type="music">
               <mediaId>6</mediaId>
               <name>New media</name>
               <genres>
                  <genre>
                     <id>1</id>
                  </genre>
                  <genre>
                     <id>5</id>
                  </genre>
               </genres>
            </media>
         </media>
      </getMediaByGenreResponse>
   </S:Body>
</S:Envelope>
```

----
### GetAllMedia
This operation provides the ability to search for all Media associated by a Genre.

#### Input
* `API_TOKEN` **\*** - [_TokenType_](#tokentype) element, provided by API owner
* `filters` - _sequence_ element
  * `type` (0..1) - [_TypeOfMediaType_](#typeofmediatype) element, filter Media by type
  * `keywords` (0..1) - _string_ element, filter Media by keywords separated by space (searches from name and description of Media).
  * `genres` (0..1) - _sequence_ element, filter Media by genres
    * `genre` (1..) **\*** - [_GenreShortType_](#genreshorttype) element, genre of media
  * `ratingLessThan` (0..1) - [_RatingType_](#ratingtype) element, filter Media by ratings lower than this
  * `ratingMoreThan` (0..1) - [_RatingType_](#ratingtype) element, filter Media by ratings higher than this

#### Output
* `media` **\*** - _sequence_ element
  * `media` (0..) **\*** - [_MediaType_](#mediatype) element

###### Example request
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ns="http://www.ttu.ee/idu0075/143076/MediaService/1.0">
   <soapenv:Header/>
   <soapenv:Body>
      <ns:getAllMediaRequest>
         <ns:API_TOKEN>TESTTOKEN</ns:API_TOKEN>
         <ns:filters>
            <ns:type>movie</ns:type>
            <ns:keywords>furious adventurer</ns:keywords>
         </ns:filters>
      </ns:getAllMediaRequest>
   </soapenv:Body>
</soapenv:Envelope>
```

###### Example response
```xml
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
   <S:Body>
      <getAllMediaResponse xmlns="http://www.ttu.ee/idu0075/143076/MediaService/1.0">
         <media>
            <media type="movie">
               <mediaId>2</mediaId>
               <name>Fast &amp; Furious 1337</name>
               <genres>
                  <genre>
                     <id>2</id>
                  </genre>
               </genres>
               <aggregateRating>60.0</aggregateRating>
               <description>It's pretty fast (and furious)</description>
            </media>
         </media>
      </getAllMediaResponse>
   </S:Body>
</S:Envelope>
```

----
# Types
Description of parameters of types

## TokenType
Token type used for such elements like `API_TOKEN` and `clientId`

_string_ type
* pattern `^[A-Za-z0-9]{8,16}$`

## TypeOfMediaType
An enumeration of types that can be associated with [Media](#mediatype)

_string enumeration_ type

##### Enumeration
* book
* movie
* music
* tv-series
* other

## RatingType
Rating type used in [Media](#mediatype)

_decimal_ type
* fraction digits - `1`
* lowest value - `0.0`
* highest value - `100.0`

## GenreShortType
A short type of [Genre](#genretype), used to associate them with [Media](#mediatype) or for searching

_sequence_ type
* `id` - _integer_ element, ID of [Genre](#genretype)
* `name` - _string_ element, case insensitive name of [Genre](#genretype)

## GenreType
Main type used for Genres

_sequence_ type
* `genreId` **\*** - _integer_ element, ID of Genre
* `name` **\*** - _string_ element, name of Genre
* `description` - _string_ element, description of Genre
* `createdAt` - _dateTime_ element, creation date of Genre

## MediaType
Main type used for Media

##### Attributes
* `type` **\*** - [TypeOfMediaType](#typeofmediatype) element, the type of Media

_sequence_ type
* `mediaId` **\*** - _integer_ element, ID of Media
* `name` **\*** - _string_ element, name of Media
* `genres` **\*** - _sequence_ element, filter Media by genres
  * `genre` (1..5) **\*** - [_GenreShortType_](#genreshorttype) element, genre of Media
* `aggregateRating` - [RatingType](#ratingtype) element, aggregated rating of Media
* `description` - _string_ element, description of Media
