import java.lang.Integer;

public class Tree{
	
	private Node root = new Node(Integer.MAX_VALUE, null);
	
	public Tree(){}
	
	public void insert(int x)
	{
		root.add(x);
	}
	
	public int size(int x)
	{
		return root.search(x);
	}
	
	public Node getRoot()
	{
		return root;
	}
	
	class Node //implements Comparable<Integer>
	{
		public Node left;
		public Node middle;
		public Node right;
		public int data1 = Integer.MAX_VALUE;
		public int data2 = Integer.MIN_VALUE;
		public Node parent;
		
		public Node(int data1, Node thePar) {
			parent = thePar;
			this.data1 = data1;
		}
		
		public void add(int x)
		{
			if(x == data1 || x == data2)
				return;
			if(left == null && middle == null && right == null)
			{
				if(data1 == Integer.MAX_VALUE)
					data1 = x;
				else if(data2 == Integer.MIN_VALUE) {
					if(x < data1)
					{
						this.data2 = data1;
						data1 = x;
					}else
						data2 = x;
				}else
				{
					splitLast(x);
					if(parent != null) {
						this.parent.moveUp(this);
					}
				}
				return;
			}if(data2 == Integer.MIN_VALUE)
			{
				if(x<data1)
					left.add(x);
				else right.add(x);
			}else {
				Node xNode = new Node(x, this);
				splitMid(xNode);
			}
		}
		
		public void splitMid(Node x)
		{
			Node temp = null;
			if(x.data1 < data1)
			{
				if(x.data1 < left.data1)
				{
					x.right = left;
				}else if(x.data1 > left.data2)
				{
					x.left = left;
				}else
				{
					temp = new Node(left.data1, x);
					temp.left = left.left;
					temp.right = left.middle;
					x.left = temp;
					temp = new Node(left.data2, x);
					temp.right=left.right;
					x.right= temp;
				}
				this.left = x;
				temp = new Node(data2, this);
				temp.left = middle;
				temp.right =right;
				right = temp;
				data2 = Integer.MIN_VALUE;
				middle = null;
			}else if(x.data1 < data2)
			{
				x.parent = this.parent;
				temp = new Node(data1, x);
				temp.left = this.left;
				temp.right = middle;
				x.left = temp;
				temp = new Node(data2, x);
				temp.right = this.right;
				x.right = temp;
				this.data1 = x.data1;
				this.left = x.left;
				this.right = x.right;
				this.middle = null;
				data2 = Integer.MIN_VALUE;
			}else
			{
				if(x.data1 < right.data1)
				{
					x.right = right;
				}else if(x.data1 > left.data2)
				{
					x.left = right;
				}else
				{
					temp = new Node(right.data1, x);
					temp.left = right.left;
					temp.right = right.middle;
					x.left = temp;
					temp = new Node(right.data2, x);
					temp.right=right.right;
					x.right= temp;
				}
				this.right = x;
				temp = new Node(data1, this);
				temp.left = left;
				temp.right =middle;
				left = temp;
				data1 = data2;
				data2 = Integer.MIN_VALUE;
				middle = null;
			}
		}
		
		public void splitLast(int x)
		{
			if(x < data1)
			{	
				left = new Node(x, this);
				right = new Node(data2, this);
				data2 = Integer.MIN_VALUE;
			}else {
				if(x <data2)
				{
					left = new Node(data1, this);
					right = new Node(data2, this);
					data2 = Integer.MIN_VALUE;
					data1 = x;
				}else
				{
					left = new Node(data1, this);
					right = new Node(x,this);
					data1 = data2;
					data2 = Integer.MIN_VALUE;
				}
			}
	
		}
		
		public void moveUp(Node moveMe) {
			if(moveMe.data1 < this.data1)
			{
				this.data2 = this.data1;
				this.data1 = moveMe.data1;
				this.left = moveMe.left;
				left.parent = this;
				this.middle = moveMe.right;
				middle.parent = this;
			}else
			{
				this.data2 = moveMe.data1;
				this.middle = moveMe.left;
				middle.parent = this;
				this.right = moveMe.right;
				right.parent = this;
			}
		}
		
		public int countSubtree(Node count)
		{
			int counter = 1;
			if(count.data2 != Integer.MIN_VALUE) {
				counter = 2;
			}
			if(count.left != null)
				counter = counter + countSubtree(count.left);
			if(count.right != null)
				counter = counter + countSubtree(count.right);
			if(count.middle != null)
				counter = counter + countSubtree(count.middle);
			return counter;
		}
		
		public int search(int x)
		{
			if(data1 == Integer.MAX_VALUE)
				return 0;
			if(data1 == x || data2 == x)
			{
				return countSubtree(this);
			}else if(left == null && right == null && middle == null)
			{
				return 0;
			}
			else if(x < data1)
			{
				if(left == null)
					return 0;
				return left.search(x);
			}else if(data2 == Integer.MIN_VALUE)
			{
				if(right == null)
					return 0;
				return right.search(x);
			}else if(x > data2)
			{
				if(right == null)
					return 0;
				return right.search(x);
			}else
			{
				if(middle == null)
					return 0;
				return middle.search(x);
			}
		}
		/*@Override
		public int compareTo(Integer o) {
			if(this.data1 < o)
				return -1;
			else if(this.data1 == o)
				return 0;
			else return 1;
		}*/
		
	}

	
}
