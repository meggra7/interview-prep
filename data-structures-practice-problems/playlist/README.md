# Playlist

## Prompt

Today we're thinking about playlists!  Have you ever been hosting a party, and you don't know what music to put on that everyone will like?  Our new startup is going to fix that!

We'll keep track of the songs that every user has liked or disliked, and allow you to produce playlists based on your party guests.

For now, assume that users and songs are managed elsewhere and may be passed in by id whenever a playlist needs to be created. We'll only be focusing on the playlists themselves in this scenario.

## Requirements

Users should be able to:

- Like or dislike a song. This requires taking in two strings - a user id and song id - and returns nothing. Users are allowed to change their selection and re-like or re-dislike a song, but we'll only maintain the most recent like or dislike per user.
- Get a list of certified bangers. In other words, we should be able to take in a list of user ids and return only the songs that have been liked by EVERYONE on the list.
- Get a list of probable bangers. In other words, we should be able to take in a list of user ids and return songs that have been liked by at least one person on the list but never disliked by anyone on the list.
- Get a list of reaches. In other words, we should be able to take in a list of user ids and return songs that have been both liked and disliked by at least one member of the list.

## Credits

Prompt created by [https://github.com/frankiethekneeman](https://github.com/frankiethekneeman)