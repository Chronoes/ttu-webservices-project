# Definitions

## Genres

Defines genres for media

### Parameters
* id
* name
* description
* created timestamp

### Operations
* get genre by id or name
* add new genre
   * idempotent
* edit genre
  * description or name
* get all genres

_______________________________________

## Media

Attribute: media type

### Parameters
* id
* name
* description
* genres
* aggregate rating

### Operations
* get media by id
* add new media
  * idempotent
* add rating to media
  * idempotent
* edit existing media
* get all media
* get media by genre
