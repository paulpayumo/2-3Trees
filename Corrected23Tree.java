package TreeTest;

import java.lang.Integer;
import java.util.ArrayList;
import java.util.Collections;

public class Tree{
	
	private Node root = new Node();
	
	public Tree(){}
	
	public boolean insert(int x)
	{
		return root.add(x);
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
		public ArrayList<Node> children = new ArrayList<Node>();
		public ArrayList<Integer> data = new ArrayList<Integer>();
		public Node parent = null;
	
		public Node() {}
		
		public Node(int data1, Node thePar) {
			data.add(data1);
			parent = thePar;
		}
		
		public boolean add(int x)
		{
			if(data.contains(x))
				return false;
			if(children.size() == 0 && data.size() < 2)
			{
				data.add(x);
				Collections.sort(data);
			}else if(children.size() == 0 && data.size() == 2)
			{	
				data.add(x);
				Collections.sort(data);
				this.splitLast();
				if(parent != null)
					parent.pushUp(this);
			}else
			{
				for(int i = 0; i < data.size(); i++)
				{
					if(x < data.get(i))
					{
						return children.get(i).add(x);
					}
				}
				return children.get(data.size()).add(x);
			}
			return true;
		}
		
		public void splitLast()
		{
			Node temp = new Node(data.get(0), this);
			children.add(temp);
			temp = new Node(data.get(2),this);
			children.add(temp);
			data.remove(0);
			data.remove(1);
		}
		
		public void pushUp(Node temps)
		{
			
			if(data.size() == 1)
			{
				children.remove(temps);
				data.add(temps.data.get(0));
				Collections.sort(data);
				children.add(temps.children.get(0));
				children.get(1).parent = this;
				children.add(temps.children.get(1));
				children.get(2).parent = this;
				if(data.get(0) == temps.data.get(0))
				{
					children.add(children.get(0));
					children.remove(children.get(0));
				}
			}else
			{
				splitMiddle(temps);
				if(parent != null) {
					parent.pushUp(this);;
				}
			}
		}
		
		public void splitMiddle(Node temps)
		{
			int tempData = temps.data.get(0);
			if(tempData < data.get(0))
			{
				Node temp = new Node(data.get(1), this);
				data.remove(1);
				temp.children.add(children.get(1));
				temp.children.add(children.get(2));
				children.removeAll(children);
				children.add(temps);
				children.add(temp);
			}else if(tempData >data.get(1))
			{
				Node temp = new Node(data.get(0), this);
				data.remove(0);
				temp.children.add(children.get(0));
				temp.children.add(children.get(1));
				children.removeAll(children);
				children.add(temp);
				children.add(temps);
			}else
			{
				Node temp = new Node(data.get(0), this);
				Node temp1 = new Node(data.get(1), this);
				Node temp2 = children.get(1);
				temp.children.add(children.get(0));
				temp.children.add(temp2.children.get(0));
				temp1.children.add(temp2.children.get(1));
				temp1.children.add(children.get(2));
				data.removeAll(data);
				data.add(temp2.data.get(0));
				children.removeAll(children);
				children.add(temp);
				children.add(temp1);
			}
		}
		
		public int countSubtree(Node count)
		{
			int counter = count.data.size();
			if(this.children.size() != 0)
			{
				for(int i = 0; i < children.size(); i++){
					counter = counter + children.get(i).countSubtree(children.get(i));
				}
			}
			return counter;
		}
		
		public int search(int x)
		{
			if(data.contains(x))
			{
				return countSubtree(this);
			}else if(children.size() != 0)
			{		
				for(int i = 0; i < data.size(); i++)
					{
						if(x < data.get(i))
						{
							 return children.get(i).search(x);
						}
					}
				 return children.get(children.size()-1).search(x);
			}
			return 0;	
		}
		
	}
}
