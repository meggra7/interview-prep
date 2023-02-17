class Solution {
  
  private Map<Integer, Entry> storage;
  private int lastKey;
  private Node createdHead;
  private Node createdTail;
  private Node modifiedHead;
  private Node modifiedTail;
  

	public Solution() {
    this.storage = new HashMap<>();
    this.lastKey = 0;
    this.createdHead = null;
    this.createdTail = null;
    this.modifiedHead = null;
    this.modifiedTail = null;
  }
  
  public int addArticle(String text) {
    
    Article newArticle = new Article(lastKey + 1, text);
    lastKey++;
    
    Node createdNode = addArticleToList(newArticle, this.createdHead, this.createdTail);
    Node modifiedNode = addArticleToList(newArticle, this.modifiedHead, this.modifiedTail);
    addArticleToMap(newArticle, createdNode, modifiedNode);
    
    return newArticle.key;
  }
  
  private Node addArticleToList(Article article, Node head, Node tail) {
    
    Node toAdd = new Node(article);
    
    if (head == null) {
      head = toAdd;
      tail = toAdd;
    } else {
      tail.next = toAdd;
      toAdd.previous = tail;
      tail = toAdd;
    }
    
    return toAdd;
  }
  
  private void addArticleToMap(Article newArticle, Node createdNode, Node modifiedNode) {
    
    Entry toAdd = new Entry();
    toAdd.article = newArticle;
    toAdd.createdNode = createdNode;
    toAdd.modifiedNode = modifiedNode;
    this.storage.put(newArticle.key, toAdd);
  }
  
  public void deleteArticle(int key) {
    
    Entry toDelete = this.storage.get(key);
    
    // Remove map reference
    this.storage.remove(key)
      
    // Remove linked list references
    deleteNodeFromList(toDelete.createdNode, this.createdHead, this.createdTail);
    deleteNodeFromList(toDelete.modifiedNode, this.modifiedHead, this.modifiedTail);
  }
  
  private void deleteNodeFromList(Node nodeToDelete, Node head, Node tail) {
    
    if (nodeToDelete.previous != null) {
      nodeToDelete.previous.next = nodeToDelete.next;
    } else {
      head = head.next;
    }
    
    if(nodeToDelete.next != null) {
      nodeToDelete.next.previous = nodeToDelete.previous;
    } else {
      tail = tail.previous;
    }
  }
  
  public void modifyArticle(int key, String text) {
    
    Entry articleToModify = this.storage.get(key);
    
    if (articleToModify != null) {
      
      // Update article
      articleToModify.article.setText(text);
    	articleToModify.article.setModified(new Date());
      
      // Get node from existing modified list
    	Node modifiedNode = articleToModify.modifiedNode;
      
      // Only need to update modified list if at least two items in list & item isn't already the tail
      if (modifiedTail.prev != null && modifiedTail != modifiedNode) {
        
        // Temporarily "delete" modified node from list
     		if (modifiedNode.previous != null) {
      		modifiedNode.previous.next = modifiedNode.next;
    		} else {
	      	modifiedHead = modifiedHead.next;
	    	}   
        // We've already checked to see that this node isn't the tail, so next node should not be null
        modifiedNode.next.previous = modifiedNode.previous;
    
 		   	// Re-add node at end of modified list
	      modifiedTail.next = modifiedNode;
	      modifiedNode.previous = modifiedTail;
	      modifiedTail = modifiedNode;
      }
    }
  }
  
  public List<Article> listAllByDateCreated() {
    
    List<Article> list = new List<Article>;
    
    Node currentNode = this.createdHead;
    while (currentNode != null) {
      list.add(currentNode.article);
      currentNode = currentNode.next;
    }
    
    return list;
  }
  
  public List<Article> listLastNModified(int n) {
    
    List<Article> list = new List<Article>;
    
    Node currentNode = this.modifiedTail;
    for (int i = 0; i < n; i++) {
      if (currentNode == null {
        break; // Break out of loop early as soon as we hit a null node, in the event we don't yet have enough entries to meet requested number.
      } else {
        list.add(currentNode.article);
        currentNode = currentNode.previous; // Walk backwards from tail
      }
    }
          
    return list;
  }
  
  public String getTextOfArticle(int key) {
    
    Entry entry = storage.get(key);
    Article article = entry.article;
    String text = article.getText();
    
    return text;
  }
  
  private static class Node {
    Article article;
    Node previous;
    Node next;
    
    Node(Article article) {
      this.article = article;
    }
  }
  
  private static class Entry {
    Article article;
    Node createdNode;
    Node modifiedNode;
  }
}

class Article {
  
  private int key;
  private String text;
  private Date created;
  private Date modified;
  
  public Article(int key, String text) {
    this.key = key;
    this.text = text;
    this.date = new Date();
    this.modified = new Date();
  }
  
  // Assume Getters and Setters
  public getText() {
    return this.text;
  }
  public void setText(String text) {
    this.text = text;
  }
}