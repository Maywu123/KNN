package KNN;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class openFile {
	private static  String temp;
	private static Double longitude;
	private static Double latitude;
	private static ArrayList<node> tempList=new ArrayList<>();
	private static double up;
	private static double down;
	private static double left;
	private static double right;
	
	public static quadTree initial() throws FileNotFoundException {
		File f=new File("la_points.txt");
		Scanner input=new Scanner(f);
		
		temp=input.nextLine();
		latitude=Double.valueOf(temp.substring(0, temp.indexOf(",")));
		up=latitude;
		down=latitude;
		longitude=Double.valueOf(temp.substring(temp.indexOf(",")+1));
		left=longitude;
		right=longitude;
		tempList.add(new node(longitude,latitude));
		
		while(input.hasNextLine()) {
			temp=input.nextLine();
			latitude=Double.valueOf(temp.substring(0, temp.indexOf(",")));
			if(latitude<down)
				down=latitude;
			else if(latitude>up)
				up=latitude;
			longitude=Double.valueOf(temp.substring(temp.indexOf(",")+1));
			if(longitude<left)
				left=longitude;
			else if(longitude>right)
				right=longitude;
			tempList.add(new node(longitude,latitude));
		}
	
			quadTree root=new quadTree(up,left,right,down,0);
			int i=0;
			while(!tempList.isEmpty()) {
				root.Insert(tempList.remove(0));
				i++;
			}

		return root;
	}
}
