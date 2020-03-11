package app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class main {

	private static fft ft;
	private static FillNull fn ;
	private static fitting fg;
	private static double[] freqResult;
	private static double[] fitResult;
    private static final int srate = 50;	

	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		double[][] resultx;		double[][] resulty;	        double[][] fitting;		double[][] fildata;
		Complex[] x;			Complex[] y; 
		
		/* read data - skip*/
		List<Double> orgX = new ArrayList<Double>();
		List<Double> orgY = new ArrayList<Double>();
		List<Double> time = new ArrayList<Double>();
        String csvFile = "/Users/Kwonda/Desktop/data_srate50_5hz.txt";
        BufferedReader br = null;
        String line = "";
        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\t+");
                orgX.add(Double.parseDouble(data[0]));
                orgY.add(Double.parseDouble(data[1]));
                time.add(Double.parseDouble(data[2]));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }        

        /* data setting - must do */
		int n = time.size();
		x = new Complex[n];		y = new Complex[n]; 
		//pre-processing		
		
		fn = new FillNull();
		fildata = new double[2][n];
	    fildata[0] = fn.FillNull(orgX,n);	
	    fildata[1] = fn.FillNull(orgY,n);	
		
		for (int i = 0; i < n; i++) {
			x[i] = new Complex(fildata[0][i], 0);
			y[i] = new Complex(fildata[1][i], 0);
		}

        //separate data 
		int start = 1;
		List<Integer> slice = new ArrayList<Integer>();
		Dataslice ds = new Dataslice();
		slice = ds.Dataslice(n);
		int m = slice.size();
		int totalL = 0;

		resultx = new double[m][5];
		resulty = new double[m][5];
		fitting = new double[m][2];
        fg = new fitting();

        /* ******************************** FFT *****************************************/
		for(int k = 0 ; k < m; k++) {
			
			int length = (int) Math.pow(2, slice.get(k));
			totalL = totalL + length;
			Complex[] xi = new Complex[length];			Complex[] yi= new Complex[length];		double[] ti = new double[length];
			for (int i = 0; i < length; i++) {
				xi[i] = x[start + i];
				yi[i] = y[start + i];
				ti[i] = time.get(i);
			}
			start = start + length; 
			Complex[] fftx = ft.fft(xi);        Complex[] ffty = ft.fft(yi);
			// change the data type: Complex ---> double 
        	double[] absfftx = new double[length/2]; 
        	double[] absffty = new double[length/2];   
        	
    		absfftx[0] = fftx[0].abs()/length;
    		absffty[0] = ffty[0].abs()/length;
    		
        	for(int i = 1; i < length/2; i++) {
        		absfftx[i] = 2*fftx[i].abs()/length;
        		absffty[i] = 2*ffty[i].abs()/length;
        	}
        	
        	float[] index = new float[length/2];
        	for(int j = 0; j < length/2 ; j++) {
        		index[j] = (float)srate*(float)j/(float)length;
        	}
        	
			resultx[k] = ft.analysis(absfftx, index);
			resulty[k] = ft.analysis(absffty, index); 
			
		/* ******************************** fitting *************************************/
	        fitting[k] =  fg.fitting(fftx, ffty,index, ti);
	        
	       System.out.println(" x: amp : " + resultx[k][3] + "	Hz : " + resultx[k][4] +  " /y: amp : " + resulty[k][3] + "	Hz : " + resulty[k][4]);        
	      //  System.out.println("section: distance mean : "+ fitting[k][0] + "	distance std : " + fitting[k][1]);

		}// end FFT calculation
		
		freqResult = new double[5];
		for(int j = 0; j < 5 ; j++) {	
			freqResult[j] = 0;
			for(int i = 0; i < m ; i++) {
				float ratio = (float)Math.pow(2, slice.get(i))/ (float)totalL;
				freqResult[j] = freqResult[j] + (resultx[i][j] + resulty[i][j]) * ratio * 0.5;
			}
		}
		
		fitResult = new double[2];
		for(int j = 0; j < 2 ; j++) {	
			fitResult[j] = 0;
			for(int i = 0; i < m ; i++) {
				float ratio = (float)Math.pow(2, slice.get(i))/ (float)totalL;
				fitResult[j] = fitResult[j] + (fitting[i][j] + fitting[i][j]) * ratio * 0.5;
			}
		}
		
        
        System.out.println("mean : " +freqResult[0] + "	std : " +freqResult[1] +
        				"	standard : " +  freqResult[2]+ "	amp : " + freqResult[3] + "	Hz : " + freqResult[4]);        
        System.out.println("fitting mean : "+ fitResult[0] + "	fitting std : " + fitResult[1]);
        
	}
}