package br.com.brunogambim.CRUDCadastroDePedidos.services.utils.filestorage;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import br.com.brunogambim.CRUDCadastroDePedidos.exceptions.FileException;

import org.imgscalr.Scalr;

public class ImageLoader {
	private BufferedImage bufferedImage;
	private String extension;
	
	private ImageLoader (BufferedImage bufferedImage,String extension) {
		this.bufferedImage = bufferedImage;
		this.extension = extension;
	}
	
	public static ImageLoader createImageLoader(MultipartFile multipartFile) {
		try {
			return new ImageLoader(ImageIO.read(multipartFile.getInputStream()),
					FilenameUtils.getExtension(multipartFile.getOriginalFilename()));
		} catch (IOException e) {
			throw new FileException("Erro ao ler arquivo");
		}
	}
	
	public InputStream getJpgImage(int size) {
		convertToJpg();
		resize(size);
		return getInputStream();
	}
	
	public InputStream getJpgImage() {
		convertToJpg();
		return getInputStream();
	}
	
	public void convertToJpg() {
		if(!isJpg() && !isPng()) {
			throw new FileException("Imagem inválida");
		}
		if(isPng()){
			convertPngToJpg(); 
		}
	}
	
	private boolean isPng(){
		if(!"png".equals(extension)) {
			return false;
		}
		return true;
	}
	
	private boolean isJpg(){
		if(!"jpg".equals(extension)) {
			return false;
		}
		return true;
	}
	
	private void convertPngToJpg() {
		BufferedImage resultImage = new BufferedImage(this.bufferedImage.getWidth(), this.bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
		resultImage.createGraphics().drawImage(this.bufferedImage, 0, 0,Color.WHITE, null);
		this.bufferedImage = resultImage;
	}
	
	private InputStream getInputStream() {
		try {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, "jpg", byteArrayOutputStream);
			return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
		} catch (IOException e) {
			throw new FileException("Erro ao ler arquivo");
		}
	}
	
	private void resize(int size) {
		cropSquare();
		this.bufferedImage = Scalr.resize(this.bufferedImage, Scalr.Method.ULTRA_QUALITY, size);
	}
	
	private void cropSquare() {
		int min = (this.bufferedImage.getHeight() <= this.bufferedImage.getWidth()) ?
				this.bufferedImage.getHeight() : this.bufferedImage.getWidth();
		System.out.println("getHeight = " + ((this.bufferedImage.getHeight()/2)-(min/2)) + " min = " + min + " h" + this.bufferedImage.getHeight());
		this.bufferedImage = Scalr.crop(this.bufferedImage,
				((this.bufferedImage.getWidth()/2)-(min/2)),
				((this.bufferedImage.getHeight()/2)-(min/2)),
				min,min);
	}
}
