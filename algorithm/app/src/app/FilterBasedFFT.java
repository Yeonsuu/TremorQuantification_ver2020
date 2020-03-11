package app;

/*
 * The FFT-based frequency filtering is used to receive the FFT result 
 * and treat all values ​​outside the desired frequency domain as zero. 
 * Then the inverse FFT is applied to get the filtered value.
 *  	input: FFT value, frequency of FFT, cutoff value
 *  	output: filtering the data 
*/

import java.util.List;

public class FilterBasedFFT {
	
	private static fft ft;
	private int srate = 50;	


	public double[] LowPassFilter(Complex[] input, float[] freq, int cutoff) {
		// TODO Auto-generated method stub
		int leng = input.length;
		double[] output = new double[leng]; 		
		Complex[] fdata = new Complex[leng];
		Complex[] inter = new Complex[leng];
		int index = leng*cutoff/srate;
				
		for (int i = 0; i < input.length ;i++) {
			if(i <= index) fdata[i] = input[i];
			else if(i > input.length - index -1) fdata[i] = input[i];
			else fdata[i] = new Complex(0, 0);
		}
		
		 ft= new fft();
		 inter = ft.ifft(fdata);
		 
		 for(int i = 0; i < freq.length ;i++) 
			 output[i] = inter[i].re();
		
		return output;
	}
}
