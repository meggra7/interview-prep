// Let's build a comment system!  Your solution will only hold comments on one "thing".  
// At first, we only need to retrieve comments by creation date - but we need to be able to get them in ascending or descending order.
// After that, we'd like to add "replies".  Comments that come in can have a parent.  
// Replies do not appear on their own in the main listings, but should be returned as a part of their parent comment - always sorted by date created.
// Replies are always oldest to newest
// Initial requirements are to add comments and replies only, deleting and modifying are optional improvements

class CommentSystem {

    private Map<Date, CommentContainer> storage;
    private CommentNode commentHead;
    private CommentNode commentTail;

    /** Constructor */
    public CommentSystem() {
        this.storage = new HashMap();
        this.commentHead = null;
        this.commentTail = null;
    }

    public void addComment(String text) {
        
        // Create a comment object
        Comment comment = new Comment();
        comment.text = text;
        comment.timestamp = new Date();

        // Add comment to list, and get the resulting node
        CommentNode commentNode = addCommentToList(comment);

        // Add comment to map
        addCommentToMap(comment, commentNode);
    }

    private CommentNode addCommentToList(Comment comment) {

        CommentNode toAdd = new CommentNode();
        toAdd.comment = comment;

        if (this.commentHead == null) {
            this.commentHead = toAdd;
            this.commentTail = toAdd;
        } else {
            toAdd.prev = this.commentTail;
            this.commentTail.next = toAdd;
            this.commentTail = toAdd;
        }

        return toAdd;
    }

    private void addCommentToMap(Comment comment, CommentNode commentNode) {

        CommentContainer commentContainer = new CommentContainer();
        commentContainer.comment = comment;
        commentContainer.node = commentNode;

        storage.put(comment.key, commentContainer);        
    }

    public void addReply(Comment comment, String text) {

        // Create a new reply object
        Reply reply = new Reply();
        reply.text = text;
        reply.timestamp = new Date();

        // Add the reply to the comment's list
        // Create new node
        ReplyNode toAdd = new ReplyNode();
        toAdd.reply = reply;

        // Append to end of the list
        if (comment.replyHead == null) {
            comment.replyHead = toAdd;
            comment.replyTail = toAdd;
        } else {
            toAdd.prev = comment.replyTail;
            comment.replyTail.next = toAdd;
            comment.replyTail = toAdd;
        }
    }

    public List<Comment> getCommentsFromMostRecent() {

        List<Comment> list = new List<Comment>;

        // Make sure list isn't empty
        if (commentTail != null) {

            // Start from commentTail moving backward
            CommentNode current = commentTail;
            while (current != null) {

                // Add comment to list
                list.add(current.comment);

                // Step comment node backward
                current = current.prev;
            }
        }

        return list;
    }

    public List<Comment> getCommentsfromLeastRecent() {

        List<Comment> list = new List<Comment>;

        // Make sure list isn't empty
        if (commentHead != null) {

            // Start from commentHead moving forward
            CommentNode current = commentHead;
            while (current != null) {

                // Add comment to list
                list.add(current.comment);

                // Step comment node forward
                current = current.next;
            }
        }

        return list;

    }

    /** Returns a list of replies for a specified comment, always ordered oldest to newest */
    public List<Reply> getRepliesForComment(Comment comment) {

        List<Reply> list = new List<Reply>;

        // Make sure list isn't empty
        if (comment.replyHead != null) {

            // Start from commentHead moving forward
            ReplyNode current = comment.replyHead;
            while (current != null) {

                // Add reply to list
                list.add(current.reply);

                // Step reply node forward
                current = current.next;
            }
        }

        return list;
    }

    private static class CommentContainer {
        Comment comment;
        CommentNode node;
    }

    private static class Comment {
        String text;
        Date timestamp;
        ReplyNode replyHead;
        ReplyNode replyTail;
    }

    private static class CommentNode {
        Comment comment;
        CommentNode prev;
        CommentNode next;
    }

    private static class Reply {
        String text;
        Date timestamp;
    }

    private static class ReplyNode {
        Reply reply;
        ReplyNode prev;
        ReplyNode next;
    }

}