package KNN;

import java.util.ArrayList;

public class quadTree {

	private int upLeft;
	private int upRight;
	private int downLeft;
	private int downRight;
	private ArrayList<node> nodeList=new ArrayList<>();
	private Boolean isLeaf=false;
	private int level;
	private double up;
	private double left;
	private double right;
	private double down;	
	private static double realmax=99999;

	public quadTree(double up,double left,double right,double down,int level) {
		this.up=up;
		this.left=left;
		this.right=right;
		this.down=down;
		this.level=level;
	}

	public void Insert(node i) {
		this.nodeList.add(i);
	}

	public void divide(ArrayList<quadTree> e) {
		//1.判断含有的node数是否为1，是则停止，并将isLeaf置true,否则继续;
		//2.将区域划分为左上、右上、左下、右下，等级为当前树的等级+1,并将节点置入;
		//3.对每一个子树使用divide.
		if(this.nodeList.size()<=1) {
			this.isLeaf=true;
		}
		else {
		quadTree newUpLeft=new quadTree(this.up,this.left,(this.left+this.right)/2,(this.up+this.down)/2,this.level+1);
		quadTree newUpRight=new quadTree(this.up,(this.left+this.right)/2,this.right,(this.up+this.down)/2,this.level+1);
		quadTree newDownLeft=new quadTree((this.up+this.down)/2,this.left,(this.left+this.right)/2,this.down,this.level+1);
		quadTree newDownRight=new quadTree((this.up+this.down)/2,(this.left+this.right)/2,this.right,this.down,this.level+1);
		
		for(int i=0;i<this.nodeList.size();i++) {
			if(this.nodeList.get(i).getx()<=(this.left+this.right)/2){
				if(this.nodeList.get(i).gety()>=(this.up+this.down)/2) {
					newUpLeft.Insert(this.nodeList.get(i));
				}
				else if(this.nodeList.get(i).gety()<(this.up+this.down)/2) {
					newDownLeft.Insert(this.nodeList.get(i));
				}
			}
			else if(this.nodeList.get(i).getx()>(this.left+this.right)/2){
				if(this.nodeList.get(i).gety()>=(this.up+this.down)/2) {
					newUpRight.Insert(this.nodeList.get(i));
				}
				else if(this.nodeList.get(i).gety()<(this.up+this.down)/2) {
					newDownRight.Insert(this.nodeList.get(i));
				}
			}
		}
		
		e.add(newUpLeft);
		this.upLeft=e.size()-1;
		
		e.add(newUpRight);
		this.upRight=e.size()-1;
		
		e.add(newDownLeft);
		this.downLeft=e.size()-1;
		
		e.add(newDownRight);
		this.downRight=e.size()-1;
		
		}
	}

	public void setNodeList(ArrayList<node> e) {
		this.nodeList=e;
	}

	public void showArea() {
		System.out.println(up+" "+down+" "+left+" "+right);
	}

	public void showNode() {
		for(int i =0;i<this.nodeList.size();i++) {
			System.out.println(this.nodeList.get(i).toString());
		}
	}

	public ArrayList<node> getNode() {
		return this.nodeList;
	}

	public int getNodeNum() {
		return this.nodeList.size();
	}

	public boolean hasNode() {
		if(this.nodeList.size()!=0)
			return true;
		else return false;
	}

	public int getLevel() {
		return this.level;
	}

	public double getUp() {
		return this.up;
	}

	public double getDown() {
		return this.down;
	}

	public double getLeft() {
		return this.left;
	}

	public double getRight() {
		return this.right;
	}

	public int getUpLeft() {
		return this.upLeft;
	}

	public int getUpRight() {
		return this.upRight;
	}

	public int getDownLeft() {
		return this.downLeft;
	}

	public int getDownRight() {
		return this.downRight;
	}

	//计算最短距离
	public static double Mindist(node real,node n) {
		return Math.sqrt( Math.pow( ( n.getx()-real.getx() ),2)+Math.pow( ( n.gety()-real.gety() ),2) );
	}

	public static double Mindist(quadTree qt,node n) {
		double answer=0;
		if(n.gety()>qt.getUp()) {
			if(n.getx()<qt.getLeft())
				answer=Math.sqrt( Math.pow( ( n.getx()-qt.getLeft() ),2)+Math.pow( ( n.gety()-qt.getUp() ),2) );
			else if(n.getx()>qt.getRight())
				answer=Math.sqrt( Math.pow( ( n.getx()-qt.getRight() ),2)+Math.pow( ( n.gety()-qt.getUp() ),2) );
			else
				answer=n.gety()-qt.getUp();
		}
		else if(n.gety()<qt.getDown()) {
			if(n.getx()<qt.getLeft())
				answer=Math.sqrt( Math.pow( ( n.getx()-qt.getLeft() ),2)+Math.pow( ( n.gety()-qt.getDown() ),2) );
			else if(n.getx()>qt.getRight())
				answer=Math.sqrt( Math.pow( ( n.getx()-qt.getRight() ),2)+Math.pow( ( n.gety()-qt.getDown() ),2) );
			else
				answer=qt.getUp()-n.gety();
		}
		else {
			if(n.getx()<qt.getLeft())
				answer=qt.getLeft()-n.getx();
			else if(n.getx()>qt.getRight())
				answer=n.getx()-qt.getRight();
			else
				answer=0;
		}
		return answer;
	}
	
	public static void search(ArrayList<quadTree> e,int k,node n,quadTree now,ArrayList<node> answer) {
		quadTree temp=now;
		while (temp.getNodeNum()!=0) {
			if(answer.size()==k&&Mindist(temp,n)>node.max)
				break;
			else if(temp.isLeaf) {
				if(answer.size()<k) {
					answer.add(temp.getNode().get(0));
					node.max=maxDistance(answer,n);
					break;
				}
				else if(answer.size()==k&&Mindist(temp.getNode().get(0),n)<node.max) {
					answer.remove(maxIndex(answer,n));
					answer.add(temp.getNode().get(0));
					node.max=maxDistance(answer,n);
					break;
				}
				break;
			}
			else {
				search(e,k,n,e.get(temp.getUpLeft()),answer);
				search(e,k,n,e.get(temp.getUpRight()),answer);
				search(e,k,n,e.get(temp.getDownLeft()),answer);
				search(e,k,n,e.get(temp.getDownRight()),answer);
				break;
			}
		}
	}

	public static int maxIndex(ArrayList<node> e,node n) {
		int maxIndex=0;
		double max=0;
		for(int i=0;i<e.size();i++) {
			if(Mindist(e.get(i),n)>max) {	
				max=Mindist(e.get(i),n);
				maxIndex=i;
			}
		}
		return maxIndex;
	}

	public static double maxDistance(ArrayList<node> e,node n) {
		double maxDis=0;
		for(int i=0;i<e.size();i++) {
			if(Mindist(e.get(i),n)>maxDis) {	
				maxDis=Mindist(e.get(i),n);
			}
		}
		return maxDis;
	}
}
