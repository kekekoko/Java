package com.Kerstin.reddit;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
//import java.util.Random;

import javax.imageio.ImageIO;

public class JuliaFractals{
	//#277 [Hard] Trippy Julia fractals
	public static void main (String[] args) throws IOException{
		int width = 1920;
		int height = 1080;
		ComplexNumber[] test = new JuliaFractals().generateArray(width, height);
		int[] bla = new JuliaFractals().applyF(test);
		new JuliaFractals().saveImage(bla, width, height, "C:\\Temp\\test.jpg");
	}
	
	private class ComplexNumber{
		double real;
		double imaginary;
		ComplexNumber(double real, double imaginary){
			this.real = real;
			this.imaginary = imaginary;
		}
		private double getAbsolute(){
			double absolute = Math.sqrt(Math.pow(this.real, 2) + Math.pow(imaginary, 2));
			return absolute;
		}
	}
	
	private ComplexNumber[] generateArray(int xPixels, int yPixels){
		int size = xPixels * yPixels;
		ComplexNumber[] numbers = new ComplexNumber[size];
		double xSteps = (double) 2 / (xPixels - 1);
		double ySteps = (double) 2 / (yPixels - 1);
		for (int i = 0; i < yPixels; i++){
			for (int j = 0; j < xPixels; j++){
				numbers[i * xPixels + j] = new ComplexNumber(-1 + j * xSteps, 1 - i * ySteps);
			}
		}
		return numbers;
	}
	
	private int[] applyF(ComplexNumber[] complexNumbers){
		int size = complexNumbers.length;
		int[] intensities = new int[size];
		//Random random = new Random();
		for (int i = 0; i < size; i++){
			double f1 = -0.221;
			double f2 = -0.713;
			int counter = 0;
			ComplexNumber current = complexNumbers[i];
			double absolute = current.getAbsolute();
			double newReal = Math.pow(current.real, 2) - Math.pow(current.imaginary, 2) + f1;
			double newImaginary = 2 * current.real * current.imaginary + f2;
			double oldReal, oldImaginary;
			do {
				//f(x) = z2 – 0.221 – 0.713 i
				//=(ac-bd)+(bc+ad)i}
				oldReal = newReal;
				oldImaginary = newImaginary;
				newReal = Math.pow(oldReal, 2) - Math.pow(oldImaginary, 2) + f1;
				newImaginary = 2 * oldReal * oldImaginary + f2;
				absolute = Math.sqrt(Math.pow(newReal, 2) + Math.pow(newImaginary, 2));
				counter++;
			} while (absolute < 2 && counter < 200);
			intensities[i] = counter;
		}
		return intensities;
	}
	private void saveImage(int[] intensities, int width, int height, String path) throws IOException{
        BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_INDEXED);
        output.setRGB(0, 0, width, height, intensities, 0, output.getWidth());
        File outputfile = new File(path);
        ImageIO.write(output, "jpg", outputfile);
    }
}