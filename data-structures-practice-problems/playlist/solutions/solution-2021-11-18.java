// Okay, so - today we're going to think about playlists.  Have you ever been hosting a party, and you don't know what music to put on so that everyone will like it?  
// Our new startup is going to fix that!  We'll keep track of the songs that every user has liked or disliked, and allow you to produce playlists based on your party guests.  
//
// We need methods to:
// 1.) like/dislike a song.  Take two Strings (userId, songId) and return nothing.  Users are allowed to re-like or re-dislike a song, but we maintain the most recent like/dislike per user.
// 2.) Get a list of certified bangers.  Take a list of userIDs, and return the ids of all the songs that have been liked by everyone on the list.
// 3.) Get a list of probable bangers.  Take a list of userIDs and return the ids of all the songs that have been liked by anyone in the list, but have never been disliked by anyone in the list.
// 4.)  Get a list of reaches.  Take a list of userIDs and return the IDS of all the songs that have been both liked and disliked by at least one member of the list.


class Playlist {

    // For now we'll assume that users are managed elsewhere and may be passed in whenever a playlist needs to be created.
    // User is a private inner class in this initial implementation which wouldn't be accessible elsewhere, so that would need to be corrected 
    // and split to a public class to manage users elsewhere in a real life implementation.
    // User map takes user ID string as the key, and the user object as the value.

    private HashMap<String, User> users;

    public Playlist(HashMap<String, User> users) {    
        this.users = users;
    }

    public void likeSong(String userId, String songId) {

        // Get user object from map using id
        User user = this.users.get(userId);

        if (user != null) {
            // Get the user's liked and disliked lists
            HashSet<String> likedSongs = user.likedSongs;
            HashSet<String> dislikedSongs = user.dislikedSongs;

            // Add song to liked list
            likedSongs.add(songId);

            // Remove song from disliked list if found
            dislikedSongs.remove(songId);
        } else {
            throw new Error("Error liking song. User not found.");
        }
    }

    public void dislikeSong(String userId, String songId) {

        // Get user object from map using id
        User user = this.users.get(userId);

        if (user != null) {
            // Get the user's liked and disliked lists
            HashSet<String> likedSongs = user.likedSongs;
            HashSet<String> dislikedSongs = user.dislikedSongs;

            // Add song to disliked list
            dislikedSongs.add(songId);

            // Remove song from liked list if found
            likedSongs.remove(songId);
        } else {
            throw new Error("Error disliking song. User not found.");
        }
    }

    public HashSet<String> getCertifiedBangers(List<String> userIds) {

        // Check to make sure we have at least one user to pull from
        if (userIds.isEmpty()) {
            throw new Error("Error getting certified bangers. User list is empty");
        }

        // Get INTERSECTION (retainAll) of all liked songs from all users

        // Initialize empty set to make changes without modifying any user's actual set
        // We'll arbitrarily initialize using the first user's liked list.
        User firstUser = this.users.get(userIds.get(0));
        HashSet<String> certifiedBangers = new HashSet<String>(firstUser.likedSongs);

        for (userId : userIds) {

            if (certifiedBangers.isEmpty()) {
                // We've reached a user at some point that does not have any liked songs in common with the other users, so no songs were able to be retained. 
                // Go ahead and break out of loop. No need to continue checking other users.
                break;
            }

            // Get the user from our map
            User user = this.users.get(userId);

            // Run the user's liked songs against our certified bangers set, retaining only the songs that are found on both liked lists.
            certifiedBangers.retainAll(user.likedSongs);
        }

        // Return set of liked songs that were retained from all users
        return certifiedBangers;
    }

    public HashSet<String> getProbableBangers(List<String> userIds) {

        // Check to make sure we have at least one user to pull from
        if (userIds.isEmpty()) {
            throw new Error("Error getting probable bangers. User list is empty");
        }

        // Get UNION of all liked & disliked songs
        // Initialize sets of all liked and disliked songs
        HashSet<String> allLikedSongs = new HashSet<String>();
        HashSet<String> allDislikedSongs = new HashSet<String>();

        for (userId : userIds) {

            // Get the user from our map
            User user = this.users.get(userId);

            // Add the user's liked and disliked songs to our union sets
            allLikedSongs.addAll(user.likedSongs);
            allDislikedSongs.addAll(user.dislikedSongs);
        }

        // Get DIFFERENCE of all liked songs and disliked songs
        allLikedSongs.removeAll(allDislikedSongs);

        // Return all liked songs that remain after removing disliked songs.
        return allLikedSongs;
    }

    public HashSet<String> getReaches(List<String> userIds) {

        // Check to make sure we have at least one user to pull from
        if (userIds.isEmpty()) {
            throw new Error("Error getting reaches. User list is empty");
        }

        // Get UNION of all liked & disliked songs
        // Initialize set of all songs
        HashSet<String> allSongs = new HashSet<String>();

        for (userId : userIds) {

            // Get the user from our map
            User user = this.users.get(userId);

            // Add the user's liked and disliked songs to our union set
            allSongs.addAll(user.likedSongs);
            allSongs.addAll(user.dislikedSongs);
        }

        // Return all songs collected from users.
        return allSongs;
    } 

    private static class User {
        String userId;
        HashSet<String> likedSongs; // Set of liked song IDs
        HashSet<String> dislikedSongs; // Set of disliked song IDs
    }
}