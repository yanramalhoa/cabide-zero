/* 
 * Copyright (c) 2003, Rafael Steil 
 * All rights reserved. 
 
 * Redistribution and use in source and binary forms,  
 * with or without modification, are permitted provided  
 * that the following conditions are met: 
 
 * 1) Redistributions of source code must retain the above  
 * copyright notice, this list of conditions and the  
 * following  disclaimer. 
 * 2)  Redistributions in binary form must reproduce the  
 * above copyright notice, this list of conditions and  
 * the following disclaimer in the documentation and/or  
 * other materials provided with the distribution. 
 * 3) Neither the name of "Rafael Steil" nor  
 * the names of its contributors may be used to endorse  
 * or promote products derived from this software without  
 * specific prior written permission. 
 *  
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT  
 * HOLDERS AND CONTRIBUTORS "AS IS" AND ANY  
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING,  
 * BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF  
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR  
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL  
 * THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE  
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,  
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES  
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF  
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,  
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER  
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER  
 * IN CONTRACT, STRICT LIABILITY, OR TORT  
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN  
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF  
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE 
 *  
 * This file creation date: 21/04/2004 - 19:54:16 
 * net.jforum.util.image.ImageUtils.java 
 * The JForum Project 
 * http://www.jforum.net 
 *  
 * $Id: ImageUtils.java,v 1.11 2004/05/04 00:59:44 rafaelsteil Exp $ 
 */
package br.com.hackfest.controller.utilitarios;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;


/**
 * Utilities methods for image manipulation. It does not support writting of GIF
 * images, but it can read from. GIF images will be saved as PNG.
 * 
 * @author Rafael Steil
 */
public class ImageUtils {
	public static final int IMAGE_JPEG = 0;
	public static final int IMAGE_PNG = 1;

	/**
	 * Resizes an image
	 * 
	 * @param imgName
	 *            The image name to resize. Must be the complet path to the file
	 * @param maxWidth
	 *            The image's max width
	 * @param maxHeight
	 *            The image's max height
	 * @return A resized <code>BufferedImage</code>
	 * @throws IOException
	 *             If the file is not found
	 */
	public static BufferedImage resizeImage(String imgName, int type,
			int maxWidth, int maxHeight) throws IOException {
		return resizeImage(ImageIO.read(new File(imgName)), type, maxWidth,
				maxHeight);
	}

	/**
	 * Resizes an image.
	 * 
	 * @param image
	 *            The image to resize
	 * @param maxWidth
	 *            The image's max width
	 * @param maxHeight
	 *            The image's max height
	 * @return A resized <code>BufferedImage</code>
	 */
	public static BufferedImage resizeImage(Image image, int type,
			int maxWidth, int maxHeight) {
		// float zoom = 1.0F;
		Dimension largestDimension = new Dimension(maxWidth, maxHeight);

		// Original size
		int imageWidth = image.getWidth(null);
		int imageHeight = image.getHeight(null);

		float aspectRation = (float) imageWidth / imageHeight;

		if (imageWidth > maxWidth || imageHeight > maxHeight) {
			// int scaledW = Math.round(imageWidth * zoom);
			// int scaledH = Math.round(imageHeight * zoom);
			//
			// Dimension preferedSize = new Dimension(scaledW, scaledH);

			if ((float) largestDimension.width / largestDimension.height > aspectRation) {
				largestDimension.width = (int) Math
						.ceil(largestDimension.height * aspectRation);
			} else {
				largestDimension.height = (int) Math
						.ceil((float) largestDimension.width / aspectRation);
			}

			imageWidth = largestDimension.width;
			imageHeight = largestDimension.height;
		}

		return createBufferedImage(image, type, imageWidth, imageHeight);
	}
	
	/**
	 * Resizes an image.
	 * 
	 * @param image
	 *            The image to resize
	 * @param maxWidth
	 *            The image's max width
	 * @param maxHeight
	 *            The image's max height
	 * @return A resized <code>BufferedImage</code>
	 */
	public static BufferedImage resizeImageProportional(Image image, int type, int h) {
		// Original size
		int imageWidth = image.getWidth(null);
		int imageHeight = image.getHeight(null);

//		int maxHeight = (maxWidth * imageHeight) / imageWidth;
		int w = (h * imageWidth) / imageHeight;

//		Dimension largestDimension = new Dimension(maxWidth, maxHeight);
//
//		float aspectRation = (float) imageWidth / imageHeight;
//
//		if (imageWidth > maxWidth || imageHeight > maxHeight) {
//			// int scaledW = Math.round(imageWidth * zoom);
//			// int scaledH = Math.round(imageHeight * zoom);
//			//
//			// Dimension preferedSize = new Dimension(scaledW, scaledH);
//
//			if ((float) largestDimension.width / largestDimension.height > aspectRation) {
//				largestDimension.width = (int) Math
//						.ceil(largestDimension.height * aspectRation);
//			} else {
//				largestDimension.height = (int) Math
//						.ceil((float) largestDimension.width / aspectRation);
//			}
//
//			imageWidth = largestDimension.width;
//			imageHeight = largestDimension.height;
//		}

		return createBufferedImage(image, type, w, h);
	}

