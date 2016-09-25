package com.Kerstin.reddit;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class DitherImage{
	//#272 [Intermediate] Dither that image
	public static void main(String[] args) throws IOException{
		ImageReader.dither("C:\\Temp\\28271511203_c83de0e841_o.jpg", "C:\\Temp\\BW28271511203_c83de0e841_o.jpg");
	}
}
class ImageReader{
	static void dither(String inputFilePath, String outputFilePath) throws IOException{
		BufferedImage imgBuffer = ImageIO.read(new File(inputFilePath));
		int[] inputColors= ImageReader.read(imgBuffer);
		int[] outputColors = ImageReader.convertToBW(inputColors, imgBuffer.getWidth());
		ImageReader.saveImage(outputColors, imgBuffer, outputFilePath);
	}
	
	
	private static int[] read(BufferedImage imgBuffer) throws IOException{
		int height = imgBuffer.getHeight();
		int width = imgBuffer.getWidth();
		int[] RGBPixels = imgBuffer.getRGB(0, 0, width, height, null, 0, width);
		return RGBPixels;
	}
	private static int[] convertToBW(int[] fullColors, int imageWidth) throws IOException{
		int[] blackWhite = new int[fullColors.length];
		float[][] TwoDimensional = new float[imageWidth][fullColors.length/imageWidth];
		int i = 0;
		for (int y = 0; y < fullColors.length/imageWidth; y++){
			for (int x = 0; x < imageWidth; x++){
				TwoDimensional[x][y] = fullColors[i];
				i++;
			}
		}
		for (int y = 0; y < fullColors.length/imageWidth; y++){
			for (int x = 0; x < imageWidth; x++){
				if (TwoDimensional[x][y] + 1 >= -8388607){
					boolean isWhite = true;
					TwoDimensional = ImageReader.setNeighbors(x, y, isWhite, TwoDimensional);
					TwoDimensional[x][y] = -1;
				} else {
					boolean isWhite = false;
					TwoDimensional = ImageReader.setNeighbors(x, y, isWhite, TwoDimensional);
					TwoDimensional[x][y] = -16777216;
				}
			}
		}
		i = 0;
		for (int y = 0; y < fullColors.length/imageWidth; y++){
			for (int x = 0; x < imageWidth; x++){
				blackWhite[i] = Math.round(TwoDimensional[x][y]);
				i++;
			}
		}
		return blackWhite;
	}
	private static float[][] setNeighbors(int x, int y, boolean isWhite, float[][] currentColors){
		float error;
		if (isWhite){
			error = currentColors[x][y] + 1;
		} else {
			error = currentColors[x][y] + 16777216;
		}
		try{
			currentColors[x+1][y] += error * 7/16;
		} catch (IndexOutOfBoundsException e){}
		try{
			currentColors[x-1][y+1] += error * 3/16;
		} catch (IndexOutOfBoundsException e){}
		try{
			currentColors[x][y+1] += error * 5/16;
		} catch (IndexOutOfBoundsException e){}
		try{
			currentColors[x+1][y+1] += error * 1/16;
		} catch (IndexOutOfBoundsException e){}
		return currentColors;
	}
	private static void saveImage(int[] colors, BufferedImage input, String outputFilePath) throws IOException{
		BufferedImage output = input;
		output.setRGB(0, 0, output.getWidth(), output.getHeight(), colors, 0, output.getWidth());
		File outputfile = new File(outputFilePath);
		ImageIO.write(output, "jpg", outputfile);
	}
}