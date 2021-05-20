import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/*
HEAP IS A COMPLETE BINARY TREE, And 
heap will stored in an array of size = 2^(h+1)-1, where h = height(0-based) of the complete binary tree. 

If the parent has index = i, then left child's index = 2*i+1, and right child's index = 2*i+2

Number of nodes at a height h(0-based) in a complete binary tree = 2^h
Total number of nodes in a complete binary tree = 2^(h+1) - 1;
In a perfect binary tree, No. of internal nodes = (No. of leaves - 1)

Therefore, In the worst case, all the levels of a tree will be completely filled, Therefore it will be a Perfect Binary Tree(PBT).

Now, if the height of a PBT = h, then No. of Nodes = 2^(h+1)-1 = size of array needed to store heap

Height of binary tree = log2N, base = 2(because of binary tree), N = number of nodes in binary tree

In a complete binary tree, 
Range(index-0 based) of leaves = (N/2) to (N-1) and 
Range(index-0 based) of internal nodes = 0 to (N/2)-1, 
where N = total number of nodes

MAX_HEAP: If elements are in decreasing order, then max heap
MIN_HEAP: If elements are in increasing order, then min heap

Parent of ith node:
if(i%2==0) then its parent if at (i/2)-1 index
else its parent is at i/2 index
*/
public class Heap_impl{

    public static void main(String[] args) {
    Scanner s = new Scanner(System.in);
    int N = s.nextInt();
    ArrayList<Integer>heap = new ArrayList<>();
    for(int i=0;i<N;i++)heap.add(s.nextInt());
    s.close();

    Max_Heap mp = new Max_Heap();
    mp.buildHeap(heap);
    System.out.println("New Heap = "+heap);

    mp.decreaseKey(heap, 0, 3);
    System.out.println("Decrease key value at 0th index, then heap = "+heap);

    mp.increaseKey(heap, 0, 9);
    System.out.println("Increase key value at 0th index, then heap = "+heap);

    mp.insertNode(heap, 10);
    System.out.println("Inserting 10 in heap, then heap = "+heap);

    ArrayList<Integer>temp_heap1 = new ArrayList<>(heap);
    ArrayList<Integer>sorted_asc = mp.HeapSortAscending(temp_heap1);
    System.out.println("Heap after sorting in ascending order = "+sorted_asc);

    ArrayList<Integer>temp_heap2 = new ArrayList<>(heap);
    ArrayList<Integer>sorted_desc = mp.HeapSortDesc(temp_heap2);
    System.out.println("Heap after sorting in descending order = "+sorted_desc);

    }
}
class Max_Heap{

    //TC = O(logN), N = arr.length = number of nodes
    //for each i, we are assuming that it's left and right sub-trees are heapified already:Percolate down
    public void max_heapify(ArrayList<Integer>heap, int i){
        int left = 2*i+1;
        int right = 2*i+2;
        int largest;
        if(left<heap.size() && heap.get(left)>heap.get(i)){
            largest=left;
        }else{
            largest = i;
        }
        if(right<heap.size() && heap.get(right)>heap.get(largest)){
            largest = right;
        }
        if(largest!=i){
            //swap A[i] with A[largest]
            int val_at_i = heap.get(i);
            heap.set(i, heap.get(largest));
            heap.set(largest, val_at_i);

            //now call for childs
            max_heapify(heap, largest);
        }

    }

    //TC = O(N)
    public void buildHeap(ArrayList<Integer>heap){
        int N = heap.size();
        //internal nodes range from 0 to (N/2)-1
        for(int i=(N/2)-1;i>=0;i--){
            max_heapify(heap, i);
        }
    }

    //TC = O(logN)
     public int extractMax(ArrayList<Integer>heap){
         if(heap.size()<1){
            System.out.println("Not a valid input for Maximum Extraction operation");
            return Integer.MIN_VALUE;
         }
         /*
         Steps:
         1] Save the max value(root value)
         2] copy last to root
         3] Remove last
         4] heapify root
         */
        int ans = heap.get(0);
        heap.set(0,heap.get(heap.size()-1));
        heap.remove(heap.size()-1);
        max_heapify(heap, 0);
        return ans;
     }

      //TC = O(logN)
     public void increaseKey(ArrayList<Integer>heap, int old_val_index, int new_val){
         if(heap.size()<1 || new_val<=heap.get(old_val_index)){
             System.out.println("Not a valid input for increment operation");
             return;
         }
         /*
         Steps:
         1] Update node value
         2] Percolate Up till:
            [i] we find a parent whose value is greater than this current node
            [ii] current node itself becomes the root of the tree
         */
        heap.set(old_val_index,new_val);

        //percolate up
        while(old_val_index>0 && heap.get(old_val_index)>heap.get(old_val_index%2==0?(old_val_index/2)-1:old_val_index/2)){
            //swap the value with its correct parent
            if(old_val_index%2!=0){
                int value_at_old_val_index = heap.get(old_val_index);
                heap.set(old_val_index, heap.get(old_val_index/2));
                heap.set(old_val_index/2,value_at_old_val_index);
            }else{
                int value_at_old_val_index = heap.get(old_val_index);
                heap.set(old_val_index, heap.get((old_val_index/2)-1));
                heap.set((old_val_index/2)-1,value_at_old_val_index);
            }
            
            //update:one step above to its correct parent
            if(old_val_index%2==0){
                old_val_index = (old_val_index/2)-1;
            }else{
                old_val_index /=2;
            }
            
        }

     }
     //TC = O(logN)
     public void decreaseKey(ArrayList<Integer>heap, int old_val_index, int new_val){
        if(heap.size()<1 || new_val >= heap.get(old_val_index)){
            System.out.println("Not a valid input for Decrement operation");
            return;
        }
        /*
         Steps:
         1] Update node value
         2] max_heapify at this index
         */

        heap.set(old_val_index, new_val);
        max_heapify(heap, old_val_index);
     }

     //TC = O(logN)
     public void insertNode(ArrayList<Integer>heap, int val_to_insert){
         /*
         Steps:
         1] Add value in the last of the heap
         2] Then percolate up
         */
        heap.add(val_to_insert);

        //percolate up
        int last_index=heap.size()-1;
        while(last_index>0 && heap.get(last_index)>heap.get(last_index%2==0?(last_index/2)-1:last_index/2)){
            //swap value with its parent
            if(last_index%2!=0){
                int value = heap.get(last_index);
                heap.set(last_index, heap.get(last_index/2));
                heap.set(last_index/2, value);
            }else{
                int value = heap.get(last_index);
                heap.set(last_index, heap.get((last_index/2)-1));
                heap.set((last_index/2)-1, value);
            }
            

            //update, send idx = its parent, if idx is odd, then its parent is at idx/2 and if it's even then its parent is at (idx/2) - 1
            if(last_index%2==0){
                last_index=(last_index/2)-1;
            }else{
                last_index/=2;
            }
            
        }
     }

     //TC = O(N*logN)
     public ArrayList<Integer> HeapSortDesc(ArrayList<Integer>heap){

        ArrayList<Integer>ans = new ArrayList<>();
        int initial_size = heap.size();
        for(int i=0;i<initial_size;i++){
            int temp = extractMax(heap);
            ans.add(temp);
        }
        return ans;

     }
     
     //TC = O(N*logN)
     public ArrayList<Integer>HeapSortAscending(ArrayList<Integer>heap){
         ArrayList<Integer>ans=new ArrayList<>();
         int initial_size = heap.size();
         for(int i=0;i<initial_size;i++){
             ans.add(extractMax(heap));
         }
        Collections.reverse(ans);
        return ans;
     }

     

}
