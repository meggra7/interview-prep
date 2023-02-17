public class Wiki {

    // Map
    private Map<Integer,ArticleContainer> storage;

    // Linked list
    private Node createdHead;
    private Node createdTail;
    private Node modifiedHead;
    private Node modifiedTail;
    
    // Article ID incrementor
    private int lastIdAssigned;

    // Constuctor
    public Wiki() {
        this.storage = new HashMap<Integer,ArticleContainer>();
        this.createdHead = null;
        this.createdTail = null;
        this.modifiedHead = null;
        this.modifiedTail = null;
        this.lastIdAssigned = 0;
    }

    // METHODS

    // Create article
    public void createArticle(String text) {

        // Get id for this article, and increment key for next article
        int currentId = ++this.lastIdAssigned;

        // Create new article object
        Article article = new Article();
        article.id = currentId;
        article.text = text;
        article.dateCreated = new Date();
        article.dateModified = new Date();

        // Add article to created list and get resulting node
        Node createdNode = addArticleToCreatedList(article);

        // Add article to modified list and get resulting node
        Node modifiedNode = addArticleToModifiedList(article);

        // Create new container for article
        ArticleContainer container = new ArticleContainer();
        container.article = article;
        container.createdNode = createdNode;
        container.modifiedNode = modifiedNode;

        // Add container to storage map
        storage.put(article.id, container);
    } 

    private Node addArticleToCreatedList(article) {

        // Create new created node
        Node nodeToAdd = new Node();
        nodeToAdd.article = article;
        nodeToAdd.prev = null;
        nodeToAdd.next = null;

        if (this.createdHead == null) {
            // List is empty, set as head and tail
            this.createdHead = nodeToAdd;
            this.createdTail = nodeToAdd;
        } else {
            // List has at least one article. Cross reference with current head and set as new head.
            this.createdHead.prev = nodeToAdd;
            nodeToAdd.next = this.createdHead;
            this.createdHead = nodeToAdd;
        }

        // Return node that we created
        return nodeToAdd;
    }

    private Node addArticleToModifiedList(article) {

        // Create new modified node
        Node nodeToAdd = new Node();
        nodeToAdd.article = article;
        nodeToAdd.prev = null;
        nodeToAdd.next = null;

        if (this.modifiedHead == null) {
            // List is empty, set as head and tail
            this.modifiedHead = nodeToAdd;
            this.modifiedHead = nodeToAdd;
        } else {
            // List has at least one article. Cross reference with current head and set as new head.
            this.modifiedHead.prev = nodeToAdd;
            nodeToAdd.next = this.modifiedHead;
            this.modifiedHead = nodeToAdd;
        }

        // Return node that we created
        return nodeToAdd;
    }

    // Modify article
    public void modifyArticle(int id, String text) {

        // Get container from storage
        ArticleContainer container = storage.get(id);

        // Modify article
        Article articleToModify = container.article;
        articleToModify.text = text;
        articleToModify.dateModified = new Date();
    
        // Update position in modified list
        updateModifiedPosition(container.modifiedNode);
    }

    private void updateModifiedPosition(Node modifiedNode) {

        // Only need to update modified list if at least two items in list & item isn't already the head
        if (modifiedHead.next != null && modifiedHead != modifiedNode) {
        
            // Temporarily "delete" modified node from list
     		if (modifiedNode.next != null) {
      		    modifiedNode.next.prev = modifiedNode.prev;
    		} else {
	      	    modifiedTail = modifiedTail.prev;
	    	}

            // We've already checked to see that this node isn't the head, so prev node should not be null
            modifiedNode.prev.next = modifiedNode.next;
    
            // Re-add node at beginning of modified list
            modifiedHead.prev = modifiedNode;
            modifiedNode.next = modifiedHead;
            modifiedHead = modifiedNode;
        }
    }

    // Delete article
    public void deleteArticle() {

    }

    // Get entries by creation date
    public void getArticlesByCreation() {

    }

    // Get last 10 modified
    public void getLastModified() {

    }

    // Search by ID
    public Article getArticle(int id) {
        return storage.get(id).article;
    }

    // CLASSES

    // Article container
    private static class ArticleContainer {
        Article article;
        Node createdNode;
        Node modifiedNode;
    }

    // Article 
    private static class Article {
        int id;
        String text;
        Date dateCreated;
        Date dateModified;
    }

    // Node
    private static class Node {
        Article article;
        Node prev;
        Node next;
    } 

}