	/**
	 * Saves an image to the disk.
	 * 
	 * @param image
	 *            The image to save
	 * @param toFileName
	 *            The filename to use
	 * @param type
	 *            The image type. Use <code>ImageUtils.IMAGE_JPEG</code> to save
	 *            as JPEG images, or <code>ImageUtils.IMAGE_PNG</code> to save
	 *            as PNG.
	 * @return <code>false</code> if no appropriate writer is found
	 * @throws IOException
	 */
	public static boolean saveImage(BufferedImage image, String toFileName,
			int type) throws IOException {
		return ImageIO.write(image, type == IMAGE_JPEG ? "jpg" : "png",
				new File(toFileName));
	}

	/**
	 * Compress and save an image to the disk. Currently this method only
	 * supports JPEG images.
	 * 
	 * @param image
	 *            The image to save
	 * @param toFileName
	 *            The filename to use
	 * @param type
	 *            The image type. Use <code>ImageUtils.IMAGE_JPEG</code> to save
	 *            as JPEG images, or <code>ImageUtils.IMAGE_PNG</code> to save
	 *            as PNG.
	 * @param compress
	 *            Set to <code>true</code> if you want to compress the image.
	 * @return <code>false</code> if no appropriate writer is found
	 * @throws IOException
	 */
	public static void saveCompressedImage(BufferedImage image,
			String toFileName, int type) throws IOException {
		if (type == IMAGE_PNG) {
			throw new UnsupportedOperationException(
					"PNG compression not implemented");
		}

		ImageWriter writer = null;

		Iterator iter = ImageIO.getImageWritersByFormatName("jpg");
		writer = (ImageWriter) iter.next();

		ImageOutputStream ios = ImageIO.createImageOutputStream(new File(
				toFileName));
		writer.setOutput(ios);

		ImageWriteParam iwparam = new JPEGImageWriteParam(Locale.getDefault());

		iwparam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		iwparam.setCompressionQuality(0.7F);

		writer.write(null, new IIOImage(image, null, null), iwparam);

		ios.flush();
		writer.dispose();
		ios.close();
	}

	/**
	 * Creates a <code>BufferedImage</code> from an <code>Image</code>.
	 * 
	 * @param image
	 *            The image to convert
	 * @param w
	 *            The desired image width
	 * @param h
	 *            The desired image height
	 * @return The converted image
	 */
	public static BufferedImage createBufferedImage(Image image, int type, int w, int h) {
		if (type == ImageUtils.IMAGE_PNG && hasAlpha(image)) {
			type = BufferedImage.TYPE_INT_ARGB;
		} else {
			type = BufferedImage.TYPE_INT_RGB;
		}

		BufferedImage bi = new BufferedImage(w, h, type);

		Graphics g = bi.createGraphics();
		g.drawImage(image, 0, 0, w, h, null);
		g.dispose();

		return bi;
	}

	/**
	 * Determines if the image has transparent pixels.
	 * 
	 * @param image
	 *            The image to check for transparent pixel.s
	 * @return <code>true</code> of <code>false</code>, according to the result
	 * @throws InterruptedException
	 */
	public static boolean hasAlpha(Image image) {
		try {
			PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
			pg.grabPixels();

			return pg.getColorModel().hasAlpha();
		} catch (InterruptedException e) {
			return false;
		}
	}

	private static int getEXIFOrientation(byte[] bytes) {
//		int orientation = -1; // default = unknown
//		ByteArrayInputStream bis = null;
//		try {
//			bis = new ByteArrayInputStream(bytes);
//			Metadata metadata = ImageMetadataReader.readMetadata(
//					new BufferedInputStream(bis), false);
//			ExifIFD0Directory exifDir = metadata
//					.getDirectory(ExifIFD0Directory.class);
//			if (exifDir != null) {
//				orientation = exifDir.getInt(274); // 274 is the EXIF
//													// orientation standard code
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (bis != null)
//				try {
//					bis.close();
//				} catch (IOException e) {
//					// nothing
//				}
//		}
//		return orientation;
		return 0;
	}
	
	/**
     * Converts a given BufferedImage into an Image
     * 
     * @param bimage The BufferedImage to be converted
     * @return The converted Image
     */
    public static Image toImage(BufferedImage bimage){
        // Casting is enough to convert from BufferedImage to Image
        Image img = (Image) bimage;
        return img;
    }
	
	/**
     * Creates an empty image with transparency
     * 
     * @param width The width of required image
     * @param height The height of required image
     * @return The created image
     */
    public static Image getEmptyImage(int width, int height){
        BufferedImage imgBuffered = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        return toImage(imgBuffered);
    }

	public static Image rotateImage(Image image, double angulo){
		double seno = Math.abs(Math.sin(Math.toRadians(angulo)));
		double coseno = Math.abs(Math.cos(Math.toRadians(angulo)));
		int largura = image.getWidth(null);
		int altura = image.getHeight(null);
		int novaLargura = (int) Math.floor(largura*coseno + altura*seno);
		int novaAltura = (int) Math.floor(altura*coseno + largura*seno);
		int tipo = ImageUtils.IMAGE_JPEG;
		Image imageBuffered = createBufferedImage(image, tipo, image.getWidth(null), image.getHeight(null));
		Image emptyImage = getEmptyImage(novaLargura, novaAltura);
		BufferedImage bufferedImage = createBufferedImage(emptyImage, tipo, emptyImage.getWidth(null), emptyImage.getHeight(null));
		Graphics2D g2d = bufferedImage.createGraphics();
		
		g2d.translate((novaLargura-largura)/2, (novaAltura-altura)/2);
		g2d.rotate(Math.toRadians(angulo), largura/2, altura/2);
		g2d.drawRenderedImage((RenderedImage) imageBuffered, null);
		g2d.dispose();
		
		return toImage(bufferedImage);
	}
}