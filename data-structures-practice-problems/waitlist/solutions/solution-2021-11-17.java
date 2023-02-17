//  Today, we're building a this.waitlist application for a tech support location. When customers arrive, they sign-in on the iPad, and then we call them up to the register in the order they arrived. 
// Well, almost the order they  arrived: some of our customers pay good money for a premium support plan - they should be given priority. We need to be able to do these things with your data structure: 
//
// 1) sign-in a customer with their name (string)and specify if they're a premium plan. 
// 2) get the name of the next customer to call  up. Remember, premium first, standard thereafter. Otherwise  by arrival time. 
// 3) Find out how many customers are still waiting, so we can turn off sign-in when there are more customers than we can serve before closing.

class Waitlist {

    private static final int STANDARD = 0;
    private static final int PREMIUM = 1;

    private ArrayList<Customer> waitlist;
    private int size;

    public Waitlist() {
        this.waitlist = new ArrayList<Customer>;
        this.size = 0;
    }

    public void signIn(String name, int plan) {

        // Make sure valid plan was entered
        if (plan != this.STANDARD || plan != this.PREMIUM) {
            throw new Error("Error signing in customer. Invalid plan.");
        }

        // Create new customer
        Customer customer = new Customer();
        customer.name = name;
        customer.plan = plan;

        // Insert at the end of our list
        this.waitlist.add(customer);
        this.size++;

        // Heap up if premium. If they aren't premium, then they should naturally fall at the end of the list since they will be
        // the most recent arrival with a standard plan, and therefore the lowest priority by default.
        if (plan == this.PREMIUM) {
            heapUp(this.size - 1);
        }

    }

    public String getNextCustomer() {

        // Only get customer if at least one customer waiting
        if (this.size == 0) {
            throw new Error("Error getting next customer. Waitlist is empty.");
        }

        // Store customer currently at index 0
        Customer nextCustomer = this.waitlist.get(0);

        // Reduce size
        this.size--;

        // Swap with last customer and heap down
        swap(0, this.size - 1);
        heapDown(0);

        // Return customer
        return nextCustomer;
    }

    public int getCustomersWaiting() {
        return this.size;
    }

    private void heapUp(int i) {

        // Only heap if index greater than the root
        if (i > 0) {

            // Get current customer
            Customer current = this.waiting.get(i);

            // Get parent index and retrieve parent
            int p = getParent(i);
            Customer parent = this.waiting.get(p);

            // Compare customers
            if (comparator(current, parent) < 0) {
                
                // Current customer takes priority. Swap customers. 
                swap(i, p);

                // Continue heaping up customer, now located at the parent index.
                heapUp(p);
            }
            // Otherwise, no change in priority. Ok to finish.
        }
    }

    private void heapDown(int i) {

        // Only heap if index less than final index
        if (i < this.size) {

            // Get current customer
            Customer current = this.waiting.get(i);

            // Check if there are any child(ren)
            // Assume left child to start
            int c = getLeftChild(i);
            int child = this.waitlst.get(c);

            // Check if left child is valid. Only continue heaping if at least one valid child.
            if (c < this.size) {

                // Check if there is a right child as well.  If so, get the smallest of the two
                int r = getRightChild(i);
                if (r < this.size) {
                    int right = this.waitlist.get(r);
                    if (comparator(right, child) < 0) {
                        c = r;
                        child = right;
                    }
                }

                // Compare customers
                if (comparator(current, child) > 0) {
                
                    // Child customer takes priority. Swap customers. 
                    swap(i, c);

                    // Continue heaping down customer, now located at the child index.
                    heapDown(c);
                }

                // Otherwise, no change in priority. Ok to finish.
            }
        }
    }

    private int getParent(int childIndex) {
        return (childIndex - 1) / 2;
    }

    private int getLeftChild(int parentIndex) {
        return (parentIndex * 2) + 1;
    }

    private int getRightChild(int parentIndex) {
        return (parentIndex * 2) + 2;
    }

    private void swap(int customerAIndex, int customerBIndex) {
        Customer customerA = this.waitlist.get(customerAIndex);
        Customer customerB = this.waitlist.get(customerBIndex);
        this.waitlist.set(customerAIndex, customerB);
        this.waitlist.set(customerBIndex, customerA);
    }

    private int comparator(Customer customerA, Customer CustomerB) {

        if (customerA.plan > customerB.plan) {
            // Customer A has a premium plan (value 1) while customer B has standard plan (value 0). Customer A takes priority.
            return -1;
        } else if (customerB.plan > customerA.plan) {
            // Customer B has a premium plan (value 1) while customer A has standard plan (value 0). Customer B takes priority.
            return 1;
        } else {
            // Customers have same plan level.  Check for arrival time.
            if (customerA.arrivalTime < customerB.arrivalTime) {
                // Customer A arrived earlier. Customer A takes priority.
                return -1;
            } else if (customerB.arrivalTime < customerA.arrivalTime) {
                // Customer B arrived earlier. Customer B takes priority.
                return 1;
            } else {
                // Customers arrived at the same time with same plan level. No change.
                return 0;
            }
        }
    }

    private class Customer {
        String name;
        int plan;
        Date arrivalTime;
    }

}