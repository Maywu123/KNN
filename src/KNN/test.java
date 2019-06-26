package KNN;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class test {
	private static ArrayList<quadTree> allQuadTree=new ArrayList<>();
	private static ArrayList<ArrayList<node>> eachKnn=new ArrayList<>();

	public static void main(String[] args) throws FileNotFoundException {
		boolean stop=true;
		double searchy,searchx;
		int knn;
		Scanner input =new Scanner(System.in);
		System.out.print("请输入要查询的点的纬度:");
		searchy=input.nextDouble();
		System.out.print("请输入要查询的点的经度:");
		searchx=input.nextDouble();
		System.out.print("请输入要查询的点的数目:");
		knn=input.nextInt();
		node target=new node(searchx,searchy);
		quadTree root=openFile.initial();
		
			
		allQuadTree.add(root);

        root.divide(allQuadTree);

        
       for(int level=1;;level++) {
        	int size=allQuadTree.size();
        	stop=true;
        	for(int i=0;i<size;i++) {
        		if(allQuadTree.get(i).getLevel()==level) {
        			allQuadTree.get(i).divide(allQuadTree);
        			stop=false;
        		}
        	}
        	if(stop==true)
        		break;
        }
       ArrayList<node> answer= new ArrayList<node>();
       quadTree.search(allQuadTree,knn,target,allQuadTree.get(0),answer);
       for(int i=0;i<answer.size();i++)
        System.out.println(answer.get(i).toString());

	}
		
	}

