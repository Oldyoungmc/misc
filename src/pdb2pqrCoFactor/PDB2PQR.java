package pdb2pqrCoFactor;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class PDB2PQR {

	public BufferedReader readFile(String textFile){
		File file = new File(textFile);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		DataInputStream dis = null;
		try {
			dis = new DataInputStream(fis);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return new BufferedReader(new InputStreamReader(dis));
	}
	public ArrayList<String[]> getCoFactor(String textFile){
		ArrayList<String[]> al = new ArrayList<String[]>();
		BufferedReader br = readFile(textFile);
		String line;
		try {
			while((line = br.readLine()) != null){
				String[] arr = line.split(" ");
				int[] indeces = getIndecesFromCoords(arr);
				System.out.println(arr[indeces[3]]);
				if (arr[indeces[3]].equals("FAD") || arr[indeces[3]].equals("FMN")){
					al.add(arr);
				}
			}
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//ATOM      1   I  ION     1 0.000   0.000   0.000  1.00 3.00 ion in pqr
		//ATOM     35  C   PHE A   7     186.486  63.869  59.188 entry in pdb
		//ATOM    122  O   SER    10     182.072  58.079  46.767 -0.5679 1.6612 entry in  pqr
		System.out.println(al.size());
		return al;
	}
	
	public ArrayList<String> getChargesFromITPFile(String textFile){
		ArrayList<String> al = new ArrayList<String>();
			BufferedReader br = readFile(textFile);
		String line;
		boolean readAtomDirective = false;
		try {
			while((line = br.readLine()) != null){
				String[] arr = line.split(" ");
				int[] indeces = getIndecesFromCoords(arr);
				if (line.startsWith(";   nr  type")){
					readAtomDirective = true;
					continue;
				}
				if(readAtomDirective && arr[indeces[4]].startsWith("H")){
					al.add(arr[indeces[6]]);					
				}
			}
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return al;
	}
	
	public int[] getIndecesFromCoords(String[] arr){
		int[] indeces = new int[9];
		int runningIndex = 0;
		for (int i = 0; i < arr.length; i++){
			if (arr[i].trim().length() > 0){
				indeces[runningIndex] = i;
				runningIndex++;				
			}
		}
		return indeces;
	}
	public static void main(String[] args){
		System.out.println("test");
		PDB2PQR li = new PDB2PQR();
		li.getCoFactor(args[0]);
	}
}		